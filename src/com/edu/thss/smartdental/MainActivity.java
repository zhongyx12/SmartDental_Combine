 package com.edu.thss.smartdental;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.edu.thss.smartdental.model.general.ParseJson;
import com.edu.thss.smartdental.model.general.SDAccount;
import com.edu.thss.smartdental.ui.drawer.NavDrawerItem;
import com.edu.thss.smartdental.ui.drawer.NavDrawerListAdapter;
import com.edu.thss.smartdental.util.Tools;


import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnItemClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	
	private String[] mNavMenuTitles;
	private TypedArray mNavMenuIconsTypeArray;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mAdapter;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new SyncDaemon().execute("test");

		findView();
		
		if(savedInstanceState == null){
			selectItem(0);
		}
	}
	
	@SuppressLint("NewApi")
	private void findView(){
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView)findViewById(R.id.left_drawer);
		
		mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_titles);
		mNavMenuIconsTypeArray = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mNavDrawerItems = new ArrayList<NavDrawerItem>();
		
		//------
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[0],mNavMenuIconsTypeArray.getResourceId(0, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[1],mNavMenuIconsTypeArray.getResourceId(1, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[2],mNavMenuIconsTypeArray.getResourceId(2, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[3],mNavMenuIconsTypeArray.getResourceId(3, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[4],mNavMenuIconsTypeArray.getResourceId(4, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[5],mNavMenuIconsTypeArray.getResourceId(5, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[6],mNavMenuIconsTypeArray.getResourceId(6, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[7],mNavMenuIconsTypeArray.getResourceId(7, -1)));
		mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[8],mNavMenuIconsTypeArray.getResourceId(8, -1)));
		
		mNavMenuIconsTypeArray.recycle();
		
		mAdapter = new NavDrawerListAdapter(getApplicationContext(),mNavDrawerItems);
		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(this);
	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg));
		
		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				R.drawable.ic_drawer,
				R.string.drawer_open,
				R.string.drawer_close
				){
			public void onDrawerClosed(View view){
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView){
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		selectItem(position);
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	private void selectItem(int position){
		Fragment fragment  = null;
		switch (position){
		case 0: 
			fragment = new InfoFragment();
			break;
		case 1: 
			fragment = new EMRFragment();
			break;
		case 2: 
			fragment = new ToothFragment();
			break;
		case 3: 
			fragment = new ImageFragment();
			break;
		case 4: 
			fragment = new AppointmentFragment();
			break;
		case 5: 
			fragment = new ClassFragment();
			break;
		case 6: 
			fragment = new DataFragment();
			break;
		case 7: 
			fragment = new BillFragment();
			break;
		case 8: 
			fragment = new SettingFragment();
			break;
		default: break;
		}
		if(fragment != null){
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(mNavMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		else{
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
    @SuppressLint("NewApi")
	@Override
    public void setTitle(CharSequence title){
    	mTitle = title;
    	getActionBar().setTitle(mTitle);
    }

     class SyncDaemon extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			
			while (true) {
				try {
					// Log.i("travis", "Sleep");
					Thread.sleep(10000);

					HttpClient httpClient = new DefaultHttpClient();

					String url = "http://smartdental.sinaapp.com/db_query.php";
					HttpGet request = new HttpGet(url + "?lastTimestmap=sdf&pid=1");
					HttpResponse response = httpClient.execute(request);
					
					String strResult = EntityUtils.toString(response.getEntity());   
					strResult = new String(strResult.getBytes("ISO-8859-1"), "UTF8");
					strResult = new String(strResult);
					
					SDAccount [] accounts = ParseJson.parseSimpleAccount(strResult.replaceFirst("<.*>", ""));
					
					SDAccount [] accounts2 = Tools.getAccountInfoFromLocal(getApplicationContext());
					if (accounts.length == accounts2.length) {
						continue;
					}
					
					
				    
					NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
                    Notification notification = new Notification(R.drawable.ic_launcher,"您有"+(accounts.length-accounts2.length)+"条新的账单",System.currentTimeMillis());
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0); 
                    notification.setLatestEventInfo(getApplicationContext(), "账单提醒", "您有"+(accounts.length-accounts2.length)+"条新的账单", pendingIntent);
                    notification.flags = Notification.FLAG_AUTO_CANCEL;//点击后自动消失
                    notification.defaults = Notification.DEFAULT_SOUND;//声音默认
                    manager.notify(0, notification);//发动通知,id由自己指定，每一个Notification对应的唯一标志
                     
					// Log.i("travis", "count: " + accounts[0].hospital);
					
					FileOutputStream fout = openFileOutput("accounts.tmp", Context.MODE_PRIVATE);;
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(accounts);
					oos.close();
						
				} catch (InterruptedException e) {
					Log.i("travis", e.toString());
					return null;
				} catch (UnsupportedEncodingException e) {
					Log.i("travis", e.toString());
					return null;
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }

}
