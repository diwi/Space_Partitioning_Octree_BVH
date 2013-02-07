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

import DwIntersectionTests.Intersect_RAY_TRIANGLE;
import DwMath.AABB;
import DwMath.DwVec3;
import DwMath.DwRay3D;
import DwOBJ_Loader.DwOBJ_Face;
import DwOBJ_Loader.DwOBJ_File;

//////////////////////////////////////////////////////////////////////////////
// TRAVERSE RAY, top->down
//////////////////////////////////////////////////////////////////////////////
//
// from the paper: An Efficient Parametric Algorithm for Octree Traversal
// URL: http://iason.fav.zcu.cz/WSCG2000/Papers_2000/X31.pdf
//
public class OctreeTraversal {
  
//  private Octree octree;
  private OctreeNode root;
  private DwOBJ_File obj;
  
  public OctreeTraversal(Octree octree){
//    this.octree = octree;
    this.root   = octree.root;
    this.obj    = octree.obj;
  }
  
  
  private int IDX_SHFT = 0;
  // traversing preparations
  public void traverseRayTopDown(OctreeHitResult hit_result){
    if( hit_result == null ){
      return ;
    }
    
    IDX_SHFT = 0;
    DwRay3D ray_mod = hit_result.ray.copy();  // copy ray

    if( mirrorComponent(root.aabb, ray_mod, 0) ) IDX_SHFT |= 4;
    if( mirrorComponent(root.aabb, ray_mod, 1) ) IDX_SHFT |= 2;
    if( mirrorComponent(root.aabb, ray_mod, 2) ) IDX_SHFT |= 1;
    
    // get intersection intervals
    float[] t0 = DwVec3.multiply_new(DwVec3.sub_new(root.aabb.min, ray_mod.o), ray_mod.d_rec);
    float[] t1 = DwVec3.multiply_new(DwVec3.sub_new(root.aabb.max, ray_mod.o), ray_mod.d_rec);

    OctreeTraversalData otd = new OctreeTraversalData(root,t0,t1);

    // if ray hits octree (root), traverse childs
    if( otd.tNear() < otd.tFar() ){ 
//      traverseOctree(otd, hit_result);
      traverseOctreeRecursive(otd, hit_result);
      for( OctreeTraversalData td_checked : hit_result.traversal_history){
        for(int id : td_checked.node.IDX_triangles){
          obj.f[id].FLAG_CHECKED = false; // reset flags!
        }
      }
    }
  }
  


  private boolean mirrorComponent(AABB aabb, DwRay3D ray, int id){
    if(ray.d[id] > 0.0) //TODO: handle special case: (ray.d[x] == 0.0)
      return false;
    ray.o[id]     = (aabb.min[id] + aabb.max[id]) - ray.o[id]; // mirror ray-origin at node-center
    ray.d[id]     = -ray.d[id];                                // mirror ray-direction
    ray.d_rec[id] = -ray.d_rec[id];                            // mirror ray-direction
    return true;                                           
  }
  
  // returns the first node, depending on the entry plane
  static private int first_node(float[] t0, float[] tm){
    float tmax = DwVec3.maxComponent(t0);
    if( t0[0] == tmax) return ((tm[1]<t0[0])?2:0) | ((tm[2]<t0[0])?1:0); // YZ:tx0=max
    if( t0[1] == tmax) return ((tm[0]<t0[1])?4:0) | ((tm[2]<t0[1])?1:0); // XZ:ty0=max
                       return ((tm[0]<t0[2])?4:0) | ((tm[1]<t0[2])?2:0); // XY:tz0=max
  }
  
  // returns next node, depending on exit-plane ... YZ:tx1=min; XZ:ty1=min; XY:tz1=min; 
  static private int next_node(float[] t1, int YZ, int XZ, int XY){
    if(t1[0] < t1[1]){
      return (t1[0] < t1[2]) ? YZ : XY; 
    } else{
      return (t1[1] < t1[2]) ? XZ : XY;
    }
  }
  
  // traverse the octree (linkedListversion)
  private void traverseOctree(OctreeTraversalData OTD, OctreeHitResult hit_result){
    while( OTD != null) {
      hit_result.COUNT_node_traversal_steps++;
      OctreeTraversalData OTD_CUR = OTD;
      OTD = OTD.prev; // pop() last element
     
      if(OTD_CUR.t1[0] < 0.0 || OTD_CUR.t1[0] < 0.0 || OTD_CUR.t1[0] < 0.0) { 
//        System.out.println("Ray origin inside AABB !"); 
        continue;   
      }

      if( OTD_CUR.node.isLeaf() ){
//        if( !OTD_CUR.node.isLeaf()) System.out.println("TRAVERSING: a not empty node, which is NOT a leaf");
        if( intersectRayObjects(OTD_CUR, hit_result)) return;
        continue;
      }
      

  
      // if current node is NOT a leaf, then check the childs for intersection
      OctreeNode node = OTD_CUR.node;
      float[] t0 = OTD_CUR.t0;
      float[] t1 = OTD_CUR.t1;
      float[] tm = OTD_CUR.tm();
      int curr_node = first_node(t0,tm); 
      
      OctreeTraversalData OTD_FIRST=null, OTD_LAST=null; // temp stacks
      
      while(curr_node < 8 ) // 8=indication for ray-exit
      {     
        switch (curr_node){     
          case 0:  OTD_CUR = new OctreeTraversalData(node.childs[0^IDX_SHFT], t0[0],t0[1],t0[2],   tm[0],tm[1],tm[2]);  curr_node = next_node(OTD_CUR.t1,4,2,1);  break;
          case 1:  OTD_CUR = new OctreeTraversalData(node.childs[1^IDX_SHFT], t0[0],t0[1],tm[2],   tm[0],tm[1],t1[2]);  curr_node = next_node(OTD_CUR.t1,5,3,8);  break;
          case 2:  OTD_CUR = new OctreeTraversalData(node.childs[2^IDX_SHFT], t0[0],tm[1],t0[2],   tm[0],t1[1],tm[2]);  curr_node = next_node(OTD_CUR.t1,6,8,3);  break;
          case 3:  OTD_CUR = new OctreeTraversalData(node.childs[3^IDX_SHFT], t0[0],tm[1],tm[2],   tm[0],t1[1],t1[2]);  curr_node = next_node(OTD_CUR.t1,7,8,8);  break;
          case 4:  OTD_CUR = new OctreeTraversalData(node.childs[4^IDX_SHFT], tm[0],t0[1],t0[2],   t1[0],tm[1],tm[2]);  curr_node = next_node(OTD_CUR.t1,8,6,5);  break;
          case 5:  OTD_CUR = new OctreeTraversalData(node.childs[5^IDX_SHFT], tm[0],t0[1],tm[2],   t1[0],tm[1],t1[2]);  curr_node = next_node(OTD_CUR.t1,8,7,8);  break;
          case 6:  OTD_CUR = new OctreeTraversalData(node.childs[6^IDX_SHFT], tm[0],tm[1],t0[2],   t1[0],t1[1],tm[2]);  curr_node = next_node(OTD_CUR.t1,8,8,7);  break;
          case 7:  OTD_CUR = new OctreeTraversalData(node.childs[7^IDX_SHFT], tm[0],tm[1],tm[2],   t1[0],t1[1],t1[2]);  curr_node = 8;                            break;
        }
        
        // create reversed stack
        // add element to stack, only when the node is not null, otherwise continue traversing the childs.
        if(OTD_CUR.node != null){
          if (OTD_FIRST == null ) {
            OTD_FIRST = (OTD_LAST = OTD_CUR); // init new temp. stack, keep pointers to start and end
          } else {
            OTD_FIRST = (OTD_FIRST.prev = OTD_CUR);       // add new element at begin of stack--> reversed building + update start pointer
          }
        }
      } 
  
      if( OTD_FIRST != null){
        OTD_FIRST.prev = OTD; // add new (reversed) stack at end of = combination of current and reversed stack
        OTD = OTD_LAST;       // set pointer to last element
      } 
    }
  }
  
  
  
  private boolean traverseOctreeRecursive(OctreeTraversalData OTD, OctreeHitResult hit_result){
    if(OTD.t1[0] < 0.0 || OTD.t1[0] < 0.0 || OTD.t1[0] < 0.0) { 
//      System.out.println("Ray origin inside AABB !"); 
      return false;   
    }
    hit_result.COUNT_node_traversal_steps++;
    
    // if current node is is a leaf, then check the childs for intersection, and return
    if( OTD.node.isLeaf() ){ // this presumes, that items are only located in leafes!
      // ALSO THE PLACE TO SUBDIVIDE THE OCTREE AT THE CURRENT NODE AND MOVE ON TRAVERSION
      return intersectRayObjects(OTD, hit_result);
    }
  
    OctreeNode node = OTD.node;
    float[] t0      = OTD.t0;
    float[] t1      = OTD.t1;
    float[] tm      = OTD.tm();
    int curr_node   = first_node(t0,tm); 
    
    while(curr_node < 8 ){ // 8=indication for ray-exit    
      switch (curr_node){     
        case 0:  OTD = new OctreeTraversalData(node.childs[0^IDX_SHFT], t0[0],t0[1],t0[2], tm[0],tm[1],tm[2]);  curr_node = next_node(OTD.t1,4,2,1);  break;
        case 1:  OTD = new OctreeTraversalData(node.childs[1^IDX_SHFT], t0[0],t0[1],tm[2], tm[0],tm[1],t1[2]);  curr_node = next_node(OTD.t1,5,3,8);  break;
        case 2:  OTD = new OctreeTraversalData(node.childs[2^IDX_SHFT], t0[0],tm[1],t0[2], tm[0],t1[1],tm[2]);  curr_node = next_node(OTD.t1,6,8,3);  break;
        case 3:  OTD = new OctreeTraversalData(node.childs[3^IDX_SHFT], t0[0],tm[1],tm[2], tm[0],t1[1],t1[2]);  curr_node = next_node(OTD.t1,7,8,8);  break;
        case 4:  OTD = new OctreeTraversalData(node.childs[4^IDX_SHFT], tm[0],t0[1],t0[2], t1[0],tm[1],tm[2]);  curr_node = next_node(OTD.t1,8,6,5);  break;
        case 5:  OTD = new OctreeTraversalData(node.childs[5^IDX_SHFT], tm[0],t0[1],tm[2], t1[0],tm[1],t1[2]);  curr_node = next_node(OTD.t1,8,7,8);  break;
        case 6:  OTD = new OctreeTraversalData(node.childs[6^IDX_SHFT], tm[0],tm[1],t0[2], t1[0],t1[1],tm[2]);  curr_node = next_node(OTD.t1,8,8,7);  break;
        case 7:  OTD = new OctreeTraversalData(node.childs[7^IDX_SHFT], tm[0],tm[1],tm[2], t1[0],t1[1],t1[2]);  curr_node = 8;                        break;
      }
      if( OTD.node != null &&  traverseOctreeRecursive(OTD, hit_result)){
        return true;
      }
    } 
    return false;
  }
  
  
  private float[] ptr_hit_backface = new float[1];
  float[] tuv = new float[3];

  // intersection tests for items in current node
  private boolean intersectRayObjects(OctreeTraversalData OTD, OctreeHitResult hit_result){
    
    hit_result.traversal_history.add(OTD);
    hit_result.COUNT_node_intersection_tests++;
    
    for(int id : OTD.node.IDX_triangles){
      DwOBJ_Face f = obj.f[id];
      if( f.FLAG_CHECKED ) continue;
      f.FLAG_CHECKED = true;
      
      if(Intersect_RAY_TRIANGLE.intersect2(hit_result.ray, f.A(), f.B(), f.C(), hit_result.two_sided_check, ptr_hit_backface, tuv)){
        hit_result.checkIfCloser(tuv, id, OTD.node, ptr_hit_backface[0]);
        hit_result.COUNT_triangle_intersection_tests++;
      }
    }
    
    // because some objects exceed the nodes aabb, an intersection (at t) can 
    // be outside the current abbb and, the next node may contains a closer
    // object with a smaller t!!!
    // in the end, ONLY if a hit(at t) is INSIDE the current node, 
    // there cant be any smaller values for t in any subsequent nodes.
    return (hit_result.t <= OTD.tFar());
  }
  
  
  
  

  
  
  
  
  
  
}
