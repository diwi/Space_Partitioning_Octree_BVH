package DwMath;


import peasy.CameraState;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PMatrix3D;

public class DwCamera {
  
  static public final float[] ndc_NBL = {-1, -1, -1, 1};
  static public final float[] ndc_NBR = {+1, -1, -1, 1};
  static public final float[] ndc_NTR = {+1, +1, -1, 1};
  static public final float[] ndc_NTL = {-1, +1, -1, 1};
  static public final float[] ndc_FBL = {-1, -1, +1, 1};
  static public final float[] ndc_FBR = {+1, -1, +1, 1};
  static public final float[] ndc_FTR = {+1, +1, +1, 1};
  static public final float[] ndc_FTL = {-1, +1, +1, 1};
  
  public float[] world_NBL = new float[4];
  public float[] world_NBR = new float[4];
  public float[] world_NTR = new float[4];
  public float[] world_NTL = new float[4];
  public float[] world_FBL = new float[4];
  public float[] world_FBR = new float[4];
  public float[] world_FTR = new float[4];
  public float[] world_FTL = new float[4];
  
  
  public float[] MAT_projection     = new float[16];
  public float[] MAT_modelview      = new float[16];
  
  public float[] MAT_projection_inv = new float[16];
  public float[] MAT_modelview_inv  = new float[16];
  
  public float[] MAT_modelview_projection     = new float[16];
  public float[] MAT_modelview_projection_inv = new float[16];
  
  
  // TODO: make them accessible per getter methods
  public float[]eye   ; 
  public float[]center;
  public float[]up    ;
  public float fovy;
  public float aspect; 
  public float n; 
  public float f;
  
  private CameraState cam_state = null;
  
  private int   viewport_size[]     = new int[2];
  private float viewport_size_inv[] = new float[2];
  
  public final PApplet papplet;
  
  public DwCamera(PApplet papplet){
    this.papplet = papplet;
    this.viewport_size[0] = papplet.width;
    this.viewport_size[1] = papplet.height;
    this.viewport_size_inv[0] = 1f/viewport_size[0];
    this.viewport_size_inv[1] = 1f/viewport_size[1];

    getP5Matrix_MODELVIEW();
    getP5Matrix_PROJECTION();
    update();
  }
  


  
  public void update(){
    DwMat4.mult_ref(MAT_projection, MAT_modelview, MAT_modelview_projection);
    
    DwMat4.inverse_ref(MAT_projection, MAT_projection_inv);
    DwMat4.inverse_ref(MAT_modelview,  MAT_modelview_inv);
    DwMat4.inverse_ref(MAT_modelview_projection,  MAT_modelview_projection_inv);
    
    updateFarPlane_worldspace();
    updateNearPlane_worldspace();
  }
  
  public void getP5Matrix_PROJECTION(){
    PMatrix3D mat_p5_tmp = new PMatrix3D(); 
    papplet.g.matrixMode(PApplet.PROJECTION);
    papplet.getMatrix( mat_p5_tmp );

    MAT_projection = mat_p5_tmp.get(MAT_projection);
    DwMat4.transpose_ref_slf(MAT_projection);
    
    papplet.g.matrixMode(PApplet.MODELVIEW);
  }
  
  public void getP5Matrix_MODELVIEW(){
    PMatrix3D mat_p5_tmp = new PMatrix3D(); 
    papplet.g.matrixMode(PApplet.MODELVIEW);
    papplet.getMatrix( mat_p5_tmp );
   
    MAT_modelview = mat_p5_tmp.get(MAT_modelview);
    DwMat4.transpose_ref_slf(MAT_modelview);
    
    papplet.g.matrixMode(PApplet.MODELVIEW);
  }
//  
//  public void setP5Matrix_PROJECTION(){
//    setMatrix(PApplet.PROJECTION, MAT_projection);
//  }
//  
//  public void setP5Matrix_MODELVIEW(){
//    setMatrix(PApplet.MODELVIEW, MAT_modelview);
//  }
//  
//  public void setState(CameraState cam_state ){
//    this.cam_state = cam_state;;
//  }
//  public CameraState getState(){
//    return cam_state; 
//  }
//  
//  private void setMatrix(int mode, float[] mat_dw){
//    PMatrix3D mat_p5 = new PMatrix3D();
//    mat_p5.set(mat_dw);
//    mat_p5.transpose();
//    papplet.g.matrixMode(mode);
//    papplet.setMatrix(mat_p5);
//    papplet.g.matrixMode(PApplet.MODELVIEW);
//  }
  
  

  

  
  public void updateNearPlane_worldspace(){
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_NBL, world_NBL);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_NBR, world_NBR);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_NTR, world_NTR);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_NTL, world_NTL);    

    DwVec4.multiply_ref_slf(world_NBL, 1.0f/world_NBL[3]);
    DwVec4.multiply_ref_slf(world_NBR, 1.0f/world_NBR[3]);
    DwVec4.multiply_ref_slf(world_NTR, 1.0f/world_NTR[3]);
    DwVec4.multiply_ref_slf(world_NTL, 1.0f/world_NTL[3]);
  }
  public void updateFarPlane_worldspace(){
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_FBL, world_FBL);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_FBR, world_FBR);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_FTR, world_FTR);
    DwMat4.multVec4_ref(MAT_modelview_projection_inv, ndc_FTL, world_FTL);    

    DwVec4.multiply_ref_slf(world_FBL, 1.0f/world_FBL[3]);
    DwVec4.multiply_ref_slf(world_FBR, 1.0f/world_FBR[3]);
    DwVec4.multiply_ref_slf(world_FTR, 1.0f/world_FTR[3]);
    DwVec4.multiply_ref_slf(world_FTL, 1.0f/world_FTL[3]);
  }
 
  
  
//  public void drawFrustum(boolean fill){
//    getNearPlane_worldspace(world_NBL, world_NBR, world_NTR, world_NTL);
//    getFarPlane_worldspace (world_FBL, world_FBR, world_FTR, world_FTL);
//
//    if( !fill ) papplet.noFill();
//    papplet.strokeWeight(2);   papplet.stroke(0);
////    papplet.stroke(255, 0, 0); if(fill)papplet.fill(255,0,0,50); drawPlane(world_NBL, world_NBR, world_NTR, world_NTL); // NEAR
////    papplet.stroke(100, 0, 0); if(fill)papplet.fill(100,0,0,50); drawPlane(world_FBL, world_FBR, world_FTR, world_FTL); // FAR
//    papplet.stroke(0, 255, 0); if(fill)papplet.fill(0,255,0,50); drawPlane(world_FBL, world_FBR, world_NBR, world_NBL); // BOTTOM
//    papplet.stroke(0, 100, 0); if(fill)papplet.fill(0,100,0,50); drawPlane(world_FTL, world_FTR, world_NTR, world_NTL); // TOP
//    papplet.stroke(0, 0, 255); if(fill)papplet.fill(0,0,255,50); drawPlane(world_FBL, world_FTL, world_NTL, world_NBL); // LEFT
//    papplet.stroke(0, 0, 100); if(fill)papplet.fill(0,0,100,50); drawPlane(world_FBR, world_FTR, world_NTR, world_NBR); // RIGHT
//    
//    papplet.strokeWeight(.1f);
//    papplet.stroke(0, 0, 0); drawLine(eye, world_NBR);
//    papplet.stroke(0, 0, 0); drawLine(eye, world_NBL);
//    papplet.stroke(0, 0, 0); drawLine(eye, world_NTR);
//    papplet.stroke(0, 0, 0); drawLine(eye, world_NTL);
////    DwVec3.print(world_FBL, 5);
//  }
//  
//  
//  private void drawPlane(float[] A, float[] B, float[] C, float[] D){
//    papplet.beginShape();vertexArr(A);vertexArr(B);vertexArr(C);vertexArr(D); papplet.endShape(PApplet.CLOSE);
//  }
//  
  
//  private void vertexArr(float[] v){
//    papplet.vertex(v[0], v[1], v[2]);
//  }
//  
//  private void drawLine(float[] A, float[] B){
//    papplet.beginShape(PApplet.LINE);vertexArr(A);vertexArr(B);papplet.endShape();
//  }
//  
//  
//  
  

//
//  private void drawPlane(float[] A, float[] B, float[] C, float[] D, PImage texture){
//    papplet.beginShape();
//    papplet.texture(texture);
//    papplet.textureMode(PApplet.NORMAL);
//    vertexArr(A, new float[]{1,1});
//    vertexArr(B, new float[]{0,1});
//    vertexArr(C, new float[]{0,0});
//    vertexArr(D, new float[]{1,0}); 
//    papplet.endShape(PApplet.CLOSE);
//  }
//
//  private void vertexArr(float[] v, float[] t){
//    papplet.vertex(v[0], v[1], v[2], t[0], t[1]);
//  }
//  

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 
  
  
//  public void makeFrustrumJitter(float jitter){
//    if( jitter == 0.0) 
//      return;
//    float[] f3_jitter = DwVec3.randF3_new(jitter);
////    f3_jitter[1] = 0;
//    
//    float[] jittered = DwVec3.add_new(eye, f3_jitter);
//    lookAt(jittered, center, up);
////    float[] mat4_model_view_jitter = DwMat4.translate_new(MAT_modelview, f3_jitter);
////    DwMat4.mult_ref(MAT_projection, MAT_modelview, MAT_modelview_projection_jitter);
//  }
  
  public DwRay3D getSceenRay(float x, float y){
    float xnorm = x*viewport_size_inv[0];
    float ynorm = y*viewport_size_inv[1];

//    float[] mix_FB_LR = DwVec3.lerp_new(world_FBL, world_FBR, xnorm);
//    float[] mix_FT_LR = DwVec3.lerp_new(world_FTL, world_FTR, xnorm);
//    
//    float[] mix_NB_LR = DwVec3.lerp_new(world_NBL, world_NBR, xnorm);
//    float[] mix_NT_LR = DwVec3.lerp_new(world_NTL, world_NTR, xnorm);
//    
//    float[] mix_F_BT  = DwVec3.lerp_new(mix_FB_LR, mix_FT_LR, ynorm);
//    float[] mix_N_BT  = DwVec3.lerp_new(mix_NB_LR, mix_NT_LR, ynorm);
    

    float[] mix_FB_LR = DwVec3.lerp_new(world_FBR, world_FBL, xnorm);
    float[] mix_FT_LR = DwVec3.lerp_new(world_FTR, world_FTL, xnorm);
    float[] mix_NB_LR = DwVec3.lerp_new(world_NBR, world_NBL, xnorm);
    float[] mix_NT_LR = DwVec3.lerp_new(world_NTR, world_NTL, xnorm);
    
    float[] mix_F_BT  = DwVec3.lerp_new(mix_FT_LR, mix_FB_LR, ynorm);
    float[] mix_N_BT  = DwVec3.lerp_new(mix_NT_LR, mix_NB_LR, ynorm);
    
    return new DwRay3D(mix_N_BT, DwVec3.sub_new(mix_F_BT, mix_N_BT));  
//    return new DwRay3D(eye, DwVec3.sub_new(mix_F_BT, eye));  
//    return getSceenRay(x, y, MAT_modelview_projection_inv);
  }
  
  
  public DwRay3D getSceenRay(float x, float y, float[] m4_MVP_inv){

    float[] f2_screen_ndc = screenToNDC(x,y);
    float[] f4_ndc_n = float4(f2_screen_ndc, -1, 1); // near 
    float[] f4_ndc_f = float4(f2_screen_ndc, +1, 1); // far
    float[] f4_world_n = DwMat4.multVec4_new(m4_MVP_inv, f4_ndc_n);  
    float[] f4_world_f = DwMat4.multVec4_new(m4_MVP_inv, f4_ndc_f);  
    DwVec4.multiply_ref_slf(f4_world_n, 1.0f/f4_world_n[3]);
    DwVec4.multiply_ref_slf(f4_world_f, 1.0f/f4_world_f[3]);
//    float[] f3_eye = DwMat4.getColumnVec3_new(camera.MAT_modelview_inv, 3);
    float[] f3_eye = f4_world_n;
    float[] f3_dir = DwVec3.sub_new(f4_world_f, f3_eye);
    return new DwRay3D(f3_eye, f3_dir);  
  }
    
  public float[] float4(float[] xy, float z, float w){
   return new float[]{xy[0], xy[1], z, w};
  }
  
  public float[] screenToNDC(float x, float y){
    float x_ndc = 2f*x*viewport_size_inv[0] - 1f;
    float y_ndc = 2f*y*viewport_size_inv[1] - 1f;
    return new float[]{x_ndc, y_ndc};
  }
  
  
  
  
  
  
  public int[] projectVertex(float[] f3_vtx){
    float[] v4 = {f3_vtx[0], f3_vtx[1], f3_vtx[2], 1.0f};
    DwMat4.multVec4_ref_slf(MAT_modelview_projection, v4);       // project to ndc
    DwVec3.scale_ref_slf(v4, -1f/v4[3]);                         // perspective divvide: range: [-1, +1]
    int px = Math.round((v4[0]*0.5f + 0.5f)*viewport_size[0]);   // map to screen_x
    int py = Math.round((v4[1]*0.5f + 0.5f)*viewport_size[1]);   // map to screen_y

    if( px >= 0 && px < viewport_size[0] &&  
        py >= 0 && py < viewport_size[1] ){
      return new int[]{px, py};
    }
    return null;
  }

  
  
}
