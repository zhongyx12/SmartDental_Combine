package com.edu.thss.smartdental;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.model.BillListElement;
import com.edu.thss.smartdental.ui.calendar.SlideView;
import com.edu.thss.smartdental.util.Tools;

import android.support.v4.app.Fragment;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout; 
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.edu.thss.smartdental.model.general.SDAccount;
import com.edu.thss.smartdental.network.AccountFlagStatusMarker;
import com.edu.thss.smartdental.network.AccountReadStatusMarker;


public class BillListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	private static final int REFRESH_COMPLETE = 0X110;
	private ListView mListView;
	private int rangeList = 0;
	private SwipeRefreshLayout mySRLayout;
	private ArrayList<BillListItem> mDatas; 
	private BillListAdapter adapter;
	private SDAccount [] accounts;
	private ViewGroup handler;
	private static int twentyYears = 7300;
	private static int oneYear = 365;
	private static int threeMonth = 92;
	private static int halfYear = 183;
	private static boolean unread = false;
	private static boolean flag = false;
	
	public int getRangeList(){
		return rangeList;
	}
	
	public boolean getRead() {
		return unread;
	}
	
	public boolean	getFlag(){
		return flag;
	}
	
	public SDAccount[] getAccounts(){
		return accounts;
	}
	public BillListFragment() {
		// TODO Auto-generated constructor stub
	}
	public BillListFragment(int index, boolean rd, boolean fl) {
		// TODO Auto-generated constructor stub
		rangeList = index;
		unread = rd;
		flag = fl;
		mDatas = new ArrayList<BillListItem>();
	}
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle state) {
		View rootView = inflater.inflate(R.layout.bill_list, container, false);
		mListView = (ListView)rootView.findViewById(R.id.bill_listView);
		mySRLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
		handler = container;
		accounts = Tools.getAccountInfoFromLocal(container.getContext());
		initData(rangeList);
		
	    return rootView;
	}
	
	private void initData(int timeRange) {
		
		Date startDate;
		Date endDate = new Date();
		String startTime;
		String endTime=Tools.getStringFormDate(endDate);
		if(accounts != null){
			switch(timeRange){
				case 1: 
					startDate = new Date(endDate.getTime() - (long)threeMonth * 24 * 60 * 60 * 1000);
					startTime = Tools.getStringFormDate(startDate);
					break;
				case 2: 
					startDate = new Date(endDate.getTime() - (long)halfYear * 24 * 60 * 60 * 1000);
					startTime = Tools.getStringFormDate(startDate);
					break;
				case 3: 
					startDate = new Date(endDate.getTime() - (long)oneYear * 24 * 60 * 60 * 1000);
					startTime = Tools.getStringFormDate(startDate);
					break;
				default: 
					startDate = new Date(endDate.getTime() - (long)twentyYears * 24 * 60 * 60 * 1000);
					startTime = Tools.getStringFormDate(startDate);
					break;
			}
			setData(startTime, endTime);
		}
		mySRLayout.setOnRefreshListener(this);
		mySRLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
		adapter = new BillListAdapter();
        mListView.setAdapter(adapter); 
		

        mListView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Intent intent = new Intent(getActivity(), BillDetailActivity.class);
        		
        		SDAccount account = accounts[(int) id];
        		intent.putExtra("id", Integer.toString(account.accountId));
        		
        		new Thread(new AccountReadStatusMarker(account.accountId, true, handler.getContext())).start();
        		
        		startActivity(intent);
        	}
		});
        
        this.registerForContextMenu(mListView);
        
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 0, 0, "设为标记");  
	}
	
	@Override 
    public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
//		switch (item.getItemId()) {
//		case 0:
		int pos = (int)adapter.getItemId(menuInfo.position);
		int accountId = accounts[pos].accountId;
		new Thread(new AccountFlagStatusMarker(accountId, true, handler.getContext())).start();
//		break;
//		case 1:
//			
//			break;
//		}
		return true;
	}
	
	private class BillListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
    	
		BillListAdapter(){
    		super();
    		mInflater = getActivity().getLayoutInflater();
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
			BillListViewHolder holder;
			View itemView = (View) convertView;
			if (itemView == null) {
                itemView = mInflater.inflate(R.layout.bill_list_item, null);

                holder = new BillListViewHolder(itemView);
                itemView.setTag(holder);
            } else {
                holder = (BillListViewHolder) itemView.getTag();
            }            
			BillListItem item = mDatas.get(position);
			
			holder.name.setText(item.billList.name);
			holder.date.setText(item.billList.date);
			holder.cost.setText(item.billList.cost);
			itemView.setTag(holder);
			
			return itemView;
		}
	}
	
	private static class BillListViewHolder{
    	public TextView name;
    	public TextView date;
    	public TextView cost;
    	
    	BillListViewHolder(View view){
    		name = (TextView) view.findViewById(R.id.bill_list_item_name);
    		date = (TextView) view.findViewById(R.id.bill_list_item_date);
    		cost = (TextView) view.findViewById(R.id.bill_list_item_cost);
    	}
    }
	
	public class BillListItem{
    	public BillListElement billList;
    	public SlideView slideView;
    	
    }
	
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
            case REFRESH_COMPLETE:  
            	accounts = Tools.getAccountInfoFromLocal(handler.getContext());
        		initData(rangeList);
                adapter.notifyDataSetChanged();  
                mySRLayout.setRefreshing(false);  
                break;  
            }
        };
    };  
	
	public void onRefresh() {
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000); 
	}
	
	private void setData(String startTime, String endTime){
		mDatas = new ArrayList<BillListItem>();
		BillListItem item;
//		List<SDAccount> list = Tools.queryAccountByRange(pid, starTime, endTime);
		accounts = Tools.filterAccounts(accounts, startTime, endTime);
		if(unread)
			accounts = Tools.filterReadAccounts(accounts);
		if(flag)
			accounts = Tools.filterFlagAccounts(accounts);
		for(int i = 0; i < accounts.length; i++){
			item = new BillListItem();
			BillListElement billList = new BillListElement(accounts[i].hospital, accounts[i].time, Integer.toString(((int) accounts[i].finalTotal))+"元");
			item.billList = billList;
			mDatas.add(item);
		}
	}
	
} 