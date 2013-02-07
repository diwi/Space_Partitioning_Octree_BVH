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
import java.util.Locale;

import DwOBJ_Loader.DwOBJ_File;
public class Bvh {
  
  public DwOBJ_File obj;

  public BvhTraversal bvh_traversal;
  public BvhBuilder bvh_builder;
  
  // BVH TREE
  public BvhNodeT root;
  // BVH FLAT
  public BvhNodeF[] nodes_flat;
  
  public Bvh(DwOBJ_File obj){

    ArrayList<Integer> IDX_triangles = new ArrayList<Integer>(obj.f.length);
    for( int i = 0; i < obj.f.length; i++){
      if( obj.f[i].isDegenerate()) continue;
      IDX_triangles.add(i);
    }
 
    this.obj = obj;
    this.root = new BvhNodeT(0, BvhBuilder.getAABBfromTriangles(obj, IDX_triangles), IDX_triangles );
    this.bvh_builder   = new BvhBuilder(this);
    
  }
  

  
  public void BUILD_defaultRoutine(){
    this.bvh_builder.BUILD_defaultRoutine();
    this.flatten(); // generates an array based "bvh tree"
    this.bvh_traversal = new BvhTraversal(this);
  }
  

  
  public int getNumberOfNodes(){
    return getNodes().size();
  }
  public ArrayList<BvhNodeT> getNodes(){
    ArrayList<BvhNodeT> nodes = new ArrayList<BvhNodeT>();
    root.getNodes(nodes);
    return nodes;
  }
  
  
  public float getAverageNodeVolume(int depth){
    return getAverageNodeVolume( getNodes(), depth );
  }
  public float getAverageNodeVolume(ArrayList<BvhNodeT> nodes, int depth){
    int count = 0;
    float volume = 0;
    for(BvhNodeT n : nodes){
      if( n._getDepth() == depth){
        count++;     
        volume+=n._getAABB().getVolume();
      }
    }
    return volume/count;
  }
  
  public float getAverageNodeSurfaceArea(int depth){
    return getAverageNodeSurfaceArea( getNodes(), depth );
  }
  public float getAverageNodeSurfaceArea(ArrayList<BvhNodeT> nodes, int depth){
    int count = 0;
    float surface_area = 0;
    for(BvhNodeT n : nodes){
      if( n._getDepth() == depth){
        count++;     
        surface_area+=n._getAABB().getSurfaceArea();
      }
    }
    return surface_area/count;
  }

  
  public float getAverageChildItemCount(){
    return getAverageChildItemCount( getNodes() );
  }
  public float getAverageChildItemCount(ArrayList<BvhNodeT> nodes){
    int count = 0, childs = 0;
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ){
        childs += n._itemCount();
        count++;
      }
    }
    return childs/(float)(count);
  }
  
  
  public float getAverageLeafDepth(){
    return getAverageLeafDepth( getNodes() );
  }
  public float getAverageLeafDepth(ArrayList<BvhNodeT> nodes){
    int count = 0;
    int depth = 0;
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ){
        depth+=n._getDepth();
        count++;
      }
    }
    return depth/(float)count;
  }
  
  
  public float getAverageLeafItems(){
    return getAverageLeafItems( getNodes() );
  }
  public float getAverageLeafItems(ArrayList<BvhNodeT> nodes){
    int count = 0;
    int items = 0;
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ){
        items+=n._itemCount();
        count++;
      }
    }
    return items/(float)count;
  }
  
  
  public int getNumberOfLeafes(){
    return getNumberOfLeafes( getNodes() );
  }
  public int getNumberOfLeafes(ArrayList<BvhNodeT> nodes){
    int count = 0;
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ){
        count++;
      }
    }
    return count;
  }
  
  
  public int getMaxDepth(){
    return getMaxDepth( getNodes() );
  }
  public int getMaxDepth(ArrayList<BvhNodeT> nodes){
    int max = root._getDepth();
    for(BvhNodeT n : nodes){
      if( n._getDepth() > max ) max = n._getDepth();
    }
    return max;
  }
  
  
  public boolean isValid(){
    return isValid( getNodes() );
  }
  public boolean isValid(ArrayList<BvhNodeT> nodes){
    for(BvhNodeT n : nodes){
      if( !n.isValid() ) return false;
    }
    return true;
  }
  
  
  public BvhNodeT getNodeWithMaxItems(){
    return getNodeWithMaxItems( getNodes() );
  }
  public BvhNodeT getNodeWithMaxItems(ArrayList<BvhNodeT> nodes){
    BvhNodeT max = nodes.get(0);
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ){
        if( max._itemCount() < n._itemCount() ) max = n;
      }
    }
    return max;
  }
  
  
  public void swapChilds(){
    swapChilds(getNodes());
  }
  public void swapChilds(ArrayList<BvhNodeT> nodes){
    for(BvhNodeT n : nodes){
      if( n._isLeaf() ) continue;
      if( n.childA().getNumberOfchilds() > n.childB().getNumberOfchilds() ){
        n.swapChilds();
      }
    }
  }
  


  
  public void traverse(BvhHitResult hit_result){
//    bvh_traversal.traverse_linkedList(hit_result);
//    bvh_traversal.traverse_recursive(hit_result);
//    bvh_traversal.traverse_stack(hit_result);
//    bvh_traversal.traverse_stackSorted(hit_result);
    bvh_traversal.traverseFlat_stackSorted(hit_result);
    
  }
  public boolean shadowTest(BvhHitResult hit_result){
//    bvh_traversal.shadowTest_linkedList(hit_result);
//    bvh_traversal.shadowTest_stack(hit_result);
//    bvh_traversal.shadowTest_stackSorted(hit_result);
    bvh_traversal.shadowTestFlat_stack(hit_result);
    return hit_result.gotHit();
  }
  

  
  

  
  
  
  public Bvh flatten(){
    nodes_flat = flatten(root);
    return this;
  }
  
  public BvhNodeF[] getFlatVersion(){
    return nodes_flat;
  }
  
  public BvhNodeF[] flatten(BvhNodeT root){
    int nodecount = getNumberOfNodes();
    int stack_end = 0;
    int stack_ptr = 0;

    BvhNodeF[] flatn = new BvhNodeF[nodecount];
    BvhNodeT[] stack = new BvhNodeT[nodecount];
    stack[stack_end++] = root;
    
    while(stack_ptr < stack_end){
      BvhNodeT cur = stack[stack_ptr];
      flatn[stack_ptr] = cur.getFlat();
      if( !cur._isLeaf() ){
        flatn[stack_ptr].setChildIdx(stack_end);
        stack[stack_end++] = cur.childA();
        stack[stack_end++] = cur.childB();
      }  
      stack_ptr++;
    }
    return flatn;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  

  
  
  public void printStatistics(){
    
    Bvh bvh = this;
    ArrayList<BvhNodeT> nodes = bvh.getNodes();
    BvhNodeT max_filled_node = bvh.getNodeWithMaxItems(nodes);
    
    boolean bvh_is_valid           = bvh.isValid(nodes);
    int     number_of_triangles    = obj.f.length;
    int     number_of_nodes        = nodes.size();
    int     number_of_leafes       = bvh.getNumberOfLeafes(nodes);      
    int     max_depth              = bvh.getMaxDepth(nodes);            
    float   average_leaf_depth     = bvh.getAverageLeafDepth(nodes);   
    float   average_leaf_items     = bvh.getAverageLeafItems(nodes);   
    float   average_child_items    = bvh.getAverageChildItemCount(nodes);

    float av_node_vol_02 = bvh.getAverageNodeVolume(nodes, 2);
    float av_node_vol_04 = bvh.getAverageNodeVolume(nodes, 4);
    float av_node_vol_06 = bvh.getAverageNodeVolume(nodes, 6);
    float av_node_vol_08 = bvh.getAverageNodeVolume(nodes, 8);

    float av_node_sa_02 = bvh.getAverageNodeSurfaceArea(nodes, 2);
    float av_node_sa_04 = bvh.getAverageNodeSurfaceArea(nodes, 4);
    float av_node_sa_06 = bvh.getAverageNodeSurfaceArea(nodes, 6);
    float av_node_sa_08 = bvh.getAverageNodeSurfaceArea(nodes, 8);

//    System.out.println("    building time          = "+ timer+"ms ");
    System.out.println("    bvh_is_valid           = "+ bvh_is_valid       );
    System.out.println("    number_of_triangles    = "+ number_of_triangles   );
    System.out.println("    number_of_nodes        = "+ number_of_nodes       );
    System.out.println("    number_of_leafes       = "+ number_of_leafes      );
    System.out.println("    max_depth              = "+ max_depth             );
    System.out.println("    average_leaf_depth     = "+ average_leaf_depth    );
    System.out.println("    average_leaf_items     = "+ average_leaf_items    );
    System.out.println("    max items per leaf     = "+ max_filled_node._itemCount()+" (depth="+max_filled_node._getDepth()+")");
    System.out.println("    average_child_items    = "+ average_child_items  );

    System.out.printf(Locale.ENGLISH, "    av_node_vol_02 = %10.1f    av_node_sa_02  = %10.1f\n", av_node_vol_02, av_node_sa_02);
    System.out.printf(Locale.ENGLISH, "    av_node_vol_04 = %10.1f    av_node_sa_04  = %10.1f\n", av_node_vol_04, av_node_sa_04);
    System.out.printf(Locale.ENGLISH, "    av_node_vol_06 = %10.1f    av_node_sa_06  = %10.1f\n", av_node_vol_06, av_node_sa_06);
    System.out.printf(Locale.ENGLISH, "    av_node_vol_08 = %10.1f    av_node_sa_08  = %10.1f\n", av_node_vol_08, av_node_sa_08);
  }
}
