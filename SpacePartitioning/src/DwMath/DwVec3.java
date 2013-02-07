/**
 * 
 *   author: (c)thomas diewald, http://thomasdiewald.com/
 *   date: 23.04.2012
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

import java.util.Locale;

public class DwVec3 {
  
  public static float[] init(float v){
    return new float[]{v,v,v};
  }
  
  public static float[] nullvector() {
    return new float[]{0, 0, 0};
  }
  public static void nullvector_slf( float[] dst) {
    dst[0] = 0; dst[1] = 0; dst[2] = 0;
  }
  
  public static void set_ref(float x, float y, float z, float[] dst) {
    dst[0] = x;
    dst[1] = y;
    dst[2] = z;
  };
  public static float[] set_new(float x, float y, float z) {
    return new float[]{x, y, z};
  };


  public static void copy_ref(float[] a, float[] dst) {
    dst[0] = a[0];
    dst[1] = a[1];
    dst[2] = a[2];
  };

  public static float[] copy_new (float[] a) {
    return new float[]{a[0], a[1], a[2]};
  };
  
  public static float[] min_new(float[] a, float[] b){
    return new float[]{
        (float)Math.min(a[0], b[0]),
        (float)Math.min(a[1], b[1]),
        (float)Math.min(a[2], b[2]),
    };
  }
  public static float[] max_new(float[] a, float[] b){
    return new float[]{
        (float)Math.max(a[0], b[0]),
        (float)Math.max(a[1], b[1]),
        (float)Math.max(a[2], b[2]),
    };
  }
  
  public static void min_ref(float[] a, float[] b, float[] dst){
    dst[0] = Math.min(a[0], b[0]);
    dst[1] = Math.min(a[1], b[1]);
    dst[2] = Math.min(a[2], b[2]);
  }
  public static void max_ref(float[] a, float[] b, float[] dst){
    dst[0] = Math.max(a[0], b[0]);
    dst[1] = Math.max(a[1], b[1]);
    dst[2] = Math.max(a[2], b[2]);
  }
  
  public static void min_ref_slf(float[] dst, float[] b){
    dst[0] = Math.min(dst[0], b[0]);
    dst[1] = Math.min(dst[1], b[1]);
    dst[2] = Math.min(dst[2], b[2]);
  }
  public static void max_ref_slf(float[] dst, float[] b){
    dst[0] = Math.max(dst[0], b[0]);
    dst[1] = Math.max(dst[1], b[1]);
    dst[2] = Math.max(dst[2], b[2]);
  }
  
  
  


  public static boolean equals(float[] a, float[] b) {
    return (a[0] == b[0] && a[1] == b[1] && a[2] == b[2]);
  };

  public static void add_ref_slf (float[] a, float[]dst) {
    dst[0] += a[0];  dst[1] += a[1];   dst[2] += a[2];
  };
  public static void add_ref (float[] a, float[] b, float[] dst) {
    dst[0] = a[0]+b[0];  dst[1] = a[1]+b[1];   dst[2] = a[2]+b[2];
  };
  public static float[] add_new(float[] a, float[] b) {
    return new float[]{(a[0]+b[0]),  (a[1]+b[1]),   (a[2]+b[2])};
  };

  public static void sub_ref_slf (float[] a, float[]dst) {
    dst[0] -= a[0];  dst[1] -= a[1];   dst[2] -= a[2];
  };
  public static void sub_ref(float[] a, float[] b, float[] dst) {
    dst[0] = a[0]-b[0];   dst[1] = a[1]-b[1];  dst[2] = a[2]-b[2];
  };
  public static float[] sub_new(float[] a, float[] b) {
    return new float[]{(a[0]-b[0]),  (a[1]-b[1]),  (a[2]-b[2])};
  };


  public static void line_midpoint_ref (float[] a, float[] b, float[] dst) {
    dst[0] = (a[0] + b[0]) * 0.5f;
    dst[1] = (a[1] + b[1]) * 0.5f;
    dst[2] = (a[2] + b[2]) * 0.5f;
  };
  public static float[]  line_midpoint_new (float[] a, float[] b) {
    return new float[]{(a[0]+b[0])*0.5f, (a[1]+b[1])*0.5f, (a[2]+b[2])*0.5f};
  };

  public static void triangle_midpoint_ref(float[] a, float[] b, float[] c, float[] dst) {
    float f = 1/3f;
    dst[0] = (a[0]+b[0]+c[0]) * f;
    dst[1] = (a[1]+b[1]+c[1]) * f;
    dst[2] = (a[2]+b[2]+c[2]) * f;
  };
  public static float[] triangle_midpoint_new(float[] a, float[] b, float[] c) {
    float f = 1/3f;
    return new float[]{(a[0]+b[0]+c[0]) * f, (a[1]+b[1]+c[1]) * f, (a[2]+b[2]+c[2]) * f};
  };


  public static void sum_ref(float[] a, float[] b, float[] c, float[] dst){
    dst[0] = a[0]+b[0]+c[0];
    dst[1] = a[1]+b[1]+c[1];
    dst[2] = a[2]+b[2]+c[2];
  }
  public static float[] sum_new(float[] a, float[] b, float[] c){
    return  new float[]{a[0]+b[0]+c[0],  a[1]+b[1]+c[1], a[2]+b[2]+c[2]};
  }


  public static void sumlist_ref(float[][] arr, float[] dst){
    float len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
      dst[2] += arr[i][2];
    }
  }

  public static float[] sumlist_new(float[]... arr){
    float[] dst = new float[3];
    float len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
      dst[2] += arr[i][2];
    }
    return dst;
  }
  





  public static void multiply_ref_slf(float[] a, float[] dst) {
    dst[0] *= a[0];
    dst[1] *= a[1];
    dst[2] *= a[2];
  };

  public static void multiply_ref(float[] a, float[] b, float[] dst) {
    dst[0] = a[0] * b[0];
    dst[1] = a[1] * b[1];
    dst[2] = a[2] * b[2];
  };
  public static float[] multiply_new(float[] a, float[] b) {
    return new float[]{a[0]*b[0], a[1]*b[1], a[2]*b[2]};
  };



  public static void negate_ref(float[] a, float[] dst) {
    dst[0] = -a[0];
    dst[1] = -a[1];
    dst[2] = -a[2];
  };
  public static void negate_ref_slf(float[] a) {
    a[0] = -a[0];
    a[1] = -a[1];
    a[2] = -a[2];
  };
  public static float[] negate_new(float[] a) {
    return new float[]{-a[0], -a[1], -a[2]};
  };
  
  public static float[] abs_new(float[] a){
    return new float[]{Math.abs(a[0]), Math.abs(a[1]), Math.abs(a[2]) };
  }
  public static void abs_ref(float[] a, float[] dst){
    dst[0] = Math.abs(a[0]);
    dst[1] = Math.abs(a[1]);
    dst[2] = Math.abs(a[2]);
  }  
  public static void abs_ref_slf(float[] a){
    a[0] = Math.abs(a[0]);
    a[1] = Math.abs(a[1]);
    a[2] = Math.abs(a[2]);
  }
  
  public static float minComponent(float[] a){
    return Math.min(Math.min(a[0], a[1]), a[2]);
  }
  public static float maxComponent(float[] a){
    return Math.max(Math.max(a[0], a[1]), a[2]);
  }
  
  public static float minComponent(float a, float b, float c){
    return Math.min(Math.min(a, b), c);
  }
  public static float maxComponent(float a, float b, float c){
    return Math.max(Math.max(a, b), c);
  }
  



  public static void scale_ref(float[] a, float val, float[] dst) {
    dst[0] = a[0] * val;
    dst[1] = a[1] * val;
    dst[2] = a[2] * val;
  };
  public static void scale_ref_slf(float[] a, float val) {
    a[0] *= val;
    a[1] *= val;
    a[2] *= val;
  };
  public static float[] scale_new(float[] a, float val) {
    return new float[]{a[0]*val, a[1]*val, a[2]*val};
  };
  
  
  public static float[] reciprocal_new(float[] a){
    return new float[]{1f/a[0], 1f/a[1], 1f/a[2]};
  }
  public static void reciprocal_ref(float[] a, float[] dst){
    dst[0] = 1f/a[0];
    dst[1] = 1f/a[1];
    dst[2] = 1f/a[2];
  }
  public static void reciprocal_ref_slf(float[] a){
    a[0] = 1f/a[0];
    a[1] = 1f/a[1];
    a[2] = 1f/a[2];
  }


  public static void normalize_ref_slf  (float[] a) {
    float x = a[0], y = a[1], z = a[2];
    float len = (float) Math.sqrt(x*x + y*y + z*z);
//    float len = fastSqrt(x*x + y*y + z*z);
    if (len != 1) {
      len = 1 / len;
      a[0] *= len;
      a[1] *= len;
      a[2] *= len;
    }
  };
  public static void normalize_ref(float[] a, float[] dst) {
    float x = a[0], y = a[1], z = a[2];
    float len = (float) Math.sqrt(x * x + y * y + z * z);
//    float len = fastSqrt(x*x + y*y + z*z);
    if (len == 0) {
      dst[0] = 0;
      dst[1] = 0;
      dst[2] = 0;
    } else if (len == 1) {
      dst[0] = x;
      dst[1] = y;
      dst[2] = z;
    } else {
      len = 1 / len;
      dst[0] = x * len;
      dst[1] = y * len;
      dst[2] = z * len;
    }
  };
  public static float[] normalize_new(float[] a) {
    float x = a[0], y = a[1], z = a[2];
    float len = (float) Math.sqrt(x * x + y * y + z * z);
//    float len = fastSqrt(x*x + y*y + z*z);
    
    if (len == 0) {
      return new float[3];
    } else if (len == 1) {
      return  new float[]{x, y, z};
    } else {
      len = 1/len;
      return new float[]{x*len, y*len, z*len};
    }
  };
  
  
//  public static float fastSqrt(float x) {
//    return (float) Math.sqrt(x);
////    return Float.intBitsToFloat(532483686 + (Float.floatToRawIntBits(x) >> 1));
//  }


  public static void cross_ref(float[] a, float[] b, float[] dst) {
    float ax = a[0], ay = a[1], az = a[2];
    float bx = b[0], by = b[1], bz = b[2];

    dst[0] = ay * bz - az * by;
    dst[1] = az * bx - ax * bz;
    dst[2] = ax * by - ay * bx;
  };
  public static float[] cross_new(float[] a, float[] b) {
    float ax = a[0], ay = a[1], az = a[2];
    float bx = b[0], by = b[1], bz = b[2];

    return new float[]{ay*bz - az*by, az*bx - ax*bz, ax*by - ay*bx};
  };



  public static float dot(float[] a, float[] b) {
    return a[0]*b[0] + a[1]*b[1] + a[2]*b[2];
  };



  public static float angleBetween(float[] a, float[] b){
    return (float) Math.acos( DwVec3.dot(a,b)/(DwVec3.mag(a)*DwVec3.mag(b)) );
  }
  public static float angleBetween_unit(float[] a, float[] b){
    return (float) Math.acos( DwVec3.dot(a,b) );
  }



  public static float mag(float[] a) {
    float x = a[0], y = a[1], z = a[2];
    return (float) Math.sqrt(x*x + y*y + z*z);
//    return fastSqrt(x*x + y*y + z*z);
  };
  public static float mag_sq (float[] a) {
    float x = a[0], y = a[1], z = a[2];
    return x*x + y*y + z*z;
  };







  public static void dir_unit_ref(float[] a, float[] b, float[] dst) {
    DwVec3.sub_ref(a, b, dst);
    DwVec3.normalize_ref_slf(dst);
  };

  public static  float[] dir_unit_new(float[] a, float[] b) {
    float[] dst = new float[3];
    DwVec3.sub_ref(a, b, dst);
    DwVec3.normalize_ref_slf(dst);
    return dst;
  };



  public static void lerp_ref(float[] a, float[] b, float val, float[] dst) {
    dst[0] = a[0] + val * (b[0] - a[0]);
    dst[1] = a[1] + val * (b[1] - a[1]);
    dst[2] = a[2] + val * (b[2] - a[2]);
  };

  public static float[] lerp_new(float[] a, float[] b, float val) {
    return new float[]{a[0]+val*(b[0]-a[0]), a[1]+val*(b[1]-a[1]), a[2]+val*(b[2]-a[2])};
  };


  public static float dist(float[] a, float[] b) {
    float[] dst = DwVec3.sub_new(a, b);
    return DwVec3.mag(dst);
  };
  
  
  
  
  
  public static float[] randF3_new(float size){
    return new float[]
      {
        (float)(Math.random()-0.5)*2f*size,
        (float)(Math.random()-0.5)*2f*size,
        (float)(Math.random()-0.5)*2f*size
      };
  }
  public static void randF3_ref(float[] dst, float size){
    dst[0] = (float)(Math.random()-0.5)*2f*size;
    dst[1] = (float)(Math.random()-0.5)*2f*size;
    dst[2] = (float)(Math.random()-0.5)*2f*size;
  }

  
  
  
  
  
  
  
  
//  V - 2.0f * dot(V,N) * N;
  public static float[] reflect_new(float[] V, float[] N){
    float c = 2f*dot(V, N);
    return new float[]{  V[0]-c*N[0], 
                         V[1]-c*N[1], 
                         V[2]-c*N[2]   };
  }
  public static void reflect_ref(float[] V, float[] N, float[] dst){
    float c = 2f*dot(V, N);
    dst[0] = V[0]-c*N[0];
    dst[1] = V[1]-c*N[1];
    dst[2] = V[2]-c*N[2];
  }
  
  public static void reflect_ref_slf(float[] V, float[] N){
    float c = 2f*dot(V, N);
    V[0] -= c*N[0];
    V[1] -= c*N[1];
    V[2] -= c*N[2];
  }
  
  
  
//  static inline float3 refract(const float3 I, const float3 N, const float eta) {
//    const float cosNI = dot( N, I );
//    const float k = 1.0f - eta*eta*(1.0f - cosNI*cosNI);
//    if( k > 0.0 )
//      return (eta*I) - (eta*cosNI + sqrt(k)) * N;
//    else
//      return (float3)(0.0);
//  }
  
  // returns true, if the ray was refracted, 
  //               R[] holds the refraction vector
  // returns false, if the ray would be reflected (total internal reflection)
  //               R holds reflection vector
  public static boolean refract_new(float[] I, float[] N, float eta, float[] R){
    float cosNI = DwVec3.dot( N, I );
    float k = 1.0f - eta*eta*(1.0f - cosNI*cosNI);
    if( k > 0.0 ) {
      R[0] = (eta*I[0]) - (eta*cosNI + (float)Math.sqrt(k)) * N[0]; 
      R[1] = (eta*I[1]) - (eta*cosNI + (float)Math.sqrt(k)) * N[1]; 
      R[2] = (eta*I[2]) - (eta*cosNI + (float)Math.sqrt(k)) * N[2]; 
      return true;
    } else {
      reflect_ref(I, N, R);
      return false;
    }
  }
  


  
  
  
  


  public static String toStr(float[] a, int prec) {
    return String.format(Locale.ENGLISH, "[%+3.3f, %+3.3f, %+3.3f]", a[0], a[1], a[2]);
  };
  public static void print(float[] a, int prec) {
    System.out.println(toStr(a, prec));
  }


}
