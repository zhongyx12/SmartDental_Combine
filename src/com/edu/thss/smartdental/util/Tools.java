package com.edu.thss.smartdental.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.edu.thss.smartdental.model.general.*;

public class Tools {
	
	public static boolean hasSDCard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	public static SDAccount [] getAccountInfoFromLocal (Context ctx) {

		try {
			FileInputStream streamIn = ctx.openFileInput("accounts.tmp");

	        ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
	        SDAccount [] account1 = (SDAccount []) objectinputstream.readObject();
	        
	        objectinputstream.close();
	        streamIn.close();
	        return account1;
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static SDAccount[] parseSimpleAccount(String accountInfo){
		SDAccount[] result;
		try{
			JSONObject account = new JSONObject(accountInfo);
			
			JSONArray bills = account.getJSONArray("bills");
			result = new SDAccount[bills.length()];
			
			for(int j = 0; j < bills.length(); j++){
				result[j] = new SDAccount();
				JSONObject patient = bills.getJSONObject(j);
				result[j].patientId = Integer.parseInt(patient.getString("id"));
				result[j].patientName = patient.getString("patient");
				result[j].time = patient.getString("time");
				result[j].hospital = patient.getString("hospital");
				JSONArray medicines = patient.getJSONArray("medicines");
				
				result[j].success = account.getInt("success");
				result[j].medicine = new SDAccount.drug[medicines.length()];
				result[j].firstTotal = 0;
				result[j].finalTotal = 0;
				for(int i = 0; i < medicines.length(); i++){
					JSONObject drugs = medicines.getJSONObject(i);
					result[j].medicine[i] = result[j].new drug();
					result[j].medicine[i].medicineId = Integer.parseInt(drugs.getString("mid"));
					result[j].medicine[i].medicineCount = Integer.parseInt(drugs.getString("mcount"));
					result[j].medicine[i].medicineName = drugs.getString("mname");
					result[j].medicine[i].medicinePrice = Double.parseDouble(drugs.getString("mprice"));
					result[j].medicine[i].medicineReimbusement = Double.parseDouble(drugs.getString("mreimbusement"));
					result[j].medicine[i].medicineRatio = Double.parseDouble(drugs.getString("mratio"));
					result[j].medicine[i].medicineUnit = drugs.getString("munit");
					result[j].firstTotal = result[j].firstTotal + result[j].medicine[i].medicineCount * result[j].medicine[i].medicinePrice;
					result[j].finalTotal = result[j].finalTotal + result[j].medicine[i].medicineCount * (result[j].medicine[i].medicinePrice - result[j].medicine[i].medicineReimbusement);
				}
				result[j].totalRatio = (result[j].firstTotal - result[j].finalTotal) / result[j].firstTotal;
			}
			return result;
		}
		catch (JSONException e){
			Log.i("travis", "Json parses error!");
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean modifyAccountReadStatus (int bid, boolean read, Context context) {
		return Tools.modifyAccountStatus("isRead", bid, read, context);
	}
	
	public static boolean modifyAccountFlagStatus (int bid, boolean flag, Context context) {
		return Tools.modifyAccountStatus("flag", bid, flag, context);
	}
	
	private static boolean modifyAccountStatus (String type, int bid, boolean value, Context context) {
		HttpClient httpClient = new DefaultHttpClient();

		Log.i("travis", "Change flag status");
		String url = "http://smartdental.sinaapp.com/db_modify.php?bid=";
		url += (bid + "&" + type + "=" + (value ? 1 : 0));
		Log.e("travis", "Start modify read status1");
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		String strResult = "";
		Log.e("travis", "Start modify read status2");
		try {
			Log.e("travis", "Start modify read status3");
			response = httpClient.execute(request);
			Log.e("travis", "After posting");
			strResult = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("travis", e.getMessage());
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("travis", e.getMessage());
			return false;
		}
		
		SDAccount [] accounts = Tools.getAccountInfoFromLocal(context);
		for (int i = 0; i < accounts.length; i ++) {
			SDAccount account = accounts[i];
			if (bid == account.accountId) {
				if (type.compareTo("isRead") == 0) {
					account.read = value ? 1 : 0;
				} else {
					account.flag = value ? 1 : 0;
				}
			}
		}
		
		FileOutputStream fout;
		try {
			fout = context.openFileOutput("accounts.tmp", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(accounts);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return strResult != "";
	}

	
	public static List<SDAccount> queryAccountByRange (int pid, String startTime, String endTime) {
		List<SDAccount> res = new ArrayList<SDAccount>();
		if (startTime.compareTo(endTime) == 1) {
			return res;
		}

		String url = "http://smartdental.sinaapp.com/db_queryByRange.php";
		url = url + "?pid=" + pid
				+ "&startTime='" + startTime
				+ "'&endTime='" + endTime + "'";
		try {		
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response;
			response = httpClient.execute(request);
			String strResult = EntityUtils.toString(response.getEntity());   
			strResult = new String(strResult.getBytes("ISO-8859-1"), "UTF8");
			
			SDAccount [] accounts = Tools.parseSimpleAccount(strResult.replaceFirst("<.*>", ""));
			res = Arrays.asList(accounts);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return res;
	}

	public static String getStringFormDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		return str;
	}
	
	public static SDAccount [] filterAccounts (SDAccount [] accounts, String startTime, String endTime) {
		List<SDAccount> res = new ArrayList<SDAccount>();
		for (int i = 0; i < accounts.length; i ++) {
			SDAccount account = accounts[i];
			String time = account.time.split(" ", 2)[0];
			if (time.compareTo(startTime) > -1 && time.compareTo(endTime) < 1) {
				res.add(account);
			}
		}
		// return (SDAccount[]) res.toArray();
		SDAccount [] list = new SDAccount [res.size()];
		for (int i = 0; i < res.size(); i ++){
			list[i] = res.get(i);
		}
		return list;
	}
	
	public static SDAccount [] filterReadAccounts (SDAccount [] accounts) {
		List<SDAccount> res = new ArrayList<SDAccount>();
		for (int i = 0; i < accounts.length; i ++) {
			SDAccount account = accounts[i];
			if (account.read == 0) {
				res.add(account);
			}
		}
		// return (SDAccount[]) res.toArray();
		SDAccount [] list = new SDAccount [res.size()];
		for (int i = 0; i < res.size(); i ++){
			list[i] = res.get(i);
		}
		return list;
	}
	
	public static SDAccount [] filterFlagAccounts (SDAccount [] accounts) {
		List<SDAccount> res = new ArrayList<SDAccount>();
		for (int i = 0; i < accounts.length; i ++) {
			SDAccount account = accounts[i];
			if (account.flag == 1) {
				res.add(account);
			}
		}
		// return (SDAccount[]) res.toArray();
		SDAccount [] list = new SDAccount [res.size()];
		for (int i = 0; i < res.size(); i ++){
			list[i] = res.get(i);
		}
		return list;
	}
}
