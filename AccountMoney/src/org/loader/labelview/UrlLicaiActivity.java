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
public class UrlLicaiActivity extends Activity {
	private TextView tv_licai;
	private WebView wb_licai;
	private ImageButton fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.url_licai);
		tv_licai = (TextView)findViewById(R.id.tv_licai);
		wb_licai = (WebView)findViewById(R.id.wb_licai);
		
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UrlLicaiActivity.this, TagCloudActivity.class);
				startActivity(intent);
			} 
		});
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		String label = intent.getStringExtra("label");
		wb_licai.getSettings().setJavaScriptEnabled(true);
		wb_licai.setWebViewClient(new WebViewClient());
		wb_licai.loadUrl(url);
		
		tv_licai.setText(label);
		
	}
}
