package org.loader.labelview;

import org.loader.labelview.LabelView.OnItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.accountantmoney.R;

public class TagCloudActivity extends Activity {
	private LabelView mLabelView;
	private ImageButton fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.tagcloud);
		
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(TagCloudActivity.this, MainActivity.class);
				startActivity(intent);
			} 
		});
		mLabelView = (LabelView) findViewById(R.id.lv);
		
		mLabelView.setLabels(new String[] {
				"理财产品", 
				"理财游戏有哪些",
				"理财小知识", 
				"银行理财",  
				"理财软件", 
				"网络理财", 
				"悟空理财",
				"理财方式有哪些"
				});
		mLabelView.setColorSchema(new int[] {Color.DKGRAY,Color.BLUE, Color.CYAN, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED});
		mLabelView.setSpeeds(new int[][] {{1,2},{1,1},{2,1},{2,3}});
		mLabelView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int index, String label) {
				switch(index){
					case 0:
						String url = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString = label;
						Intent intent = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent.putExtra("url", url);
						intent.putExtra("label", labelString);
						startActivity(intent);
						break;
					case 1:
						String url1 = "http://www.qqtn.com/qqkey/lcyxapp/";
						String labelString1 = label;
						Intent intent1 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent1.putExtra("url", url1);
						intent1.putExtra("label", labelString1);
						startActivity(intent1);
						break;
					case 2:
						String url2 = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString2 = label;
						Intent intent2 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent2.putExtra("url", url2);
						intent2.putExtra("label", labelString2);
						startActivity(intent2);
						break;
					case 3:
						String url3 = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString3 = label;
						Intent intent3 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent3.putExtra("url", url3);
						intent3.putExtra("label", labelString3);
						startActivity(intent3);
						break;
					case 4:
						String url4 = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString4 = label;
						Intent intent4 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent4.putExtra("url", url4);
						intent4.putExtra("label", labelString4);
						startActivity(intent4);
						break;
					case 5:
						String url5 = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString5 = label;
						Intent intent5 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent5.putExtra("url", url5);
						intent5.putExtra("label", labelString5);
						startActivity(intent5);
						break;
					case 6:
						String url6 = "http://finance.qq.com/l/financenews/jinrongshichang/jinrong.htm";
						String labelString6 = label;
						Intent intent6 = new Intent(TagCloudActivity.this, UrlLicaiActivity.class);
						intent6.putExtra("url", url6);
						intent6.putExtra("label", labelString6);
						startActivity(intent6);
						break;
				}
			}
		});
	}
}
