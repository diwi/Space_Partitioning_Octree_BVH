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




package DwMath;



public class AABB {
  public float[] min;
  public float[] max;
  public AABB(float[] min, float[] max){
    this.min = min;
    this.max = max;
  }
  public AABB(float[] A, float[] B, float[] C){
    this.min = new float[]
      { 
        DwVec3.minComponent(A[0], B[0], C[0]),
        DwVec3.minComponent(A[1], B[1], C[1]),
        DwVec3.minComponent(A[2], B[2], C[2]) 
      };
    this.max = new float[]
      { 
        DwVec3.maxComponent(A[0], B[0], C[0]),
        DwVec3.maxComponent(A[1], B[1], C[1]),
        DwVec3.maxComponent(A[2], B[2], C[2]) 
      };
  }
  
  static public AABB init(){
    return new AABB
      (
        new float[]{ Float.MAX_VALUE,  Float.MAX_VALUE,  Float.MAX_VALUE},
        new float[]{-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE}
      );
  }
  
  public AABB copy(){
    return new AABB(DwVec3.copy_new(min), DwVec3.copy_new(max));
  }
  
  
  public float getVolume(){
    float[] s = getSize(); return s[0]*s[1]*s[2];
  }
  public float getSurfaceArea(){
    float[] s = getSize(); return (s[0]*s[1] + s[0]*s[2] + s[1]*s[2]) * 2;
  }
  public float[] getSize(){
    return DwVec3.sub_new(max, min);
  }
  public float[] getHalfSize(){
    return DwVec3.scale_new(getSize(), 0.5f);
  }
  public float[] getCenter(){
    return DwVec3.scale_new(DwVec3.add_new(max, min), 0.5f);
  }
  public float[][] getCorners(){
    return new float[][]
      {
        { min[0], min[1],min[2] }, // [0]
        { min[0], min[1],max[2] }, // [1]
        { min[0], max[1],min[2] }, // [2]
        { min[0], max[1],max[2] }, // [3]
        { max[0], min[1],min[2] }, // [4]
        { max[0], min[1],max[2] }, // [5]
        { max[0], max[1],min[2] }, // [6]
        { max[0], max[1],max[2] }, // [7]
      };
  }
  
  public boolean isInside(float[] v0, float[] v1, float[] v2){
    return isInside(v0) && isInside(v1) && isInside(v2);
  }
  
  public boolean isInside(float[] v){
    return( (v[0]>=min[0]) && (v[1]>=min[1]) && (v[2]>=min[2]) &&
            (v[0]<=max[0]) && (v[1]<=max[1]) && (v[2]<=max[2]));
  }
  
  public AABB grow(AABB aabb){
//    DwVec3.min_ref_slf(this.min, aabb.min);
//    DwVec3.max_ref_slf(this.max, aabb.max);
    if( aabb.min[0] < min[0] ) min[0]=aabb.min[0];
    if( aabb.min[1] < min[1] ) min[1]=aabb.min[1];
    if( aabb.min[2] < min[2] ) min[2]=aabb.min[2];
    if( aabb.max[0] > max[0] ) max[0]=aabb.max[0];
    if( aabb.max[1] > max[1] ) max[1]=aabb.max[1];
    if( aabb.max[2] > max[2] ) max[2]=aabb.max[2];
    return this;
  }
  
  public boolean hasSameValues(AABB aabb){
    if( aabb.min[0] != min[0] ) return false;
    if( aabb.min[1] != min[1] ) return false;
    if( aabb.min[2] != min[2] ) return false;
    if( aabb.max[0] != max[0] ) return false;
    if( aabb.max[1] != max[1] ) return false;
    if( aabb.max[2] != max[2] ) return false;
    return true;
  }
  
//  public void grow(float[] a){
//    DwVec3.min_ref_slf(this.min, a);
//    DwVec3.max_ref_slf(this.max, a);
//  }
//  
//  public void grow(float[] a, float[] b, float[] c){
//    grow(a);
//    grow(b);
//    grow(c);
//  }
  
}
