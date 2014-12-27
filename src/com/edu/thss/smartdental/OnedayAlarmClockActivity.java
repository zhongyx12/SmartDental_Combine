package com.edu.thss.smartdental;

import java.util.ArrayList;

import com.edu.thss.smartdental.model.AlarmClock;
import com.edu.thss.smartdental.model.AlarmClockBusiness;
import com.edu.thss.smartdental.model.LocalDB;
import com.edu.thss.smartdental.model.Model;
import com.edu.thss.smartdental.ui.alarmclock.ListViewCompat;
import com.edu.thss.smartdental.ui.alarmclock.SlideView;
import com.edu.thss.smartdental.ui.alarmclock.SlideView.OnSlideListener;
import com.edu.thss.smartdental.util.Tools;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class OnedayAlarmClockActivity extends Activity implements OnSlideListener{
	
	private ListViewCompat listView; //
	private SlideView mLastSlideViewWithStatusOn;
	private ArrayList<AlarmClockItem> data;
	private String listDate;
	private Model[] mdList;

	private String toChineseFormat(String Date) {
		String[] sp = Date.split("-");
		return sp[1]+"月"+sp[2]+"日";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_oneday_alarm_clock);
		listDate = getIntent().getExtras().getString("date");
		
		listView = (ListViewCompat) findViewById(R.id.alarmclock_oneday_listcompat);
		
		TextView date = (TextView) findViewById(R.id.alarmclock_oneday_date);
		date.setText(toChineseFormat(listDate));
		TextView add_button = (TextView) findViewById(R.id.alarmclock_add);
		add_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//添加事件
				Intent intent = new Intent(OnedayAlarmClockActivity.this, SetClockActivity.class);
			    intent.putExtra("id", (long)-1);
			    intent.putExtra("date", listDate);
			    startActivityForResult(intent, 0);
//				Intent intent = new Intent(OnedayAlarmClockActivity.this, AlarmBill.class);
//    		    intent.putExtra("rate", 0.8);
//    		    intent.putExtra("threshold", 1000);
//    		    intent.putExtra("current", (double)11500);
//    		    startActivityForResult(intent, 0);
			}});
		initData();
	}
	
	@Override
	public void onActivityResult(int req, int res, Intent itt)
	{
		listView.mFocusedPosition = -1;
		initData();
	}
	

	/**
	 * 数据初始化，数据是从上一个活动传过来的
	 * */
	private void initData() {
		LocalDB ldb = new LocalDB(getApplication());
        mdList = ldb.findList(listDate);
		data = new ArrayList<AlarmClockItem>();
		AlarmClockItem item;
		for (int i = 0; i < mdList.length; i++)
		{
			item = new AlarmClockItem();
			AlarmClock alarmclock = new AlarmClock(mdList[i].date,mdList[i].time,mdList[i].title,mdList[i].content,0,0,"");
			item.alarmclock = alarmclock;
			data.add(item);
		}
		this.listView.setAdapter(new SlideAdapter());
		ldb.close();
	}

    private class SlideAdapter extends BaseAdapter{

    	private LayoutInflater mInflater;
    	
    	SlideAdapter(){
    		super();
    		mInflater = OnedayAlarmClockActivity.this.getLayoutInflater();
    	}
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.alarmclock_list_item, null);

                slideView = new SlideView(OnedayAlarmClockActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(OnedayAlarmClockActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
			AlarmClockItem item = data.get(position);
			item.slideView = slideView;
			item.slideView.shrink();
			
			holder.title.setText(item.alarmclock.title);
			holder.time.setText(item.alarmclock.time);
			
			holder.delete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					//删除事件
					LocalDB ldb = new LocalDB(getApplicationContext());
		            boolean flag = ldb.delete(mdList[position]._id);
		            ldb.close();
		            if (flag == true)
		            {
		            	AlarmClockBusiness acb = new AlarmClockBusiness();
		            	acb.deleteAlarmClock(mdList[position]._id, OnedayAlarmClockActivity.this);
		            	data.remove(position);  //把数据源里面相应数据删除
						notifyDataSetChanged(); 
		            }
				}});
			return slideView;
		}
    	
    }
    public class AlarmClockItem{
    	public AlarmClock alarmclock;
    	public SlideView slideView;
    	
    }
    private static class ViewHolder{
    	public TextView title;
    	public TextView time;
    	public TextView delete;
    	
    	ViewHolder(View view){
    		title = (TextView) view.findViewById(R.id.alarmclock_item_title);
    		time = (TextView) view.findViewById(R.id.alarmclock_item_time);
    		delete = (TextView)view.findViewById(R.id.alarmclock_item_holder_del);
    	}
    	
    }
	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
		
	}

	@Override
	public void onClick(View view) {
		int n = listView.mFocusedPosition;
		if (n == -1)
			return;
		Long id = mdList[n]._id;
		Intent intent = new Intent(OnedayAlarmClockActivity.this, SetClockActivity.class);
	    intent.putExtra("id", id);
	    startActivityForResult(intent, 0);
	}



	
}
