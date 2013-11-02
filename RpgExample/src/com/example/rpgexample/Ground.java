package com.example.rpgexample;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class Ground {

	private Bitmap bitmap;      // the animation sequence
	private int x;              // the X coordinate of the object (top left of the image)
	private int y;              // the Y coordinate of the object (top left of the image)
	private int srcX;
	private int srcY;
	private int srcWidth;
	private int srcHeight;
	private Rect srcRect;
    private Rect dstRect;
    public static final int MAX_VELOCIDAD = 20;


	public Ground(Bitmap bitmap, int x, int y, int srcX,int srcY, int srcWidth, int srcHeight) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.srcX = srcX;
		this.srcY=srcY;
		this.srcWidth=srcWidth;
		this.srcHeight=srcHeight;
		
		srcRect = new Rect();
		dstRect = new Rect();
		
		srcRect.left = srcX;
	    srcRect.top = srcY;
	    srcRect.right = srcX + srcWidth;// - 1;//hay que restar uno o el valor seria 1 mayor que el pixel real
	    srcRect.bottom = srcY + srcHeight;// - 1;
	    dstRect.left = x;
	    dstRect.top = y;
	    dstRect.right = x + srcWidth;// - 1;
	    dstRect.bottom = y + srcHeight;// - 1;


	}
	

	
	public void draw(Canvas canvas, View view) {
		canvas.save();
		canvas.drawBitmap(bitmap, srcRect, dstRect,null);
		canvas.restore();
		int rInval = (int) Math.hypot(64,64)/2 + MAX_VELOCIDAD;

        view.invalidate(x-rInval, y-rInval, x+rInval, y+rInval);
	}
	
	public void drawInPos(Canvas canvas, View view, int gx, int gy) {
		//new Ground(bitmap,x,y,64*cd.getX(),64*cd.getY(),64,64);
		//Bitmap bitmap, int x, int y, int srcX,int srcY, int srcWidth, int srcHeight
		Rect srcRect2 = new Rect();
	    Rect dstRect2 = new Rect();
		srcRect2.left = 64*gx;
	    srcRect2.top = 64*gy;
	    srcRect2.right = 64*gx + 64;// - 1;//hay que restar uno o el valor seria 1 mayor que el pixel real
	    srcRect2.bottom = 64*gy + 64;// - 1;
	    dstRect2.left = 100;
	    dstRect2.top = 150;
	    dstRect2.right = 100 + 64;// - 1;
	    dstRect2.bottom = 150 + 64;// - 1;
	    
		canvas.save();
		canvas.drawBitmap(bitmap, srcRect2, dstRect2,null);
		canvas.restore();
		int rInval = (int) Math.hypot(64,64)/2 + MAX_VELOCIDAD;

        view.invalidate(100-rInval, 100-rInval, 150+rInval, 150+rInval);
	}
	
	public void updateGround(){
		srcRect = new Rect();
		dstRect = new Rect();
		
		srcRect.left = srcX;
	    srcRect.top = srcY;
	    srcRect.right = srcX + srcWidth;// - 1;//hay que restar uno o el valor seria 1 mayor que el pixel real
	    srcRect.bottom = srcY + srcHeight;// - 1;
	    dstRect.left = x;
	    dstRect.top = y;
	    dstRect.right = x + srcWidth;// - 1;
	    dstRect.bottom = y + srcHeight;// - 1;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	public void setX(int posX){
		this.x = posX;
	}
	
	public void setY(int posY){
		this.y = posY;
	}



}
