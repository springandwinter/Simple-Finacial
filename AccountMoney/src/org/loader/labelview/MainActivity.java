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
	 * ��Ѷ����
	 */
	private ListView lv_zixun = null;
	private List<NewsItem> newLists = new ArrayList<NewsItem>();
	private NewsAdapter newsAdapter = null;
	
	private DrawerLayout mDrawerLayout = null;
    private ListView mLv,id_more ; 
    String[] str,str1;
    private TabHost tabhost;
	private GestureDetector gestureDetector; 
	/** ��¼��ǰ��ҳID */
	private int currentView = 0;  
	private static int maxTabIndex = 4;  
	private static final int SWIPE_MIN_DISTANCE = 120;  
	private static final int SWIPE_MAX_OFF_PATH = 250;  
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;  
	View.OnTouchListener gestureListener;  
	/**
	 * ��Ѷ�б�
	 */
	  private ArrayList<HashMap<String, Object>>   listItems;    //������֡�ͼƬ��Ϣ
	  private SimpleAdapter listItemAdapter;           //������    
	/*
	 * ����������
	 */
	/* ��� */
	private RelativeLayout switchAvatar;
	private ImageView faceImage;

	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	/* ͷ������ */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 0xa1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	
	protected   int DATE_DIALOG_ID;
	
/*
 * ��һ�ſ�Ƭ
 */  
	 
	private EditText txtLiuruMoney,txtLiuruTime,txtLiuruBeizhu,txtInHandler; 
	private Spinner spLiuruType;
	private Button LiuruOK,LiuruCancel;
	/*�ڶ��ſ�Ƭ����*/ 
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
	    * �����ſ�Ƭ
	    */
	   private EditText bianqian;
	   private Button id_chuangjian,id_fangqi,id_yuyin;
	   private int InputResultCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //�����ޱ���
  		requestWindowFeature(Window.FEATURE_NO_TITLE);
  		//����ȫ��
  		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      		
        setContentView(R.layout.activity_main);
        /*
         * ������������ʼ��
         */ 
		switchAvatar = (RelativeLayout) findViewById(R.id.switch_face_rl);
		faceImage = (ImageView) findViewById(R.id.face);
		// �����¼�����
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
					"C919���Ϳͻ����Ϻ�����ת��ǰ���һ���Է�",
					"�����찲��ǰ����������������",
					"��ý������S400���ù������ѳɽ�",
					"�����գ�����ͨ��������ú�ƽ",
					"��������ϰ��ƽչʾ����Ů�����ĳ���",
					"���ʼ����ڸ����У��ɼ�Ŀǰ��100��Ԫ������90%",
					"�й��ڿ�������ս������ײ��",
					"�����նԳ���������Ҫ�͹����ǣ�Ҳ��Ҫ��������",
					"�����AI֮·�������ݼ��㵽�˹�����"
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
        		map.put("ItemImage", R.drawable.tuichu);//����ͼƬ          
        		map.put("ItemTitle", "       ��ҳ");    
        	}else if(i == 1){
        		map.put("ItemImage", R.drawable.jinrong);//����ͼƬ          
        		map.put("ItemTitle", "       ����");    
        	}else if(i == 2){
        		map.put("ItemImage", R.drawable.xiaofei);//����ͼƬ          
        		map.put("ItemTitle", "       ֧��");    
        	}else if(i == 3){
        		map.put("ItemImage", R.drawable.shishi);//����ͼƬ          
        		map.put("ItemTitle", "       ���ݴ洢");    
        	}else if(i == 4){
        		map.put("ItemImage", R.drawable.licai);//����ͼƬ          
        		map.put("ItemTitle", "       ��Ʊ�ǩ��");   
        	}else if(i == 5){
        		map.put("ItemImage", R.drawable.zixun);//����ͼƬ          
        		map.put("ItemTitle", "       �˳�");
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
        group1.put("group", "�� ��");
        Map<String,String>group2 = new HashMap<String,String>(); 
        group2.put("group", "�� ��");
        Map<String,String>group3 = new HashMap<String,String>(); 
        group3.put("group", "�� Ȩ");
        Map<String,String>group4 = new HashMap<String,String>(); 
        group4.put("group", "�� ��");
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
        child3Data.put("child", "�ð�Ȩ��������ʦ����ѧ����");
        child3.add(child3Data);
        
        List<Map<String,String>> child4 = new ArrayList<Map<String,String>>();
        Map<String,String> child4Data = new HashMap<String,String>();
        child4Data.put("child", "��app�����ڸ��˵��˻��������ڸ��˵�һ��С�ͼ���ƽ̨��������ã�������ÿ���money�ĳ����Ұ����μ�����Ŷ��");
        child4.add(child4Data);
        
        List<List<Map<String,String>>>childs = new ArrayList<List<Map<String,String>>>();
        childs.add(child1);
        childs.add(child2);
        childs.add(child3);
        childs.add(child4); 
        SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(this, groups, R.layout.group ,new String[]{"group"}, 
        		new int[]{R.id.groupTo},childs, R.layout.child, new String[]{"child"},new int[]{R.id.childTo}  );
        //��SimpleExpandableListAdapter�������ø���ǰ��ExpandagleListAdapter
        setListAdapter(sela); 
        //�õ�TabHost����ʵ��
          tabhost =(TabHost) findViewById(R.id.tabs);
        //���� TabHost.setup()
        tabhost.setup();
        //����Tab��ǩ
        tabhost.addTab(tabhost.newTabSpec("one").setIndicator("����").setContent(R.id.shouru)); 
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("֧��").setContent(R.id.zhichu));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("��ǩ").setContent(R.id.bianqian)); 
        tabhost.addTab(tabhost.newTabSpec("four").setIndicator("��Ѷ").setContent(R.id.zixun)); 
        tabhost.addTab(tabhost.newTabSpec("five").setIndicator("����").setContent(R.id.gengduo)); 

    	TextView tv = (TextView) tabhost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); 
    	tv.setTextSize(12);//���ñ��������С 
    	tv.setTextColor(Color.WHITE);
        
    	TextView tv1 = (TextView) tabhost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); 
    	tv1.setTextSize(12);//���ñ��������С 
    	tv1.setTextColor(Color.WHITE);
    	
    	TextView tv2= (TextView) tabhost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); 
    	tv2.setTextSize(12);//���ñ��������С 
    	tv2.setTextColor(Color.WHITE);
    	
    	TextView tv3 = (TextView) tabhost.getTabWidget().getChildAt(3).findViewById(android.R.id.title); 
    	tv3.setTextSize(12);//���ñ��������С 
    	tv3.setTextColor(Color.WHITE);
    	
    	TextView tv4 = (TextView) tabhost.getTabWidget().getChildAt(4).findViewById(android.R.id.title); 
    	tv4.setTextSize(12);//���ñ��������С 
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
    	 * ��һ�ſ�Ƭ
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
        txtLiuruTime.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
			@SuppressWarnings("deprecation")
			@Override
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DATE_DIALOG_ID = 0;
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});

        LiuruOK.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strInMoney = txtLiuruMoney.getText().toString();// ��ȡ����ı����ֵ
				if (!strInMoney.isEmpty()) {// �жϽ�Ϊ��
					// ����InaccountDAO����
					InaccountDAO inaccountDAO = new InaccountDAO(
							MainActivity.this);
					 
					Tb_inaccount tb_inaccount = new Tb_inaccount(
							inaccountDAO.getMaxId() + 1, Double
									.parseDouble(strInMoney), txtLiuruTime
									.getText().toString(), spLiuruType
									.getSelectedItem().toString(),
							txtInHandler.getText().toString(),
							txtLiuruBeizhu.getText().toString());
					inaccountDAO.add(tb_inaccount);// ���������Ϣ
					// ������Ϣ��ʾ
					Toast.makeText(MainActivity.this, "���������롽������ӳɹ���",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Inaccountinfo.class);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "�����������",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

        LiuruCancel.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				txtLiuruMoney.setText("");// ���ý���ı���Ϊ��
				txtLiuruMoney.setHint("0.00");// Ϊ����ı���������ʾ
				txtLiuruTime.setText("");// ����ʱ���ı���Ϊ��
				txtLiuruTime.setHint("2015-11-11");// Ϊʱ���ı���������ʾ 
				txtLiuruBeizhu.setText("");// ���ñ�ע�ı���Ϊ��
				txtLiuruBeizhu.setSelection(0);// ������������б�Ĭ��ѡ���һ��
			}
		}); 
		final Calendar c = Calendar.getInstance();// ��ȡ��ǰϵͳ����
		mYear = c.get(Calendar.YEAR);// ��ȡ���
		mMonth = c.get(Calendar.MONTH);// ��ȡ�·�
		mDay = c.get(Calendar.DAY_OF_MONTH);// ��ȡ����  
    	
    	/*�ڶ��ſ�Ƭ*/
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
					Toast.makeText(MainActivity.this, "��¼���˸ñ�֧��"+Double.parseDouble(Smoney), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Outaccountinfo.class);
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this, "����������ѽ��", Toast.LENGTH_SHORT).show();
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
		final Calendar c1 = Calendar.getInstance();// ��ȡ��ǰϵͳ����
		mYear = c1.get(Calendar.YEAR);// ��ȡ���
		mMonth = c1.get(Calendar.MONTH);// ��ȡ�·�
		mDay = c1.get(Calendar.DAY_OF_MONTH);// ��ȡ���� 
		 /*
	     * �����ſ�Ƭ��ǩ
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
					Toast t = Toast.makeText(getApplicationContext(), "��Ǹ����ϵͳ��֧����������", Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
		id_chuangjian.setOnClickListener(new OnClickListener() {// Ϊ���水ť���ü����¼�
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String strFlag = bianqian.getText().toString();// ��ȡ��ǩ�ı����ֵ
				if (!strFlag.isEmpty()) {// �жϻ�ȡ��ֵ��Ϊ��
					FlagDAO flagDAO = new FlagDAO(MainActivity.this);// ����FlagDAO����
					Tb_flag tb_flag = new Tb_flag(
							flagDAO.getMaxId() + 1, strFlag);// ����Tb_flag����
					flagDAO.add(tb_flag);// ��ӱ�ǩ��Ϣ
					// ������Ϣ��ʾ
					Toast.makeText(MainActivity.this, "��������ǩ��������ӳɹ���",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Showinfo.class);
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "�������ǩ��",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		id_fangqi.setOnClickListener(new OnClickListener() {// Ϊȡ����ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bianqian.setText("");// ��ձ�ǩ�ı���
			}
		});
		
		/**
		 * �����ſ�Ƭ
		 */
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Music�� " + i); // ����
			// map.put("ItemImage", R.drawable.music); //ͼƬ
			listItems.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		listItemAdapter = new SimpleAdapter(this, listItems, // listItems����Դ
				R.layout.list_item, // ListItem��XML����ʵ��
				new String[] { "ItemTitle", "ItemImage" }, // ��̬������ImageItem��Ӧ������
				new int[] { R.id.ItemTitle, R.id.ItemImage } // list_item.xml�����ļ������һ��ImageView��ID,һ��TextView
																// ��ID
		);
        
           
        
		
 }
    
    protected void onListItemClick(ListView l, View v, int position, long id)  {
        // TODO Auto-generated method stub
        Log.e("position", "" + position);
        setTitle("������"+position+"��"); 
    }
    
    /**
     * �������
     */
	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
		}
	};

	/**
	 * ��ʾѡ��Ի���
	 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("����ͷ��")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // �����ļ�����
							intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1: 
							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// �жϴ洢���Ƿ�����ã����ý��д洢
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
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show(); 
	} 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//����벻����ȡ��ʱ��
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
					Toast.makeText(MainActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��",
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
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * ����ü�֮���ͼƬ����
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

	
    /*�ڶ��ſ�Ƭ*/
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

	
	// ���һ����պ�ҳ��Ҳ�л���Ч��   
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