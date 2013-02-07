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

public class DwVec4 {
  public static float[] nullvector () {
    return new float[]{0, 0, 0, 0};
  }
  
  public float[] toVec3_new(float[] a){
    return new float[]{a[0], a[1], a[2]};
  }


  public static void copy_ref(float[]a, float[]dst) {
    dst[0] = a[0];
    dst[1] = a[1];
    dst[2] = a[2];
    dst[3] = a[3];
  };

  public static float[] copy_new(float[]a) {
    return new float[]{a[0], a[1], a[2], a[3]};
  };


  public static boolean equals (float[]a, float[]b) {
    return (a[0] == b[0] && a[1] == b[1] && a[2] == b[2] && a[3] == b[3]);
  };
  
  
  public static float dot(float[] a, float[] b) {
    return a[0]*b[0] + a[1]*b[1] + a[2]*b[2] + a[3]*b[3];
  }
  
  public static float[] sub_new(float[] a, float[] b){
    return new float[]{a[0]-b[0], a[1]-b[1], a[2]-b[2], a[3]-b[3]};
  }
  public static void sub_ref_slf(float[] a, float[] b){
    a[0]-=b[0]; 
    a[1]-=b[1]; 
    a[2]-=b[2]; 
    a[3]-=b[3];
  }
  
  public static float[] add_new(float[] a, float[] b){
    return new float[]{a[0]+b[0], a[1]+b[1], a[2]+b[2], a[3]+b[3]};
  }
  public static void add_ref_slf(float[] a, float[] b){
    a[0]+=b[0]; 
    a[1]+=b[1]; 
    a[2]+=b[2]; 
    a[3]+=b[3];
  }
  
  
  
  public static float[] multiply_new(float[] a, float b){
    return new float[]{a[0]*b, a[1]*b, a[2]*b, a[3]*b};
  }
  public static void multiply_ref_slf(float[] a, float b){
    a[0]*=b; 
    a[1]*=b; 
    a[2]*=b; 
    a[3]*=b;
  }
  
  public static float[] multiply3_new(float[] a, float b){
    return new float[]{a[0]*b, a[1]*b, a[2]*b, a[3]};
  }
  public static void multiply3_ref_slf(float[] a, float b){
    a[0]*=b; 
    a[1]*=b; 
    a[2]*=b; 
//    a[3]*=b;
  }
  
  
  public static void scale_ref(float[] a, float val, float[] dst) {
    dst[0] = a[0] * val;
    dst[1] = a[1] * val;
    dst[2] = a[2] * val;
    dst[3] = a[3] * val;
  };
  public static void scale_ref_slf(float[] a, float val) {
    a[0] *= val;
    a[1] *= val;
    a[2] *= val;
    a[3] *= val;
  };
  public static float[] scale_new(float[] a, float val) {
    return new float[]{a[0] * val, a[1] * val, a[2] * val, a[3] * val};
  };
  
  
  public static float mag(float[] a){
    return (float) Math.sqrt(a[0]*a[0] + a[1]*a[1] + a[2]*a[2] + a[3]*a[3]);
  }
  
  public static float[] normalize_new(float[] a){
    return multiply_new(a, 1.0f/mag(a));
  }
  public static void normalize_ref_slf(float[] a){
    multiply_ref_slf(a, 1.0f/mag(a));
  }
  
  
  
  public static void sum_ref(float[] a, float[] b, float[] c, float[] dst){
    dst[0] = a[0]+b[0]+c[0];
    dst[1] = a[1]+b[1]+c[1];
    dst[2] = a[2]+b[2]+c[2];
    dst[3] = a[3]+b[3]+c[3];
  }
  public static float[] sum_new(float[] a, float[] b, float[] c){
    return  new float[]{a[0]+b[0]+c[0],  a[1]+b[1]+c[1], a[2]+b[2]+c[2], a[3]+b[3]+c[3]};
  }


  public static void sumlist_ref(float[][] arr, float[] dst){
    float len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
      dst[2] += arr[i][2];
      dst[3] += arr[i][3];
    }
  }

  public static float[] sumlist_new(float[]... arr){
    float[] dst = new float[4];
    float len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
      dst[2] += arr[i][2];
      dst[3] += arr[i][3];
    }
    return dst;
  }
  
  
  public static void negate_ref(float[] a, float[] dst) {
    dst[0] = -a[0];
    dst[1] = -a[1];
    dst[2] = -a[2];
    dst[3] = -a[3];
  };
  public static void negate_ref_slf(float[] a) {
    a[0] = -a[0];
    a[1] = -a[1];
    a[2] = -a[2];
    a[3] = -a[3];
  };
  public static float[] negate_new(float[] a) {
    return new float[]{-a[0], -a[1], -a[2], -a[3]};
  };
  
  
  
  
  
  
  

  
  public static String toStr(float[] a, int prec) {
    return String.format(Locale.ENGLISH, "[%+3.3f, %+3.3f, %+3.3f, %+3.3f]", a[0], a[1], a[2], a[3]);
  };

  public static void print(float[] a, int prec) {
    System.out.println(toStr(a, prec));
  }
  

  
}
