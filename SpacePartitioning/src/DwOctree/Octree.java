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
import DwMath.DwVec3;
import DwOBJ_Loader.DwOBJ_File;

/*
//Octree: Octants numbering
//
//             +Y                        +Z
//             |                         /
//             |                        /
//             |                       /
//             |                      
//             |       o---------------o---------------o
//             |      /               /               /|
//             |     /       3       /       7       / | 
//             |    /               /               /  | 
//             |   o---------------o---------------o   | 
//             |  /               /               /|   |
//             | /       2       /       6       / | 7 |
//             /               /               /  |   o
//             o---------------o---------------o   |  /|
//             |               |               |   | / |
//             |               |               | 6 |/  |
//             |               |               |   o   |
//             |       2       |       6       |  /|   |
//             |               |               | / | 5 |
//             |               |               |/  |   o
//             o---------------o---------------o   |  /
//             |               |               |   | /
//             |               |               | 4 |/
//             |               |               |   o
//             |       0       |       4       |  / 
//             |               |               | /  
//             |               |               |/   
//             o---------------o---------------o -----------------+X
//
//
//
*/



public class Octree {
  public DwOBJ_File obj;
  public OctreeNode root;
  public OctreeBuilder oct_builder;
  public OctreeTraversal oct_traversal;
  
  public Octree(DwOBJ_File obj, boolean cubic){
    this.obj = obj;

    AABB aabb = obj.aabb.copy();
    if(cubic){
      float[] center = aabb.getCenter();
      float[] hs     = aabb.getHalfSize();
      int pl         = getSubdivisionPlane(aabb);
      DwVec3.add_ref(center, DwVec3.init(-hs[pl]), aabb.min);
      DwVec3.add_ref(center, DwVec3.init(+hs[pl]), aabb.max);
    }
    this.root = new OctreeNode(0, aabb );

    this.oct_builder = new OctreeBuilder(this);
    this.oct_traversal = new OctreeTraversal(this);
  }
  


  
  public int getSubdivisionPlane(AABB aabb){
    float[] s = aabb.getSize();
    float max = DwVec3.maxComponent(s);
         if( s[0] == max ) return 0; // x-extent is max
    else if( s[1] == max ) return 1; // y-extent is max
    else                   return 2; // z-extent is max
  }

  
  public void BUILD_defaultRoutine(){
    oct_builder.BUILD_defaultRoutine();
  }
  


  

  

  
  
  
  
  
  public int getNumberOfNodes(){
    return getNodes().size();
  }
  public ArrayList<OctreeNode> getNodes(){
    ArrayList<OctreeNode> nodes = new ArrayList<OctreeNode>();
    root.getNodes_recursive(nodes);
    return nodes;
  }

  
  public OctreeNode getNodeWithMaxItems(){
    return getNodeWithMaxItems( getNodes() );
  }
  public OctreeNode getNodeWithMaxItems(ArrayList<OctreeNode> nodes){
    OctreeNode max = nodes.get(0);
    for(OctreeNode n : nodes){
      if( n.isLeaf() ){
        if( max.itemCount() < n.itemCount() ) max = n;
      }
    }
    return max;
  }
  
  
  public int getNumberOfStoredItems(){
    return getNumberOfStoredItems( getNodes() );
  }
  public int getNumberOfStoredItems(ArrayList<OctreeNode> nodes){
    int count = 0;
    for(OctreeNode n : nodes){
//      if( n.isLeaf() ){
        count += n.itemCount();
//      }
    }
    return count;
  }
  
  
  public int getNumberOfLeafes(){
    return getNumberOfLeafes( getNodes() );
  }
  public int getNumberOfLeafes(ArrayList<OctreeNode> nodes){
    int count = 0;
    for(OctreeNode n : nodes){
      if( n.isLeaf() ){
        count++;
      }
    }
    return count;
  }
  
  
  public int sumUpLeafDepth(){
    return sumUpLeafDepth( getNodes() );
  }
  public int sumUpLeafDepth(ArrayList<OctreeNode> nodes){
    int count = 0;
    for(OctreeNode n : nodes){
      if( n.isLeaf() ){
        count+=n.depth;
      }
    }
    return count;
  }

  
  public int getMaxDepth(){
    return getMaxDepth( getNodes() );
  }
  public int getMaxDepth(ArrayList<OctreeNode> nodes){
    int max = 0;
    for(OctreeNode n : nodes){
      if( n.depth > max ) max = n.depth;
    }
    return max;
  }
  
  
  public float getAverageLeafDepth(){
   return getAverageLeafDepth( getNodes() );
  }
  public float getAverageLeafDepth(ArrayList<OctreeNode> nodes){
    int leaf_depth = sumUpLeafDepth(nodes);
    int leaf_count = getNumberOfLeafes(nodes);
    return leaf_depth/(float)leaf_count;
  }
  
  public float getAverageLeafItems(){
    return getAverageLeafDepth( getNodes() );
  }
  public float getAverageLeafItems(ArrayList<OctreeNode> nodes){
    int item_count = getNumberOfStoredItems(nodes);
    int leaf_count = getNumberOfLeafes(nodes);
    return item_count/(float)leaf_count;
  }

  
  public boolean isValid(){
    return isValid( getNodes() );
  }
  public boolean isValid(ArrayList<OctreeNode> nodes){
    for(OctreeNode n : nodes){
      if( !n.isValid() ) return false;
    }
    return true;
  }
  
  
  
  
  public void traverse(OctreeHitResult hit){
    oct_traversal.traverseRayTopDown(hit);
  }
  

  
  
  
  
  public void printStatistics(){
    Octree octree = this;

    ArrayList<OctreeNode> nodes    = octree.getNodes();
    OctreeNode max_filled_node     = octree.getNodeWithMaxItems();

    int     number_of_triangles    = obj.f.length;
    boolean octree_is_valid        = octree.isValid(nodes);
    int     number_of_nodes        = nodes.size();       
    int     number_of_leafes       = octree.getNumberOfLeafes(nodes);      
    int     number_of_stored_items = octree.getNumberOfStoredItems(nodes); 
    int     max_depth              = octree.getMaxDepth(nodes);            
    float   average_leaf_depth     = octree.getAverageLeafDepth(nodes);   
    float   average_leaf_items     = octree.getAverageLeafItems(nodes);   
    
    System.out.println("    octree_is_valid        = "+ octree_is_valid       );
    System.out.println("    number_of_triangles    = "+ number_of_triangles   );
    System.out.println("    number_of_nodes        = "+ number_of_nodes       );
    System.out.println("    number_of_leafes       = "+ number_of_leafes      );
    System.out.println("    number_of_stored_items = "+ number_of_stored_items);
    System.out.println("    max_depth              = "+ max_depth             );
    System.out.println("    average_leaf_depth     = "+ average_leaf_depth    );
    System.out.println("    average_leaf_items     = "+ average_leaf_items    );
    System.out.println("    max items per leaf     = "+ max_filled_node.itemCount()+" (depth="+max_filled_node.depth+")");
    
    System.out.println("    ratio: nodes/triangles = "+ (number_of_nodes/(float)number_of_triangles) );
    System.out.println("    ratio: items/triangles = "+ (number_of_stored_items/(float)number_of_triangles) );
    
  }
  
  
  
  
  
  
  

}
