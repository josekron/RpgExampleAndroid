package com.example.rpgexample;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Display;
import android.view.View;

public class TileGround {
	
	private View view;
	//private ArrayList<ArrayList<CoordGround>>grounds;
	private ArrayList<ArrayList<Ground>>grounds;
	private Bitmap bitmap;
	private int width;
	private int height;
	private int posX;
	private int posY;
	private int widthPlayer;
	private int heightPlayer;
	
	public TileGround(View view,Bitmap bitmap, ArrayList<ArrayList<CoordGround>>grounds, int width, int height, 
			int widthPlayer, int heightPlayer){
		this.view=view;
		//this.grounds = grounds;
		this.bitmap = bitmap;
		this.width = width;
		this.height= height;
		this.posX=0;
		this.posY=0;
		this.widthPlayer=widthPlayer;
		this.heightPlayer=heightPlayer;
		
		int x=posX;
		int y=posY;
		this.grounds=new ArrayList<ArrayList<Ground>>();
		for(ArrayList<CoordGround>list : grounds){
			ArrayList<Ground> listGround = new ArrayList<Ground>();
			for(CoordGround cd : list){
				//Ground(Bitmap bitmap, int x, int y, int srcX,int srcY, int srcWidth, int srcHeight)
				Ground g;
				if(cd!=null)//coordenadas -1,-1 (sin ground)
					g = new Ground(bitmap,x,y,64*cd.getX(),64*cd.getY(),64,64);
				else
					g = null;
				listGround.add(g);
				x+=64;
			}
			this.grounds.add(listGround);
			x=posX;
			y+=64;
		}
		
	}
	
	public void drawTileGround(Canvas canvas){
		for(ArrayList<Ground>list : grounds){
			for(Ground g : list){
				if(g!=null)
					g.draw(canvas,this.view);
			}
		}
	}
	
	public boolean checkIntersecPlayer(int posPlayerX, int posPlayerY){
		for(ArrayList<Ground>list : grounds){
			for(Ground g : list){
				if(g!=null){
					if(g.getY()+32>posPlayerY+heightPlayer){
						if(Math.abs((g.getX()+32)-(posPlayerX+widthPlayer))<33+widthPlayer 
								&& Math.abs((g.getY()+32)-(posPlayerY+heightPlayer))<33+heightPlayer+10){
							return true;
						}
					}else{
						if(Math.abs((g.getX()+32)-(posPlayerX+widthPlayer))<33+widthPlayer 
								&& Math.abs((g.getY()+32)-(posPlayerY+heightPlayer))<0){
							return true;
						}
					}
					
				}
					
			}
		}
		return false;
	}
	
	public void updateLand(int posPlayerX, int posPlayerY)
    {

        posX = posPlayerX - this.view.getWidth() / 2;
        if (posX < 0)
        {
            posX = 0;
        }
        else if (posX > (64*this.width - this.view.getWidth()))
        {
            posX = 64 * this.width - this.view.getWidth();
        }
        posY = posPlayerY - this.view.getHeight() / 2;
        if (posY < 0)
        {
            posY = 0;
        }
        if (posY > (64*this.height - this.view.getHeight()))
        {
            posY = 64 * this.height - this.view.getHeight();
        }
		
        int x=-posX;
		int y=-posY;
	
		for(ArrayList<Ground>list : this.grounds){
			for(Ground g : list){
				if(g!=null){
					g.setX(x);//x
					g.setY(y);//y
					g.updateGround();
				}
				
				x+=64;
			}
			x=-posX;
			y+=64;
		}

    }
	
	public int getPosX(){
		return this.posX;
	}
	
	public int getPosY(){
		return this.posY;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}

}
