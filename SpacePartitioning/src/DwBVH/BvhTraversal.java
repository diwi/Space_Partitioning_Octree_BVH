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


import DwIntersectionTests.Intersect_RAY_AABB;
import DwIntersectionTests.Intersect_RAY_TRIANGLE;
import DwOBJ_Loader.DwOBJ_Face;

/**
 * several methods (stack-based, recursive, ...) to traverse a BVH.
 * 
 * @author thomas diewald
 *
 */
class BvhTraversal {
  
  private Bvh bvh;
  private BvhTraversalData[] stack;

  protected BvhTraversal(Bvh bvh){
    this.bvh    = bvh;
    this.stack  = new BvhTraversalData[bvh.getMaxDepth()*2];
  }

  /*

  //----------------------------------------------------------------------------
  // TRAVERSE - sorted linked list version
  //----------------------------------------------------------------------------
  // version using a linked-list. the elements are inserted, depending on "tn"
  // so the list is sorted, and the first item is the one with the smallest "tn".
  // this maximizes the chance for a hit, and early hit/dismisses of other aabb's.
  public void traverse_linkedList(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return;

    
    BvhTraversalData first = new BvhTraversalData(bvh.root, tn);

    // TODO: description
    while(first != null){
      // no smaller tn is available since the list is sorted, so we can return here.
      if( first.tn >= hit_result.t)return; 
      

      BvhTraversalData cur = first;
      first = first.next;
      
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
      BvhNode node = cur.node;
      
      if( node._isLeaf() ){
        intersectRayObjects(cur.node, hit_result);
        continue;
      }
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
  
      // order makes a slight difference, since A and B are pre-ordered during the building process!!
      // note: ONLY when ta==tb the pre-ordered childs make a difference in performance!!
      if( ta < hit_result.t ) first = insertAscending(first, new BvhTraversalData(A, ta));
      if( tb < hit_result.t ) first = insertAscending(first, new BvhTraversalData(B, tb));
    }
  }

  //----------------------------------------------------------------------------
  // TRAVERSE - SHADOW_TEST - linkedList
  //----------------------------------------------------------------------------
  // same as traverse_linkedList, but returns immediately on any intersection.
  public boolean shadowTest_linkedList(BvhHitResult hit_result){
    if( hit_result == null )
      return false;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return false;
    
    BvhTraversalData first = new BvhTraversalData(bvh.root, tn);
      
    while(first != null){
      BvhTraversalData cur = first;
      first = first.next;
      
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
      
      if( cur.node._isLeaf() ){
        if( intersectRayObjects(cur.node, hit_result) ) return true;
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
  
      if( ta < hit_result.t ) { first = insertAscending(first, new BvhTraversalData(A, ta)); }
      if( tb < hit_result.t ) { first = insertAscending(first, new BvhTraversalData(B, tb)); }
    }
    return false;
  }

  
  static private BvhTraversalData insertAscending(BvhTraversalData first, BvhTraversalData A){
    if( first == null ) 
      return A;
//    if( first.tn > A.tn){
    if( insertBefore(first, A)){
      A.next = first;
      return A;
    }

    BvhTraversalData node = first;
//    while(node.next != null && node.next.tn < A.tn ) node = node.next;
    while(node.next != null && insertBefore( A, node.next)) node = node.next;
    
    A.next = node.next;
    node.next = A;
    return first;
  }
  
  //TODO: some potential here
  static private boolean insertBefore(BvhTraversalData a, BvhTraversalData b){
//    if( a.tn == b.tn ){
//      if( )
//      b.node.swapChilds();
//      a.node.swapChilds();
//      return (!a.node.isLeaf() );
//      return (a.node.getNumberOfchilds() > b.node.getNumberOfchilds() );
//      return (a.node.depth >= b.node.depth );
//      return (a.node.aabb.getVolume() >= b.node.aabb.getVolume() );
//      return (a.node.aabb.getSurfaceArea() <= b.node.aabb.getSurfaceArea() );
//    }
    return (a.tn > b.tn );
  }
  
//  static private void print(BVH_TraversalData first){
//    while(first != null){
//      System.out.println(first.tn);
//      first = first.next;
//    }
//  }
  
  
  */
  
  
  
  
  
  
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - recursive version
  //----------------------------------------------------------------------------
  public void traverse_recursive(BvhHitResult hit_result){
    if( hit_result == null ) 
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn < hit_result.t) {
      traverse_recursive( new BvhTraversalData(bvh.root, tn) , hit_result);
    }
  }
  private boolean traverse_recursive(BvhTraversalData cur, BvhHitResult hit_result){
//    hit_result.traversal_history.add(P);
//    hit_result.COUNT_node_traversal_steps++;
    
    if( cur.node._isLeaf() ) return intersectRayObjects(cur.node, hit_result);
    
    BvhNode A = cur.node._childA(bvh);
    BvhNode B = cur.node._childB(bvh);
    float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
    float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
   
    // check, which child has the nearest intersection
    if( ta < tb && ta < hit_result.t){
      
      if( traverse_recursive(new BvhTraversalData(A, ta), hit_result) ){
        if( tb < hit_result.t) traverse_recursive(new BvhTraversalData(B, tb), hit_result);
        return true;
      }
      if( tb < hit_result.t) return traverse_recursive(new BvhTraversalData(B, tb), hit_result);
      
    } else if( tb < hit_result.t){
      
      if( traverse_recursive(new BvhTraversalData(B, tb), hit_result) ){
        if( ta < hit_result.t) traverse_recursive(new BvhTraversalData(A, ta), hit_result);
        return true;
      }
      if( ta < hit_result.t) return traverse_recursive(new BvhTraversalData(A, ta), hit_result);
    } 
    return false;
  }
  
  
  


  
  
  
  
  
  
  
  
  
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - sorted stack
  //----------------------------------------------------------------------------
  // version using an array+stackpointer. the elements are inserted, depending on "tn"
  // so the stack is sorted, and the last item is the one with the smallest "tn".
  // this maximizes the chance for a hit, and early hit/dismisses of other aabb's.
  public void traverse_stackSorted(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    insertSorted(stack, ++stack_ptr, bvh.root, tn);

    // TODO: description
    while(stack_ptr>=0){

      BvhTraversalData cur = stack[stack_ptr--];
      if( cur.tn >= hit_result.t) return;
      
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        intersectRayObjects(cur.node, hit_result);
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      if( ta > tb ) {
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
      } else {
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
      }
    }
  }
  
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - SHADOW_TEST - stack sorted
  //----------------------------------------------------------------------------
  public void shadowTest_stackSorted(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    insertSorted(stack, ++stack_ptr, bvh.root, tn);

    // TODO: description
    while(stack_ptr>=0){

      BvhTraversalData cur = stack[stack_ptr--];

//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        if( intersectRayObjects(cur.node, hit_result) ) return;
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
   
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      if( ta > tb ) {
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
      } else {
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
      }
    }
  }
  
  
  
  


  
  //----------------------------------------------------------------------------
  // TRAVERSE - stack
  //----------------------------------------------------------------------------
  // version using an array+stackpointer. 
  public void traverse_stack(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    stack[++stack_ptr] = new BvhTraversalData(bvh.root, tn);

    // TODO: description
    while(stack_ptr>=0){

      BvhTraversalData cur = stack[stack_ptr--];
      if( cur.tn >= hit_result.t) continue;
      
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        intersectRayObjects(cur.node, hit_result);
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      // push the closer one, at last on the stack
      if( ta > tb ) {
        if( ta < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(A, ta);
        if( tb < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(B, tb);
      } else {
        if( tb < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(B, tb);
        if( ta < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(A, ta);
      }
    }
  }
  
  
  
  
  
  
  
  
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - SHADOW_TEST - stack
  //----------------------------------------------------------------------------
  // version using an array+stackpointer. 
  public void shadowTest_stack(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, bvh.root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    stack[++stack_ptr] = new BvhTraversalData(bvh.root, tn);

    // TODO: description
    while(stack_ptr>=0){
      BvhTraversalData cur = stack[stack_ptr--];
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        if( intersectRayObjects(cur.node, hit_result) ) return;
        continue;
      }

      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      // push the closer one, at last on the stack
      if( ta > tb ) {
        if( ta < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(A, ta);
        if( tb < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(B, tb);
      } else {
        if( tb < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(B, tb);
        if( ta < hit_result.t ) stack[++stack_ptr] = new BvhTraversalData(A, ta);
      }
    }
  }
  
  
  
  
 
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - sorted stack (flat bvh)
  //----------------------------------------------------------------------------
  // version using an array+stackpointer. the elements are inserted, depending on "tn"
  // so the stack is sorted, and the last item is the one with the smallest "tn".
  // this maximizes the chance for a hit, and early hit/dismisses of other aabb's.
  public void traverseFlat_stackSorted(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    BvhNode root = bvh.nodes_flat[0];

    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    insertSorted(stack, ++stack_ptr, root, tn);

    // TODO: description
    while(stack_ptr>=0){
      BvhTraversalData cur = stack[stack_ptr--];
      if( cur.tn >= hit_result.t) return;
      
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        intersectRayObjects(cur.node, hit_result); //TODO
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      if( ta > tb ) {
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
      } else {
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
      }
    }
  }
  
  
  //----------------------------------------------------------------------------
  // TRAVERSE - Shadow Test - sorted stack (flat bvh)
  //----------------------------------------------------------------------------
  // version using an array+stackpointer. the elements are inserted, depending on "tn"
  // so the stack is sorted, and the last item is the one with the smallest "tn".
  // this maximizes the chance for a hit, and early hit/dismisses of other aabb's.
  public void shadowTestFlat_stack(BvhHitResult hit_result){
    if( hit_result == null )
      return;
    
    BvhNode root =  bvh.nodes_flat[0];
    float tn = Intersect_RAY_AABB.intersectNear(hit_result.ray, root._getAABB());
    if( tn >= hit_result.t) 
      return;
    
    int stack_ptr  = -1;
    insertSorted(stack, ++stack_ptr, root, tn);

    // TODO: description
    while(stack_ptr>=0){
      BvhTraversalData cur = stack[stack_ptr--];
//      hit_result.COUNT_node_traversal_steps++;
//      hit_result.traversal_history.add(cur);
     
      if( cur.node._isLeaf() ){
        if( intersectRayObjects(cur.node, hit_result) ) return;
        continue;
      }
      
      BvhNode A = cur.node._childA(bvh);
      BvhNode B = cur.node._childB(bvh);
      float ta = Intersect_RAY_AABB.intersectNear(hit_result.ray, A._getAABB());
      float tb = Intersect_RAY_AABB.intersectNear(hit_result.ray, B._getAABB());
      
      if( ta > tb ) {
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
      } else {
        if( tb < hit_result.t ) insertSorted(stack, ++stack_ptr, B, tb); 
        if( ta < hit_result.t ) insertSorted(stack, ++stack_ptr, A, ta); 
      }
    }
  }
  
  
  private static final void insertSorted(BvhTraversalData[] stack, int ptr, BvhNode node, float tn){
    while( (ptr > 0) && (tn > stack[ptr-1].tn)){
      stack[ptr] = stack[--ptr];
    }
    stack[ptr] = new BvhTraversalData(node, tn);
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  private float[] ptr_hit_backface = new float[1];
  private float[] tuv = new float[3];
  
  private final boolean intersectRayObjects(BvhNode node, BvhHitResult hit_result){
//    hit_result.COUNT_node_intersection_tests++;
    for(int id : node._getObjects()){
      DwOBJ_Face f = bvh.obj.f[id];
//      if(Intersect_RAY_TRIANGLE.intersect(hit_result.ray, bvh.obj.f[id], hit_result.two_sided_check, ptr_hit_backface, tuv)){
      if(Intersect_RAY_TRIANGLE.intersect2(hit_result.ray, f.A(), f.B(), f.C(), hit_result.two_sided_check, ptr_hit_backface, tuv)){
        hit_result.checkIfCloser(tuv, id, node, ptr_hit_backface[0]);
//        hit_result.COUNT_triangle_intersection_tests++;
      }
    }
    return hit_result.gotHit();
  }

 
}
