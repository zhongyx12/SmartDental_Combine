package com.edu.thss.smartdental;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.edu.thss.smartdental.model.BillListElement;
import com.edu.thss.smartdental.model.MedicineItem;
import com.edu.thss.smartdental.model.general.SDAccount;
import com.edu.thss.smartdental.util.Tools;
import com.edu.thss.smartdental.BillListFragment.BillListItem;

import android.os.Bundle;
import android.R.bool;
import android.R.xml;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BillDetailActivity extends Activity {
	private CharSequence mTitle;
	private ListView mListView;
	private ArrayList<MedicineItem> mDatas; 
	private MedicineAdapter adapter;
	private String accountid;
	private TextView totalCost;
	
	static private Boolean isLandscape;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){  
		    Log.i("info", "landscape"); // ���� 
		    isLandscape = true;
		    super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_bill_detail_land);
		}  
		else if (this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT) {  
		    Log.i("info", "portrait"); // ���� 
		    isLandscape = false;
		    super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_bill_detail);
		}
		accountid = getIntent().getExtras().getString("id");
		mListView = (ListView) findViewById(R.id.bill_detail_listview);
		totalCost = (TextView) findViewById(R.id.total_cost);
		mListView.setAdapter(adapter);
		
		initData();
		
		//����ActionBar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg));
		
		setTitle("账单详情");
	}
	
	private void initData() {
		mDatas = new ArrayList<MedicineItem>();
		MedicineItem item;
		
		SDAccount [] accounts = Tools.getAccountInfoFromLocal(getApplicationContext());
		SDAccount bill = null;
		for(int i = 0; i < accounts.length; i++){
			if(accounts[i].accountId == Integer.parseInt(accountid)){
				bill = accounts[i];
				break;
			}
		}
		double total = 0;
		if(bill != null){
			if(bill.medicine != null){
				for(int i = 0; i < bill.medicine.length; i++){
					SDAccount.drug m = bill.medicine[i];
					total += m.medicinePrice * m.medicineCount;
					item = new MedicineItem(m.medicineName, Double.toString(m.medicineCount), Double.toString(m.medicinePrice), Double.toString(m.medicinePrice*m.medicineCount), Double.toString(m.medicineReimbusement * m.medicineCount), Double.toString(m.medicinePrice*(1-m.medicineRatio)*m.medicineCount));
					mDatas.add(item);
				}
			}
		}
		totalCost.setText(Double.toString(total)+"元");
		adapter = new MedicineAdapter();
        mListView.setAdapter(adapter); 
	}
	
	private class MedicineAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
    	
		MedicineAdapter(){
    		super();
    		mInflater = getLayoutInflater();
    	}
		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			MedicineViewHolder holder;
			View itemView = (View) convertView;
			if (itemView == null) {
				//�������
				if(isLandscape) {
					itemView = mInflater.inflate(R.layout.bill_detail_item_land, null);
				}
				else {
					itemView = mInflater.inflate(R.layout.bill_detail_item, null);
				}

                holder = new MedicineViewHolder(itemView);
                itemView.setTag(holder);
            } else {
                holder = (MedicineViewHolder) itemView.getTag();
            }            
			MedicineItem item = mDatas.get(position);
			
			holder.name.setText(item.name);
			holder.num.setText(item.num);
			holder.cost.setText(item.cost);
			//�������
			if (isLandscape) {
				holder.officialCost.setText(item.officialCost);
				holder.selfCost.setText(item.selfCost);
				holder.addition.setText(item.addition);
			}
			
			return itemView;
		}
	}
	
	private static class MedicineViewHolder{
    	public TextView name;
    	public TextView num;
    	public TextView cost;
    	public TextView officialCost;
    	public TextView selfCost;
    	public TextView addition;
    	
    	MedicineViewHolder(View view){
    		name = (TextView) view.findViewById(R.id.medicine_name);
    		num = (TextView) view.findViewById(R.id.medicine_num);
    		cost = (TextView) view.findViewById(R.id.medicine_price);
    		//�������
    		if (isLandscape) {
    			officialCost = (TextView) view.findViewById(R.id.medicine_offical_cost);
    			selfCost = (TextView) view.findViewById(R.id.medicine_self_cost);
    			addition = (TextView) view.findViewById(R.id.medicine_addition);
    		}
    	}
    }
	
	
	
	@Override  
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	    case android.R.id.home:  
	    	finish();  
	    }  
	    return true;
	}  
	
	@Override
    public void setTitle(CharSequence title){
    	mTitle = title;
    	getActionBar().setTitle(mTitle);
    }
}
