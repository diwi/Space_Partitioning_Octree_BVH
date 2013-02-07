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


// adapted from:
// http://fileadmin.cs.lth.se/cs/personal/tomas_akenine-moller/pubs/tribox.pdf
// http://fileadmin.cs.lth.se/cs/Personal/Tomas_Akenine-Moller/code/tribox3.txt
// http://www.toxicengine.org/developers/reference/triboxoverlap_8cpp-source.html
// http://www.koders.com/cpp/fid17736F013E5860D198107D9B7D494AF15ABB6A79.aspx?s=ray
//
// other: 
// http://www.geometrictools.com/LibMathematics/Distance/Distance.html
// http://tog.acm.org/resources/GraphicsGems/gemsiii/triangleCube.c
// http://clb.demon.fi/MathGeoLib/docs/Triangle.cpp_code.html#459
public class Intersect_AABB_TRIANGLE {
  
  private static boolean AXISTEST(float rad, float p0, float p1){
    return (Math.min(p0,p1) > rad || Math.max(p0,p1)< -rad);
  }
  
  private static boolean directionTest(float a, float b, float c, float hs){
    return (DwVec3.minComponent(a,b,c) > hs ||DwVec3.maxComponent(a,b,c) < -hs); 
  }
  
  public static boolean planeBoxOverlap(float[] normal, float d, float[] hs) {
    float[] vmin ={ (normal[0]>0.0)? -hs[0]:+hs[0],
                    (normal[1]>0.0)? -hs[1]:+hs[1],
                    (normal[2]>0.0)? -hs[2]:+hs[2] };
    
    if(DwVec3.dot(normal, vmin) + d >  0.0) return false;
    float[] vmax = DwVec3.negate_new(vmin);
    if(DwVec3.dot(normal, vmax) + d >= 0.0) return true;
    return false;
  }
  
       
  // modified version, ... better refer to original version!
  public static boolean overlaps_( float[] center, float[] halfsize, float[] A, float[] B, float[] C) {
    // Use separating axis theorem to test overlap between triangle and box.
    // Need to test for overlap in these directions:
    // 1) the {x,y,z}-directions (actually, since we use the AABB of the triangle we do not even need to test these)
    // 2) normal of the triangle
    // 3) crossproduct(edge from triangle, {x,y,z}-direction). This gives 3x3=9 more tests.
    
    // Move everything so that the boxcenter is in (0,0,0).
    float[] hs = halfsize;
    float[] v0 = DwVec3.sub_new(A, center);
    float[] v1 = DwVec3.sub_new(B, center);
    float[] v2 = DwVec3.sub_new(C, center);
   
    // Bullet 3:
    //   Test the 9 tests first (this was faster).
    
    float[] ea   = new float[3];
    float[] e_v0 = new float[3];
    float[] e_v1 = new float[3];
    float[] e_v2 = new float[3];
    
    // EDGE 0
    float[] e0 = DwVec3.sub_new(v1,v0);
    DwVec3.abs_ref  (e0, ea);
    DwVec3.cross_ref(e0, v0, e_v0);
    DwVec3.cross_ref(e0, v1, e_v1);
    DwVec3.cross_ref(e0, v2, e_v2);
    
    if( AXISTEST(ea[2]*hs[1] + ea[1]*hs[2],  e_v0[0], e_v2[0]) ) return false; // X
    if( AXISTEST(ea[2]*hs[0] + ea[0]*hs[2],  e_v0[1], e_v2[1]) ) return false; // Y
    if( AXISTEST(ea[1]*hs[0] + ea[0]*hs[1],  e_v1[2], e_v2[2]) ) return false; // Z 
    
    // EDGE 1
    float[] e1 = DwVec3.sub_new(v2,v1);
    DwVec3.abs_ref  (e1, ea);
    DwVec3.cross_ref(e1, v0, e_v0);
    DwVec3.cross_ref(e1, v1, e_v1);
    DwVec3.cross_ref(e1, v2, e_v2);
    
    if( AXISTEST(ea[2]*hs[1] + ea[1]*hs[2],  e_v0[0], e_v2[0]) ) return false;   
    if( AXISTEST(ea[2]*hs[0] + ea[0]*hs[2],  e_v0[1], e_v2[1]) ) return false;   
    if( AXISTEST(ea[1]*hs[0] + ea[0]*hs[1],  e_v0[2], e_v1[2]) ) return false; 
    
    // EDGE 2
    float[] e2 = DwVec3.sub_new(v0,v2);
    DwVec3.abs_ref  (e2, ea);
    DwVec3.cross_ref(e2, v0, e_v0);
    DwVec3.cross_ref(e2, v1, e_v1);
    DwVec3.cross_ref(e2, v2, e_v2);
    
    if( AXISTEST(ea[2]*hs[1] + ea[1]*hs[2],  e_v0[0], e_v1[0]) ) return false;   
    if( AXISTEST(ea[2]*hs[0] + ea[0]*hs[2],  e_v0[1], e_v1[1]) ) return false;   
    if( AXISTEST(ea[1]*hs[0] + ea[0]*hs[1],  e_v1[2], e_v2[2]) ) return false;   
    

    // Bullet 1:
    //   First test overlap in the {x,y,z}-directions.
    //   Find min, max of the triangle each direction, and test for overlap in that
    //   direction -- this is equivalent to testing a minimal AABB around the triangle against the AABB.
    if(directionTest(v0[0], v1[0], v2[0], hs[0]) ) return false; // Test in X-direction.
    if(directionTest(v0[1], v1[1], v2[1], hs[1]) ) return false; // Test in Y-direction.
    if(directionTest(v0[2], v1[2], v2[2], hs[2]) ) return false; // Test in Z-direction.
    
    
    // Bullet 2:
    //   Test if the box intersects the plane of the triangle. Compute plane equation of triangle: normal*x+d=0.
    float[] normal = DwVec3.cross_new(e0, e1);
    float d = -DwVec3.dot(normal, v0);  // plane eq: normal.x+d=0
    if(!planeBoxOverlap(normal, d, hs)) return false;
   
    return true; // box and triangle overlaps
  }
  
  public static boolean overlaps( float[] aabb_min, float[] aabb_max, float[] A, float[] B, float[] C) {
    float[] hs     = DwVec3.scale_new(DwVec3.sub_new(aabb_max, aabb_min), 0.5f);
    float[] center = DwVec3.add_new(aabb_min, hs);
    return overlaps_(center, hs, A, B, C);
  }
  
}
