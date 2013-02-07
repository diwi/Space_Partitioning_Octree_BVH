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

import DwMath.AABB;
import DwMath.DwRay3D;


public class Intersect_RAY_AABB {
  
  
  
//  static public float[] ray_d_rec = new float[3];
//  static public float[] t0 = new float[3];
//  static public float[] t1 = new float[3];
//  static public float[] t  = new float[2];
  
//  static private float[] t0_tmp = new float[3];
//  static private float[] t1_tmp = new float[3];
  
  
//  /**
//   * returns t[2] by reference - t[0]=near, t[1]=far;
//   * intersection if t[0]<t[1]
//   * @param ray
//   * @param aabb
//   * @return
//   */
//  static public float[] intersect_tRef(DwRay3D ray, AABB aabb){
//    DwVec3.reciprocal_ref(ray.d, ray_d_rec);
//    DwVec3.sub_ref(aabb.min, ray.o, t0);
//    DwVec3.sub_ref(aabb.max, ray.o, t1);
//    DwVec3.multiply_ref_slf(ray_d_rec, t0);
//    DwVec3.multiply_ref_slf(ray_d_rec, t1);
//    
//    t[0] = DwVec3.maxComponent(t0);
//    t[1] = DwVec3.minComponent(t1);
//    return t;
//  }
//  
//  
//  /**
//   * returns t[2] by copy - t[0]=near, t[1]=far;
//   * intersection if t[0]<t[1]
//   * @param ray
//   * @param aabb
//   * @return
//   */
//  static public float[] intersect_tCpy(DwRay3D ray, AABB aabb){
//    DwVec3.reciprocal_ref(ray.d, ray_d_rec);
//    
//    DwVec3.sub_ref(aabb.min, ray.o, t0);
//    DwVec3.sub_ref(aabb.max, ray.o, t1);
//    DwVec3.multiply_ref_slf(ray_d_rec, t0);
//    DwVec3.multiply_ref_slf(ray_d_rec, t1);
//    
//    return new float[]{DwVec3.maxComponent(t0), DwVec3.minComponent(t1)};
//  }
//  
//  /**
//   * returns true, if ray hits the aabb, otherwise false
//   * @param ray
//   * @param aabb
//   * @return
//   */
//  static public boolean intersect_bool(DwRay3D ray, AABB aabb){
//    DwVec3.reciprocal_ref(ray.d, ray_d_rec);
//    
//    DwVec3.sub_ref(aabb.min, ray.o, t0);
//    DwVec3.sub_ref(aabb.max, ray.o, t1);
//    DwVec3.multiply_ref_slf(ray_d_rec, t0);
//    DwVec3.multiply_ref_slf(ray_d_rec, t1);
//    
//    return DwVec3.maxComponent(t0)< DwVec3.minComponent(t1);
//  }
  
  
  /**
   * returns true, if ray hits the aabb, otherwise false
   * @param ray
   * @param aabb
   * @param t
   * @return
   */
//  static public boolean intersect(DwRay3D ray, AABB aabb, float[] t){
//    DwVec3.reciprocal_ref(ray.d, ray_d_rec);
//    
//    DwVec3.sub_ref(aabb.min, ray.o, t0_tmp);
//    DwVec3.sub_ref(aabb.max, ray.o, t1_tmp);
//    
//    DwVec3.multiply_ref_slf(ray_d_rec, t0_tmp);
//    DwVec3.multiply_ref_slf(ray_d_rec, t1_tmp);
//
//    DwVec3.min_ref(t0_tmp, t1_tmp, t0);
//    DwVec3.max_ref(t0_tmp, t1_tmp, t1);
//
//    t[0] = DwVec3.maxComponent(t0);
//    t[1] = DwVec3.minComponent(t1);
// 
//    return t[0]<t[1];
//  }
  
//  static public boolean intersect(DwRay3D ray, AABB aabb, float[] t){
//    ray_d_rec[0] = 1f/ray.d[0]; ray_d_rec[1] = 1f/ray.d[1]; ray_d_rec[2] = 1f/ray.d[2];
//    
//    t0_tmp[0] = (aabb.min[0]-ray.o[0])*ray_d_rec[0]; 
//    t0_tmp[1] = (aabb.min[1]-ray.o[1])*ray_d_rec[1]; 
//    t0_tmp[2] = (aabb.min[2]-ray.o[2])*ray_d_rec[2];
//    
//    t1_tmp[0] = (aabb.max[0]-ray.o[0])*ray_d_rec[0]; 
//    t1_tmp[1] = (aabb.max[1]-ray.o[1])*ray_d_rec[1];
//    t1_tmp[2] = (aabb.max[2]-ray.o[2])*ray_d_rec[2];
//    
//    if( t0_tmp[0]<0 && t1_tmp[0]<0) return false;
//    if( t0_tmp[1]<0 && t1_tmp[1]<0) return false;
//    if( t0_tmp[2]<0 && t1_tmp[2]<0) return false;
//    
//    // assure, that t0[] holds min values, and t1[] holds max values
//    if( t0_tmp[0] < t1_tmp[0] ) { t0[0]=t0_tmp[0]; t1[0]=t1_tmp[0]; } else { t0[0]=t1_tmp[0]; t1[0]=t0_tmp[0]; } 
//    if( t0_tmp[1] < t1_tmp[1] ) { t0[1]=t0_tmp[1]; t1[1]=t1_tmp[1]; } else { t0[1]=t1_tmp[1]; t1[1]=t0_tmp[1]; } 
//    if( t0_tmp[2] < t1_tmp[2] ) { t0[2]=t0_tmp[2]; t1[2]=t1_tmp[2]; } else { t0[2]=t1_tmp[2]; t1[2]=t0_tmp[2]; } 
//    
//    
//    // get the min component of t1[]
//    if( t1[0] < t1[1]){
//      if( t1[0] < t1[2]) t[1]=t1[0]; else t[1]=t1[2];
//    } else {
//      if( t1[1] < t1[2]) t[1]=t1[1]; else t[1]=t1[2];
//    }
//
////    if( t[1] < 0 ) return false;
//
//    // get the max component of t0[]
//    if( t0[0] > t0[1]){
//      if( t0[0] > t0[2]) t[0]=t0[0]; else t[0]=t0[2];
//    } else {
//      if( t0[1] > t0[2]) t[0]=t0[1]; else t[0]=t0[2];
//    }
//
//    return t[0]<t[1];
//  }
  
  
//  private static float rd_rec0, rd_rec1, rd_rec2;
//  static public boolean intersect(DwRay3D ray, AABB aabb, float[] t){
//    rd_rec0 = 1f/ray.d[0]; 
//    t0_tmp[0] = (aabb.min[0]-ray.o[0])*rd_rec0; 
//    t1_tmp[0] = (aabb.max[0]-ray.o[0])*rd_rec0; 
//    if( t0_tmp[0]<0 && t1_tmp[0]<0) return false;
//    
//    rd_rec1 = 1f/ray.d[1]; 
//    t0_tmp[1] = (aabb.min[1]-ray.o[1])*rd_rec1; 
//    t1_tmp[1] = (aabb.max[1]-ray.o[1])*rd_rec1;
//    if( t0_tmp[1]<0 && t1_tmp[1]<0) return false;
//    
//    rd_rec2 = 1f/ray.d[2];
//    t0_tmp[2] = (aabb.min[2]-ray.o[2])*rd_rec2;
//    t1_tmp[2] = (aabb.max[2]-ray.o[2])*rd_rec2;
//    if( t0_tmp[2]<0 && t1_tmp[2]<0) return false;
//    
//
//    // assure, that t0[] holds min values, and t1[] holds max values
//    if( t0_tmp[0] < t1_tmp[0] ) { t0[0]=t0_tmp[0]; t1[0]=t1_tmp[0]; } else { t0[0]=t1_tmp[0]; t1[0]=t0_tmp[0]; } 
//    if( t0_tmp[1] < t1_tmp[1] ) { t0[1]=t0_tmp[1]; t1[1]=t1_tmp[1]; } else { t0[1]=t1_tmp[1]; t1[1]=t0_tmp[1]; } 
//    if( t0_tmp[2] < t1_tmp[2] ) { t0[2]=t0_tmp[2]; t1[2]=t1_tmp[2]; } else { t0[2]=t1_tmp[2]; t1[2]=t0_tmp[2]; } 
//    
//    
//    // get the min component of t1[]
//    if( t1[0] < t1[1]){
//      if( t1[0] < t1[2]) t[1]=t1[0]; else t[1]=t1[2];
//    } else {
//      if( t1[1] < t1[2]) t[1]=t1[1]; else t[1]=t1[2];
//    }
//
////    if( t[1] < 0 ) return false;
//
//    // get the max component of t0[]
//    if( t0[0] > t0[1]){
//      if( t0[0] > t0[2]) t[0]=t0[0]; else t[0]=t0[2];
//    } else {
//      if( t0[1] > t0[2]) t[0]=t0[1]; else t[0]=t0[2];
//    }
//
//    return t[0]<t[1];
//  }
  
  
  private static float t_tmp;
//  private static float rd_rec_x, rd_rec_y, rd_rec_z;
  private static float t0_x, t0_y, t0_z;
  private static float t1_x, t1_y, t1_z;
  private static float tn, tf;

//  static public boolean intersect(final DwRay3D ray, final AABB aabb, final float[] t){
//    
//    t0_x = (aabb.min[0]-ray.o[0])*ray.d_rec[0]; 
//    t1_x = (aabb.max[0]-ray.o[0])*ray.d_rec[0]; 
//    if( t0_x<0 && t1_x<0) return false;
//    
//    t0_y = (aabb.min[1]-ray.o[1])*ray.d_rec[1]; 
//    t1_y = (aabb.max[1]-ray.o[1])*ray.d_rec[1];
//    if( t0_y<0 && t1_y<0) return false;
//    
//    t0_z = (aabb.min[2]-ray.o[2])*ray.d_rec[2];
//    t1_z = (aabb.max[2]-ray.o[2])*ray.d_rec[2];
//    if( t0_z<0 && t1_z<0) return false;
//    
//    // assure, that t0_xyz holds min values, and t1_xyz holds max values
//    if( t0_x > t1_x) { t_tmp=t0_x; t0_x=t1_x; t1_x=t_tmp; }
//    if( t0_y > t1_y) { t_tmp=t0_y; t0_y=t1_y; t1_y=t_tmp; }
//    if( t0_z > t1_z) { t_tmp=t0_z; t0_z=t1_z; t1_z=t_tmp; }
//
//    // get the max component of t0_xyz
//    if( t0_x > t0_y){
//      if( t0_x > t0_z) t[0]=t0_x; else t[0]=t0_z;
//    } else {
//      if( t0_y > t0_z) t[0]=t0_y; else t[0]=t0_z;
//    }
//    
//    // get the min component of t1_xyz
//    if( t1_x < t1_y){
//      if( t1_x < t1_z) t[1]=t1_x; else t[1]=t1_z;
//    } else {
//      if( t1_y < t1_z) t[1]=t1_y; else t[1]=t1_z;
//    }
//    
//    return t[0]<=t[1];
//  }
  
  
  
  static public float intersectNear(final DwRay3D ray, final AABB aabb){
    t0_x = (aabb.min[0]-ray.o[0])*ray.d_rec[0]; 
    t1_x = (aabb.max[0]-ray.o[0])*ray.d_rec[0]; 
    if( t0_x<0.0 && t1_x<0.0) return Float.MAX_VALUE;
    
    t0_y = (aabb.min[1]-ray.o[1])*ray.d_rec[1]; 
    t1_y = (aabb.max[1]-ray.o[1])*ray.d_rec[1];
    if( t0_y<0.0 && t1_y<0.0) return Float.MAX_VALUE;
    
    t0_z = (aabb.min[2]-ray.o[2])*ray.d_rec[2];
    t1_z = (aabb.max[2]-ray.o[2])*ray.d_rec[2];
    if( t0_z<0.0 && t1_z<0.0) return Float.MAX_VALUE;
    
    // assure, that t0_xyz holds min values, and t1_xyz holds max values
    if( t0_x > t1_x) { t_tmp=t0_x; t0_x=t1_x; t1_x=t_tmp; }
    if( t0_y > t1_y) { t_tmp=t0_y; t0_y=t1_y; t1_y=t_tmp; }
    if( t0_z > t1_z) { t_tmp=t0_z; t0_z=t1_z; t1_z=t_tmp; }

    // get the max component of t0_xyz
    if( t0_x > t0_y){
      if( t0_x > t0_z) tn=t0_x; else tn=t0_z;
    } else {
      if( t0_y > t0_z) tn=t0_y; else tn=t0_z;
    }
    
    // get the min component of t1_xyz
    if( t1_x < t1_y){
      if( t1_x < t1_z) tf=t1_x; else tf=t1_z;
    } else {
      if( t1_y < t1_z) tf=t1_y; else tf=t1_z;
    }
 
    return (tn<=tf) ? tn : Float.MAX_VALUE;
  }


}
