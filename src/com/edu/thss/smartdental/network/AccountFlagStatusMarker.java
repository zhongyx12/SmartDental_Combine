package com.edu.thss.smartdental.network;

import android.content.Context;
import android.util.Log;

import com.edu.thss.smartdental.util.Tools;

public class AccountFlagStatusMarker implements Runnable{
	private int bid;
	private boolean value;
	private Context context;
	
	public AccountFlagStatusMarker(int bid, boolean value, Context ctx) {
		this.bid = bid;
		this.value = value;
		this.context = ctx;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean response = Tools.modifyAccountFlagStatus(this.bid, this.value, this.context);
		Log.i("travis", "modify account read status response" + response);
	}
}
