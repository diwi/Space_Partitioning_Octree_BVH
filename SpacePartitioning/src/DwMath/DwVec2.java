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

public class DwVec2 {


  public static float[] nullvector() {
    return new float[]{0, 0};
  }

  public static void copy_ref(float[]a, float[]dst) {
    dst[0] = a[0];
    dst[1] = a[1];
  };

  public static float[] copy_new(float[]a) {
    return new float[]{a[0], a[1]};
  };


  public static boolean equals(float[]a, float[]b) {
    return (a[0] == b[0] && a[1] == b[1] );
  };


  public static void add_ref(float[]a, float[]b, float[]dst) {
    dst[0] = a[0]+b[0];  dst[1] = a[1]+b[1];
  };
  public static float[] add_new(float[]a, float[]b) {
    return new float[]{(a[0]+b[0]),  (a[1]+b[1])};
  };


  public static void sub_ref(float[]a, float[]b, float[]dst) {
    dst[0] = a[0]-b[0];   dst[1] = a[1]-b[1];
  };
  public static float[] sub_new(float[]a, float[]b) {
    return new float[]{(a[0]-b[0]),  (a[1]-b[1])};
  };


  public static void line_midpoint_ref(float[]a, float[]b, float[]dst) {
    dst[0] = (a[0] + b[0]) * 0.5f;
    dst[1] = (a[1] + b[1]) * 0.5f;
  };
  public static float[] line_midpoint_new(float[]a, float[]b) {
    return new float[]{(a[0]+b[0])*0.5f, (a[1]+b[1])*0.5f};
  };

  public static void triangle_midpoint_ref(float[]a, float[]b, float[]c, float[]dst) {
    float f = 1/3f;
    dst[0] = (a[0]+b[0]+c[0]) * f;
    dst[1] = (a[1]+b[1]+c[1]) * f;
  };
  public static float[] triangle_midpoint_new(float[]a, float[]b, float[]c) {
    float f = 1/3;
    return new float[]{(a[0]+b[0]+c[0]) * f, (a[1]+b[1]+c[1]) * f};
  };


  public static void sum_ref(float[]a, float[]b, float[]c, float[]dst){
    dst[0] = a[0]+b[0]+c[0];
    dst[1] = a[1]+b[1]+c[1];
  }
  public static float[] sum_new(float[]a, float[]b, float[]c){
    return new float[]{a[0]+b[0]+c[0],  a[1]+b[1]+c[1]};
  }


  public static void sumlist_ref(float[][] arr, float[]dst){
    float len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
    }
  }

  public static float[] sumlist_new(float[][] arr){
    float[] dst = new float[3];
    int len = arr.length;
    for(int i = 0; i < len; i++){
      dst[0] += arr[i][0];
      dst[1] += arr[i][1];
    }
    return dst;
  }






  public static void multiply_ref(float[]a, float[]b, float[]dst) {
    dst[0] = a[0] * b[0];
    dst[1] = a[1] * b[1];
  };
  public static float[] multiply_new(float[]a, float[]b) {
    return new float[]{a[0]*b[0], a[1]*b[1]};
  };



  public static void negate_ref(float[]a, float[]dst) {
    dst[0] = -a[0];
    dst[1] = -a[1];
  };
  public static void negate_ref_slf(float[]a) {
    a[0] = -a[0];
    a[1] = -a[1];
  };
  public static float[] negate_new(float[]a) {
    return new float[]{-a[0], -a[1]};
  };



  public static void scale_ref(float[]a, float val, float[]dst) {
    dst[0] = a[0] * val;
    dst[1] = a[1] * val;
  };
  public static void scale_ref_slf(float[]a, float val) {
    a[0] *= val;
    a[1] *= val;
  };
  public static float[] scale_new(float[]a, float val) {
    return new float[]{a[0] * val, a[1] * val};
  };



  public static void normalize_ref_slf(float[]a) {
    float x = a[0], y = a[1];
    float len = (float) Math.sqrt(x*x + y*y);

    if (len != 1) {
      len = 1 / len;
      a[0] *= len;
      a[1] *= len;
    }
  };
  public static void normalize_ref(float[]a, float[]dst) {
    float x = a[0], y = a[1];
    float len = (float) Math.sqrt(x*x + y*y);

    if (len == 0) {
      dst[0] = 0;
      dst[1] = 0;
    } else if (len == 1) {
      dst[0] = x;
      dst[1] = y;
    } else {
      len = 1 / len;
      dst[0] = x * len;
      dst[1] = y * len;
    }
  };
  public static float[] normalize_new(float[]a) {
    float x = a[0], y = a[1];
    float len = (float) Math.sqrt(x*x + y*y);

    if (len == 0) {
      return new float[3];
    } else if (len == 1) {
      return new float[]{x, y};
    } else {
      return new float[]{x*len, y*len};
    }
  };





  public static float dot(float[]a, float[]b) {
    return a[0]*b[0] + a[1]*b[1];
  };



  public static float angleBetween(float[]a, float[]b){
    return  (float) Math.acos( DwVec2.dot(a,b)/(DwVec2.mag(a)*DwVec2.mag(b)) );
  }
  public static float angleBetween_unit(float[]a, float[]b){
    return  (float) Math.acos( DwVec2.dot(a,b) );
  }



  public static float mag(float[]a) {
    float x = a[0], y = a[1];
    return (float) Math.sqrt(x*x + y*y);
  };
  public static float mag_sq(float[]a) {
    float x = a[0], y = a[1];
    return x*x + y*y;
  };







  public static void dir_unit_ref(float[]a, float[]b, float[]dst) {
    DwVec2.sub_ref(a, b, dst);
    DwVec2.normalize_ref_slf(dst);
  };

  public static float[] dir_unit_new(float[]a, float[]b) {
    float[] dst = new float[3];
    DwVec2.sub_ref(a, b, dst);
    DwVec2.normalize_ref_slf(dst);
    return dst;
  };



  public static void lerp_ref(float[]a, float[]b, float val, float[]dst) {
    dst[0] = a[0] + val * (b[0] - a[0]);
    dst[1] = a[1] + val * (b[1] - a[1]);
  };

  public static float[] lerp_new(float[]a, float[]b, float val) {
    return new float[]{a[0]+val*(b[0]-a[0]), a[1]+val*(b[1]-a[1])};
  };


  public static float dist(float[]a, float[]b) {
    float[] dst = DwVec2.sub_new(a, b);
    return DwVec2.mag(dst);
  };



  public static String toStr(float[] a, int prec) {
    return String.format(Locale.ENGLISH, "[%+3.3f, %+3.3f]", a[0], a[1]);
  };

  public static void print(float[] a, int prec) {
    System.out.println(toStr(a, prec));
  }

}
