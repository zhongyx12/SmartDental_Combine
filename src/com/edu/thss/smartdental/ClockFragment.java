package com.edu.thss.smartdental;

import java.util.Date;
import java.util.Calendar;//add by Lin Yangmei

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.ui.calendar.CalendarView.OnItemClickListener;

public class ClockFragment extends Fragment{
	CalendarView calendar;
	Button left;
	Button right;
	TextView title;
	private OnClickListener clicklistener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(v.getId() == left.getId()){
				title.setText(calendar.clickLeftMonth());
			}
			if(v.getId() == right.getId()){
				title.setText(calendar.clickRightMonth());
			}
		}};
	
	
	public ClockFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_appointment, container,false);
		this.calendar = (CalendarView)rootView.findViewById(R.id.calendar);
		calendar.setOnItemClickListener(new calendarItemClickListener());
		
		
		left = (Button)rootView.findViewById(R.id.calendar_left);
		left.setOnClickListener(this.clicklistener);
		right = (Button)rootView.findViewById(R.id.calendar_right);
		right.setOnClickListener(this.clicklistener);
		
		title = (TextView)rootView.findViewById(R.id.calendar_title);
		title.setText(calendar.getYearAndmonth());
		return rootView;
	}
	
	class calendarItemClickListener implements OnItemClickListener{

		@Override
		public void OnItemClick(Date date) {
			//add by Lin Yangmei
			Calendar cal = Calendar.getInstance();
            cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int t_date = cal.get(Calendar.DATE);
			String time = buildTime(year,month,t_date);
			
			//System.out.println("被点击的日期是："+time);
			//end
			 
			Intent intent = new Intent(getActivity(),OnedayAlarmClockActivity.class);
			intent.putExtra("date",time);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}  
	 }
	private String buildTime(int year, int month, int date)
    {
    	String time;
    	int t_month = month+1;//对month进行修正
    	String s_month;
    	String s_date;
    	//把月和日的信息转换成符合查询要求的格式
    	if(t_month<10)
    		s_month = "0"+t_month;	    
    	else
    		s_month = t_month+"";
    	if(date<10)	    	
    		s_date = "0"+date;	    
    	else
    		s_date = date+"";
    	time = year+"-"+s_month+"-"+s_date;
        return time;
    }
}
