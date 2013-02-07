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




package DwBVH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import DwMath.AABB;
import DwOBJ_Loader.DwOBJ_File;


public class BvhBuilder {
//  private Bvh bvh;
  private BvhNodeT root;
  private DwOBJ_File obj;
  
  private final Comparator<Integer> sort_X0 = new SortByX0();
  private final Comparator<Integer> sort_Y0 = new SortByY0();
  private final Comparator<Integer> sort_Z0 = new SortByZ0();
  
  private final Comparator<Integer> sort_X1 = new SortByX1();
  private final Comparator<Integer> sort_Y1 = new SortByY1();
  private final Comparator<Integer> sort_Z1 = new SortByZ1();
  
  private final Comparator<Integer> sort_X2 = new SortByX2();
  private final Comparator<Integer> sort_Y2 = new SortByY2();
  private final Comparator<Integer> sort_Z2 = new SortByZ2();
  
  
  public int MIN_ITEM_COUNT   = 1;
  public int MAX_DEPTH        = 15;
  public int SAH_OPTIMIZATION = 0; // 0, 1, 2

  public BvhBuilder(Bvh bvh){
//    this.bvh  = bvh;
    this.root = bvh.root;
    this.obj  = bvh.obj;
  }

  public void BUILD_defaultRoutine(){
    System.out.println("    > start building");
    subdivide(root);
    System.out.println("    > finished building\n");
  }
  
  private boolean needsSubdivision(BvhNodeT node){
    if( node._itemCount() <= MIN_ITEM_COUNT) return false;
    if( node._getDepth()  >= MAX_DEPTH     ) return false;
    return true;
  }
  

  public void subdivide(BvhNodeT node){
    if( !needsSubdivision(node)) 
      return;
    
    ArrayList<Integer> subset_A = new ArrayList<Integer>(0);
    ArrayList<Integer> subset_B = new ArrayList<Integer>(0);
    
    float SAH = Float.MAX_VALUE;

    // abb max
    Collections.sort( node._getObjects(), sort_X0 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
    Collections.sort( node._getObjects(), sort_Y0 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
    Collections.sort( node._getObjects(), sort_Z0 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
    
    // abb center
    if( SAH_OPTIMIZATION >= 1) {
      Collections.sort( node._getObjects(), sort_X1 ); SAH = genChildSubsets(SAH,  node, subset_A, subset_B);
      Collections.sort( node._getObjects(), sort_Y1 ); SAH = genChildSubsets(SAH,  node, subset_A, subset_B);
      Collections.sort( node._getObjects(), sort_Z1 ); SAH = genChildSubsets(SAH,  node, subset_A, subset_B);
    }
    
    // face center
    if( SAH_OPTIMIZATION >= 2) {
      Collections.sort( node._getObjects(), sort_X2 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
      Collections.sort( node._getObjects(), sort_Y2 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
      Collections.sort( node._getObjects(), sort_Z2 ); SAH = genChildSubsets(SAH, node, subset_A, subset_B);
    }

    node.deleteObjects(); // node.IDX_triangles.clear();
    
    node.childA( new BvhNodeT(node._getDepth()+1, BvhBuilder.getAABBfromTriangles(obj, subset_A), subset_A) );
    node.childB( new BvhNodeT(node._getDepth()+1, BvhBuilder.getAABBfromTriangles(obj, subset_B), subset_B) );
    
    if( subset_A.size() >= subset_B.size() ){
      node.swapChilds();
    }
    
//    float[] eps = {0.01f, 0.01f, 0.01f};
//
//    DwVec3.add_ref_slf(eps, node.childA()._getAABB().max);
//    DwVec3.sub_ref_slf(eps, node.childA()._getAABB().min);
//    DwVec3.add_ref_slf(eps, node.childB()._getAABB().max);
//    DwVec3.sub_ref_slf(eps, node.childB()._getAABB().min);
    
    subdivide(node.childA());
    subdivide(node.childB());
    
    
//    if( node.child_A.aabb.getSurfaceArea() < node.child_B.aabb.getSurfaceArea() ){
//      node.swapChilds();
//    }
//    
//    if( node.child_A.getNumberOfchilds() >= node.child_B.getNumberOfchilds() ){
//      node.swapChilds();
//    }
    
  }


  public float genChildSubsets( float cur_SAH, BvhNodeT node, ArrayList<Integer> subset_A, ArrayList<Integer> subset_B){
    int items = node._itemCount();
   
    float   SA_P = 1/node._getAABB().getSurfaceArea();
    float[] SA_A = new float[items-1];
    float[] SA_B = new float[items-1];

    // precompute the arrays of SURFACE AREAS using a sorted! triangle-list. SA_A[]=left->right, and SA_B[]=left<-right
    AABB a = AABB.init(); for(int i = 0;  i < items-1; i++) SA_A[i] = a.grow(obj.f[node._getObjects().get(i  )].aabb).getSurfaceArea();
    AABB b = AABB.init(); for(int i = items-2; i >= 0; i--) SA_B[i] = b.grow(obj.f[node._getObjects().get(i+1)].aabb).getSurfaceArea();
    
    int cur_items_A = -1;
    for(int i = 0, items_A = 1; i < items-1; i++, items_A++){
      float new_SAH = getSAH(SA_P, SA_A[i], items_A, SA_B[i], items-items_A);
      if( new_SAH < cur_SAH ){
        cur_SAH = new_SAH;
        cur_items_A = items_A;
      }
    }

    if( cur_items_A >= 0){ 
      subset_A.clear();  subset_A.addAll(node._getObjects().subList(0,    cur_items_A));     subset_A.trimToSize();
      subset_B.clear();  subset_B.addAll(node._getObjects().subList(cur_items_A, items));    subset_B.trimToSize();
    }
    return cur_SAH;
  }


  static public float getSAH(float sa_0_rec, float sa_A, int items_A, float sa_B, int items_B){
    return (sa_A*items_A + sa_B*items_B)*sa_0_rec;
  }    
  
  
  public static AABB getAABBfromTriangles(DwOBJ_File obj, ArrayList<Integer> IDX_triangles){
    AABB aabb = AABB.init();
    for( int idx : IDX_triangles ){
      aabb.grow( obj.f[idx].aabb );
    }   
    return aabb;
  }
  
  
  
  
  
  

  //----------------------------------------------------------------------------
  // SORTING:  aabb.max .... !FASTEST! for building/traversing
  private final class SortByX0 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.max[0]>=obj.f[b].aabb.max[0])?1:-1; }
  }
  private final class SortByY0 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.max[1]>=obj.f[b].aabb.max[1])?1:-1; }
  }
  private final class SortByZ0 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.max[2]>=obj.f[b].aabb.max[2])?1:-1; }
  }
//----------------------------------------------------------------------------
  //SORTING:  aabb.center .... !FASTEST! for building/traversing
  private final class SortByX1 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.getCenter()[0]>obj.f[b].aabb.getCenter()[0])?1:-1; }
  }
  private final class SortByY1 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.getCenter()[1]>obj.f[b].aabb.getCenter()[1])?1:-1; }
  }
  private final class SortByZ1 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].aabb.getCenter()[2]>obj.f[b].aabb.getCenter()[2])?1:-1; }
  }
  //----------------------------------------------------------------------------
  //SORTING:  face.center .... !FASTEST! for building/traversing
  private final class SortByX2 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].getCenter()[0]>obj.f[b].getCenter()[0])?1:-1; }
  }
  private final class SortByY2 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].getCenter()[1]>obj.f[b].getCenter()[1])?1:-1; }
  }
  private final class SortByZ2 implements Comparator<Integer>{
    @Override public int compare( Integer a, Integer b ) {  return (obj.f[a].getCenter()[2]>obj.f[b].getCenter()[2])?1:-1; }
  }



}
