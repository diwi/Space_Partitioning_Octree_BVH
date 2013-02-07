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




package DwOBJ_Loader;

import DwMath.AABB;
import DwMath.DwVec3;


public class DwOBJ_Face {
  public DwOBJ_File parent_obj_file;
  public DwOBJ_Material MATERIAL = DwOBJ_Material.MAT_DEFAULT;
  public DwOBJ_Mesh MESH = null;
  
  public int[] IDX_V = new int[3]; // indices - vertices
  public int[] IDX_N = new int[3]; // indices - normals
  public int[] IDX_T = new int[3]; // indices - texture coordinates
  
  public AABB aabb;
  
  public boolean FLAG_CHECKED = false;
  
  public DwOBJ_Face(DwOBJ_File parent_obj_file){
    this.parent_obj_file = parent_obj_file;
  }
  
  public float[] A() { return parent_obj_file.v[IDX_V[0]]; }
  public float[] B() { return parent_obj_file.v[IDX_V[1]]; }
  public float[] C() { return parent_obj_file.v[IDX_V[2]]; }
                       
  public float[] An(){ return parent_obj_file.n[IDX_N[0]]; }
  public float[] Bn(){ return parent_obj_file.n[IDX_N[1]]; }
  public float[] Cn(){ return parent_obj_file.n[IDX_N[2]]; }
  
  public void computeAABB(){
    aabb = new AABB(A(), B(), C());
  }
  
  public float[] getCenter(){
    return DwVec3.triangle_midpoint_new(A(), B(), C());
  }
  
  public boolean isDegenerate(){
    float[] n = getNormalUnormalized();
    return DwVec3.mag(n) == 0.0f;
  }
  
  public float getArea(){
    return DwVec3.mag(getNormalUnormalized())*0.5f;
  }
  
  public float[] getNormalUnormalized(){
    float[] E1 = DwVec3.sub_new(B(), A());
    float[] E2 = DwVec3.sub_new(C(), A());
    return DwVec3.cross_new(E1, E2);
  }
  
  public float[] getNormal(){
    float[] n = getNormalUnormalized();
    DwVec3.normalize_ref_slf(n);
    return n;
  }


  public float[] getNormal(float u, float v){
    float[] nA = An(), nB = Bn(),  nC = Cn();
    float w = 1-u-v;
    float[] n = {   nA[0]*w + nC[0]*u + nB[0]*v,
                    nA[1]*w + nC[1]*u + nB[1]*v,
                    nA[2]*w + nC[2]*u + nB[2]*v     };
    DwVec3.normalize_ref_slf(n);
    return n;
  }
  public void getNormal_ref(float u, float v, float[] dst){
    float[] nA = An(), nB = Bn(),  nC = Cn();
    float w = 1-u-v;
    dst[0] = nA[0]*w + nC[0]*u + nB[0]*v;
    dst[1] = nA[1]*w + nC[1]*u + nB[1]*v;
    dst[2] = nA[2]*w + nC[2]*u + nB[2]*v;
    DwVec3.normalize_ref_slf(dst);
  }
  
  
  
  public float[] getPoint(float u, float v){
    float[] A = A(), B = B(),  C = C();
    float w = 1-u-v;
    return new float[] {   A[0]*w + C[0]*u + B[0]*v,
                           A[1]*w + C[1]*u + B[1]*v,
                           A[2]*w + C[2]*u + B[2]*v     };
  }
  public float[] getPoint(float u, float v, float w){
    float[] A = A(), B = B(),  C = C();
    return new float[] {   A[0]*w + C[0]*u + B[0]*v,
                           A[1]*w + C[1]*u + B[1]*v,
                           A[2]*w + C[2]*u + B[2]*v     };
  }
  
  public float[] getUniformlySampledPoint(){
    double u = Math.random();
    double v = Math.random();
    double w = Math.sqrt(u);
    u = 1-w;
    v = v*w;
    return getPoint((float)(u), (float)(v));
  }
  static public void getUniformlySampledUVs(float[] uv){
    double u = Math.random();
    double v = Math.random();
    double w = Math.sqrt(u);
    uv[0] = (float)(1-w);
    uv[1] = (float)(v*w);
  }
  
  public float[] getCenterWeightedSampledPoint(){
    float r = (float)(Math.random());
    float s = (float)(Math.random());
    float t = (float)(Math.random());
    float z = 1/(r+s+t);
    return getPoint(r*z, s*z, t*z);
    
  }
  
}