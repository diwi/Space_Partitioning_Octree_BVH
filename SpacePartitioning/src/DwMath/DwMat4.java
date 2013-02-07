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



public class DwMat4 {
  /*
   0 4  8 12
   1 5  9 13
   2 6 10 14
   3 7 11 15
  */
  
  
  

  
  public static float[] nullmatrix(){
    return new float[]{0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0 };
  }


  public static void copy_ref(float[] m4, float[] dst){
    dst[0] = m4[0]; dst[4] = m4[4]; dst[ 8] = m4[ 8]; dst[12] = m4[12];
    dst[1] = m4[1]; dst[5] = m4[5]; dst[ 9] = m4[ 9]; dst[13] = m4[13];
    dst[2] = m4[2]; dst[6] = m4[6]; dst[10] = m4[10]; dst[14] = m4[14];
    dst[3] = m4[3]; dst[7] = m4[7]; dst[11] = m4[11]; dst[15] = m4[15];
  };
  public static float[] copy_ref(float[] m4){
    return new float[]{m4[0], m4[1], m4[2], m4[3], m4[4], m4[5], m4[6], m4[7], m4[8], m4[9], m4[10], m4[11], m4[12], m4[13], m4[14], m4[15]};
  };




  public static void identity_ref(float[] dst){
    dst[0] = 1; dst[4] = 0; dst[ 8] = 0; dst[12] = 0;
    dst[1] = 0; dst[5] = 1; dst[ 9] = 0; dst[13] = 0;
    dst[2] = 0; dst[6] = 0; dst[10] = 1; dst[14] = 0;
    dst[3] = 0; dst[7] = 0; dst[11] = 0; dst[15] = 1;
  };
  public static float[] identity_new() {
    return new float[]{1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1 };
  };




  public static void transpose_ref(float[] m4, float[] dst){
    dst[ 0] = m4[ 0];  dst[ 4] = m4[ 1];  dst[ 8] = m4[ 2];  dst[12] = m4[ 3];
    dst[ 1] = m4[ 4];  dst[ 5] = m4[ 5];  dst[ 9] = m4[ 6];  dst[13] = m4[ 7];
    dst[ 2] = m4[ 8];  dst[ 6] = m4[ 9];  dst[10] = m4[10];  dst[14] = m4[11];
    dst[ 3] = m4[12];  dst[ 7] = m4[13];  dst[11] = m4[14];  dst[15] = m4[15];
  };
  public static float[] transpose_new (float[] m4) {
    return new float[]{m4[0], m4[4], m4[8], m4[12],    m4[1], m4[5], m4[9], m4[13],    m4[2], m4[6], m4[10], m4[14],  m4[3], m4[7], m4[11], m4[15]    };
  };
  public static void transpose_ref_slf(float[] dst) {
    float a01 = dst[1],
          a02 = dst[2],
          a03 = dst[3],
          a12 = dst[6],
          a13 = dst[7],
          a23 = dst[11];

    dst[ 1] = dst[ 4];
    dst[ 2] = dst[ 8];
    dst[ 3] = dst[12];
    dst[ 4] = a01;
    dst[ 6] = dst[ 9];
    dst[ 7] = dst[13];
    dst[ 8] = a02;
    dst[ 9] = a12;
    dst[11] = dst[14];
    dst[12] = a03;
    dst[13] = a13;
    dst[14] = a23;
  };




  public static void toMat3_ref (float[] m4, float[] dst_m3) {
    dst_m3[0] = m4[ 0];  dst_m3[3] = m4[ 4];  dst_m3[6] = m4[ 8];
    dst_m3[1] = m4[ 1];  dst_m3[4] = m4[ 5];  dst_m3[7] = m4[ 9];
    dst_m3[2] = m4[ 2];  dst_m3[5] = m4[ 6];  dst_m3[8] = m4[10];

  };
  public static float[] toMat3_new(float[] m4) {
    return new float[]{m4[0], m4[1], m4[2],     m4[4], m4[5], m4[6],    m4[8], m4[9], m4[10] };
  };







  public static float[] getColumnVec4_new(float[] m4, int idx){
    idx *= 4;
    return new float[]{ m4[idx+0], m4[idx+1], m4[idx+2], m4[idx+3]};
  }
  public static void getColumnVec4_ref(float[] m4, int idx, float[] v4){
    idx *= 4;
    v4[0] =  m4[idx+0];
    v4[1] =  m4[idx+1];
    v4[2] =  m4[idx+2];
    v4[3] =  m4[idx+3];
  }
  public static float[] getColumnVec3_new(float[] m4, int idx){
    idx *= 4;
    return new float[]{ m4[idx+0], m4[idx+1], m4[idx+2]};
  }
  public static void getColumnVec3_ref(float[] m4, int idx, float[] v3){
    idx *= 4;
    v3[0] =  m4[idx+0];
    v3[1] =  m4[idx+1];
    v3[2] =  m4[idx+2];
  }
  public static float[] getRowVec4_new(float[] m4, int idx){
    return new float[]{ m4[idx+0], m4[idx+4], m4[idx+8], m4[idx+12]};
  }
  public static void getRowVec4_ref(float[] m4, int idx, float[] v4){
    v4[0] =  m4[idx+ 0];
    v4[1] =  m4[idx+ 4];
    v4[2] =  m4[idx+ 8];
    v4[3] =  m4[idx+12];
  }
  public static float[] getRowVec3_new(float[] m4, int idx){
    return new float[]{ m4[idx+0], m4[idx+4], m4[idx+8]};
  }
  public static void getRowVec3_ref(float[] m4, int idx, float[] v3){
    v3[0] =  m4[idx+ 0];
    v3[1] =  m4[idx+ 4];
    v3[2] =  m4[idx+ 8];
  }




  public static void getAxisX_ref(float[] m4, float[] v3_x) {
    v3_x[0] = m4[0];
    v3_x[1] = m4[1];
    v3_x[2] = m4[2];
  };
  public static float[] getAxisX_new(float[] m4) {
    return new float[]{m4[0], m4[1], m4[2] };
  };


  public static void getAxisY_ref(float[] m4, float[] v3_y) {
    v3_y[0] = m4[4];
    v3_y[1] = m4[5];
    v3_y[2] = m4[6];
  };
  public static float[] getAxisY_new(float[] m4) {
    return new float[]{ m4[4], m4[5], m4[6] };
  };


  public static void getAxisZ_ref (float[] m4, float[] v3_z) {
    v3_z[0] = m4[ 8];
    v3_z[1] = m4[ 9];
    v3_z[2] = m4[10];
  };
  public static float[] getAxisZ_new(float[] m4) {
    return new float[]{ m4[8], m4[9], m4[10] };
  };


  public static void getAxisXYZ_ref(float[] m4, float[] v3_x, float[] v3_y, float[] v3_z) {
    v3_x[0] = m4[0];  v3_y[0] = m4[4];  v3_z[0] = m4[ 8];
    v3_x[1] = m4[1];  v3_y[1] = m4[5];  v3_z[1] = m4[ 9];
    v3_x[2] = m4[2];  v3_y[2] = m4[6];  v3_z[2] = m4[10];
  };


  public static void setAxisX(float[] m4, float[] v3_x) {
    m4[0] = v3_x[0];
    m4[1] = v3_x[1];
    m4[2] = v3_x[2];
  };

  public static void setAxisY(float[] m4, float[] v3_y) {
    m4[4] = v3_y[0];
    m4[5] = v3_y[1];
    m4[6] = v3_y[2];
  };
  public static void setAxisZ(float[] m4, float[] v3_z) {
    m4[ 8] = v3_z[0];
    m4[ 9] = v3_z[1];
    m4[10] = v3_z[2];
  };
  public static void setAxisXYZ_ref(float[] m4, float[] v3_x, float[] v3_y, float[] v3_z) {
    m4[0] = v3_x[0];  m4[4] = v3_y[0];  m4[ 8] = v3_z[0];
    m4[1] = v3_x[1];  m4[5] = v3_y[1];  m4[ 9] = v3_z[1];
    m4[2] = v3_x[2];  m4[6] = v3_y[2];  m4[10] = v3_z[2];
  };

  public static void setTranslation(float[] m4, float[] v3_t) {
    m4[12] = v3_t[0];
    m4[13] = v3_t[1];
    m4[14] = v3_t[2];
  };




  public static float[] determinant(float[] m4) {
    float
      a00 = m4[ 0], a10 = m4[ 4], a20 = m4[ 8], a30 = m4[12],
      a01 = m4[ 1], a11 = m4[ 5], a21 = m4[ 9], a31 = m4[13],
      a02 = m4[ 2], a12 = m4[ 6], a22 = m4[10], a32 = m4[14],
      a03 = m4[ 3], a13 = m4[ 7], a23 = m4[11], a33 = m4[15];

    return new float[]{
      a30 * a21 * a12 * a03 - a20 * a31 * a12 * a03 - a30 * a11 * a22 * a03 + a10 * a31 * a22 * a03 +
      a20 * a11 * a32 * a03 - a10 * a21 * a32 * a03 - a30 * a21 * a02 * a13 + a20 * a31 * a02 * a13 +
      a30 * a01 * a22 * a13 - a00 * a31 * a22 * a13 - a20 * a01 * a32 * a13 + a00 * a21 * a32 * a13 +
      a30 * a11 * a02 * a23 - a10 * a31 * a02 * a23 - a30 * a01 * a12 * a23 + a00 * a31 * a12 * a23 +
      a10 * a01 * a32 * a23 - a00 * a11 * a32 * a23 - a20 * a11 * a02 * a33 + a10 * a21 * a02 * a33 +
      a20 * a01 * a12 * a33 - a00 * a21 * a12 * a33 - a10 * a01 * a22 * a33 + a00 * a11 * a22 * a33};
  };


  public static boolean inverse_ref(float[] m4, float[] dst) {
    float
      a00 = m4[ 0], a10 = m4[ 4], a20 = m4[ 8], a30 = m4[12],
      a01 = m4[ 1], a11 = m4[ 5], a21 = m4[ 9], a31 = m4[13],
      a02 = m4[ 2], a12 = m4[ 6], a22 = m4[10], a32 = m4[14],
      a03 = m4[ 3], a13 = m4[ 7], a23 = m4[11], a33 = m4[15];

    float
      b00 = a00 * a11 - a01 * a10,
      b01 = a00 * a12 - a02 * a10,
      b02 = a00 * a13 - a03 * a10,
      b03 = a01 * a12 - a02 * a11,
      b04 = a01 * a13 - a03 * a11,
      b05 = a02 * a13 - a03 * a12,
      b06 = a20 * a31 - a21 * a30,
      b07 = a20 * a32 - a22 * a30,
      b08 = a20 * a33 - a23 * a30,
      b09 = a21 * a32 - a22 * a31,
      b10 = a21 * a33 - a23 * a31,
      b11 = a22 * a33 - a23 * a32;

    float det = (b00 * b11 - b01 * b10 + b02 * b09 + b03 * b08 - b04 * b07 + b05 * b06);

    if (det == 0) //TODO
      return false;
    float det_inv = 1f / det;

    dst[ 0] = det_inv * ( a11 * b11 - a12 * b10 + a13 * b09);
    dst[ 1] = det_inv * (-a01 * b11 + a02 * b10 - a03 * b09);
    dst[ 2] = det_inv * ( a31 * b05 - a32 * b04 + a33 * b03);
    dst[ 3] = det_inv * (-a21 * b05 + a22 * b04 - a23 * b03);
    dst[ 4] = det_inv * (-a10 * b11 + a12 * b08 - a13 * b07);
    dst[ 5] = det_inv * ( a00 * b11 - a02 * b08 + a03 * b07);
    dst[ 6] = det_inv * (-a30 * b05 + a32 * b02 - a33 * b01);
    dst[ 7] = det_inv * ( a20 * b05 - a22 * b02 + a23 * b01);
    dst[ 8] = det_inv * ( a10 * b10 - a11 * b08 + a13 * b06);
    dst[ 9] = det_inv * (-a00 * b10 + a01 * b08 - a03 * b06);
    dst[10] = det_inv * ( a30 * b04 - a31 * b02 + a33 * b00);
    dst[11] = det_inv * (-a20 * b04 + a21 * b02 - a23 * b00);
    dst[12] = det_inv * (-a10 * b09 + a11 * b07 - a12 * b06);
    dst[13] = det_inv * ( a00 * b09 - a01 * b07 + a02 * b06);
    dst[14] = det_inv * (-a30 * b03 + a31 * b01 - a32 * b00);
    dst[15] = det_inv * ( a20 * b03 - a21 * b01 + a22 * b00);
    return true;
  };


  public static float[] inverse_new(float[] m4) {
    float[] dst = new float[16];
    DwMat4.inverse_ref(m4, dst);
    return dst;
  };
  
  public static void inverse_ref_self(float[] m4){
    DwMat4.inverse_ref(m4, m4);
  }




  public static boolean toMat3inverse_ref(float[] m4, float[] dst_m3) {
    float
      a00 = m4[0], a10 = m4[4], a20 = m4[ 8],
      a01 = m4[1], a11 = m4[5], a21 = m4[ 9],
      a02 = m4[2], a12 = m4[6], a22 = m4[10];
    float
      b01 =  a22 * a11 - a12 * a21,
      b11 = -a22 * a10 + a12 * a20,
      b21 =  a21 * a10 - a11 * a20;

    float det = a00 * b01 + a01 * b11 + a02 * b21;

    if (det == 0)//TODO
      return false;
    float det_inv = 1f / det;

    dst_m3[0] = det_inv *  ( b01);
    dst_m3[1] = det_inv *  (-a22 * a01 + a02 * a21);
    dst_m3[2] = det_inv *  ( a12 * a01 - a02 * a11);

    dst_m3[3] = det_inv *  ( b11);
    dst_m3[4] = det_inv *  ( a22 * a00 - a02 * a20);
    dst_m3[5] = det_inv *  (-a12 * a00 + a02 * a10);

    dst_m3[6] = det_inv *  ( b21) ;
    dst_m3[7] = det_inv *  (-a21 * a00 + a01 * a20);
    dst_m3[8] = det_inv *  ( a11 * a00 - a01 * a10);
    return true;
  };


  public static float[] toMat3inverse_new (float[] m4) {
    float[] dst_m3 = new float[9];
    DwMat4.toMat3inverse_ref(m4, dst_m3);
    return dst_m3;
  };


  public static boolean toMat3inverseTranspose_ref(float[] m4, float[] dst_m3) {
    float
      a00 = m4[0], a10 = m4[4], a20 = m4[8],
      a01 = m4[1], a11 = m4[5], a21 = m4[9],
      a02 = m4[2], a12 = m4[6], a22 = m4[10];
    float
      b01 =  a22 * a11 - a12 * a21,
      b11 = -a22 * a10 + a12 * a20,
      b21 =  a21 * a10 - a11 * a20;

    float det = a00 * b01 + a01 * b11 + a02 * b21;

    if (det == 0)//TODO
      return false;
    float det_inv = 1f / det;

    dst_m3[0] = det_inv *  ( b01);
    dst_m3[3] = det_inv *  (-a22 * a01 + a02 * a21);
    dst_m3[6] = det_inv *  ( a12 * a01 - a02 * a11);

    dst_m3[1] = det_inv *  ( b11);
    dst_m3[4] = det_inv *  ( a22 * a00 - a02 * a20);
    dst_m3[7] = det_inv *  (-a12 * a00 + a02 * a10);

    dst_m3[2] = det_inv *  ( b21) ;
    dst_m3[5] = det_inv *  (-a21 * a00 + a01 * a20);
    dst_m3[8] = det_inv *  ( a11 * a00 - a01 * a10);
    return false;
  };


  public static float[] toMat3inverseTranspose_new(float[] m4) {
    float[] dst_m3 = new float[9];
    DwMat4.toMat3inverseTranspose_ref(m4, dst_m3);
    return dst_m3;
  };









  public static void mult_ref(float[] m4_A, float[] m4_B, float[] dst_m4) {
    float
      a00 = m4_A[ 0], a10 = m4_A[ 4], a20 = m4_A[ 8], a30 = m4_A[12],
      a01 = m4_A[ 1], a11 = m4_A[ 5], a21 = m4_A[ 9], a31 = m4_A[13],
      a02 = m4_A[ 2], a12 = m4_A[ 6], a22 = m4_A[10], a32 = m4_A[14],
      a03 = m4_A[ 3], a13 = m4_A[ 7], a23 = m4_A[11], a33 = m4_A[15];
    float
      b00 = m4_B[ 0], b10 = m4_B[ 4], b20 = m4_B[ 8], b30 = m4_B[12],
      b01 = m4_B[ 1], b11 = m4_B[ 5], b21 = m4_B[ 9], b31 = m4_B[13],
      b02 = m4_B[ 2], b12 = m4_B[ 6], b22 = m4_B[10], b32 = m4_B[14],
      b03 = m4_B[ 3], b13 = m4_B[ 7], b23 = m4_B[11], b33 = m4_B[15];

    dst_m4[ 0] = b00 * a00 + b01 * a10 + b02 * a20 + b03 * a30;
    dst_m4[ 1] = b00 * a01 + b01 * a11 + b02 * a21 + b03 * a31;
    dst_m4[ 2] = b00 * a02 + b01 * a12 + b02 * a22 + b03 * a32;
    dst_m4[ 3] = b00 * a03 + b01 * a13 + b02 * a23 + b03 * a33;
    dst_m4[ 4] = b10 * a00 + b11 * a10 + b12 * a20 + b13 * a30;
    dst_m4[ 5] = b10 * a01 + b11 * a11 + b12 * a21 + b13 * a31;
    dst_m4[ 6] = b10 * a02 + b11 * a12 + b12 * a22 + b13 * a32;
    dst_m4[ 7] = b10 * a03 + b11 * a13 + b12 * a23 + b13 * a33;
    dst_m4[ 8] = b20 * a00 + b21 * a10 + b22 * a20 + b23 * a30;
    dst_m4[ 9] = b20 * a01 + b21 * a11 + b22 * a21 + b23 * a31;
    dst_m4[10] = b20 * a02 + b21 * a12 + b22 * a22 + b23 * a32;
    dst_m4[11] = b20 * a03 + b21 * a13 + b22 * a23 + b23 * a33;
    dst_m4[12] = b30 * a00 + b31 * a10 + b32 * a20 + b33 * a30;
    dst_m4[13] = b30 * a01 + b31 * a11 + b32 * a21 + b33 * a31;
    dst_m4[14] = b30 * a02 + b31 * a12 + b32 * a22 + b33 * a32;
    dst_m4[15] = b30 * a03 + b31 * a13 + b32 * a23 + b33 * a33;
  };

  public static float[] mult_new(float[] m4_A, float[] m4_B) {
    float[] dst = new float[16];
    float
      a00 = m4_A[ 0], a10 = m4_A[ 4], a20 = m4_A[ 8], a30 = m4_A[12],
      a01 = m4_A[ 1], a11 = m4_A[ 5], a21 = m4_A[ 9], a31 = m4_A[13],
      a02 = m4_A[ 2], a12 = m4_A[ 6], a22 = m4_A[10], a32 = m4_A[14],
      a03 = m4_A[ 3], a13 = m4_A[ 7], a23 = m4_A[11], a33 = m4_A[15];
    float
      b00 = m4_B[ 0], b10 = m4_B[ 4], b20 = m4_B[ 8], b30 = m4_B[12],
      b01 = m4_B[ 1], b11 = m4_B[ 5], b21 = m4_B[ 9], b31 = m4_B[13],
      b02 = m4_B[ 2], b12 = m4_B[ 6], b22 = m4_B[10], b32 = m4_B[14],
      b03 = m4_B[ 3], b13 = m4_B[ 7], b23 = m4_B[11], b33 = m4_B[15];

    dst[ 0] = b00 * a00 + b01 * a10 + b02 * a20 + b03 * a30;
    dst[ 1] = b00 * a01 + b01 * a11 + b02 * a21 + b03 * a31;
    dst[ 2] = b00 * a02 + b01 * a12 + b02 * a22 + b03 * a32;
    dst[ 3] = b00 * a03 + b01 * a13 + b02 * a23 + b03 * a33;
    dst[ 4] = b10 * a00 + b11 * a10 + b12 * a20 + b13 * a30;
    dst[ 5] = b10 * a01 + b11 * a11 + b12 * a21 + b13 * a31;
    dst[ 6] = b10 * a02 + b11 * a12 + b12 * a22 + b13 * a32;
    dst[ 7] = b10 * a03 + b11 * a13 + b12 * a23 + b13 * a33;
    dst[ 8] = b20 * a00 + b21 * a10 + b22 * a20 + b23 * a30;
    dst[ 9] = b20 * a01 + b21 * a11 + b22 * a21 + b23 * a31;
    dst[10] = b20 * a02 + b21 * a12 + b22 * a22 + b23 * a32;
    dst[11] = b20 * a03 + b21 * a13 + b22 * a23 + b23 * a33;
    dst[12] = b30 * a00 + b31 * a10 + b32 * a20 + b33 * a30;
    dst[13] = b30 * a01 + b31 * a11 + b32 * a21 + b33 * a31;
    dst[14] = b30 * a02 + b31 * a12 + b32 * a22 + b33 * a32;
    dst[15] = b30 * a03 + b31 * a13 + b32 * a23 + b33 * a33;
    return dst;
  };



  public static void multVec3_ref (float[] m4,  float[]v3, float[] dst_v3) {
    float x = v3[0], y = v3[1], z = v3[2];

    dst_v3[0] = m4[0]*x + m4[4]*y + m4[ 8]*z + m4[12];
    dst_v3[1] = m4[1]*x + m4[5]*y + m4[ 9]*z + m4[13];
    dst_v3[2] = m4[2]*x + m4[6]*y + m4[10]*z + m4[14];
  };

  public static float[] multVec3_new(float[] m4, float[] v3) {
    float[] dst_v3 = new  float[3];
    float x = v3[0], y = v3[1], z = v3[2];

    dst_v3[0] = m4[0]*x + m4[4]*y + m4[ 8]*z + m4[12];
    dst_v3[1] = m4[1]*x + m4[5]*y + m4[ 9]*z + m4[13];
    dst_v3[2] = m4[2]*x + m4[6]*y + m4[10]*z + m4[14];
    return dst_v3;
  };



  public static void multVec4_ref(float[] m4, float[] v4, float[] dst_v4) {
    float x = v4[0], y = v4[1], z = v4[2], w = v4[3];

    dst_v4[0] = m4[0]*x + m4[4]*y + m4[ 8]*z + m4[12]*w;
    dst_v4[1] = m4[1]*x + m4[5]*y + m4[ 9]*z + m4[13]*w;
    dst_v4[2] = m4[2]*x + m4[6]*y + m4[10]*z + m4[14]*w;
    dst_v4[3] = m4[3]*x + m4[7]*y + m4[11]*z + m4[15]*w;
  };
  public static void multVec4_ref_slf(float[] m4, float[] v4) {
    float x = v4[0], y = v4[1], z = v4[2], w = v4[3];

    v4[0] = m4[0]*x + m4[4]*y + m4[ 8]*z + m4[12]*w;
    v4[1] = m4[1]*x + m4[5]*y + m4[ 9]*z + m4[13]*w;
    v4[2] = m4[2]*x + m4[6]*y + m4[10]*z + m4[14]*w;
    v4[3] = m4[3]*x + m4[7]*y + m4[11]*z + m4[15]*w;
  };


  public static float[] multVec4_new(float[] m4, float[] v4) {
    float[] dst = new float[4];
    float x = v4[0], y = v4[1], z = v4[2], w = v4[3];

    dst[0] = m4[0]*x + m4[4]*y + m4[ 8]*z + m4[12]*w;
    dst[1] = m4[1]*x + m4[5]*y + m4[ 9]*z + m4[13]*w;
    dst[2] = m4[2]*x + m4[6]*y + m4[10]*z + m4[14]*w;
    dst[3] = m4[3]*x + m4[7]*y + m4[11]*z + m4[15]*w;
    return dst;
  };



  public static void translate_ref(float[] m4, float[] v3, float[] dst_m4) {
    float x = v3[0], y = v3[1], z = v3[2];
    float
      a00 = m4[0], a01 = m4[1], a02 = m4[ 2], a03 = m4[ 3],
      a10 = m4[4], a11 = m4[5], a12 = m4[ 6], a13 = m4[ 7],
      a20 = m4[8], a21 = m4[9], a22 = m4[10], a23 = m4[11];

    dst_m4[0] = a00;  dst_m4[4] = a10;  dst_m4[ 8] = a20;  dst_m4[12] = a00 * x + a10 * y + a20 * z + m4[12];
    dst_m4[1] = a01;  dst_m4[5] = a11;  dst_m4[ 9] = a21;  dst_m4[13] = a01 * x + a11 * y + a21 * z + m4[13];
    dst_m4[2] = a02;  dst_m4[6] = a12;  dst_m4[10] = a22;  dst_m4[14] = a02 * x + a12 * y + a22 * z + m4[14];
    dst_m4[3] = a03;  dst_m4[7] = a13;  dst_m4[11] = a23;  dst_m4[15] = a03 * x + a13 * y + a23 * z + m4[15];
  };

  public static void translate_ref_slf(float[] m4, float[] v3) {
    float x = v3[0], y = v3[1], z = v3[2];

    m4[12] = m4[0] * x + m4[4] * y + m4[ 8] * z +m4[12];
    m4[13] = m4[1] * x + m4[5] * y + m4[ 9] * z +m4[13];
    m4[14] = m4[2] * x + m4[6] * y + m4[10] * z +m4[14];
    m4[15] = m4[3] * x + m4[7] * y + m4[11] * z +m4[15];
  };

  public static float[] translate_new(float[] m4, float[] v3) {
    float[] dst_m4 = new float[16];
    float x = v3[0], y = v3[1], z = v3[2];
    float
      a00 = m4[0], a01 = m4[1], a02 = m4[ 2], a03 = m4[ 3],
      a10 = m4[4], a11 = m4[5], a12 = m4[ 6], a13 = m4[ 7],
      a20 = m4[8], a21 = m4[9], a22 = m4[10], a23 = m4[11];

    dst_m4[0] = a00;  dst_m4[4] = a10;  dst_m4[ 8] = a20;  dst_m4[12] = a00 * x + a10 * y + a20 * z + m4[12];
    dst_m4[1] = a01;  dst_m4[5] = a11;  dst_m4[ 9] = a21;  dst_m4[13] = a01 * x + a11 * y + a21 * z + m4[13];
    dst_m4[2] = a02;  dst_m4[6] = a12;  dst_m4[10] = a22;  dst_m4[14] = a02 * x + a12 * y + a22 * z + m4[14];
    dst_m4[3] = a03;  dst_m4[7] = a13;  dst_m4[11] = a23;  dst_m4[15] = a03 * x + a13 * y + a23 * z + m4[15];
    return dst_m4;
  };




  public static void scale_ref(float[] m4, float[] v3, float[] dst_m4) {
    float x = v3[0], y = v3[1], z = v3[2];

    dst_m4[ 0] = m4[ 0] * x;  dst_m4[ 4] = m4[ 4] * y;  dst_m4[ 8] = m4[ 8] * z;  dst_m4[12] = m4[12];
    dst_m4[ 1] = m4[ 1] * x;  dst_m4[ 5] = m4[ 5] * y;  dst_m4[ 9] = m4[ 9] * z;  dst_m4[13] = m4[13];
    dst_m4[ 2] = m4[ 2] * x;  dst_m4[ 6] = m4[ 6] * y;  dst_m4[10] = m4[10] * z;  dst_m4[14] = m4[14];
    dst_m4[ 3] = m4[ 3] * x;  dst_m4[ 7] = m4[ 7] * y;  dst_m4[11] = m4[11] * z;  dst_m4[15] = m4[15];
  };

  public static void scale_ref_slf(float[]m4, float[]v3) {
    float x = v3[0], y = v3[1], z = v3[2];

    m4[ 0] *= x;  m4[ 4] *= y;  m4[ 8] *= z;
    m4[ 1] *= x;  m4[ 5] *= y;  m4[ 9] *= z;
    m4[ 2] *= x;  m4[ 6] *= y;  m4[10] *= z;
    m4[ 3] *= x;  m4[ 7] *= y;  m4[11] *= z;
  };

  public static float[] scale_new(float[]m4, float[]v3) {
    float[] dst_m4 = new float[16];
    float x = v3[0], y = v3[1], z = v3[2];

    dst_m4[ 0] = m4[ 0] * x;  dst_m4[ 4] = m4[ 4] * y;  dst_m4[ 8] = m4[ 8] * z;  dst_m4[12] = m4[12];
    dst_m4[ 1] = m4[ 1] * x;  dst_m4[ 5] = m4[ 5] * y;  dst_m4[ 9] = m4[ 9] * z;  dst_m4[13] = m4[13];
    dst_m4[ 2] = m4[ 2] * x;  dst_m4[ 6] = m4[ 6] * y;  dst_m4[10] = m4[10] * z;  dst_m4[14] = m4[14];
    dst_m4[ 3] = m4[ 3] * x;  dst_m4[ 7] = m4[ 7] * y;  dst_m4[11] = m4[11] * z;  dst_m4[15] = m4[15];
    return dst_m4;
  };


  /**
   *
   * @param mat
   * @param angle rotation-angle (radians)
   * @param axis  rotation axis (normalized)
   * @param dst
   */
  public static void rotate_ref(float[]m4, float angle_f, float[]axis_v3, float[]dst_m4) {
    float x = axis_v3[0], y = axis_v3[1], z = axis_v3[2];

    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f),
      t = 1 - c;

    float
      a00 = m4[0], a10 = m4[4], a20 = m4[ 8],
      a01 = m4[1], a11 = m4[5], a21 = m4[ 9],
      a02 = m4[2], a12 = m4[6], a22 = m4[10],
      a03 = m4[3], a13 = m4[7], a23 = m4[11];

    // rotation matrix components
    float
      b00 = x*x*t + c,      b10 = x*y*t - z*s,   b20 = x*z*t + y*s,
      b01 = y*x*t + z*s,    b11 = y*y*t + c,     b21 = y*z*t - x*s,
      b02 = z*x*t - y*s,    b12 = z*y*t + x*s,   b22 = z*z*t + c;

    dst_m4[ 0] = a00 * b00 + a10 * b01 + a20 * b02;
    dst_m4[ 1] = a01 * b00 + a11 * b01 + a21 * b02;
    dst_m4[ 2] = a02 * b00 + a12 * b01 + a22 * b02;
    dst_m4[ 3] = a03 * b00 + a13 * b01 + a23 * b02;

    dst_m4[ 4] = a00 * b10 + a10 * b11 + a20 * b12;
    dst_m4[ 5] = a01 * b10 + a11 * b11 + a21 * b12;
    dst_m4[ 6] = a02 * b10 + a12 * b11 + a22 * b12;
    dst_m4[ 7] = a03 * b10 + a13 * b11 + a23 * b12;

    dst_m4[ 8] = a00 * b20 + a10 * b21 + a20 * b22;
    dst_m4[ 9] = a01 * b20 + a11 * b21 + a21 * b22;
    dst_m4[10] = a02 * b20 + a12 * b21 + a22 * b22;
    dst_m4[11] = a03 * b20 + a13 * b21 + a23 * b22;

    dst_m4[12] = m4[12];
    dst_m4[13] = m4[13];
    dst_m4[14] = m4[14];
    dst_m4[15] = m4[15];
  };


  public static void rotate_ref_slf(float[]m4, float angle_f, float[]axis_v3) {
    float x = axis_v3[0], y = axis_v3[1], z = axis_v3[2];

    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f),
      t = 1 - c;

    float
      a00 = m4[0], a10 = m4[4], a20 = m4[ 8],
      a01 = m4[1], a11 = m4[5], a21 = m4[ 9],
      a02 = m4[2], a12 = m4[6], a22 = m4[10],
      a03 = m4[3], a13 = m4[7], a23 = m4[11];

    // rotation matrix components
    float
      b00 = x*x*t + c,      b10 = x*y*t - z*s,   b20 = x*z*t + y*s,
      b01 = y*x*t + z*s,    b11 = y*y*t + c,     b21 = y*z*t - x*s,
      b02 = z*x*t - y*s,    b12 = z*y*t + x*s,   b22 = z*z*t + c;

    m4[ 0] = a00 * b00 + a10 * b01 + a20 * b02;
    m4[ 1] = a01 * b00 + a11 * b01 + a21 * b02;
    m4[ 2] = a02 * b00 + a12 * b01 + a22 * b02;
    m4[ 3] = a03 * b00 + a13 * b01 + a23 * b02;

    m4[ 4] = a00 * b10 + a10 * b11 + a20 * b12;
    m4[ 5] = a01 * b10 + a11 * b11 + a21 * b12;
    m4[ 6] = a02 * b10 + a12 * b11 + a22 * b12;
    m4[ 7] = a03 * b10 + a13 * b11 + a23 * b12;

    m4[ 8] = a00 * b20 + a10 * b21 + a20 * b22;
    m4[ 9] = a01 * b20 + a11 * b21 + a21 * b22;
    m4[10] = a02 * b20 + a12 * b21 + a22 * b22;
    m4[11] = a03 * b20 + a13 * b21 + a23 * b22;
  };


  public static float[] rotate_new(float[]m4, float angle_f, float[]axis_v3) {
    float[] dst_m4 = new float[16];
    float x = axis_v3[0], y = axis_v3[1], z = axis_v3[2];

    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f),
      t = 1 - c;

    float
      a00 = m4[0], a10 = m4[4], a20 = m4[ 8],
      a01 = m4[1], a11 = m4[5], a21 = m4[ 9],
      a02 = m4[2], a12 = m4[6], a22 = m4[10],
      a03 = m4[3], a13 = m4[7], a23 = m4[11];

    // rotation matrix components
    float
      b00 = x*x*t + c,      b10 = x*y*t - z*s,   b20 = x*z*t + y*s,
      b01 = y*x*t + z*s,    b11 = y*y*t + c,     b21 = y*z*t - x*s,
      b02 = z*x*t - y*s,    b12 = z*y*t + x*s,   b22 = z*z*t + c;

    dst_m4[ 0] = a00 * b00 + a10 * b01 + a20 * b02;
    dst_m4[ 1] = a01 * b00 + a11 * b01 + a21 * b02;
    dst_m4[ 2] = a02 * b00 + a12 * b01 + a22 * b02;
    dst_m4[ 3] = a03 * b00 + a13 * b01 + a23 * b02;

    dst_m4[ 4] = a00 * b10 + a10 * b11 + a20 * b12;
    dst_m4[ 5] = a01 * b10 + a11 * b11 + a21 * b12;
    dst_m4[ 6] = a02 * b10 + a12 * b11 + a22 * b12;
    dst_m4[ 7] = a03 * b10 + a13 * b11 + a23 * b12;

    dst_m4[ 8] = a00 * b20 + a10 * b21 + a20 * b22;
    dst_m4[ 9] = a01 * b20 + a11 * b21 + a21 * b22;
    dst_m4[10] = a02 * b20 + a12 * b21 + a22 * b22;
    dst_m4[11] = a03 * b20 + a13 * b21 + a23 * b22;

    dst_m4[12] = m4[12];
    dst_m4[13] = m4[13];
    dst_m4[14] = m4[14];
    dst_m4[15] = m4[15];
    return dst_m4;
  };





  public static void rotateX_ref(float[]m4, float angle_f, float[]dst_m4) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a10 = m4[ 4],  a20 = m4[ 8],
      a11 = m4[ 5],  a21 = m4[ 9],
      a12 = m4[ 6],  a22 = m4[10],
      a13 = m4[ 7],  a23 = m4[11];

    dst_m4[0] = m4[0];  dst_m4[ 4] = a10 * c + a20 * s;   dst_m4[ 8] = a10 * -s + a20 * c;  dst_m4[12] = m4[12];
    dst_m4[1] = m4[1];  dst_m4[ 5] = a11 * c + a21 * s;   dst_m4[ 9] = a11 * -s + a21 * c;  dst_m4[13] = m4[13];
    dst_m4[2] = m4[2];  dst_m4[ 6] = a12 * c + a22 * s;   dst_m4[10] = a12 * -s + a22 * c;  dst_m4[14] = m4[14];
    dst_m4[3] = m4[3];  dst_m4[ 7] = a13 * c + a23 * s;   dst_m4[11] = a13 * -s + a23 * c;  dst_m4[15] = m4[15];
  };


  public static void rotateX_ref_slf(float[]m4, float angle_f) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a10 = m4[ 4],  a20 = m4[ 8],
      a11 = m4[ 5],  a21 = m4[ 9],
      a12 = m4[ 6],  a22 = m4[10],
      a13 = m4[ 7],  a23 = m4[11];

    m4[ 4] = a10 * c + a20 * s;  m4[ 8] = a10 * -s + a20 * c;
    m4[ 5] = a11 * c + a21 * s;  m4[ 9] = a11 * -s + a21 * c;
    m4[ 6] = a12 * c + a22 * s;  m4[10] = a12 * -s + a22 * c;
    m4[ 7] = a13 * c + a23 * s;  m4[11] = a13 * -s + a23 * c;
  };


  public static float[] rotateX_new(float[]m4, float angle_f) {
    float[] dst_m4 = new float[16];
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a10 = m4[ 4],  a20 = m4[ 8],
      a11 = m4[ 5],  a21 = m4[ 9],
      a12 = m4[ 6],  a22 = m4[10],
      a13 = m4[ 7],  a23 = m4[11];

    dst_m4[0] = m4[0];  dst_m4[ 4] = a10 * c + a20 * s;   dst_m4[ 8] = a10 * -s + a20 * c;  dst_m4[12] = m4[12];
    dst_m4[1] = m4[1];  dst_m4[ 5] = a11 * c + a21 * s;   dst_m4[ 9] = a11 * -s + a21 * c;  dst_m4[13] = m4[13];
    dst_m4[2] = m4[2];  dst_m4[ 6] = a12 * c + a22 * s;   dst_m4[10] = a12 * -s + a22 * c;  dst_m4[14] = m4[14];
    dst_m4[3] = m4[3];  dst_m4[ 7] = a13 * c + a23 * s;   dst_m4[11] = a13 * -s + a23 * c;  dst_m4[15] = m4[15];
    return dst_m4;
  };



  public static void rotateY_ref (float[]m4, float angle_f, float[]dst_m4) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a20 = m4[ 8],
      a01 = m4[1],  a21 = m4[ 9],
      a02 = m4[2],  a22 = m4[10],
      a03 = m4[3],  a23 = m4[11];

    dst_m4[ 0] = a00 * c + a20 * -s;  dst_m4[ 4] = m4[ 4];   dst_m4[ 8] = a00 * s + a20 * c;    dst_m4[12] =m4[12];
    dst_m4[ 1] = a01 * c + a21 * -s;  dst_m4[ 5] = m4[ 5];   dst_m4[ 9] = a01 * s + a21 * c;    dst_m4[13] =m4[13];
    dst_m4[ 2] = a02 * c + a22 * -s;  dst_m4[ 6] = m4[ 6];   dst_m4[10] = a02 * s + a22 * c;    dst_m4[14] =m4[14];
    dst_m4[ 3] = a03 * c + a23 * -s;  dst_m4[ 7] = m4[ 7];   dst_m4[11] = a03 * s + a23 * c;    dst_m4[15] =m4[15];
  };


  public static void rotateY_ref_slf(float[]m4, float angle_f) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a20 = m4[ 8],
      a01 = m4[1],  a21 = m4[ 9],
      a02 = m4[2],  a22 = m4[10],
      a03 = m4[3],  a23 = m4[11];

    m4[ 0] = a00 * c + a20 * -s;  m4[ 8] = a00 * s + a20 * c;
    m4[ 1] = a01 * c + a21 * -s;  m4[ 9] = a01 * s + a21 * c;
    m4[ 2] = a02 * c + a22 * -s;  m4[10] = a02 * s + a22 * c;
    m4[ 3] = a03 * c + a23 * -s;  m4[11] = a03 * s + a23 * c;
  };


  public static float[] rotateY_new(float[]m4, float angle_f) {
    float[] dst_m4 = new float[16];
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a20 = m4[ 8],
      a01 = m4[1],  a21 = m4[ 9],
      a02 = m4[2],  a22 = m4[10],
      a03 = m4[3],  a23 = m4[11];

    dst_m4[ 0] = a00 * c + a20 * -s;  dst_m4[ 4] = m4[ 4];   dst_m4[ 8] = a00 * s + a20 * c;  dst_m4[12] = m4[12];
    dst_m4[ 1] = a01 * c + a21 * -s;  dst_m4[ 5] = m4[ 5];   dst_m4[ 9] = a01 * s + a21 * c;  dst_m4[13] = m4[13];
    dst_m4[ 2] = a02 * c + a22 * -s;  dst_m4[ 6] = m4[ 6];   dst_m4[10] = a02 * s + a22 * c;  dst_m4[14] = m4[14];
    dst_m4[ 3] = a03 * c + a23 * -s;  dst_m4[ 7] = m4[ 7];   dst_m4[11] = a03 * s + a23 * c;  dst_m4[15] = m4[15];
    return dst_m4;
  };





  public static void rotateZ_ref (float[] m4, float angle_f, float[] dst_m4) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a10 = m4[4],
      a01 = m4[1],  a11 = m4[5],
      a02 = m4[2],  a12 = m4[6],
      a03 = m4[3],  a13 = m4[7];

    dst_m4[0] = a00 * c + a10 * s;  dst_m4[4] = a00 * -s + a10 * c;   dst_m4[ 8] = m4[ 8];   dst_m4[12] = m4[12];
    dst_m4[1] = a01 * c + a11 * s;  dst_m4[5] = a01 * -s + a11 * c;   dst_m4[ 9] = m4[ 9];   dst_m4[13] = m4[13];
    dst_m4[2] = a02 * c + a12 * s;  dst_m4[6] = a02 * -s + a12 * c;   dst_m4[10] = m4[10];   dst_m4[14] = m4[14];
    dst_m4[3] = a03 * c + a13 * s;  dst_m4[7] = a03 * -s + a13 * c;   dst_m4[11] = m4[11];   dst_m4[15] = m4[15];
  };

  public static void rotateZ_ref_slf(float[] m4, float angle_f) {
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a10 = m4[4],
      a01 = m4[1],  a11 = m4[5],
      a02 = m4[2],  a12 = m4[6],
      a03 = m4[3],  a13 = m4[7];

    m4[0] = a00 * c + a10 * s;  m4[4] = a00 * -s + a10 * c;
    m4[1] = a01 * c + a11 * s;  m4[5] = a01 * -s + a11 * c;
    m4[2] = a02 * c + a12 * s;  m4[6] = a02 * -s + a12 * c;
    m4[3] = a03 * c + a13 * s;  m4[7] = a03 * -s + a13 * c;
  };


  public static float[] rotateZ_new(float[] m4, float angle_f) {
    float[] dst_m4 = new float[16];
    float
      s = (float) Math.sin(angle_f),
      c = (float) Math.cos(angle_f);
    float
      a00 = m4[0],  a10 = m4[4],
      a01 = m4[1],  a11 = m4[5],
      a02 = m4[2],  a12 = m4[6],
      a03 = m4[3],  a13 = m4[7];

    dst_m4[0] = a00 * c + a10 * s;  dst_m4[4] = a00 * -s + a10 * c;   dst_m4[ 8] = m4[ 8];   dst_m4[12] = m4[12];
    dst_m4[1] = a01 * c + a11 * s;  dst_m4[5] = a01 * -s + a11 * c;   dst_m4[ 9] = m4[ 9];   dst_m4[13] = m4[13];
    dst_m4[2] = a02 * c + a12 * s;  dst_m4[6] = a02 * -s + a12 * c;   dst_m4[10] = m4[10];   dst_m4[14] = m4[14];
    dst_m4[3] = a03 * c + a13 * s;  dst_m4[7] = a03 * -s + a13 * c;   dst_m4[11] = m4[11];   dst_m4[15] = m4[15];
    return dst_m4;
  };


  /**
   *
   * @param r      (float) right
   * @param l      (float) left
   * @param b      (float) top
   * @param t      (float) bottom
   * @param n      (float) near
   * @param f      (float) far
   * @param dst_m4 (mat4) output matrix
   */
  public static void frustum_ref(float l, float r, float b, float t, float n, float f, float[] dst_m4) {
    float
      rl = (r - l),
      tb = (t - b),
      nf = (n - f);

    dst_m4[0] = (2*n) / rl;  dst_m4[4] = 0;            dst_m4[ 8] = (r+l) / rl;    dst_m4[12] = 0;
    dst_m4[1] = 0;           dst_m4[5] = (2*n) / tb;   dst_m4[ 9] = (t+b) / tb;    dst_m4[13] = 0;
    dst_m4[2] = 0;           dst_m4[6] = 0;            dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
    dst_m4[3] = 0;           dst_m4[7] = 0;            dst_m4[11] = -1;            dst_m4[15] = 0;
  };


  public static float[] frustum_new(float l, float r, float b, float t, float n, float f) {
    float[] dst_m4 = new float[16];
    float
      rl = (r - l),
      tb = (t - b),
      nf = (n - f);

    dst_m4[0] = (2*n) / rl;  dst_m4[4] = 0;            dst_m4[ 8] = (r+l) / rl;    dst_m4[12] = 0;
    dst_m4[1] = 0;           dst_m4[5] = (2*n) / tb;   dst_m4[ 9] = (t+b) / tb;    dst_m4[13] = 0;
    dst_m4[2] = 0;           dst_m4[6] = 0;            dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
    dst_m4[3] = 0;           dst_m4[7] = 0;            dst_m4[11] = -1;            dst_m4[15] = 0;
    return dst_m4;
  };


  /**
   *
   * @param fovy   (float) field of view, y (degrees)
   * @param aspect (float) ratio (width/height)
   * @param n      (float) near
   * @param f      (float) far
   * @param dst_m4 (mat4) output matrix
   */
  public static void perspective_ref(float fovy, float aspect, float n, float f, float[] dst_m4) {
//  float cot = (float) (1.0f/Math.tan(fovy*Math.PI/360));
    float cot = (float) (Math.tan((180 - fovy)*Math.PI/360f));
    float nf = (n - f);

    dst_m4[0] = cot/aspect;  dst_m4[4] = 0;     dst_m4[ 8] = 0;             dst_m4[12] = 0;
    dst_m4[1] = 0;           dst_m4[5] = cot;   dst_m4[ 9] = 0;             dst_m4[13] = 0;
    dst_m4[2] = 0;           dst_m4[6] = 0;     dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
    dst_m4[3] = 0;           dst_m4[7] = 0;     dst_m4[11] = -1;            dst_m4[15] = 0;



//    var t = n*Math.tan(fovy/2);
//    var r = t*aspect;
  //
//    dst_m4[0] = n/r;  dst_m4[4] = 0;     dst_m4[ 8] = 0;             dst_m4[12] = 0;
//    dst_m4[1] = 0;    dst_m4[5] = n/t;   dst_m4[ 9] = 0;             dst_m4[13] = 0;
//    dst_m4[2] = 0;    dst_m4[6] = 0;     dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
//    dst_m4[3] = 0;    dst_m4[7] = 0;     dst_m4[11] = -1;            dst_m4[15] = 0;
  };

  public static float[] perspective_new(float fovy, float aspect, float n, float f) {
    float[] dst_m4 = new float[16];
//  float cot = (float) (1.0f/Math.tan(fovy*Math.PI/360));
    float cot = (float) (Math.tan((180 - fovy)*Math.PI/360f));
    float nf = (n - f);
    
    

    dst_m4[0] = cot/aspect;  dst_m4[4] = 0;     dst_m4[ 8] = 0;             dst_m4[12] = 0;
    dst_m4[1] = 0;           dst_m4[5] = cot;   dst_m4[ 9] = 0;             dst_m4[13] = 0;
    dst_m4[2] = 0;           dst_m4[6] = 0;     dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
    dst_m4[3] = 0;           dst_m4[7] = 0;     dst_m4[11] = -1;            dst_m4[15] = 0;

//    var t = n*Math.tan(fovy/2);
//    var r = t*aspect;
  //
//    dst_m4[0] = n/r;  dst_m4[4] = 0;     dst_m4[ 8] = 0;             dst_m4[12] = 0;
//    dst_m4[1] = 0;    dst_m4[5] = n/t;   dst_m4[ 9] = 0;             dst_m4[13] = 0;
//    dst_m4[2] = 0;    dst_m4[6] = 0;     dst_m4[10] = (n+f) / nf;    dst_m4[14] = (2*n*f) / nf;
//    dst_m4[3] = 0;    dst_m4[7] = 0;     dst_m4[11] = -1;            dst_m4[15] = 0;

    return dst_m4;
  };


  /**
   *
   * @param r      (float) right
   * @param l      (float) left
   * @param b      (float) top
   * @param t      (float) bottom
   * @param n      (float) near
   * @param f      (float) far
   * @param dst_m4 (mat4) output matrix
   */
  public static void ortho_ref(float l, float r, float b, float t, float n, float f, float[] dst_m4) {
    float
      rl = (r - l),
      tb = (t - b),
      nf = (n - f);
    dst_m4[ 0] = 2 / rl;   dst_m4[ 4] = 0;        dst_m4[ 8] = 0;          dst_m4[12] = (-l - r) / rl;
    dst_m4[ 1] = 0;        dst_m4[ 5] = 2 / tb;   dst_m4[ 9] = 0;          dst_m4[13] = (-t - b) / tb;
    dst_m4[ 2] = 0;        dst_m4[ 6] = 0;        dst_m4[10] = 2 / nf;     dst_m4[14] = ( f + n) / nf;
    dst_m4[ 3] = 0;        dst_m4[ 7] = 0;        dst_m4[11] = 0;          dst_m4[15] = 1;
  };


  public static float[] ortho_new(float l, float r, float b, float t, float n, float f) {
    float[] dst_m4 = new float[16];
    float
      rl = (r - l),
      tb = (t - b),
      nf = (n - f);
    dst_m4[ 0] = 2 / rl;   dst_m4[ 4] = 0;        dst_m4[ 8] = 0;          dst_m4[12] = (-l - r) / rl;
    dst_m4[ 1] = 0;        dst_m4[ 5] = 2 / tb;   dst_m4[ 9] = 0;          dst_m4[13] = (-t - b) / tb;
    dst_m4[ 2] = 0;        dst_m4[ 6] = 0;        dst_m4[10] = 2 / nf;     dst_m4[14] = ( f + n) / nf;
    dst_m4[ 3] = 0;        dst_m4[ 7] = 0;        dst_m4[11] = 0;          dst_m4[15] = 1;
    return dst_m4;
  };




  public static void lookAt_ref (float[]eye, float[]center, float[]up, float[] dst_m4) {
    if(  DwVec3.equals(eye, center) ){
      DwMat4.identity_ref(dst_m4);
      return;
    }

    float[] z = DwVec3.sub_new(eye, center); DwVec3.normalize_ref_slf(z);
    float[] x = DwVec3.cross_new(up, z );    DwVec3.normalize_ref_slf(x);
    float[] y = DwVec3.cross_new(z, x );     DwVec3.normalize_ref_slf(y);

    float[] w = { -(DwVec3.dot(x, eye)),
                  -(DwVec3.dot(y, eye)),
                  -(DwVec3.dot(z, eye)) };

    dst_m4[ 0] = x[0];   dst_m4[ 4] = x[1];   dst_m4[ 8] = x[2];   dst_m4[12] = w[0];
    dst_m4[ 1] = y[0];   dst_m4[ 5] = y[1];   dst_m4[ 9] = y[2];   dst_m4[13] = w[1];
    dst_m4[ 2] = z[0];   dst_m4[ 6] = z[1];   dst_m4[10] = z[2];   dst_m4[14] = w[2];
    dst_m4[ 3] = 0;      dst_m4[ 7] = 0;      dst_m4[11] = 0;      dst_m4[15] = 1;
  };



  public static float[] lookAt_new(float[]eye, float[]center, float[]up) {
    float[] dst_m4 = new float[16];
    if(  DwVec3.equals(eye, center) ){
      DwMat4.identity_ref(dst_m4);
      return dst_m4;
    }

    float[] z = DwVec3.sub_new(eye, center); DwVec3.normalize_ref_slf(z);
    float[] x = DwVec3.cross_new(up, z );    DwVec3.normalize_ref_slf(x);
    float[] y = DwVec3.cross_new(z, x );     DwVec3.normalize_ref_slf(y);

    float[] w = { -(DwVec3.dot(x, eye)),
                  -(DwVec3.dot(y, eye)),
                  -(DwVec3.dot(z, eye)) };

    dst_m4[ 0] = x[0];   dst_m4[ 4] = x[1];   dst_m4[ 8] = x[2];   dst_m4[12] = w[0];
    dst_m4[ 1] = y[0];   dst_m4[ 5] = y[1];   dst_m4[ 9] = y[2];   dst_m4[13] = w[1];
    dst_m4[ 2] = z[0];   dst_m4[ 6] = z[1];   dst_m4[10] = z[2];   dst_m4[14] = w[2];
    dst_m4[ 3] = 0;      dst_m4[ 7] = 0;      dst_m4[11] = 0;      dst_m4[15] = 1;
    return dst_m4;
  };






  public static String toStr(float[] m4, int prec) {
    String line1 = String.format(Locale.ENGLISH, "[%+3.4f, %+3.4f, %+3.4f, %+3.4f]", m4[0], m4[4], m4[ 8], m4[12]);
    String line2 = String.format(Locale.ENGLISH, "[%+3.4f, %+3.4f, %+3.4f, %+3.4f]", m4[1], m4[5], m4[ 9], m4[13]);
    String line3 = String.format(Locale.ENGLISH, "[%+3.4f, %+3.4f, %+3.4f, %+3.4f]", m4[2], m4[6], m4[10], m4[14]);
    String line4 = String.format(Locale.ENGLISH, "[%+3.4f, %+3.4f, %+3.4f, %+3.4f]", m4[3], m4[7], m4[11], m4[15]);
    return String.format(Locale.ENGLISH, "%s\n%s\n%s\n%s\n", line1, line2, line3, line4);
  };
  
  public static void print(float[] m4, int prec) {
    System.out.println(toStr(m4, prec));
  }




//  public static void print( FloatBuffer fb ){
//    String row1 = String.format(Locale.ENGLISH, "%+2.3f  %+2.3f  %+2.3f  %+2.3f", fb.get(0), fb.get(4), fb.get( 8), fb.get(12));
//    String row2 = String.format(Locale.ENGLISH, "%+2.3f  %+2.3f  %+2.3f  %+2.3f", fb.get(1), fb.get(5), fb.get( 9), fb.get(13));
//    String row3 = String.format(Locale.ENGLISH, "%+2.3f  %+2.3f  %+2.3f  %+2.3f", fb.get(2), fb.get(6), fb.get(10), fb.get(14));
//    String row4 = String.format(Locale.ENGLISH, "%+2.3f  %+2.3f  %+2.3f  %+2.3f", fb.get(3), fb.get(7), fb.get(11), fb.get(15));
//    System.out.println(row1);
//    System.out.println(row2);
//    System.out.println(row3);
//    System.out.println(row4);
//  }

}
