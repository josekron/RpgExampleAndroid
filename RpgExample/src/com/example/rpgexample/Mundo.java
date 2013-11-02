package com.example.rpgexample;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Mundo extends Activity {

	private VistaJuego vistaJuego;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mundo);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);
	}

	
	@Override protected void onStart() {
		   super.onStart();
		   //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onResume() {
		   super.onResume();
		   vistaJuego.getThread().reanudar();
		   //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onPause() {
		   
		   //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		   super.onPause();
		   vistaJuego.getThread().pausar();
		   
		}
		 
		@Override protected void onStop() {
			
		   //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		   super.onStop();
		}
		 
		@Override protected void onRestart() {
		   super.onRestart();
		   //Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onDestroy() {
		   //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		   super.onDestroy();
		   vistaJuego.getThread().detener();
		}
}
