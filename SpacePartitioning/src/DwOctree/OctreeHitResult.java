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

import DwMath.DwRay3D;

public class OctreeHitResult {
  public DwRay3D ray;
  public float t_max;
  public float t_min;
  public float t;
  public OctreeNode node;
  public int item_idx = -1;
  
  public boolean two_sided_check = true;
  public float hit_backface = 1.0f;
  public float u, v;
  public boolean got_hit = false;
  
  
  public int COUNT_triangle_intersection_tests = 0;
  public int COUNT_node_intersection_tests     = 0;
  public int COUNT_node_traversal_steps        = 0;
  
  
  public ArrayList<OctreeTraversalData> traversal_history = new ArrayList<OctreeTraversalData>();
  
  public OctreeHitResult(DwRay3D ray, float t_min, float t_max){
    set(ray, t_min, t_max);
  }
  
  public OctreeHitResult set(DwRay3D ray, float t_min, float t_max){
    this.ray = ray;
    this.t_min = t_min;
    this.t_max = t_max;
    this.t = t_max;
    this.item_idx = -1;
    this.hit_backface = 1.0f;
    this.got_hit = false;
    this.traversal_history.clear();
    return this;
  }
  
  public boolean checkIfCloser(float t_new, int item_idx, OctreeNode node, float hit_backface, float u, float v){
    if( t_min < t_new && t_new < t){
      this.t            = t_new;
      this.u            = u;
      this.v            = v;
      this.item_idx     = item_idx;
      this.node         = node;
      this.hit_backface = hit_backface;
      this.got_hit      = true;
      return true;
    }
    return false;
  }
  
  public boolean checkIfCloser(float[] tuv, int item_idx, OctreeNode node, float hit_backface){
    if( t_min < tuv[0] && tuv[0] < t){
      this.t            = tuv[0];
      this.u            = tuv[1];
      this.v            = tuv[2];
      this.item_idx     = item_idx;
      this.node         = node;
      this.hit_backface = hit_backface;
      this.got_hit      = true;
      return true;
    }
    return false;
  }
  public boolean gotHit(){
    return got_hit;
  }
  
  public float[] getHitPoint(){
    return ray.getPoint(t);
  }
  
  
  public void resetCounters(){
    COUNT_triangle_intersection_tests = 0;
    COUNT_node_intersection_tests     = 0;
    COUNT_node_traversal_steps        = 0;
  }

  
  public void printCounters(){
    System.out.println("---< HitResult: stats >---");
    System.out.println("   COUNT_triangle_intersection_tests = "+COUNT_triangle_intersection_tests);
    System.out.println("   COUNT_node_intersection_tests     = "+COUNT_node_intersection_tests);
    System.out.println("   COUNT_node_traversal_steps        = "+COUNT_node_traversal_steps);
    System.out.println("---</ HitResult: stats >---");
  }
 
}
