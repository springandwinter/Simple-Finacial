package org.loader.labelview;

import java.util.Timer;
import java.util.TimerTask;

import com.example.accountantmoney.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_third);
        final Intent intent = new Intent();
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){ 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				intent.setClass(ThirdActivity.this, MainActivity.class);
				startActivity(intent);
			} 
        };
        timer.schedule(task, 1000*1);
    } 
}
