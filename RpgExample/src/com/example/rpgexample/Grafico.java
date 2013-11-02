package com.example.rpgexample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.View;

class Grafico {

    private Drawable drawable;   //Imagen que dibujaremos

    private double posX, posY;   //Posición

    private double incX, incY;   //Velocidad desplazamiento

    private int ancho, alto;     //Dimensiones de la imagen

    private int radioColision;   //Para determinar colisión

     //Donde dibujamos el gráfico (usada en view.ivalidate)

    private View view;

     // Para determinar el espacio a borrar (view.ivalidate)

    public static final int MAX_VELOCIDAD = 20;

   private boolean up, down, right, left = false;

    public Grafico(View view, Drawable drawable){

          this.view = view;

          this.drawable = drawable;

          ancho = drawable.getIntrinsicWidth();  

          alto = drawable.getIntrinsicHeight();

          radioColision = (alto+ancho)/4;
          this.posX=0;
          this.posY=0;

    }

         public void dibujaGrafico(Canvas canvas, int posGX, int posGY){

          canvas.save();

          int x=(int) (posX+ancho/2)-posGX;

          int y=(int) (posY+alto/2)-posGY;


          drawable.setBounds((int)posX-posGX, (int)posY-posGY,(int)posX-posGX+ancho, (int)posY-posGY+alto);

          drawable.draw(canvas);


          canvas.restore();

          int rInval = (int) Math.hypot(ancho,alto)/2 + MAX_VELOCIDAD;

          view.invalidate(x-rInval, y-rInval, x+rInval, y+rInval);

    }
    public void updatePos(int posGX, int posGY, int widthG, int heightG){
    	if(this.down){
			//posY+=4;
    		if (posY+60 < heightG*64)
            {
    			posY+=4;
            }
    	}
    	if(this.up){
    		if(posY > 0)
    			posY-=4;
    	}
    	if(this.left){
    		if(posX > 0)
    			posX-=4;
    	}
    	if(this.right){
    		//posX+=4;
    		if (posX+60 < widthG*64)
            {
    			posX+=4;
            }
    	}
    }

    public void incrementaPos(double factor){

          if(posX<-ancho/2) {
        	  //posX=view.getWidth()-ancho/2;
        	  posX=-ancho/2+1;
          }

          if(posX>view.getWidth()-ancho/2) {
        	  //posX=-ancho/2;
        	  posX=view.getWidth()-ancho/2-1;
          }

          posY+=incY * factor;

          if(posY<-alto/2) {
        	  //posY=view.getHeight()-alto/2;
        	  posY=-alto/2+1;
          }

          if(posY>view.getHeight()-alto/2) {
        	  //posY=-alto/2;
        	  posY=view.getHeight()-alto/2-1;
          }

    }
    




    public double distancia(Grafico g) {

          return Math.hypot(posX-g.posX, posY-g.posY);

    }



    public boolean verificaColision(Grafico g) {

          return(distancia(g) < (radioColision+g.radioColision));

    }

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getIncX() {
		return incX;
	}

	public void setIncX(double incX) {
		this.incX = incX;
	}

	public double getIncY() {
		return incY;
	}

	public void setIncY(double incY) {
		this.incY = incY;
	}


	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getRadioColision() {
		return radioColision;
	}

	public void setRadioColision(int radioColision) {
		this.radioColision = radioColision;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getMaxVelocidad() {
		return MAX_VELOCIDAD;
	}
	
	public void setUp(){
		this.up=true;
		this.down=false;
		this.left=false;
		this.right=false;
	}
	public void setDown(){
		this.down=true;
		this.up=false;
		this.left=false;
		this.right=false;
	}
	
	public void setRight(){
		this.right=true;
		this.up=false;
		this.down=false;
		this.left=false;
	}
	
	public void setleft(){
		this.left=true;
		this.up=false;
		this.down=false;
		this.right=false;
	}
	
	public void desactivateArrows(){
		this.up=false;
		this.down=false;
		this.left=false;
		this.right=false;
	}



}