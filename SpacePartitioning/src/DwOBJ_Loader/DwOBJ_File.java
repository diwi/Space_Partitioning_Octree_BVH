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

import java.io.File;
import java.util.ArrayList;

import DwMath.AABB;
import DwUtils.HELPER;


public class DwOBJ_File {
  
  public File file;
  public DwOBJ_Material[] materials; // materials
  public DwOBJ_Mesh[]     m; // meshes
  public DwOBJ_Face[]     f; // faces
  public float[][]        v; // vertices
  public float[][]        t; // texture coordinates (normalized)
  public float[][]        n; // vertex-normals
  public AABB aabb;          // Axis Aligned Bounding Box
  
  public DwOBJ_File(String path, String filename){
    file = new File(path, filename);

    StringBuffer sb = HELPER.readASCIIfile(file.getAbsolutePath());
    String[] lines  = sb.toString().split(System.getProperty("line.separator"));

    // tmp data buffers
    ArrayList<float[]>    tmp_v = new ArrayList<float[]>();
    ArrayList<float[]>    tmp_t = new ArrayList<float[]>();
    ArrayList<float[]>    tmp_n = new ArrayList<float[]>();
    ArrayList<DwOBJ_Face> tmp_f = new ArrayList<DwOBJ_Face>();
    ArrayList<DwOBJ_Mesh> tmp_m = new ArrayList<DwOBJ_Mesh>();
    
    DwOBJ_Material mat_cur  = DwOBJ_Material.MAT_DEFAULT;
    DwOBJ_Mesh     mesh_cur = new DwOBJ_Mesh(this, "___DEFAULT___");
    tmp_m.add(mesh_cur);
    
    // extract data
    for(int i = 0; i < lines.length; i++){
      
      
      String line = lines[i].trim();

      // rhino export breaks too long lines, so this tries to rebuild the line 
      // as it should be
      while( line.endsWith("\\") ){
        line = line.substring(0, line.length()-1);
        line += " "+lines[++i].trim();
      }
      
      // split token, and also ignores mutliple whitespaces, so no empty tokens are generated
      String[] token = line.split("\\s+");    
      
      // load materials from file
      if( token[0].matches("mtllib") ){
        materials = DwOBJ_Material.loadFromFile(path, token[1].trim());
        if( materials == null ) System.err.println("(DwOBJ_File) no materials found");
      }
      // use material
      else if( token[0].matches("usemtl") ){
        DwOBJ_Material mat_tmp = DwOBJ_Material.getByName(materials, token[1].trim());
        mat_cur = (mat_tmp != null) ? mat_tmp : mat_cur;
      }
      // new mesh
      else if( token[0].matches("g") ){
        mesh_cur = new DwOBJ_Mesh(this, token[1].trim());
        tmp_m.add(mesh_cur);
      }
      // vertices
      else if(token[0].matches("v") ){
        float vx = Float.parseFloat(token[1]);
        float vy = Float.parseFloat(token[2]);
        float vz = Float.parseFloat(token[3]);
        tmp_v.add( new float[]{vy, vx, vz} );
      }
      // texture coordinates
      else if( token[0].matches("vt") ){
        float u = Float.parseFloat(token[1]);
        float v = Float.parseFloat(token[2]);
        tmp_t.add( new float[]{u, v} );
      }
      // vertex normals
      else if( token[0].matches("vn") ){
        float nx = Float.parseFloat(token[1]);
        float ny = Float.parseFloat(token[2]);
        float nz = Float.parseFloat(token[3]);
        tmp_n.add( new float[]{ny, nx, nz} );
      }
      // faces
      else if( token[0].matches("f") ){
        //TODO: this assumes the obj-file contains only triangles, which is NOT guaranteed!
        String[] A = token[1].split("/");
        String[] B = token[2].split("/");
        String[] C = token[3].split("/");
//        System.out.println(A.length+"A[0]="+A[0]+"A[1]="+A[1]+"A[2]="+A[2]);
        DwOBJ_Face face = new DwOBJ_Face(this);

                                             face.IDX_V[0] = Integer.parseInt(A[0]) - 1;
        if( A.length > 1 && !A[1].isEmpty()) face.IDX_T[0] = Integer.parseInt(A[1]) - 1;
        if( A.length > 2 && !A[2].isEmpty()) face.IDX_N[0] = Integer.parseInt(A[2]) - 1;
        
                                             face.IDX_V[1] = Integer.parseInt(B[0]) - 1;
        if( B.length > 1 && !B[1].isEmpty()) face.IDX_T[1] = Integer.parseInt(B[1]) - 1;
        if( B.length > 2 && !B[2].isEmpty()) face.IDX_N[1] = Integer.parseInt(B[2]) - 1;
        
                                             face.IDX_V[2] = Integer.parseInt(C[0]) - 1;
        if( C.length > 1 && !C[1].isEmpty()) face.IDX_T[2] = Integer.parseInt(C[1]) - 1;
        if( C.length > 2 && !C[2].isEmpty()) face.IDX_N[2] = Integer.parseInt(C[2]) - 1;
        
        tmp_f.add( face );
        mesh_cur.faces.add(face);
        face.MESH     = mesh_cur;
        face.MATERIAL = mat_cur;
      }
    }
    
    v = new float[tmp_v.size()][3];
    t = new float[tmp_t.size()][2];
    n = new float[tmp_n.size()][3];
    f = new DwOBJ_Face[tmp_f.size()];
    m = new DwOBJ_Mesh[tmp_m.size()];
    
    tmp_v.toArray( v );
    tmp_t.toArray( t );
    tmp_n.toArray( n );
    tmp_f.toArray( f );
    tmp_m.toArray( m );
    
//    System.out.println(v.length);
//    System.out.println(t.length);
//    System.out.println(n.length);
//    System.out.println(f.length);
//    System.out.println(m.length);
  }
  
  public void computeAABB(){
    aabb = AABB.init();
    for( DwOBJ_Mesh mesh : m){
      mesh.computeAABB();
      aabb.grow(mesh.aabb);
    }
  }
}
