package com.example.rpgexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;



@SuppressLint("NewApi")
public class VistaEditor extends View implements SensorEventListener{

	class ThreadJuego extends Thread {
		   private boolean pausa,corriendo;
		 
		   public synchronized void pausar() {
		          pausa = true;
		   }
		 
		   public synchronized void reanudar() {
		          pausa = false;
		          notify();
		   }
		 
		   public void detener() {
		          corriendo = false;
		          if (pausa) reanudar();
		   }
		  
		   @Override    public void run() {
		          corriendo = true;
		          while (corriendo) {
		                 actualizaFisica();
		                 synchronized (this) {
		                       while (pausa) {
		                              try {
		                                     wait();
		                              } catch (Exception e) {
		                              }
		                       }
		                 }
		          }
		   }
		   
		}
	
	SensorManager mSensorManager; 
	
	private float mX=0, mY=0;
	private boolean disparo=false;
	
	// //// THREAD Y TIEMPO //////
	// Thread encargado de procesar el juego
	private ThreadJuego thread = new ThreadJuego();
	// Cada cuanto queremos procesar cambios (ms)
	private static int PERIODO_PROCESO = 100;
	// Cuando se realizó el último proceso
	private long ultimoProceso = 0;
	

    Ground suelo;
    TileGroundEditor tileGround;
    TileGroundEditor tileGroundL2;
    TileGroundEditor tileGroundL3;
    private PlayerEditor election;
    private Context context;
    private AttributeSet attrs;

    private boolean hideControls = true;
    private boolean hideSelecGround = true;
    private int gx=12;
    private int gy=1;



    public VistaEditor(Context context, AttributeSet attrs) {

    	

          super(context, attrs);

          this.context = context;
          this.attrs = attrs;
          //drawableNave = context.getResources().getDrawable(R.drawable.nave);
          Drawable drawableGround;
          drawableGround = context.getResources().getDrawable(R.drawable.tileworld);

          
          suelo = new Ground(BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),0,0,64*4,0,64,64);
         
          WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
          Display display = wm.getDefaultDisplay();
           
          Point size = new Point();
          display.getSize(size);
          int width = size.x;
          int height = size.y;
          
          ArrayList<CoordGround>lista1 = new ArrayList<CoordGround>();
          ArrayList<ArrayList<CoordGround>>lista2 = new ArrayList<ArrayList<CoordGround>>();
          CoordGround cd;       
          for(int i=0;i<15;i++){
        	  lista1 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd = new CoordGround(5,0);
                  lista1.add(cd);
        	  }
              lista2.add(lista1);
          }
          tileGround = new TileGroundEditor(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),lista2,20,15,15,40);
          
          ArrayList<CoordGround>listaL21 = new ArrayList<CoordGround>();
          ArrayList<ArrayList<CoordGround>>listaL22 = new ArrayList<ArrayList<CoordGround>>();
          CoordGround cd2; 
          
          for(int i=0;i<20;i++){
        	  listaL21 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd2 = null;
                  listaL21.add(cd2);
        	  }
              listaL22.add(listaL21);
          }
         
          tileGroundL2 = new TileGroundEditor(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),listaL22,20,15,15,40);
          
          ArrayList<CoordGround>listaL31 = new ArrayList<CoordGround>();
          ArrayList<ArrayList<CoordGround>>listaL32 = new ArrayList<ArrayList<CoordGround>>();
          CoordGround cd3; 
          
          for(int i=0;i<20;i++){
        	  listaL31 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd3 = null;
                  listaL31.add(cd3);
        	  }
              listaL32.add(listaL31);
          }
          
          tileGroundL3 = new TileGroundEditor(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),listaL32,20,15,15,40);


          election = new PlayerEditor(BitmapFactory.decodeResource(getResources(), R.drawable.elaine) 
        		  , 0, 0    // initial position
        		  , 64, 64    // width and height of sprite
        		  , 5, 5);    // FPS and number of frames in the animation

        		           
          
          ///SENSOR ORIENTACION
          mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
          List<Sensor> listSensors = mSensorManager.getSensorList( 
                        Sensor.TYPE_ORIENTATION);
          if (!listSensors.isEmpty()) {
             Sensor orientationSensor = listSensors.get(0);
             mSensorManager.registerListener(this, orientationSensor,
                                        SensorManager.SENSOR_DELAY_GAME);
          }

    }



    @Override protected void onSizeChanged(int ancho, int alto,
                                                         int ancho_anter, int alto_anter) {

          super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);

          
          ultimoProceso = System.currentTimeMillis();
          thread.start();
          

    }



    @Override protected void onDraw(Canvas canvas) {
  		
          super.onDraw(canvas);
          tileGround.drawTileGround(canvas);
          tileGroundL2.drawTileGround(canvas);
          tileGroundL3.drawTileGround(canvas);
          election.draw(canvas,this.tileGround.getPosX(),this.tileGround.getPosY());
          Paint colorCirculo = new Paint();
          colorCirculo.setColor(Color.RED);
          colorCirculo.setStyle(Style.FILL);
          colorCirculo.setAlpha(100);
          Paint colorCirculo2 = new Paint();
          colorCirculo2.setColor(Color.BLACK);
          colorCirculo2.setStyle(Style.FILL);
          colorCirculo2.setAlpha(100);
          canvas.drawCircle(180, 600, 120, colorCirculo);
          canvas.drawCircle(180, 540, 40, colorCirculo2);
          canvas.drawCircle(180, 660, 40, colorCirculo2);
          canvas.drawCircle(120, 600, 40, colorCirculo2);
          canvas.drawCircle(240, 600, 40, colorCirculo2);
          
          Paint colorTexto = new Paint();
          colorTexto.setColor(Color.BLACK);
          colorTexto.setTextSize(40);
          SharedPreferences preferences;
		  preferences = PreferenceManager.getDefaultSharedPreferences(context);
		  String n = preferences.getString("nameworld", "nada");
		  canvas.drawText(n, 200, 50, colorTexto);
		  
		  canvas.drawCircle(this.getWidth()/2, this.getHeight(), 60, colorCirculo);
		  
		  if(hideControls){
			  canvas.drawRect(this.getWidth()-250, 50, this.getWidth()-50, 110, colorCirculo);//Guardar
			  canvas.drawRect(this.getWidth()-250, 160, this.getWidth()-50, 220, colorCirculo);//Opciones
			  canvas.drawRect(this.getWidth()-250, 270, this.getWidth()-50, 330, colorCirculo);//Suelo			  
			  canvas.drawCircle(this.getWidth()-600, 600, 40, colorCirculo);
			  canvas.drawCircle(this.getWidth()-500, 600, 40, colorCirculo);
			  canvas.drawCircle(this.getWidth()-400, 600, 40, colorCirculo);
	          
	          canvas.drawText("Save", this.getWidth()-250+50, 90, colorTexto);
	          canvas.drawText("Options", this.getWidth()-250+25, 200, colorTexto);
	          canvas.drawText("Ground", this.getWidth()-250+35, 310, colorTexto);	          
	          canvas.drawText("L1", this.getWidth()-600-20, 615, colorTexto);
	          canvas.drawText("L2", this.getWidth()-500-20, 615, colorTexto);
	          canvas.drawText("L3", this.getWidth()-400-20, 615, colorTexto);
	          
	          if(this.hideSelecGround){
	        	  canvas.drawRect(this.getWidth()/2-100, this.getHeight()/2-100, this.getWidth()/2+100, 
	        			  this.getHeight()/2+10, colorCirculo);
	        	  canvas.drawCircle(this.getWidth()/2-160, this.getHeight()/2-40, 40, colorCirculo2);
	        	  canvas.drawCircle(this.getWidth()/2+160, this.getHeight()/2-40, 40, colorCirculo2);
	        	  canvas.drawCircle(this.getWidth()/2, this.getHeight()/2-160, 40, colorCirculo2);
	        	  canvas.drawCircle(this.getWidth()/2, this.getHeight()/2+70, 40, colorCirculo2);
	        	  Ground g = new Ground(BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),
	        			  this.getWidth()/2-32,this.getHeight()/2-70,64*gx,64*gy,64,64);
	        	  g.draw(canvas, this);
	          }
		  }
		  canvas.drawRect(this.getWidth()-250, 560, this.getWidth()-50, 640, colorCirculo2);//Poner
		  canvas.drawText("Put", this.getWidth()-250+60, 610, colorTexto);
          

		  

          

          
          



    }
    
    protected void actualizaFisica() {
        long ahora = System.currentTimeMillis();
        // No hagas nada si el período de proceso no se ha cumplido.
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
              return;
        }
        // Para una ejecución en tiempo real calculamos retardo           
        double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora; // Para la próxima vez

        election.update(System.currentTimeMillis());
        election.updatePos(tileGroundL2);

        this.tileGround.updateLand((int)election.getX(), (int)election.getY());
        this.tileGroundL2.updateLand((int)election.getX(), (int)election.getY());
        this.tileGroundL3.updateLand((int)election.getX(), (int)election.getY());

	     
        
    }
    
    @Override
    public boolean onTouchEvent (MotionEvent event) {
       super.onTouchEvent(event);
       float x = event.getX();
       float y = event.getY();
       /*canvas.drawCircle(180, 540, 40, colorCirculo2);
         canvas.drawCircle(180, 660, 40, colorCirculo2);
         canvas.drawCircle(120, 600, 40, colorCirculo2);
         canvas.drawCircle(240, 600, 40, colorCirculo2);*/
       if(event.getAction()==MotionEvent.ACTION_MOVE){
    	   if(x>160 && x<200 && y>520 && y<560){
        	   //nave.setPosY(nave.getPosY()-1);
        	   //nave.setUp();
        	   election.setUp();
        	   
        	   //this.getThread().detener();
        	   
           }else if(x>160 && x<200 && y>640 && y<680){
        	   //nave.setPosY(nave.getPosY()+1);
        	   //nave.setDown();
        	   election.setDown();
        	   
           }else if(x>100 && x<140 && y>580 && y<620){
        	   //nave.setPosX(nave.getPosX()-1);
        	   //nave.setleft();
        	   election.setleft();
        	   
           }else if(x>220 && x<260 && y>580 && y<620){
        	   //nave.setPosX(nave.getPosX()+1);
        	   //nave.setRight();
        	   election.setRight();
           }else if(x>950 && x<1150 && y>580 && y< 640){//Poner ground
        	   //canvas.drawRect(950, 580, 1150, 640, colorCirculo);
        	   
           }else if(x>this.getWidth()-250 && x < this.getWidth()-50
        		   && y>160 && y < 220 && this.hideControls){//options
        	   Intent i = new Intent(context, Preferencias.class);
   			   context.startActivity(i);
           }else if(x>this.getWidth()-250 && x<this.getWidth()-50
        		   && y>270 && y< 330 && this.hideControls){//ground
        	   if(!this.hideSelecGround)
        		   this.hideSelecGround=true;
        	   else
        		   this.hideSelecGround=false;

           }else if(x>getWidth()/2+140 && x<getWidth()/2+180
        		   && y> this.getHeight()/2-60 && y<this.getHeight()/2-20 && this.hideSelecGround){//ground derecha
        	   //canvas.drawCircle(this.getWidth()/2+160, this.getHeight()/2-40, 40, colorCirculo);
        	   gx++;
        	   if(gx>15)
        		   gx=0;
           }else if(x>getWidth()/2-180 && x<getWidth()/2-140
        		   && y> this.getHeight()/2-60 && y<this.getHeight()/2-20 && this.hideSelecGround){//ground izquierda
        	   //canvas.drawCircle(this.getWidth()/2-160, this.getHeight()/2-40, 40, colorCirculo);
        	   gx--;
        	   if(gx<0)
        		   gx=15;
           }else if(x>this.getWidth()/2-20 && x<this.getWidth()/2+20
        		   && y>this.getHeight()/2-180 && y<this.getHeight()/2-140){
        	   //canvas.drawCircle(this.getWidth()/2, this.getHeight()/2-160, 40, colorCirculo2);
        	   gy--;
        	   if(gy<0){
        		   gy=11;
        	   }
           }else if(x>this.getWidth()/2-20 && x<this.getWidth()/2+20
        		   && y>this.getHeight()/2+50 && y<this.getHeight()/2+90){
        	   //canvas.drawCircle(this.getWidth()/2, this.getHeight()/2+70, 40, colorCirculo2);
        	   gy++;
        	   if(gy>11){
        		   gy=0;
        	   }
           }else if(x>this.getWidth()/2 && x<(this.getWidth()/2)+60
        		   &&y>this.getHeight()-60){
        	   //canvas.drawCircle(this.getWidth()/2, this.getHeight(), 60, colorCirculo);
        	   if(!this.hideControls)
        		   this.hideControls=true;
        	   else
        		   this.hideControls=false;
           }
           

       }else if(event.getAction()==MotionEvent.ACTION_UP){
    	   //nave.desactivateArrows();
    	   election.desactivateArrows();
       }

       


       return true;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

    private boolean hayValorInicial = false;
    private float valorInicial;

    @Override 
    public void onSensorChanged(SensorEvent event) {
          float valor = event.values[1];
          if (!hayValorInicial){
        	  valorInicial = valor;
              hayValorInicial = true;
          }
          
          //giroNave=(int) (valor-valorInicial)/3 ;
    }
    



	public ThreadJuego getThread() {
		return thread;
	}


}