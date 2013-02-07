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

import java.util.ArrayList;

import DwMath.AABB;



public class OctreeNode {

  public final AABB aabb;
  public final int depth;
  public final ArrayList<Integer> IDX_triangles = new ArrayList<Integer>(1);
  public OctreeNode[] childs;
  
  //////////////////////////////////////////////////////////////////////////////
  // CONSTRUCTOR
  //////////////////////////////////////////////////////////////////////////////
  protected OctreeNode(int depth, AABB aabb){
    this.depth = depth;
    this.aabb  = aabb;
  }
  


  
  //////////////////////////////////////////////////////////////////////////////
  // NODE INFO
  //////////////////////////////////////////////////////////////////////////////
  public boolean isLeaf() { return childs == null; }
  public boolean isEmpty(){ return IDX_triangles.isEmpty(); }
  // true: if node is leaf not is empty, also true if not is leaf and is empty
  public boolean isValid(){  return isLeaf() ? !isEmpty() : isEmpty();}

  public int itemCount(){ return IDX_triangles.size(); }
  
  
  
  //////////////////////////////////////////////////////////////////////////////
  // GET ALL NODES IN A LIST, ... no recursion used, but a simple linked list
  //////////////////////////////////////////////////////////////////////////////
  private static class LLNode {
    private LLNode prev;
    private OctreeNode value;
    private LLNode(OctreeNode value, LLNode prev){
      this.value = value;
      this.prev = prev;
    }
    private static LLNode push(LLNode current, OctreeNode element){
      return new LLNode(element, current);
    }
    private static LLNode pop(LLNode current){
      return current.prev;
    }
  }

  public void getNodes_linkedList(ArrayList<OctreeNode> nodes){  // linked list version, just for testing
    LLNode llist = new LLNode(this, null);
    while ( llist != null ){
      OctreeNode item = llist.value;
      llist = LLNode.pop(llist);
      nodes.add(item);
      if( !item.isLeaf() ){
        for(OctreeNode child : item.childs)
          if( child != null)
            llist = LLNode.push(llist, child);
      }
    }
  }
  
  
  public void getNodes_recursive(ArrayList<OctreeNode> nodes){
    nodes.add(this);
    if( isLeaf() ) return;
    for(OctreeNode child : childs){
      if( child!= null)
        child.getNodes_recursive(nodes);
    }
  }

  


  

  



}
