package com.edu.thss.smartdental;


import java.util.ArrayList;

import com.edu.thss.smartdental.model.AppointmentElement;
import com.edu.thss.smartdental.ui.calendar.ListViewCompat;
import com.edu.thss.smartdental.ui.calendar.SlideView;
import com.edu.thss.smartdental.ui.calendar.SlideView.OnSlideListener;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OnedayAppointListFragment extends Fragment implements OnSlideListener{
	
	private ListViewCompat listView; 
	private SlideView mLastSlideViewWithStatusOn;
	private ArrayList<AppointmentItem> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_oneday_appoint_list, container,false);
		
		listView = (ListViewCompat)rootView.findViewById(R.id.appoint_oneday_listcompat);
		initData(); //���ݳ�ʼ��
		return rootView;
	}
	/**
	 * ���ݳ�ʼ���������Ǵ���һ�����������
	 * */
	private void initData() {
		data = new ArrayList<AppointmentItem>();
		AppointmentItem item;
		item = new AppointmentItem();
		AppointmentElement appoint = new AppointmentElement("ȥ�����ƽ��еڶ��λ���","2014-6-20","09:00","10:00");
		item.appointment = appoint;
		data.add(item);
		item = new AppointmentItem();
		appoint = new AppointmentElement("����","2014-6-20","14:00","15:30");
		item.appointment = appoint;
		data.add(item);
		this.listView.setAdapter(new SlideAdapter());
	}

    private class SlideAdapter extends BaseAdapter{
    	private LayoutInflater mInflater;
    	
    	SlideAdapter(){
    		super();
    		mInflater = getActivity().getLayoutInflater();
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
                View itemView = mInflater.inflate(R.layout.appoint_list_item, null);

                slideView = new SlideView(getActivity());
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(OnedayAppointListFragment.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
			AppointmentItem item = data.get(position);
			item.slideView = slideView;
			item.slideView.shrink();
			
			holder.name.setText(item.appointment.name);
			holder.date.setText(item.appointment.date);
			holder.start.setText(item.appointment.startTime);
			holder.end.setText(item.appointment.endTime);
			
			
			holder.delete.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//ɾ���¼�
					data.remove(position);  //������Դ������Ӧ����ɾ��
					notifyDataSetChanged(); 
				}});
			holder.done.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//����¼�
					//Log.i("click", "���");
				}});
			return slideView;
		}
    }
    public class AppointmentItem{
    	public AppointmentElement appointment;
    	public SlideView slideView;
    	
    }
    private static class ViewHolder{
    	public TextView name;
    	public TextView date;
    	public TextView start;
    	public TextView end;
    	public TextView delete;
    	public TextView done;
    	
    	ViewHolder(View view){
    		name = (TextView) view.findViewById(R.id.appoint_item_title);
    		date = (TextView) view.findViewById(R.id.appoint_item_date);
    		start = (TextView) view.findViewById(R.id.appoint_item_start);
    		end = (TextView) view.findViewById(R.id.appoint_item_end);
    		delete = (TextView)view.findViewById(R.id.appoint_item_holder_del);
    		done = (TextView)view.findViewById(R.id.appoint_item_holder_do);
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

	





	
	}
