package com.example.contrastincrementor;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	
	Button ok ;
	
	EditText heightview;
	SharedPreferences preference;
	SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final SharedPreferences.Editor editor = preference.edit();
		//editor.clear().commit();
		setContentView(R.layout.activity_main);
		
		ok = (Button) findViewById(R.id.ok);
		
		heightview = (EditText) findViewById(R.id.height);
		
		try{
		ok.setOnClickListener(new OnClickListener () {
			public void onClick(View v) {
				Editable height = heightview.getText();
				System.out.println(height);

				Intent intent = new Intent("com.example.contrastincrementor.IQ");
				startActivity(intent);
					}		
		});
		
		}
		catch (Exception E){
			E.printStackTrace();
			
		}
		
		
		
		
		
    }
		
		
		
		
		
		

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


/*

public boolean dispatchKeyEvent(KeyEvent event) {
    int action = event.getAction();
    int keyCode = event.getKeyCode();
        switch (keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
            if (action == KeyEvent.ACTION_UP) {
                //TODO
            }
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            if (action == KeyEvent.ACTION_DOWN) {
                //TODO
            }
            return true;
        default:
            return super.dispatchKeyEvent(event);
        }
    }*/