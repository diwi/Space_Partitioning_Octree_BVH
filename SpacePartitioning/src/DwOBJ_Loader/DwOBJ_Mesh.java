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

import java.util.ArrayList;

import DwMath.AABB;

public class DwOBJ_Mesh {
  public DwOBJ_File parent_obj_file;
  public String name;
  
  public final ArrayList<DwOBJ_Face> faces = new ArrayList<DwOBJ_Face>();
  
  public AABB aabb;
  
  public DwOBJ_Mesh(DwOBJ_File parent_obj_file, String name){
    this.parent_obj_file = parent_obj_file;
    this.name = name;
  } 
  public void computeAABB(){
    aabb = AABB.init();
    for( DwOBJ_Face face : faces ){
      face.computeAABB();
      aabb.grow(face.aabb);
    }
  }
}
