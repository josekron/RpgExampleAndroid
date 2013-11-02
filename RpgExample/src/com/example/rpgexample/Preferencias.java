package com.example.rpgexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceActivity;




@SuppressLint("NewApi")
public class Preferencias extends PreferenceActivity {

   @Override protected void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);

      addPreferencesFromResource(R.xml.preferenciaeditor);
      //addPreferencesFromResource(R.xml.preferencias_editor);
      //setContentView(R.layout.acercade); 
 
   }
	
	

}