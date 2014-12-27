package com.edu.thss.smartdental;

import java.util.ArrayList;

import com.edu.thss.smartdental.model.AppointmentElement;
import com.edu.thss.smartdental.ui.calendar.AppointmentChartView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OnedayAppointChartFragment extends Fragment {

	ArrayList<AppointmentElement> data; //������Դ
	AppointmentChartView chartView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_oneday_appoint_chart, container,false);
		//chartView = (AppointmentChartView)rootView.findViewById(R.id.app_chartview);
	  
	   
		//chartView.invalidate();
		initData(); //���ݳ�ʼ��
		return rootView;
		
	}
	/**
	 * ��ʼ������
	 * */
	private void initData() {
		data = new ArrayList<AppointmentElement>();
	
		AppointmentElement appoint = new AppointmentElement("ȥ�����ƽ��еڶ��λ���","2014-5-15","09:00","10:00");
		data.add(appoint);
		
		appoint = new AppointmentElement("����","2014-5-15","14:00","15:30");
		
		data.add(appoint);
		
	}
}
