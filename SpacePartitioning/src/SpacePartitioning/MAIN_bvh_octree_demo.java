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




package SpacePartitioning;

import java.util.ArrayList;
import java.util.Locale;

import DwBVH.Bvh;
import DwBVH.BvhHitResult;
import DwBVH.BvhNodeT;
import DwMath.AABB;
import DwMath.DwCamera;
import DwMath.DwRay3D;
import DwMath.DwVec3;
import DwOBJ_Loader.DwOBJ_Face;
import DwOBJ_Loader.DwOBJ_File;
import DwOBJ_Loader.DwOBJ_Mesh;
import DwOctree.Octree;
import DwOctree.OctreeHitResult;
import DwOctree.OctreeNode;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * 
 * a WIP framework for Space-Partitioning techiques: Octree, BVH.
 * 
 * rendering: processing 1.5.1 (P3D/OPENGL)
 * camera: peasycam 
 * 
 * @author thomas diewald
 *
 */
@SuppressWarnings("serial")
public class MAIN_bvh_octree_demo extends PApplet{

  boolean BVH_DEMO    = true;
  boolean OCTREE_DEMO = !BVH_DEMO;
  boolean FILL        = true;
  boolean VISUALIZE   = !false;
  
  PeasyCam cam;
  DwOBJ_File obj;
  
  Bvh bvh;
  Octree octree;
  
  PFont font;
  
  String model_name = "";
  

  //----------------------------------------------------------------------------
  // SETUP
  //----------------------------------------------------------------------------
  @Override
  public void setup(){
    size(800, 800, P3D); // processing 1.5.1, P3D
//    size(800, 800, OPENGL); // processing 1.5.1, OPENGL
    
    cam = new PeasyCam(this, 0, 0, 0, 400);
    cam.setDistance(2000f);
    cam.setRotations(-0.81915313, 0.7923712, -0.5462533);
    cam.lookAt(-121.927666, -62.400963, 251.16692);
    cam.feed();
    
    float fov = PI/3.0f;
    float cameraZ = (height/2.0f) / tan(fov/2.0f);
    perspective(fov, (float)(width)/(float)(height), cameraZ/10.0f, cameraZ*100.0f);

    font = createFont("Calibri", 14);
    textFont(font);
    textMode(SCREEN);

    hint(ENABLE_OPENGL_4X_SMOOTH);
    
    System.out.println("");
    String path = System.getProperty("user.dir") +"/data/scene/";

    float scale = 1f;
    System.out.println(path);

    model_name = "bunny";
//    model_name = "buddha"; 


    obj = new DwOBJ_File(path, model_name+".obj");
    
    if( model_name.equals("bunny")){
      scale = 1000;
      cam.setDistance(2947);
      cam.setRotations(-0.6039191, 0.46715748, 1.4392722);
      cam.lookAt(660.9142, -466.15, -399.57956);
      cam.feed();
    }
    if( model_name.equals("buddha")){
      scale = .3f;
      cam.setDistance(3375);
      cam.setRotations(-0.56092066, 0.1761529, 1.5551788);
      cam.lookAt(-289.3921, 59.28672, 184.11087);
      cam.feed();
    }
 

    for(int i = 0; i < obj.v.length; i++){ DwVec3.scale_ref_slf(obj.v[i], scale); } //TODO
    for(DwOBJ_Face f : obj.f) f.computeAABB();
    obj.computeAABB();
    
 
//    if( BVH_DEMO )
    {
      System.out.println("\n-------------------------------< generating BVH >-------------------------------\n");
      {
        long timer = System.currentTimeMillis();
        bvh = new Bvh(obj);
        bvh.bvh_builder.MAX_DEPTH        = 19;
        bvh.bvh_builder.MIN_ITEM_COUNT   =  1;
        bvh.bvh_builder.SAH_OPTIMIZATION =  0;
        
        bvh.BUILD_defaultRoutine();
  //      bvh.swapChilds();
        System.out.println("    building time          = "+ (System.currentTimeMillis()-timer) +"ms ");
        bvh.printStatistics();
      }
      System.out.println("\n-------------------------------< finished BVH >-------------------------------");
    }
    
    
    
//    if( OCTREE_DEMO )
    {
      System.out.println("\n-------------------------------< generating Octree >-------------------------------\n");
      {
        long timer = System.currentTimeMillis();
        octree = new Octree(obj, true);
        octree.oct_builder.MIN_DEPTH_FILL_RATIO = 1.2f;
        octree.oct_builder.MAX_DEPTH            = 11;
        octree.BUILD_defaultRoutine();
        timer = System.currentTimeMillis()-timer;
        System.out.println("    building time          = "+ timer+"ms ");
        octree.printStatistics();
      }
      System.out.println("\n-------------------------------< finished Octree >-------------------------------");
    }
    

    keyReleased();
  }



  //----------------------------------------------------------------------------
  // DRAW
  //----------------------------------------------------------------------------
  @Override
  public void draw(){
    scale(10);
   
    background(255);
//    BKS(100);
    directionalLight(70, 70, 70, 0, -1, +1);
    lights();
    fill(200);stroke(0); 
    fill(250, 250, 200);
    fill(100);
    fill(250);
    strokeWeight(.5f); 
    noStroke();
    drawOBJ(obj);

    noLights();
    
    // visualize BVH/OCTREE
    if( VISUALIZE ){
      if( BVH_DEMO ){
        visualizeBVH();
      }
      if( OCTREE_DEMO ){
        visualizeOCTREE();
      }
    }

    // traverse BVH/OCTREE
    int item = -1;
    DwRay3D ray = new DwCamera(this).getSceenRay(width-mouseX, height-mouseY);
    
    if( BVH_DEMO ){
      BvhHitResult hit_result = new BvhHitResult(ray, 0, 1);
      bvh.traverse(hit_result);
//      hit_result.print();
      item = hit_result.item_idx;
    }
    if( OCTREE_DEMO ){
      OctreeHitResult hit_result = new OctreeHitResult(ray, 0, 1);
      octree.traverse(hit_result);
      item = hit_result.item_idx;
    }

    // draw hit face
    if( item >= 0 ){
      fill(255,0,0);
      noStroke();
      beginShape(PApplet.TRIANGLES);
      {
        vertexArr(obj.f[item].A()); // v0
        vertexArr(obj.f[item].B()); // v1
        vertexArr(obj.f[item].C()); // v2
      }
      endShape();
    }

    
    
    
    // print info
    noLights();
    
    cam.beginHUD();
    {
      fill(100);
      float x = 30;
      float y = 0;
  
      y+= height-200;
      text("model", x, y);
      String model_file = obj.file.getName().toUpperCase();
      y+=17;
      text("     model: "+ model_file, x, y);
      y+=17;
      text("     triangles: "+ obj.f.length, x, y);
      
      String mode = BVH_DEMO ? "BVH" : "Octree";
      y+= 30;
      text(mode, x, y);
      
      int nodes = BVH_DEMO ? bvh.getNumberOfNodes() : octree.getNumberOfNodes();
      y+=17;
      text("     nodes: "+nodes, x, y);
      
      int leafes = BVH_DEMO ? bvh.getNumberOfLeafes() : octree.getNumberOfLeafes();
      y+=17;
      text("     leafes: "+leafes, x, y);
       
      int depth = BVH_DEMO ? bvh.getMaxDepth() : octree.getMaxDepth();
      y+=17;
      text("     max depth: "+depth, x, y);
      
      y+=25;
      text("selection", x, y);
      y+=17;
      text("     depth: "+IDX, x, y);

      int nodes_at_depth = BVH_DEMO? numberOfNodes(bvh, IDX) : numberOfNodes(octree, IDX);
      y+=17;
      text("     nodes: "+nodes_at_depth, x, y);
      
      
      fill(220);
      String author = "Thomas Diewald";
      text(author, width-textWidth(author)-20, height-20);
    }
    cam.endHUD();
    
    updateWindowTitle();
  }
  
  
  
  void updateWindowTitle(){
    String title = "Space Partitioning - Octree vs. BVH";
    String framerate = String.format(Locale.ENGLISH, "fps %4.2f", frameRate);
    title += " | " + framerate;
    frame.setTitle(title);
  }
  

  
  // TODO 
  void visualizeBVH(){
    ArrayList<BvhNodeT> nodes = bvh.getNodes();
    
    for( BvhNodeT n : nodes){
       if( n._getDepth() != IDX && n._getDepth() <= 15){
        noFill(); stroke(0, 100); strokeWeight(0.8f);
        draw(n);
      }
    }
 
    for( BvhNodeT n : nodes){
      if( n._getDepth() == IDX){
        if(FILL) fill(0,50);  
        stroke(150,70,0);  strokeWeight(1.5f);  stroke(0);
        draw(n);
      } 
    }
    
  }
  // TODO 
  void visualizeOCTREE(){
    ArrayList<OctreeNode> nodes = octree.getNodes();
    
    for( OctreeNode n : nodes){
       if(n.depth != IDX && n.depth <= 12){
        noFill(); stroke(0, 100); strokeWeight(0.8f);
        draw(n);
      }
    }

    for( OctreeNode n : nodes){
      if( n.depth == IDX){
        if(FILL) fill(0,50); 
        stroke(150,70,0);  strokeWeight(1.5f);  stroke(0);
        draw(n);
      } 
    
    }
  }


  int numberOfNodes(Bvh bvh, int depth){

    ArrayList<BvhNodeT> nodes = bvh.getNodes();
    int count = 0; 
    for( BvhNodeT n : nodes){
      if(n._getDepth()==depth)count++;
    }
    return count;
  }
  int numberOfNodes(Octree octree, int depth){
    ArrayList<OctreeNode> nodes = octree.getNodes();
    int count = 0; 
    for( OctreeNode n : nodes){
      if(n.depth==depth)count++;
    }
    return count;
  }
  
  
  
  
  
  int IDX = -1;
  @Override
  public void keyReleased(){
    if( key == 'x')IDX++;
    if( key == 'y' && IDX > -1)IDX--;
    
    if( key == 'v') VISUALIZE = !VISUALIZE;
    if( key == ' ') {
      BVH_DEMO    = !BVH_DEMO;
      OCTREE_DEMO = !BVH_DEMO;
    }
    if( key == 'f') FILL = !FILL;
    
    System.out.println("[SPACE]  BVH_DEMO    = "+BVH_DEMO);
    System.out.println("[SPACE]  OCTREE_DEMO = "+OCTREE_DEMO);
    System.out.println("[v]      VISUALIZE   = "+VISUALIZE);
    System.out.println("[x,y]    IDX         = "+IDX);
    
    if( key == 'p') printPeasyCam();
    
    if( key == 's') saveScreenShot();
      
  }
  
  
  
  
  void saveScreenShot(){
    String onlygrid  = IDX>=0 ? "_selection" : "";
    String obj_model = obj.file.getName().toUpperCase().split("[.]")[0];
    String author    = "diewald";
    String res       = width+"x"+height;
    String mode      = BVH_DEMO ? "BVH" : "Octree";
    String path      = System.getProperty("user.dir") +"/data/screenshots/bvh_octree_demo/";
    String filename  = "BIG_"+author+"_"+obj_model+"_"+mode+"_"+res+onlygrid+".jpg";

    save(path+filename);
    
    println("saved screenshot:\n "+path+filename);
  }
  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public void draw(AABB aabb){
    float[] s = aabb.getSize();
    float[] c = aabb.getCenter();
    pushMatrix();
    translate(c[0], c[1], c[2]);
    float e = 1.00f;
    box(s[0]*e, s[1]*e, s[2]*e);
    popMatrix();
  }
  
  public void draw(BvhNodeT node){
    draw(node._getAABB());
  }
  public void draw(OctreeNode node){
    draw(node.aabb);
  }
  
  
  public void drawTriangles(BvhNodeT node){
    beginShape(PApplet.TRIANGLES);
    {
      for(int idx : node._getObjects() ){
        DwOBJ_Face f = obj.f[idx];
        vertexArr( f.A() ); 
        vertexArr( f.B() );
        vertexArr( f.C() );
      }
    }
    endShape();
  }
  
  
//  public void drawTriangleNormal(OctreeNode node){
//    beginShape(PApplet.LINES);
//    {
//      for(int idx : node.IDX_triangles ){
//        DwOBJ_Face f = obj.f[idx];
//        float[] center = f.getCenter();
//        float[] n = f.getNormal(); DwVec3.scale_ref_slf(n, 10);
//        float[] end = DwVec3.add_new(center, n);
//        vertexArr(center);
//        vertexArr(end);
//      }
//    }
//    endShape();
//  }
//  
//  public void drawTriangleVerticesNormal(OctreeNode node){
//    beginShape(PApplet.LINES);
//    {
//      for(int idx : node.IDX_triangles ){
//        DwOBJ_Face f = obj.f[idx];
//        float[] An = DwVec3.scale_new(f.An(), 10);
//        float[] Bn = DwVec3.scale_new(f.Bn(), 10);
//        float[] Cn = DwVec3.scale_new(f.Cn(), 10);
//        
//        float[] A2 = DwVec3.add_new(f.A(), An); vertexArr(f.A()); vertexArr(A2);
//        float[] B2 = DwVec3.add_new(f.B(), Bn); vertexArr(f.B()); vertexArr(B2);
//        float[] C2 = DwVec3.add_new(f.C(), Cn); vertexArr(f.C()); vertexArr(C2);
//      }
//    }
//    endShape();
//  }
  
  public void drawTriangleVerticesNormal(DwOBJ_Face f ){
    beginShape(PApplet.LINES);
    {
      float[] An = DwVec3.scale_new( f.An(), 5);
      float[] Bn = DwVec3.scale_new( f.Bn(), 5);
      float[] Cn = DwVec3.scale_new( f.Cn(), 5);
      
      float[] A2 = DwVec3.add_new(f.A(), An); vertexArr(f.A()); vertexArr(A2);
      float[] B2 = DwVec3.add_new(f.B(), Bn); vertexArr(f.B()); vertexArr(B2);
      float[] C2 = DwVec3.add_new(f.C(), Cn); vertexArr(f.C()); vertexArr(C2);
    }
    endShape();
  }
  
  public void drawTriangleFaceNormal(DwOBJ_Face f ){
    beginShape(PApplet.LINES);
    {
      float[] center = f.getCenter();
      float[] n = f.getNormal(); 
      DwVec3.scale_ref_slf(n, 10);
      float[] end = DwVec3.add_new(center, n);
      vertexArr(center);
      vertexArr(end);
    }
    endShape();
  }
  
  void drawOBJ(DwOBJ_File obj){
    beginShape(PApplet.TRIANGLES);
    {
      for(DwOBJ_Mesh m : obj.m){     // draw meshes
        for(DwOBJ_Face f : m.faces){ // draw triangles
          vertexArr(f.A()); // v0
          vertexArr(f.B()); // v1
          vertexArr(f.C()); // v2
        }
      }
    }
    endShape();
  }
  private void drawPlane(float[] A, float[] B, float[] C, float[] D){
    beginShape();vertexArr(A);vertexArr(B);vertexArr(C);vertexArr(D); endShape(PApplet.CLOSE);
  }
  
 
  public void vertexArr(float[] v){
    vertex(v[0], v[1], v[2]);
  }
  
  void printPeasyCam(){
    double  dis = cam.getDistance();
    float[] pos = cam.getPosition();
    float[] rot = cam.getRotations();
    float[] lat = cam.getLookAt();
    println("dis = "+dis);
    println("pos = "+pos[0]+", "+pos[1]+", "+pos[2]);
    println("rot = "+rot[0]+", "+rot[1]+", "+rot[2]);
    println("lat = "+lat[0]+", "+lat[1]+", "+lat[2]);
  }
  
 
  //----------------------------------------------------------------------------
  // BKS
  //----------------------------------------------------------------------------
  void BKS(int s){
    stroke(255, 0, 0); line(0, 0, 0, s, 0, 0 );
    stroke(0, 255, 0); line(0, 0, 0, 0, s, 0 );
    stroke(0, 0, 255); line(0, 0, 0, 0, 0, s );
  }

  //----------------------------------------------------------------------------
  // MAIN
  //----------------------------------------------------------------------------
  public static void main(final String args[]) {
    PApplet.main(new String[] { MAIN_bvh_octree_demo.class.getName() });
  }
}

