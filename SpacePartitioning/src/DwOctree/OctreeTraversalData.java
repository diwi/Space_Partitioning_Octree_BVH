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
 */package DwOctree;

import DwMath.DwVec3;


public class OctreeTraversalData{
  public OctreeTraversalData prev;
  public OctreeNode node;
  public float[] t0, t1;
  
  public OctreeTraversalData(OctreeNode node, float[] t0, float[] t1){
    this.node = node;
    this.t0 = t0;
    this.t1 = t1;
  }
  
  public OctreeTraversalData(OctreeNode node, float t0x,float t0y, float t0z, float t1x,float t1y,float t1z){
    this.node = node;
    this.t0 = new float[]{t0x, t0y, t0z};
    this.t1 = new float[]{t1x, t1y, t1z};
  }
  
  public float[] tm(){
    return DwVec3.scale_new( DwVec3.add_new(t0, t1), 0.5f);
  }

  
  public float tNear(){ return DwVec3.maxComponent(t0); };
  public float tFar (){ return DwVec3.minComponent(t1); };
}
