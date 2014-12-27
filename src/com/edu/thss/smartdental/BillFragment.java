package com.edu.thss.smartdental;
import javax.annotation.PostConstruct;

import com.edu.thss.smartdental.model.general.SDAccount;
import com.edu.thss.smartdental.util.Tools;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.Spinner;

public class BillFragment extends Fragment  {
	private FragmentManager fm = null; 
	private RadioGroup radioGroup;
	private Button bt;
	private Spinner sp;
	private ViewGroup handler;
	private static final int REQUEST_CODE = 0; //«Î«Û¬Î
	private BillListFragment recentListFragment = null;
	public BillFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bill, container,false);
		handler = container;
		fm = getChildFragmentManager();
		radioGroup = (RadioGroup)rootView.findViewById(R.id.bill_tab);
		radioGroup.check(R.id.bill_tab_all);
		changeFragment(0);
		sp = (Spinner) rootView.findViewById(R.id.spinner);
		this.bt = (Button) rootView.findViewById(R.id.show_chart);
		
		sp.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				getRangeList(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		}); 
		
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(), LineChartActivity.class);
//				SDAccount [] accounts = Tools.getAccountInfoFromLocal(handler.getContext());
				String s = "";
				if(recentListFragment != null)
					s = getPriceInfo(recentListFragment.getAccounts());
				intent.putExtra("data", s);
				startActivityForResult(intent,REQUEST_CODE);
			}});
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId){
				case R.id.bill_tab_all: changeFragment(0); break;
				case R.id.bill_tab_unread: changeFragment(1); break;
				case R.id.bill_tab_mark: changeFragment(2); break;
				}
               
			}} );
		
		return rootView;
	}

	private void changeFragment(int index){
		FragmentTransaction transaction = fm.beginTransaction();
		Fragment tempfragment = null;
		int range = recentListFragment == null ? 0 : recentListFragment.getRangeList();
		switch(index){
		case 0: tempfragment = new BillListFragment(range, false, false);break;
		case 1: tempfragment = new BillListFragment(range, true, false);break;
		case 2: tempfragment = new BillListFragment(range, false, true);break;
		default: break;
		}
		if(tempfragment != null){
			recentListFragment = (BillListFragment)tempfragment;
        	transaction.replace(R.id.bill_tabcontent, tempfragment);
        	transaction.commit();
        }
	}
	
	private void getRangeList(int index) {
		FragmentTransaction transaction = fm.beginTransaction();
		Fragment tempfragment = null;
		if(recentListFragment == null)
			tempfragment = new BillListFragment(index, false, false);
		else 
			tempfragment = new BillListFragment(index, recentListFragment.getRead(), recentListFragment.getFlag());
		if(tempfragment != null){
			recentListFragment = (BillListFragment)tempfragment;
        	transaction.replace(R.id.bill_tabcontent, tempfragment);
        	transaction.commit();
        }
	}
	
	private String getPriceInfo(SDAccount[] accounts){
		String s = "{\"bills\":[";
		for(int i = 0; i < accounts.length; i++){
			s += "{\"id\": \"" + Integer.toString(accounts[i].accountId) + "\",";
			s += "\"patient\": \"" + accounts[i].patientName + "\",";
			s += "\"time\": \"" + accounts[i].time + "\",";
			s += "\"hospital\": \"" + accounts[i].hospital + "\",";
			s += "\"firstTotal\": \"" + accounts[i].firstTotal + "\",";
			s += "\"finalTotal\": \"" + accounts[i].finalTotal + "\"";
			s += "}";
			if(i < accounts.length-1)
				s += ",";
		}
		s += "],\"success\":1}";
		return s;
	}
}
