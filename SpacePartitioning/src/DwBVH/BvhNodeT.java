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

import DwMath.AABB;

/**
 * BvhNode using child Pointers.
 * @author Thomas Diewald
 *
 */
public final class BvhNodeT implements BvhNode{

  private AABB aabb;
  private int depth;
  private ArrayList<Integer> IDX_triangles;
  private BvhNodeT child_A, child_B;
  
  //////////////////////////////////////////////////////////////////////////////
  // CONSTRUCTOR
  //////////////////////////////////////////////////////////////////////////////
  protected BvhNodeT(int depth, AABB aabb, ArrayList<Integer> IDX_triangles ){
    this.depth = depth;
    this.aabb = aabb;
    this.IDX_triangles = IDX_triangles;
    if( IDX_triangles.isEmpty()) 
      System.err.println("(BVH_Node) SOMETHING IS WRONG, triangle list is empty"); //TODO
  }
  
  
  //////////////////////////////////////////////////////////////////////////////
  // NODE INFO
  //////////////////////////////////////////////////////////////////////////////

  @Override public final boolean _isLeaf() { return child_A == null && child_B == null; }
  @Override public final boolean _isEmpty(){ return IDX_triangles == null; }
  @Override public final int _itemCount(){ return IDX_triangles.size(); }
  // true: if node is leaf not is empty, also true if not is leaf and is empty
  public final boolean isValid(){  return _isLeaf() ? !_isEmpty() : _isEmpty();}

  @Override
  public final AABB _getAABB() {
    return aabb;
  }

  @Override
  public final int _getDepth() {
    return depth;
  }

  @Override
  public final ArrayList<Integer> _getObjects() {
    return IDX_triangles;
  }
  
  public final void deleteObjects(){
    IDX_triangles = null;
  }
  

  @Override
  public final BvhNode _childA(final Bvh bvh) {
    return child_A;
  }

  @Override
  public final BvhNode _childB(final Bvh bvh) {
    return child_B;
  }

  public final BvhNodeT childA() {
    return child_A;
  }
  public final BvhNodeT childB() {
    return child_B;
  }
  public final void childA( BvhNodeT child_A) {
    this.child_A = child_A;
  }
  public final void childB( BvhNodeT child_B) {
    this.child_B = child_B;
  }
  
  
  public final void swapChilds(){
    BvhNodeT tmp = child_A;
    child_A = child_B;
    child_B = tmp;
  }
  
  
  public final int getNumberOfchilds(){
    return getNumberOfChilds(0);
  }
  
  private final int getNumberOfChilds(int count){
    if( !_isLeaf() ) {
      count = child_A.getNumberOfChilds(count);
      count = child_B.getNumberOfChilds(count);
    }
    return count+1;
  }
  
  
  public final void getNodes(ArrayList<BvhNodeT> nodes){
    if (child_A == null){
      if( child_B != null) System.err.println("error, A!=null, B==null");
      nodes.add(this);
      return;
    }
    if (child_B == null){
      if( child_A != null) System.err.println("error, B!=null, A==null");
      nodes.add(this);
      return;
    }
    
    child_A.getNodes(nodes);
    child_B.getNodes(nodes);
    nodes.add(this);
  }
  
  
  
  public final BvhNodeF getFlat(){
    return new BvhNodeF(depth, aabb, IDX_triangles, 0) ;
  }





  

  
}
