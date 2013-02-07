/**
 * 
 *   author: (c)thomas diewald, http://thomasdiewald.com/
 *   date: 12.09.2012
 *   
 *
 * This source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * A copy of the GNU General Public License is available on the World
 * Wide Web at <http://www.gnu.org/copyleft/gpl.html>. You can also
 * obtain it by writing to the Free Software Foundation,
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */




package DwOctree;

import DwIntersectionTests.Intersect_AABB_TRIANGLE;
import DwMath.AABB;
import DwMath.DwVec3;
import DwOBJ_Loader.DwOBJ_Face;
import DwOBJ_Loader.DwOBJ_File;

public class OctreeBuilder {
  
//  public static float OCTANT_POS[][] =
//  {
//    //i&4, i&2, i&1
//    { 0, 0, 0 },    // [0] - 000
//    { 0, 0, 1 },    // [1] - 001
//    { 0, 1, 0 },    // [2] - 010
//    { 0, 1, 1 },    // [3] - 011
//    
//    { 1, 0, 0 },    // [4] - 100
//    { 1, 0, 1 },    // [5] - 101
//    { 1, 1, 0 },    // [6] - 110
//    { 1, 1, 1 },    // [7] - 111
//  };
  
  // most important value, small values makes deep trees, especially for big scenes!!
  public float MIN_DEPTH_FILL_RATIO = 1.5f;
  public int   MAX_DEPTH = 10;
//static float MAX_SIDE_RATIO = 0.15f;
//  static int   MAX_ITEMS_PER_NODE = 25;


  private Octree octree;
  private OctreeNode root;
  private DwOBJ_File obj;
  
  public OctreeBuilder(Octree octree){
    this.octree = octree;
    this.root   = octree.root;
    this.obj    = octree.obj;
  }

  
  public void BUILD_defaultRoutine(){
    long timer, elapsed;
    String txt_time;

    System.out.println("    > start building");
   
    timer = System.currentTimeMillis();
    for(int i = 0; i < obj.f.length; i++){
      if( obj.f[i].isDegenerate()) continue;
      storeAtFirstFit(root, i);
    }
    elapsed =  System.currentTimeMillis() - timer;  txt_time = String.format("%4d ms", elapsed);
    System.out.println("       1) storeAtFirstFit   ("+txt_time+")   stored items: "+octree.getNumberOfStoredItems());

    timer = System.currentTimeMillis();
    pushToLeafes(root);
    elapsed =  System.currentTimeMillis() - timer; txt_time = String.format("%4d ms", elapsed);
    System.out.println("       2) pushToLeafes      ("+txt_time+")   stored items: "+octree.getNumberOfStoredItems());
 
    timer = System.currentTimeMillis();
    optimizeSpaceCost(root);
    elapsed =  System.currentTimeMillis() - timer; txt_time = String.format("%4d ms", elapsed);
    System.out.println("       3) optimizeSpaceCost ("+txt_time+")   stored items: "+octree.getNumberOfStoredItems());

//    timer = System.currentTimeMillis();
//    optimizeMaxItemsPerNode(octree, obj);
//    System.out.println("  ____ octree.getNumberOfStoredItems() = "+octree.getNumberOfStoredItems()+"     optimizeMaxItemsPerNode");
    cleanUp(root);
    System.out.println("    > finished building\n");
  }
  
 
  //////////////////////////////////////////////////////////////////////////////
  // BUILD OCTREE
  //////////////////////////////////////////////////////////////////////////////
  private boolean assureChilds(OctreeNode ot, int max_depth){
    if( ot.depth >= max_depth) return false;
    if( ot.isLeaf() ) {
      
      ot.childs = new OctreeNode[8];
      float[] half_size = ot.aabb.getHalfSize();
      int child_depth = ot.depth+1;

      for(int i = 0; i < ot.childs.length; i++){
//        float[] ch_bb_min = DwVec3.add_new(ot.aabb.min, DwVec3.multiply_new(half_size, OCTANT_POS[i]));
        
        float[] ch_bb_min =  { ot.aabb.min[0] + (((i&4)>0)?half_size[0]:0),
                               ot.aabb.min[1] + (((i&2)>0)?half_size[1]:0),
                               ot.aabb.min[2] + (((i&1)>0)?half_size[2]:0) };

        float[] ch_bb_max = DwVec3.add_new(ch_bb_min, half_size);
        ot.childs[i] = new OctreeNode(child_depth, new AABB(ch_bb_min, ch_bb_max));
      }
    }
    return true;
  }
  
  
  private boolean saveTriangleToNode(OctreeNode ot, int idx){   
    if( !ot.IDX_triangles.contains(idx)){ // just in case
      ot.IDX_triangles.add(idx);
    }
    return true;
  }
  
  
  // save in smallest nodes, that fully contains the triangle
  public boolean storeAtFirstFit(OctreeNode ot, int idx){
    // 1) if we reached the max depth, save the triangle and return
    if( ot.depth >= MAX_DEPTH ){
      saveTriangleToNode(ot, idx);
      return true;
    }
      
    // 2) generate childs, if not possible, this node is a leaf, so save the item here
    if(!assureChilds(ot, MAX_DEPTH) ) {
      saveTriangleToNode(ot, idx);
      return true;
    }
    
    // 3)) check if one child fully contains the triangle. if so, step down to the child
    for(OctreeNode child : ot.childs){
      if( fullyContains(child, obj.f[idx]) ){
        if(storeAtFirstFit(child, idx)) return true;
      }
    }
    
    // 4) no child fully contains the triangle. so push it to the leafes
    for(OctreeNode child : ot.childs){
      storeInLeafes(child, idx);
    }
    // saveTriangleToNode(ot, idx);
    return true;
  }
      
      

  
  // make sure all triangles are in leaves
  public void pushToLeafes(OctreeNode ot){
    if( ot.isLeaf() ) return;
    
    // since current node is no leaf, if it isn't empty either, then move its items down it childs
    if( !ot.isEmpty() ){
      for( int idx: ot.IDX_triangles){
        for(OctreeNode child : ot.childs){
          storeInLeafes(child, idx);
        }
      }
      ot.IDX_triangles.clear();
    }

    // repeat routine for all childs
    for(OctreeNode child : ot.childs){
      pushToLeafes(child);
    }
  }
  
  
  public void storeInLeafes(OctreeNode ot, int idx){
    // if there's no overlap between the current node and the triangle, return
    if(!overlapsWithTriangle(ot, obj.f[idx]) )
      return;
    
    // current node is leaf, and overlaps with the triangle, so save it here
    if( ot.isLeaf() ) {
      saveTriangleToNode(ot, idx);
      return;
    }
    
    // if the current node is no leaf, so step down the childs
    for(OctreeNode child : ot.childs)
      storeInLeafes(child, idx);
  }
  

  public void optimizeSpaceCost(OctreeNode ot){
    if( !ot.isEmpty()){
      if( !positiveFillRatio(ot)){
        assureChilds(ot, MAX_DEPTH);
        pushToLeafes(ot);
      }
    }
    if( ot.isLeaf() ) return;
    
    for(OctreeNode child : ot.childs){
      optimizeSpaceCost(child);
    }
  }
  
  
  //private void optimizeMaxItemsPerNode(Octree ot){
  //
  //  if( !positiveFillRatio(ot)){
  //    assureChilds(ot, ot.depth+1);
  //    moveTrianglesToLeafes(ot);
  //  }
  //  if( ot.isLeaf() ) return;
  //  
  //  for(Octree child : ot.childs){
  //    optimizeMaxItemsPerNode(child);
  //  }
  //}
  
  public boolean positiveFillRatio(OctreeNode ot){
    float ratio_items_depth = ot.itemCount() / (float) (ot.depth);
    return ratio_items_depth < MIN_DEPTH_FILL_RATIO;
  }
  
  //public boolean positiveFillRatio(OctreeNode ot, float min_ratio){
  //  float ratio_items_depth = ot.itemCount() / (float) (ot.depth);
  //  return ratio_items_depth < min_ratio;
  //}
  
  
//  boolean CHECK_1 = false;
//  boolean CHECK_2 = false;
//  
//  private boolean needsSubdivision(OctreeNode ot){
//
//    if( !positiveFillRatio(ot) ){
////      assureChilds(ot, ot.depth+1);
//      return true;
//    }
//    return false;
//
//    CHECK_1 = CHECK_2 = false;
//    
//    if(CHECK_2)
//    {
//      int count = 0;
//      for( int idx: ot.IDX_triangles){
//        if(isToCostly(ot, obj.f[idx])) count++;
//  //      if(isToCostly(ot, obj.f[idx])) return true;
//      }
//      float ratio = count/(float) ot.itemCount();
//  
//      if( ratio > 0.95 ) {
//  //    assureChilds(ot, Octree.MAX_DEPTH);
////        if( positiveFillRatio(ot, 2.5f) ) return false;
//        if( positiveFillRatio(ot) ) return false;
//        assureChilds(ot, ot.depth+1);
//        return true;
//      }
//    }
//    
//  
//    
//    if(CHECK_2)
//    {
//   
////      if( positiveFillRatio(ot, 1.5f) ) return false;
//      if( positiveFillRatio(ot) ) return false;
//
//      assureChilds(ot, ot.depth+1);
//      
//      int child_count = 0;
//      for(OctreeNode child : ot.childs){
//        AABB aabb = child.aabb;
//        int count = 0;
//        for( int idx: ot.IDX_triangles){
//          DwOBJ_Face f = obj.f[idx];
//          if(Intersect_AABB_TRIANGLE.overlaps(aabb.min, aabb.max, f.A(), f.B(), f.C())) count++;
//        }
//        float ratio = count/(float) (ot.itemCount()); // prozent der triangles die diesen node schneiden
//        if( ratio < 0.4 )child_count++; // wenn weniger als 20 % den node schneiden, erhöhe den counter
//      }
//      if( child_count >= 4 ) return true;
//    }
//    
//    return false;
//  }
//
//  private boolean isToCostly(OctreeNode ot, DwOBJ_Face face){
//    float[] fn = face.getNormal();
//    float max_len = 0.85f;
//    if( Math.abs(fn[0]) > max_len ) return true;
//    if( Math.abs(fn[1]) > max_len ) return true;
//    if( Math.abs(fn[2]) > max_len ) return true;
//    return false;
//  }
//  
  
  


  
  public boolean cleanUp(OctreeNode ot){
    if( ot.isLeaf() ){
      return ot.isEmpty();
    }
  
    boolean delete_all_childs = true;

    for(int i = 0; i < ot.childs.length; i++){
      if( ot.childs[i]==null) continue;
      if(cleanUp(ot.childs[i]) ) {
        ot.childs[i] = null;
      } else {
        delete_all_childs = false; 
      }
    }
    if( delete_all_childs ){
      ot.childs = null;
      return ot.isEmpty();
    } else {
      return false;
    }
  }
      
  
  
  
  
  
  
  
  
  
  
  
  public boolean fullyContains(OctreeNode ot, DwOBJ_Face f){
    return ot.aabb.isInside(f.A(), f.B(), f.C());
  }
  
  public boolean overlapsWithTriangle(OctreeNode ot, DwOBJ_Face f){
    return Intersect_AABB_TRIANGLE.overlaps(ot.aabb.min, ot.aabb.max, f.A(), f.B(), f.C());
  }
  
  
  
  
  
  
  
  
  
  /**
   * unsave version, because it subdivides without checking for max depth or anything.
   * ... subdividing only will be successful, when the node is NOT empty.
   * @param ot node, to divide
   */
//  public void subdivideNode(OctreeNode ot){
//    if( ot.isEmpty() ) return;
//    assureChilds(ot, ot.depth+1);
//    pushToLeafes(ot);
//  }
//  
  
 
  
}
