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

public class DwSampling {
  
  public static final double PI_TWO = Math.PI*2.0;
  
  public static float[] float3(double x, double y, double z){
    return new float[]{(float)x, (float)y, (float)z};
  }
  public static float[] float3(float x, float y, float z){
    return new float[]{x, y, z};
  }
  
  public static float[] cosineWeightedDirection(float[] normal, double scale, double exp) {
    float[] n = DwVec3.copy_new(normal);
    float[] s = DwVec3.cross_new(n, (Math.abs(n[0])<0.5) ? float3(1,0,0) : float3(0,1,0));
    float[] t = DwVec3.cross_new(n, s);
    float[] h = cosineSampleHemisphere(scale, exp);
    DwVec3.scale_ref_slf(s, h[0]);
    DwVec3.scale_ref_slf(t, h[1]);
    DwVec3.scale_ref_slf(n, h[2]);
    return DwVec3.sum_new(s, t, n);
  }
  
  public static float[] cosineWeightedDirection(float[] normal, double scale) {
    float[] n = DwVec3.copy_new(normal);
    float[] s = DwVec3.cross_new(n, (Math.abs(n[0])<0.5) ? float3(1,0,0) : float3(0,1,0));
    float[] t = DwVec3.cross_new(n, s);
    float[] h = cosineSampleHemisphere(scale);
    DwVec3.scale_ref_slf(s, h[0]);
    DwVec3.scale_ref_slf(t, h[1]);
    DwVec3.scale_ref_slf(n, h[2]);
    return DwVec3.sum_new(s, t, n);
  }
  public static float[] cosineWeightedReflection(float[] N, float[] R, float refl){
    float refl_inv = 1-refl;
    float[] RN =  new float[]{  R[0] * refl + N[0] * refl_inv,
                                R[1] * refl + N[1] * refl_inv,
                                R[2] * refl + N[2] * refl_inv };
    
    float[] sample_dir;
    do{
      sample_dir = DwSampling.cosineWeightedDirection(RN, refl_inv);
    } while ((DwVec3.dot(sample_dir, N)) <= 0);
    return sample_dir;
  }
  
  public static float[] cosineSampleHemisphere(double scale, double exp){
    double phi = Math.random() * PI_TWO ;
    double rnd = Math.random();
    double rad = Math.sqrt(1-Math.pow(rnd, 2/(exp+1))) * scale;
    double X   = Math.cos(phi) * rad;
    double Y   = Math.sin(phi) * rad;
    double Z   = Math.pow(rnd, 1/(exp+1));
    return float3(X,Y,Z);
  }
  
  
  // same as cosineSampleHemisphere(scale, 1);
  public static float[] cosineSampleHemisphere(double scale){
    double phi = Math.random() * PI_TWO ;
    double rnd = Math.random();
    double rad = Math.sqrt(1.0-rnd) * scale;
    double X   = Math.cos(phi) * rad;
    double Y   = Math.sin(phi) * rad;
    double Z   = Math.sqrt(rnd);
    return float3(X,Y,Z);
  }
  
  // same as cosineSampleHemisphere(scale, 0);
  public static float[] uniformSampleHemisphere(double scale){
    double phi = Math.random() * PI_TWO;
    double rnd = Math.random();
    double rad = Math.sqrt(1.0 - rnd*rnd) * scale;
    double X   = Math.cos(phi) * rad;
    double Y   = Math.sin(phi) * rad;
    double Z   = rnd;
    return float3(X,Y,Z);
  }

  
  public static float[] uniformSampleSphere(double scale) {
    double phi = Math.random() * PI_TWO;
    double rnd = Math.random() * 2.0 - 1.0;
    double rad = Math.sqrt(1.0 - rnd*rnd);
    double X   = scale * Math.cos(phi) * rad;
    double Y   = scale * Math.sin(phi) * rad;
    double Z   = scale * rnd;
    return float3(X,Y,Z);
  }
  public static float[] centerSampleSphere(double scale) {
    double s = scale * Math.sqrt(Math.random());
    double phi = Math.random() * PI_TWO;
    double rnd = Math.random() * 2.0 - 1.0;
    double rad = Math.sqrt(1.0 - rnd*rnd);
    double X   = s * Math.cos(phi) * rad;
    double Y   = s * Math.sin(phi) * rad;
    double Z   = s * rnd;
    return float3(X,Y,Z);
  }
}
