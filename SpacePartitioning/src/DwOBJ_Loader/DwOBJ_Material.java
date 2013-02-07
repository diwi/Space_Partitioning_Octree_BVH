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
import java.util.Locale;

import DwUtils.HELPER;

public class DwOBJ_Material {
  
  public static DwOBJ_Material MAT_DEFAULT;
  
  // global list of all materials
//  public static final ArrayList<DwOBJ_Material> MATERIALS = new ArrayList<DwOBJ_Material>();

  
  public String  name  = "";      // material name
  public float   Ns    = 0;       // coeff specular [0,100] ... glossiness
  public float   Ni    = 0;       // index of refraction
  public float   d     = 0;       // transparency or dissolved [0,1] = 1-Tr
  public float   Tr    = 0;       // transparency [0, 1]
  public float[] Tf    = {0,0,0}; // color transparency
  public float   illum = 0;       // illumination model
  public float[] Ka    = {0,0,0}; // color ambient 3x[0,1]
  public float[] Kd    = {0,0,0}; // color diffuse 3x[0,1]
  public float[] Ks    = {0,0,0}; // color specular 3x[0,10] ... specular*specular-level (3dsmax)
  public float[] Ke    = {0,0,0}; // color self emitting
  
  public float reflectivity = 0;
  
  public boolean is_reflective = false;
  public boolean is_emissive   = false;
  public boolean is_glossy     = false;

  
  // some mods:
  // Ns ranges from 0-100
  // Ks ranges from [0-10][0-10][0-10];
  //
  // my changes:
  // make divide Ks by 10 --> range from 0-1 (specular intensity)
  // and multiply Ns by 10 --> range 0-1000  ( specular exponent)
  // reflectivity: kA[0]
  // KA is not used in the usal way
  
  

  static{
    MAT_DEFAULT = new DwOBJ_Material("mat_default");
    MAT_DEFAULT.Ns     = 10.0f;
    MAT_DEFAULT.Ni     = 1.5f;
    MAT_DEFAULT.d      = 1.0f;
    MAT_DEFAULT.Tr     = 0.0f;
    MAT_DEFAULT.Tf     = new float[]{1.0f, 1.0f, 1.0f};
    MAT_DEFAULT.illum  = 2;
    MAT_DEFAULT.Ka     = new float[]{0.5880f, 0.5880f, 0.5880f};
    MAT_DEFAULT.Kd     = new float[]{0.5880f, 0.5880f, 0.5880f};
    MAT_DEFAULT.Ks     = new float[]{0.0000f, 0.0000f, 0.0000f};
    MAT_DEFAULT.Ke     = new float[]{0.0000f, 0.0000f, 0.0000f};
  }
  
  public DwOBJ_Material(String name){
    this.name = name;
  }
  
  
  public void printMaterial(){
    System.out.printf(Locale.ENGLISH, "------------------- < OBJ MTL > -------------------\n");
    System.out.printf(Locale.ENGLISH, "   name   = %s\n", name );
    System.out.printf(Locale.ENGLISH, "   Ns     = %f\n", Ns   );
    System.out.printf(Locale.ENGLISH, "   Ni     = %f\n", Ni   );
    System.out.printf(Locale.ENGLISH, "   d      = %f\n", d    );
    System.out.printf(Locale.ENGLISH, "   Tr     = %f\n", Tr   );
    System.out.printf(Locale.ENGLISH, "   Tf     = %f, %f, %f\n", Tf[0], Tf[1], Tf[2] );
    System.out.printf(Locale.ENGLISH, "   illum  = %f\n", illum);
    System.out.printf(Locale.ENGLISH, "   Ka     = %f, %f, %f\n", Ka[0], Ka[1], Ka[2] );
    System.out.printf(Locale.ENGLISH, "   Kd     = %f, %f, %f\n", Kd[0], Kd[1], Kd[2] );
    System.out.printf(Locale.ENGLISH, "   Ks     = %f, %f, %f\n", Ks[0], Ks[1], Ks[2] );
    System.out.printf(Locale.ENGLISH, "   Ke     = %f, %f, %f\n", Ke[0], Ke[1], Ke[2] );
    System.out.printf(Locale.ENGLISH, "   reflectivity = %f\n", reflectivity );
    System.out.printf(Locale.ENGLISH, "------------------- </ OBJ MTL > -------------------\n");
  }
  
  

  
  
  
  static public DwOBJ_Material[] loadFromFile(String path, String filename){
    File file = new File(path, filename);
    
    if( !file.exists() ){
      System.err.println("DwOBJ_Material.loadFromFile(): file does not exist!");
      System.err.println(file.getAbsolutePath());
      return null;
    }
    StringBuffer sb = HELPER.readASCIIfile(file.getAbsolutePath());
    String[] lines = sb.toString().split(System.getProperty("line.separator"));
    
    
    ArrayList<DwOBJ_Material> material_list = new ArrayList<DwOBJ_Material>();
    
    
    DwOBJ_Material mat_cur = null;
    for(int i = 0; i < lines.length; i++){
      String line = lines[i].trim();
      String[] tokens = line.split(" ");
      if(line.startsWith("newmtl ")){
        if( mat_cur != null ){
          addToList(material_list, mat_cur);
        }
        mat_cur = new DwOBJ_Material(tokens[1].trim());
      }
     
 
      if(line.startsWith("Ns ")) mat_cur.Ns = Float.valueOf(tokens[1]);
      if(line.startsWith("Ni ")) mat_cur.Ni = Float.valueOf(tokens[1]);
      if(line.startsWith("d " )) mat_cur.d  = Float.valueOf(tokens[1]);
      if(line.startsWith("Tr ")) mat_cur.Tr = Float.valueOf(tokens[1]);
      if(line.startsWith("Tf ")) mat_cur.Tf = new float[]{Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])};
      if(line.startsWith("Ka ")) mat_cur.Ka = new float[]{Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])};
      if(line.startsWith("Kd ")) mat_cur.Kd = new float[]{Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])};
      if(line.startsWith("Ks ")) mat_cur.Ks = new float[]{Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])};
      if(line.startsWith("Ke ")) mat_cur.Ke = new float[]{Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])};
      if(line.startsWith("illum ")) mat_cur.illum = Float.valueOf(tokens[1]);
    }
    if( mat_cur != null ){
      addToList(material_list, mat_cur);
    }
    
    DwOBJ_Material[] materials = new DwOBJ_Material[material_list.size()];
    material_list.toArray(materials);
    
    for( DwOBJ_Material m : materials ){
      m.printMaterial();
    }
    return materials;
  }
  
  static private void addToList(ArrayList<DwOBJ_Material> material_list, DwOBJ_Material mat){
//    mat.Ks[0] *= 0.1f;
//    mat.Ks[1] *= 0.1f;
//    mat.Ks[2] *= 0.1f;
//    mat.Ns    *= 10.0f;
    mat.reflectivity  = mat.Ka[0];
//    mat.printMaterial();
    
    mat.is_reflective = (mat.Ka[0] > 0.0);
    mat.is_emissive   = (mat.Ke[0] > 0.0) || (mat.Ke[1] > 0.0) || (mat.Ke[2] > 0.0);
    mat.is_glossy     = (mat.Ks[0] > 0.0) || (mat.Ks[1] > 0.0) || (mat.Ks[2] > 0.0);
    mat.Ke[0] *= 10;
    mat.Ke[1] *= 10;
    mat.Ke[2] *= 10;
    material_list.add(mat);
  }
  
  
  static public DwOBJ_Material getByName(DwOBJ_Material[] materials, String name){
    if( materials == null) 
      return null;
    for( DwOBJ_Material mat_tmp : materials ){
      if( name.equals(mat_tmp.name))
        return mat_tmp;
    }
    return null;
  }

 
    

  
}
