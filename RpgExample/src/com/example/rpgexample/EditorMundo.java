package com.example.rpgexample;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

public class EditorMundo extends Activity {

	private VistaEditor vistaEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editormundo);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		vistaEditor = (VistaEditor) findViewById(R.id.VistaEditor);

		
		
	}


	public void lanzarFinish(View view){

        finish();

      }
	@Override protected void onStart() {
		   super.onStart();
		   //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onResume() {
		   super.onResume();
		   vistaEditor.getThread().reanudar();
		   //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onPause() {
		   
		   //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		   super.onPause();
		   vistaEditor.getThread().pausar();
		   
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
		   vistaEditor.getThread().detener();
		}
}
