package com.example.rpgexample;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	 public void lanzarJuego(View view) {

			Intent i = new Intent(this, Mundo.class);
			startActivity(i);

			}
	 
	 public void lanzarEditor(View view) {

			Intent i = new Intent(this, EditorMundo.class);
			startActivity(i);

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
		   //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		}
		 
		@Override protected void onPause() {
		   
		   //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		   super.onPause();
		   
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
		}

}
