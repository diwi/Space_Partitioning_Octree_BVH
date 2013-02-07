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
 * BvhNode using child index. nodes are saved within an array, and childs are 
 * placed next to each other (A before B).
 * 
 * @author Thomas Diewald
 *
 */
public final class BvhNodeF implements BvhNode{
  private AABB aabb;
  private int depth;
  private ArrayList<Integer> IDX_triangles = null;
  private int child_idx = 0;
  
  protected BvhNodeF(int depth, AABB aabb, ArrayList<Integer> IDX_triangles, int child_idx){
    this.depth = depth;
    this.aabb = aabb;
    this.IDX_triangles = IDX_triangles;
    this.child_idx = child_idx;
  }
  
  public int getSizeInBytes(){
    int size_bytes = 0;
    size_bytes += 3*4 + 3*4; //aabb min/max: float[3]+float[3];
    size_bytes += 1*4;       // depth;
    size_bytes += 1*4;       // child_idx
    if( IDX_triangles != null )
      size_bytes += IDX_triangles.size()*4; // triangles indices
    return size_bytes;
  }
  
  public final void setChildIdx(int child_idx){
    this.child_idx = child_idx;
  }
  public final int getChildsIDx(){
    return this.child_idx;
  }
  
  @Override
  public final boolean _isLeaf(){
    return child_idx == 0;
  }

  @Override
  public final AABB _getAABB() {
    return aabb;
  }

  @Override
  public final int _getDepth() {
    return depth;
  }

  @Override
  public final boolean _isEmpty() {
    return IDX_triangles == null;
  }

  @Override
  public final int _itemCount() {
    return IDX_triangles.size();
  }

  @Override
  public final ArrayList<Integer> _getObjects() {
    return IDX_triangles;
  }

  @Override
  public final BvhNode _childA(final Bvh bvh) {
    return bvh.nodes_flat[child_idx];
  }

  @Override
  public final BvhNode _childB(final Bvh bvh) {
    return bvh.nodes_flat[child_idx+1];
  }
}
