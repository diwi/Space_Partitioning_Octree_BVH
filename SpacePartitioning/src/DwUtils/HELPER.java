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




package DwUtils;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public final class HELPER {

  public static final float[] array_int2float(int ai[]) {
    float af[] = new float[ai.length];
    for (int i = 0;i < af.length; i++)
      af[i] = (float) ai[i];
    return af;
  }





  public static final int max(int[] numbers) {  
    int maxValue = numbers[0];  
    for (int i=1;i < numbers.length;i++) {  
      if (numbers[i] > maxValue) {  
        maxValue = numbers[i];
      }
    }  
    return maxValue;
  }  

  public static final int min(int[] numbers) {  
    int minValue = numbers[0];  
    for (int i=1;i<numbers.length;i++) {  
      if (numbers[i] < minValue) {  
        minValue = numbers[i];
      }
    }  
    return minValue;
  }  

  public static final float max(float[] numbers) {  
    float maxValue = numbers[0];  
    for (int i=1;i < numbers.length;i++) {  
      if (numbers[i] > maxValue) {  
        maxValue = numbers[i];
      }
    }  
    return maxValue;
  }  

  public static final float min(float[] numbers) {  
    float minValue = numbers[0];  
    for (int i=1;i<numbers.length;i++) {  
      if (numbers[i] < minValue) {  
        minValue = numbers[i];
      }
    }  
    return minValue;
  }  

















  public static StringBuffer readASCIIfile(String path) {
    File file = new File( path );
    StringBuffer contents = new StringBuffer();
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(file));
      String text = null;

      // repeat until all lines is read
      while ( (text = reader.readLine ()) != null) {
        contents.append(text).append(System.getProperty("line.separator"));
      }
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } 
    catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
    finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } 
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    // System.out.println(contents.toString());
    return contents;
  }
  
  
  public static final float string2float(String s){
     try {
      return Float.valueOf(s.trim()).floatValue();
    } catch (NumberFormatException nfe){
      //System.out.println("NumberFormatException: " + nfe.getMessage());
      return Float.NaN;
    }
  }
  
  public static final int string2integer(String s){
     try {
      return Integer.valueOf(s.trim()).intValue();
    } catch (NumberFormatException nfe){
      //System.out.println("NumberFormatException: " + nfe.getMessage());
      return 0;
    }
  }
  
  
}

