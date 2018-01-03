package org.loader.labelview;   
 

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DataBase.FlagDAO;
import com.example.DataBase.InaccountDAO;
import com.example.DataBase.OutaccountDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_flag;
import com.example.qingdan.Tb_inaccount;
import com.example.qingdan.Tb_outaccount;


public class MainActivity extends ExpandableListActivity 
{    
	/**
	 * 资讯变量
	 */
	private ListView lv_zixun = null;
	private List<NewsItem> newLists = new ArrayList<NewsItem>();
	private NewsAdapter newsAdapter = null;
	
	private DrawerLayout mDrawerLayout = null;
    private ListView mLv,id_more ; 
    String[] str,str1;
    private TabHost tabhost;
	private GestureDetector gestureDetector; 
	/** 记录当前分页ID */
	private int currentView = 0;  
	private static int maxTabIndex = 4;  
	private static final int SWIPE_MIN_DISTANCE = 120;  
	private static final int SWIPE_MAX_OFF_PATH = 250;  
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;  
	View.OnTouchListener gestureListener;  
	/**
	 * 资讯列表
	 */
	  private ArrayList<HashMap<String, Object>>   listItems;    //存放文字、图片信息
	  private SimpleAdapter listItemAdapter;           //适配器    
	/*
	 * 侧面抽屉变量
	 */
	/* 组件 */
	private RelativeLayout switchAvatar;
	private ImageView faceImage;

	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 0xa1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	
	protected   int DATE_DIALOG_ID;
	
/*
 * 第一张卡片
 */  
	 
	private EditText txtLiuruMoney,txtLiuruTime,txtLiuruBeizhu,txtInHandler; 
	private Spinner spLiuruType;
	private Button LiuruOK,LiuruCancel;
	/*第二张卡片变量*/ 
	private int mYear,mMonth,mDay; 
	   TextView jine;
	   EditText money;
	   TextView shijian;
	   EditText riqi;
	   TextView leibie;
	   Spinner leibiefangshi;
	   TextView zhifu;
	   Spinner zhifufangshi;
	   EditText beizhu;
	   Button ok,cancel; 
	   /*
	    * 第三张卡片
	    */
	   private EditText bianqian;
	   private Button id_chuangjian,id_fangqi,id_yuyin;
	   private int InputResultCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置无标题
  		requestWindowFeature(Window.FEATURE_NO_TITLE);
  		//设置全屏
  		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      		
        setContentView(R.layout.activity_main);
        /*
         * 侧面抽屉变量初始化
         */ 
		switchAvatar = (RelativeLayout) findViewById(R.id.switch_face_rl);
		faceImage = (ImageView) findViewById(R.id.face);
		// 设置事件监听
		switchAvatar.setOnClickListener(listener); 
		lv_zixun = (ListView) findViewById(R.id.id_lv_zixun);
		int[] pics = { 
				R.drawable.a, 
				R.drawable.b, 
				R.drawable.c, 
				R.drawable.d, 
				R.drawable.e,
				R.drawable.f, 
				R.drawable.g, 
				R.drawable.h, 
				R.drawable.i
		};
		String[] descTitle = {
					"C919大型客机在上海进行转场前最后一次试飞",
					"北京天安门前悬挂中美两国国旗",
					"俄媒称土俄S400采用购案空难成交",
					"特朗普：美国通过武力求得和平",
					"特朗普向习近平展示外孙女用中文唱歌",
					"阅问集团在港上市，股价目前超100港元，暴涨90%",
					"中国黑客让美军战舰连续撞船",
					"特朗普对朝喊话：不要低估我们，也不要考验我们",
					"联想的AI之路：从数据计算到人工智能"
		};
		String[] urlString = {
				"http://www.sohu.com/picture/203091667?_f=index_chan08focus_2",
				"http://www.sohu.com/picture/203080046?_f=index_chan08focus_1",
				"http://www.sohu.com/a/203067816_114911?_f=index_chan08mil_culfocus_3",
				"http://www.sohu.com/a/203061252_626685?_f=index_chan08mil_culfocus_0",
				"http://www.sohu.com/a/203096243_115479?_f=index_chan08focus_0",
				"http://www.sohu.com/a/203070945_118622?_f=index_chan08businessfocus_3",
				"http://www.sohu.com/a/203050996_612653?_f=index_chan08mil_culfocus_1",
				"http://www.sohu.com/a/203045642_162522?_f=index_chan08news_10",
				"http://www.sohu.com/a/202883373_522913?_f=index_chan08businessfocus_0",
				
		};
			
		for (int i = 0; i < 9; i++) {
			newLists.add(new NewsItem(pics[i], descTitle[i] ,urlString[i]));
		}
		
		newsAdapter = new NewsAdapter(getApplicationContext(), newLists);
		lv_zixun.setAdapter(newsAdapter);
		lv_zixun.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String urlString = newLists.get(position).getContent();
				String labelString = newLists.get(position).getTitle();
				Intent intent = new Intent(MainActivity.this, UrlZixunActivity.class);
				intent.putExtra("url", urlString);
				intent.putExtra("label", labelString);
				startActivity(intent);
				
				
			}
			
		});
		
		mLv = (ListView)findViewById(R.id.id_lv);
        ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();
        for(int i = 0 ; i <= 5 ; i++ ){
        	HashMap<String,Object>map = new HashMap<String,Object>();
        	if(i == 0 ){
        		map.put("ItemImage", R.drawable.tuichu);//加入图片          
        		map.put("ItemTitle", "       首页");    
        	}else if(i == 1){
        		map.put("ItemImage", R.drawable.jinrong);//加入图片          
        		map.put("ItemTitle", "       收入");    
        	}else if(i == 2){
        		map.put("ItemImage", R.drawable.xiaofei);//加入图片          
        		map.put("ItemTitle", "       支出");    
        	}else if(i == 3){
        		map.put("ItemImage", R.drawable.shishi);//加入图片          
        		map.put("ItemTitle", "       数据存储");    
        	}else if(i == 4){
        		map.put("ItemImage", R.drawable.licai);//加入图片          
        		map.put("ItemTitle", "       理财标签云");   
        	}else if(i == 5){
        		map.put("ItemImage", R.drawable.zixun);//加入图片          
        		map.put("ItemTitle", "       退出");
        	}
        	listItem.add(map);   
        }
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,
        		R.layout.item,
        		new String[]{"ItemImage","ItemTitle"},
        		new int[]{R.id.ItemImage,R.id.ItemTitle}); 
         mLv.setAdapter(mSimpleAdapter);
         mLv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();		 
				if(arg2 == 0){
					 intent.setClass(MainActivity.this, MainActivity.class);
					 startActivity(intent);
				}else if(arg2 == 1){
					 intent.setClass(MainActivity.this, Inaccountinfo.class);
					 startActivity(intent);
				}else if(arg2 == 2){
					 intent.setClass(MainActivity.this, Outaccountinfo.class);
					 startActivity(intent);
				}else if(arg2 == 3){
					 intent.setClass(MainActivity.this, Showinfo.class);
					 startActivity(intent);
				}else if(arg2 == 4){
					 intent.setClass(MainActivity.this, TagCloudActivity.class);
					 startActivity(intent);
				}else if(arg2 == 5){
					   Intent startMain = new Intent(Intent.ACTION_MAIN);
					   startMain.addCategory(Intent.CATEGORY_HOME);
					   startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					   startActivity(startMain);
					   System.exit(0);
			}
         }});
         
         
         
        List<Map<String,String>> groups = new ArrayList<Map<String,String>>();
        Map<String,String>group1 = new HashMap<String,String>(); 
        group1.put("group", "作 者");
        Map<String,String>group2 = new HashMap<String,String>(); 
        group2.put("group", "版 本");
        Map<String,String>group3 = new HashMap<String,String>(); 
        group3.put("group", "版 权");
        Map<String,String>group4 = new HashMap<String,String>(); 
        group4.put("group", "简 介");
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);
        groups.add(group4);
        
        List<Map<String,String>>child1 = new ArrayList<Map<String,String>>();
        Map<String,String>childData1 = new HashMap<String,String>();
        childData1.put("child", "springandwinter@outlook.com");
        child1.add(childData1);
         
        List<Map<String,String>> child2 = new ArrayList<Map<String,String>>();
        Map<String,String> child2Data = new HashMap<String,String>();
        child2Data.put("child", "AccountMoney version 1.1");
        child2.add(child2Data);
        
        List<Map<String,String>> child3 = new ArrayList<Map<String,String>>();
        Map<String,String> child3Data = new HashMap<String,String>();
        child3Data.put("child", "该版权归属江西师范大学所有");
        child3.add(child3Data);
        
        List<Map<String,String>> child4 = new ArrayList<Map<String,String>>();
        Map<String,String> child4Data = new HashMap<String,String>();
        child4Data.put("child", "该app适用于个人的账户管理，用于个人的一个小型记账平台，简洁易用，这样你每天的money的出入我帮你牢记于心哦！");
        child4.add(child4Data);
        
        List<List<Map<String,String>>>childs = new ArrayList<List<Map<String,String>>>();
        childs.add(child1);
        childs.add(child2);
        childs.add(child3);
        childs.add(child4); 
        SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(this, groups, R.layout.group ,new String[]{"group"}, 
        		new int[]{R.id.groupTo},childs, R.layout.child, new String[]{"child"},new int[]{R.id.childTo}  );
        //将SimpleExpandableListAdapter对象设置给当前的ExpandagleListAdapter
        setListAdapter(sela); 
        //得到TabHost对象实例
          tabhost =(TabHost) findViewById(R.id.tabs);
        //调用 TabHost.setup()
        tabhost.setup();
        //创建Tab标签
        tabhost.addTab(tabhost.newTabSpec("one").setIndicator("收入").setContent(R.id.shouru)); 
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("支出").setContent(R.id.zhichu));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("便签").setContent(R.id.bianqian)); 
        tabhost.addTab(tabhost.newTabSpec("four").setIndicator("资讯").setContent(R.id.zixun)); 
        tabhost.addTab(tabhost.newTabSpec("five").setIndicator("更多").setContent(R.id.gengduo)); 

    	TextView tv = (TextView) tabhost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); 
    	tv.setTextSize(12);//设置标题字体大小 
    	tv.setTextColor(Color.WHITE);
        
    	TextView tv1 = (TextView) tabhost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); 
    	tv1.setTextSize(12);//设置标题字体大小 
    	tv1.setTextColor(Color.WHITE);
    	
    	TextView tv2= (TextView) tabhost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); 
    	tv2.setTextSize(12);//设置标题字体大小 
    	tv2.setTextColor(Color.WHITE);
    	
    	TextView tv3 = (TextView) tabhost.getTabWidget().getChildAt(3).findViewById(android.R.id.title); 
    	tv3.setTextSize(12);//设置标题字体大小 
    	tv3.setTextColor(Color.WHITE);
    	
    	TextView tv4 = (TextView) tabhost.getTabWidget().getChildAt(4).findViewById(android.R.id.title); 
    	tv4.setTextSize(12);//设置标题字体大小 
    	tv4.setTextColor(Color.WHITE);
    	
        tabhost.setCurrentTab(0);

        gestureDetector = new GestureDetector(new MyGestureDetector());  
		gestureListener = new View.OnTouchListener() {  
			@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
				 if (gestureDetector.onTouchEvent(event)) { 
				  return true;  
				  }
				return false;
			}
    	};   
    	/*
    	 * 第一张卡片
    	 */ 
    	txtLiuruMoney = (EditText)findViewById(R.id.txtLiuruMoney); 
    	txtLiuruTime = (EditText)findViewById(R.id.txtLiuruTime);      
    	Time time = new Time("GMT+8");       
    	time.setToNow();      
    	int year = time.year;      
    	int month = time.month;      
        int day = time.monthDay;      
        txtLiuruTime.setText(year+"-"+month+"-"+day);    
    	
    	txtLiuruBeizhu = (EditText)findViewById(R.id.txtLiuruBeizhu); 
        spLiuruType = (Spinner)findViewById(R.id.spLiuruType);
        txtInHandler = (EditText)findViewById(R.id.txtfukuanf);
        LiuruOK = (Button)findViewById(R.id.LiuruOK);
        LiuruCancel = (Button)findViewById(R.id.LiuchuCancel);   
        txtLiuruTime.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
			@SuppressWarnings("deprecation")
			@Override
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DATE_DIALOG_ID = 0;
				showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
			}
		});

        LiuruOK.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strInMoney = txtLiuruMoney.getText().toString();// 获取金额文本框的值
				if (!strInMoney.isEmpty()) {// 判断金额不为空
					// 创建InaccountDAO对象
					InaccountDAO inaccountDAO = new InaccountDAO(
							MainActivity.this);
					 
					Tb_inaccount tb_inaccount = new Tb_inaccount(
							inaccountDAO.getMaxId() + 1, Double
									.parseDouble(strInMoney), txtLiuruTime
									.getText().toString(), spLiuruType
									.getSelectedItem().toString(),
							txtInHandler.getText().toString(),
							txtLiuruBeizhu.getText().toString());
					inaccountDAO.add(tb_inaccount);// 添加收入信息
					// 弹出信息提示
					Toast.makeText(MainActivity.this, "〖新增收入〗数据添加成功！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Inaccountinfo.class);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "请输入收入金额！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

        LiuruCancel.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				txtLiuruMoney.setText("");// 设置金额文本框为空
				txtLiuruMoney.setHint("0.00");// 为金额文本框设置提示
				txtLiuruTime.setText("");// 设置时间文本框为空
				txtLiuruTime.setHint("2015-11-11");// 为时间文本框设置提示 
				txtLiuruBeizhu.setText("");// 设置备注文本框为空
				txtLiuruBeizhu.setSelection(0);// 设置类别下拉列表默认选择第一项
			}
		}); 
		final Calendar c = Calendar.getInstance();// 获取当前系统日期
		mYear = c.get(Calendar.YEAR);// 获取年份
		mMonth = c.get(Calendar.MONTH);// 获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数  
    	
    	/*第二张卡片*/
    	jine = (TextView)findViewById(R.id.tvMoney);
		money = (EditText)findViewById(R.id.txtMoney);
		shijian = (TextView)findViewById(R.id.tvTime);
		riqi = (EditText)findViewById(R.id.txtTime);
		leibie = (TextView)findViewById(R.id.tvType);
		leibiefangshi = (Spinner)findViewById(R.id.spType);
		zhifu = (TextView)findViewById(R.id.tvTzhifu);
		zhifufangshi = (Spinner)findViewById(R.id.zhifuType);
		beizhu = (EditText)findViewById(R.id.txtBeizhu);
		Time time1 = new Time("GMT+8");       
    	time1.setToNow();      
    	int year1 = time1.year;      
    	int month1 = time1.month;      
        int day1 = time1.monthDay;          
        riqi.setText(year1+"-"+month1+"-"+day1);    
		
		
		riqi.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DATE_DIALOG_ID = 1;
				showDialog(DATE_DIALOG_ID); 
			} 
		});  
		ok = (Button)findViewById(R.id.OK);
		cancel = (Button)findViewById(R.id.Cancel);
		ok.setOnClickListener(new OnClickListener(){ 
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String Smoney = money.getText().toString();
				if(!Smoney.isEmpty()){
					OutaccountDAO zhichuDB = new OutaccountDAO(
							MainActivity.this);
					Tb_outaccount zhichufuzhi = new Tb_outaccount(
							zhichuDB.getMaxId()+1,Double.parseDouble(Smoney),
							riqi.getText().toString(),
							leibiefangshi.getSelectedItem().toString(),
							zhifufangshi.getSelectedItem().toString(),
							beizhu.getText().toString());
					zhichuDB.add(zhichufuzhi);
					Toast.makeText(MainActivity.this, "已录入了该笔支出"+Double.parseDouble(Smoney), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Outaccountinfo.class);
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this, "请输入该消费金额", Toast.LENGTH_SHORT).show();
				}
			}
		}); 
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				money.setText("");
				money.setHint("0.00");
				riqi.setText("");
				riqi.setHint("2015-11-11");
				beizhu.setText("");
				leibiefangshi.setSelection(0);
				zhifufangshi.setSelection(0);
			}
			
		}); 
		final Calendar c1 = Calendar.getInstance();// 获取当前系统日期
		mYear = c1.get(Calendar.YEAR);// 获取年份
		mMonth = c1.get(Calendar.MONTH);// 获取月份
		mDay = c1.get(Calendar.DAY_OF_MONTH);// 获取天数 
		 /*
	     * 第三张卡片便签
	     */
		bianqian = (EditText)findViewById(R.id.mybianqian);
		id_chuangjian = (Button)findViewById(R.id.id_chuangjian);
		id_fangqi = (Button)findViewById(R.id.id_fangqi);
		id_yuyin = (Button)findViewById(R.id.yuyin);
		id_yuyin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
				try{
					startActivityForResult(intent,InputResultCode);
					bianqian.setText("");
				}catch(ActivityNotFoundException a){
					Toast t = Toast.makeText(getApplicationContext(), "抱歉您的系统不支持语音输入", Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
		id_chuangjian.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strFlag = bianqian.getText().toString();// 获取便签文本框的值
				if (!strFlag.isEmpty()) {// 判断获取的值不为空
					FlagDAO flagDAO = new FlagDAO(MainActivity.this);// 创建FlagDAO对象
					Tb_flag tb_flag = new Tb_flag(
							flagDAO.getMaxId() + 1, strFlag);// 创建Tb_flag对象
					flagDAO.add(tb_flag);// 添加便签信息
					// 弹出信息提示
					Toast.makeText(MainActivity.this, "〖新增便签〗数据添加成功！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Showinfo.class);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "请输入便签！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		id_fangqi.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bianqian.setText("");// 清空便签文本框
			}
		});
		
		/**
		 * 第四张卡片
		 */
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Music： " + i); // 文字
			// map.put("ItemImage", R.drawable.music); //图片
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(this, listItems, // listItems数据源
				R.layout.list_item, // ListItem的XML布局实现
				new String[] { "ItemTitle", "ItemImage" }, // 动态数组与ImageItem对应的子项
				new int[] { R.id.ItemTitle, R.id.ItemImage } // list_item.xml布局文件里面的一个ImageView的ID,一个TextView
																// 的ID
		);
        
           
        
		
 }
    
    protected void onListItemClick(ListView l, View v, int position, long id)  {
        // TODO Auto-generated method stub
        Log.e("position", "" + position);
        setTitle("你点击第"+position+"行"); 
    }
    
    /**
     * 侧面抽屉
     */
	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
		}
	};

	/**
	 * 显示选择对话框
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1: 
							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Tools.hasSdcard()) {
					            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					                    .fromFile(new File(Environment
					                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
					        }

					        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
							
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show(); 
	} 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				} 
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageBitmap(photo);
		}
	}

	
    /*第二张卡片*/
	@Override 
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub 
		 if(id == 0){
			 return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay); 
		 }else if(id == 1){
			 return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);  
		 }return null;
	}
private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		mYear = year;
		mMonth = month+1;
		mDay = day;
		updateDisplay(); 
	}
};
public void updateDisplay(){
	 if(DATE_DIALOG_ID == 0){
		 txtLiuruTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(mDay));
	 }else if(DATE_DIALOG_ID == 1){
		 riqi.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(mDay));
	 }
	
}
	@Override
	 public boolean dispatchTouchEvent(MotionEvent ev) {
		 if (gestureDetector.onTouchEvent(ev)) {
			 ev.setAction(MotionEvent.ACTION_CANCEL); 
		 }
		 return super.dispatchTouchEvent(ev); 
	}

	
	// 左右滑动刚好页面也有滑动效果   
	class MyGestureDetector extends SimpleOnGestureListener {
		  @Override  
		  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, 
				  float velocityY){
			   try {
				   if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) 
					   return false;  
				   if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE 
						   && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
					   Log.i("test", "right");  
					   if (currentView == maxTabIndex){
						   currentView = maxTabIndex; 
					   }else { 
						   currentView++;  
					   }
					   tabhost.setCurrentTab(currentView);   
				   }else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE  
						   && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					   Log.i("test", "left");  
					   if (currentView == 0) {  
						   currentView = 0;   
				   }else{
					   currentView --;   
				   }
					   tabhost.setCurrentTab(currentView); 
			   }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
			   return false;
		  }
	}
}