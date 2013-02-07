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

import processing.core.PApplet;


public class DwRay3D {
  public float[] 
      o, 
      d, 
      d_rec
      ;

  public DwRay3D(float[] o, float[] d){
    setStartpoint(o);
    setDirection(d);
  }
  public void set(float[] o, float[] d){
    setStartpoint(o);
    setDirection(d);
  }
  
  public DwRay3D(){
  }
  
  public DwRay3D copy(){
    return new DwRay3D(DwVec3.copy_new(o), DwVec3.copy_new(d));
  }

  public void normalize(){
    DwVec3.normalize_ref_slf(d);
    setDirection(d);
  }
  
  public float[] getPoint(float t){
    return DwVec3.add_new(o, DwVec3.scale_new(d, t) );
  }
  public void getPoint_ref(float t, float[] dst){
    DwVec3.add_ref(o, DwVec3.scale_new(d, t), dst);
  }

  public void draw(PApplet p){
    float[] e = getPoint(1);
    p.line(o[0], o[1], o[2], e[0], e[1], e[2]);
  }
  
  public void draw(PApplet p, float t){
    float[] e = getPoint(t);
    p.line(o[0], o[1], o[2], e[0], e[1], e[2]);
  }
  
  public void setEndpoint(float[] endpoint){
    d = DwVec3.sub_new(endpoint, o);
    d_rec = new float[]{1f/d[0], 1f/d[1], 1f/d[2]};
  }
  public void setStartpoint(float[] startpoint){
    o = startpoint;
  }
  public void setDirection(float[] direction){
    this.d = direction;
    this.d_rec = new float[]{1f/d[0], 1f/d[1], 1f/d[2]};
  }
}
