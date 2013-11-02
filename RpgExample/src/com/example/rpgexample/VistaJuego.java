package com.example.rpgexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import android.annotation.SuppressLint;
import android.content.Context;
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
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;



@SuppressLint("NewApi")
public class VistaJuego extends View implements SensorEventListener{

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
	private static int PERIODO_PROCESO = 50;
	// Cuando se realizó el último proceso
	private long ultimoProceso = 0;
	

    Ground suelo;
    TileGround tileGround;
    TileGround tileGroundL2;
    TileGround tileGroundL3;
    private Player elaine;



    public VistaJuego(Context context, AttributeSet attrs) {

    	

          super(context, attrs);


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
          tileGround = new TileGround(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),lista2,20,15,15,40);
          
          ArrayList<CoordGround>listaL21 = new ArrayList<CoordGround>();
          ArrayList<ArrayList<CoordGround>>listaL22 = new ArrayList<ArrayList<CoordGround>>();
          CoordGround cd2; 
          
          for(int i=0;i<3;i++){
        	  listaL21 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd2 = null;
                  listaL21.add(cd2);
        	  }
              listaL22.add(listaL21);
          }
          for(int i=0;i<3;i++){
        	  listaL21 = new ArrayList<CoordGround>();
        	  cd2 = null;
              listaL21.add(cd2);
              cd2 = null;
              listaL21.add(cd2);
        	  for(int j =0;j<3;j++){
        		  cd2 = new CoordGround(7,3);
                  listaL21.add(cd2);
        	  }
              for(int j =0;j<15;j++){
        		  cd2 = null;
                  listaL21.add(cd2);
        	  }
              listaL22.add(listaL21);
          }
          for(int i=0;i<9;i++){
        	  listaL21 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd2 = null;
                  listaL21.add(cd2);
        	  }
              listaL22.add(listaL21);
          }
          tileGroundL2 = new TileGround(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),listaL22,20,15,15,40);
          
          ArrayList<CoordGround>listaL31 = new ArrayList<CoordGround>();
          ArrayList<ArrayList<CoordGround>>listaL32 = new ArrayList<ArrayList<CoordGround>>();
          CoordGround cd3; 
          
          for(int i=0;i<2;i++){
        	  listaL31 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd3 = null;
                  listaL31.add(cd3);
        	  }
              listaL32.add(listaL31);
          }
          for(int i=0;i<1;i++){
        	  listaL31 = new ArrayList<CoordGround>();
        	  cd3 = null;
              listaL31.add(cd3);
              cd3 = null;
              listaL31.add(cd3);
        	  for(int j =0;j<3;j++){
        		  cd3 = new CoordGround(13,3);
                  listaL31.add(cd3);
        	  }
              for(int j =0;j<15;j++){
        		  cd3 = null;
                  listaL31.add(cd3);
        	  }
              listaL32.add(listaL31);
          }
          for(int i=0;i<12;i++){
        	  listaL31 = new ArrayList<CoordGround>();
        	  for(int j =0;j<20;j++){
        		  cd3 = null;
                  listaL31.add(cd3);
        	  }
              listaL32.add(listaL31);
          }
          tileGroundL3 = new TileGround(this,BitmapFactory.decodeResource(getResources(), R.drawable.tileworld),listaL32,20,15,15,40);


          elaine = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.elaine) 
        		  , 10, 50    // initial position
        		  , 30, 47    // width and height of sprite
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
          

          elaine.draw(canvas,this.tileGround.getPosX(),this.tileGround.getPosY());
          tileGroundL3.drawTileGround(canvas);



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

        elaine.update(System.currentTimeMillis());
        elaine.updatePos(tileGroundL2);

        this.tileGround.updateLand((int)elaine.getX(), (int)elaine.getY());
        this.tileGroundL2.updateLand((int)elaine.getX(), (int)elaine.getY());
        this.tileGroundL3.updateLand((int)elaine.getX(), (int)elaine.getY());

	     
        
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
        	   elaine.setUp();
        	   
           }else if(x>160 && x<200 && y>640 && y<680){
        	   //nave.setPosY(nave.getPosY()+1);
        	   //nave.setDown();
        	   elaine.setDown();
        	   
           }else if(x>100 && x<140 && y>580 && y<620){
        	   //nave.setPosX(nave.getPosX()-1);
        	   //nave.setleft();
        	   elaine.setleft();
        	   
           }else if(x>220 && x<260 && y>580 && y<620){
        	   //nave.setPosX(nave.getPosX()+1);
        	   //nave.setRight();
        	   elaine.setRight();
           }
           

       }else if(event.getAction()==MotionEvent.ACTION_UP){
    	   //nave.desactivateArrows();
    	   elaine.desactivateArrows();
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