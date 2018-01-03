package org.loader.labelview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.accountantmoney.R;
/**
 * 理财云标签连接url
 * @author Administrator
 *
 */
public class UrlZixunActivity extends Activity {
	private TextView tv_zixun;
	private WebView wb_zixun;
	private ImageButton fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.url_zixun);
		tv_zixun = (TextView)findViewById(R.id.tv_zixun);
		wb_zixun = (WebView)findViewById(R.id.wb_zixun);
		
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UrlZixunActivity.this, MainActivity.class);
				startActivity(intent);
			} 
		});
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		String label = intent.getStringExtra("label");
		wb_zixun.getSettings().setJavaScriptEnabled(true);
		wb_zixun.setWebViewClient(new WebViewClient());
		wb_zixun.loadUrl(url);
		
		tv_zixun.setText(label);
		
	}
}
