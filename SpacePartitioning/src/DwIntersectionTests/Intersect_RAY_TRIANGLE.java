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




package DwIntersectionTests;

import DwMath.DwVec3;
import DwMath.DwRay3D;
import DwOBJ_Loader.DwOBJ_Face;

public class Intersect_RAY_TRIANGLE {
  
  
  private static float epsilon = 0.0001f;
  private static float[] n  = new float[3];
  private static float[] E1 = new float[3];
  private static float[] E2 = new float[3];
  private static float[] E_TMP;
  private static float[] P  = new float[3];
  private static float[] T  = new float[3];
  private static float[] Q  = new float[3];
  private static float det;
  public  static float u, v;

  
  static public boolean intersect2(DwRay3D ray, float[] A, float[] B, float[] C, boolean two_sided, float[] ptr_hit_backface, float[] tuv){
    DwVec3.sub_ref(B, A, E1); // edge1: v0 -> v1
    DwVec3.sub_ref(C, A, E2); // edge2: v0 -> v2
    
    DwVec3.cross_ref(E1, E2, n);     // normal
    if( DwVec3.dot(n, ray.d) > 0.0){ // angle > 0.0
      ptr_hit_backface[0] = -1.0f;   // if there is any intersection it would be with a backface
      if( two_sided ) {              // so if the triangle is two_sided, switch edges for further computation
        E_TMP = E1; E1 = E2; E2 = E_TMP;
      } else {
        return false;      // otherwise return "no intersection"
      }
    } else {
      ptr_hit_backface[0]= +1.0f;    // no backface, so normal direction stays the same
    }

    DwVec3.cross_ref(ray.d, E2, P); // cross of ray-direction and edge2
    det = DwVec3.dot(E1, P);        // determinant
    if( Math.abs(det) < epsilon ) return false;
//    if( det < epsilon ) return false;
    
    DwVec3.sub_ref(ray.o, A, T);   // vector: v0 -> ray-origin
    u = DwVec3.dot(T, P);        // param u, + testing bounds
    if( u < 0.0 || u > det) return false;

    DwVec3.cross_ref(T, E1, Q); // cross of T and edge2
    v = DwVec3.dot(Q, ray.d);        // param v, + testing bounds
    if( v < 0.0 || (u+v) > det) return false;

    det = 1f/det;
    tuv[0] = det*DwVec3.dot(Q, E2);
    tuv[1] = det*u;
    tuv[2] = det*v;
    return true;
  }
  
  static public boolean intersect(DwRay3D ray, DwOBJ_Face face, boolean two_sided, float[] ptr_hit_backface, float[] tuv){
    float[] A = face.A();
    float[] B = face.B();
    float[] C = face.C();
    n = face.getNormal(0.33f, 0.33f);
    DwVec3.sub_ref(B, A, E1); // edge1: v0 -> v1
    DwVec3.sub_ref(C, A, E2); // edge2: v0 -> v2
    
//    DwVec3.cross_ref(E1, E2, n);     // normal
    if( DwVec3.dot(n, ray.d) < 0.0){ // angle > 0.0
      ptr_hit_backface[0] = -1.0f;   // if there is any intersection it would be with a backface
      if( two_sided ) {              // so if the triangle is two_sided, switch edges for further computation
        E_TMP = E1; E1 = E2; E2 = E_TMP;
      } else {
        return false;      // otherwise return "no intersection"
      }
    } else {
      ptr_hit_backface[0]= +1.0f;    // no backface, so normal direction stays the same
    }

    DwVec3.cross_ref(ray.d, E2, P); // cross of ray-direction and edge2
    det = DwVec3.dot(E1, P);        // determinant
    if( Math.abs(det) < epsilon ) return false;
//    if( det < epsilon ) return false;
    
    DwVec3.sub_ref(ray.o, A, T);   // vector: v0 -> ray-origin
    u = DwVec3.dot(T, P);        // param u, + testing bounds
    if( u < 0.0 || u > det) return false;

    DwVec3.cross_ref(T, E1, Q); // cross of T and edge2
    v = DwVec3.dot(Q, ray.d);        // param v, + testing bounds
    if( v < 0.0 || (u+v) > det) return false;

    det = 1f/det;
    tuv[0] = det*DwVec3.dot(Q, E2);
    tuv[1] = det*u;
    tuv[2] = det*v;
    return true;
  }

  
  
  static private float E1x, E1y, E1z;
  static private float E2x, E2y, E2z;
  static private float nx, ny, nz;
  static private float Px, Py, Pz;
  static private float Tx, Ty, Tz;
  static private float Qx, Qy, Qz;
  
  
  private static void swap_E1_E2(){
    float E_TMP_x = E1x; 
    float E_TMP_y = E1y; 
    float E_TMP_z = E1z; 
    E1x = E2x;
    E1y = E2y;
    E1z = E2z;
    E2x = E_TMP_x;
    E2y = E_TMP_y;
    E2z = E_TMP_z;
  }
  
  static public boolean intersect3(DwRay3D ray, float[] A, float[] B, float[] C, boolean two_sided, float[] ptr_hit_backface, float[] tuv){
    
    float rdx = ray.d[0];
    float rdy = ray.d[1];
    float rdz = ray.d[2];
    
    // edge1: v0 -> v1     // edge2: v0 -> v2
    E1x = B[0]-A[0];  E2x = C[0]-A[0];
    E1y = B[1]-A[1];  E2y = C[1]-A[1]; 
    E1z = B[2]-A[2];  E2z = C[2]-A[2];

    // normal
    nx = E1y * E2z - E1z * E2y;
    ny = E1z * E2x - E1x * E2z;
    nz = E1x * E2y - E1y * E2x;
    
    if( (nx*rdx+ny*rdy+nz*rdz) > 0.0){ // angle > 0.0
      ptr_hit_backface[0] = -1.0f;   // if there is any intersection it would be with a backface
      if( two_sided ) {              // so if the triangle is two_sided, switch edges for further computation
        swap_E1_E2();
      } else {
        return false;      // otherwise return "no intersection"
      }
    } else {
      ptr_hit_backface[0]= +1.0f;    // no backface, so normal direction stays the same
    }
    
    // cross of ray-direction and edge2
    Px = rdy * E2z - rdz * E2y;
    Py = rdz * E2x - rdx * E2z;
    Pz = rdx * E2y - rdy * E2x;
    
    det = E1x*Px + E1y*Py + E1z*Pz; // determinant
    if( Math.abs(det) < epsilon ) return false;
//    if( det < epsilon ) return Float.MAX_VALUE;
    
    // vector: v0 -> ray-origin
    Tx = ray.o[0] - A[0];
    Ty = ray.o[1] - A[1];
    Tz = ray.o[2] - A[2];
    u = Tx*Px + Ty*Py + Tz*Pz; // param u, + testing bounds
    if( u < 0.0 || u > det) return false;

    // cross of T and edge2
    Qx = Ty * E1z - Tz * E1y;
    Qy = Tz * E1x - Tx * E1z;
    Qz = Tx * E1y - Ty * E1x;
    v = Qx*rdx + Qy*rdy + Qz*rdz; // param v, + testing bounds
    if( v < 0.0 || (u+v) > det) return false;

    det = 1f/det;
    tuv[0] = det*(Qx*E2x + Qy*E2y + Qz*E2z);
    tuv[1] = det*u;
    tuv[2] = det*v;
    return true;
  }
  
  
  static public float intersect(DwRay3D ray, float[] A, float[] B, float[] C, boolean two_sided, float[] ptr_hit_backface){
    DwVec3.sub_ref(B, A, E1); // edge1: v0 -> v1
    DwVec3.sub_ref(C, A, E2); // edge2: v0 -> v2
    
    DwVec3.cross_ref(E1, E2, n);     // normal
    if( DwVec3.dot(n, ray.d) > 0.0){ // angle > 0.0
      ptr_hit_backface[0] = -1.0f;   // if there is any intersection it would be with a backface
      if( two_sided ) {              // so if the triangle is two_sided, switch edges for further computation
        float[] tmp = E1; E1 = E2; E2 = tmp;
      } else {
        return Float.MAX_VALUE;      // otherwise return "no intersection"
      }
    } else {
      ptr_hit_backface[0]= +1.0f;    // no backface, so normal direction stays the same
    }

    
    DwVec3.cross_ref(ray.d, E2, P); // cross of ray-direction and edge2
    det = DwVec3.dot(E1, P);       // determinant
    if( Math.abs(det) < epsilon ) return Float.MAX_VALUE;
//    if( det < epsilon ) return Float.MAX_VALUE;
    
    DwVec3.sub_ref(ray.o, A, T);   // vector: v0 -> ray-origin
    u = DwVec3.dot(T, P);        // param u, + testing bounds
    if( u < 0.0 || u > det) return Float.MAX_VALUE;

    DwVec3.cross_ref(T, E1, Q); // cross of T and edge2
    v = DwVec3.dot(Q, ray.d);        // param v, + testing bounds
    if( v < 0.0 || (u+v) > det) return Float.MAX_VALUE;

    u/=det;
    v/=det;
    return DwVec3.dot(Q, E2)/det; // intersection parameter for ray: P(intersect) = o(origin) + t*d(direction);
  }
}
