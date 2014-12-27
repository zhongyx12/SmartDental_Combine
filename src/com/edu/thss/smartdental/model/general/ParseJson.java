package com.edu.thss.smartdental.model.general;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/*这个大类定义了账单的数据格式，并将数据库传来的json数据解析成该格式*/
public class ParseJson {

	/*这个类定义并初始化了每一种药品的详细信息*/
	public class drug {
		public int medicineId;
		public String medicineName;
		public double medicinePrice;
		public int medicineCount;
		public double medicineReimbusement;
		public double medicineRatio;
		public String medicineUnit;
		public drug (){
			medicineId = -1;
			medicineName = "hello";
			medicinePrice = -1.0;
			medicineCount = -1;
			medicineReimbusement = -1.0;
			medicineRatio = -1.0;
			medicineUnit = "hello";
		}
	}

	/*此类定义并初始化了每一个账单的详细信息*/
	public class Account {
		public int patientId;
		public int read;
		public int flag;
		public String patientName;
		public int accountId;
		public String time;
		public String hospital;
		public drug[] medicine;
		public int success;
		public double firstTotal;
		public double finalTotal;
		public double totalRatio;
		public Account (){
			patientName = "hello";
			accountId = -1;
			patientId = -1;
			read = 0;
			flag = 0;
			time = "hello";
			hospital = "hello";
			success = -1;
			firstTotal = -1.0;
			finalTotal = -1.0;
			totalRatio = -1.0;
		}

		public Account[] searchAccountByHospital(Account[] list, String hospital)
		{
			int i;
			Account[] result = new Account[list.length];
			
			for (i = 0; i < list.length; i++) {
				if (list[i].hospital.indexOf(hospital) >= 0) {
					result[i] = new Account();
					result[i] = list[i];
				}
			}
			return result;
		}
		public Account[] searchAccountByTime(Account[] list, int days) throws Exception
		{
			int i, j;
			Account[] result = new Account[list.length];

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date;
			Date now = new Date();
			now = new Date(now.getTime() - days * 24 * 60 * 60 * 1000);


			for (i = 0, j = 0; i < list.length; i++) {
				date = sdf.parse(list[i].time);
				if(date.after(now)){
					result[j] = list[i];
					j++;
				}
			}
			return result;
		}
	}

	/*将json数据解析成账单列表的格式*/
	public static SDAccount[] parseSimpleAccount(String accountInfo){
		
		SDAccount[] result; //记录返回结果
		try{
			JSONObject account = new JSONObject(accountInfo);
			JSONArray bills = account.getJSONArray("bills"); //获取一堆账单
			result = new SDAccount[bills.length()];
			for(int j = 0; j < bills.length(); j++){ //获取一堆账单中的每一个账单中的每一个信息
				result[j] = new SDAccount();
				JSONObject patient = bills.getJSONObject(j);
				result[j].accountId = Integer.parseInt(patient.getString("id"));
				result[j].patientName = patient.getString("patient");
				result[j].time = patient.getString("time");
				result[j].hospital = patient.getString("hospital");
				result[j].read = Integer.parseInt(patient.getString("read"));
				result[j].flag = Integer.parseInt(patient.getString("flag"));
				result[j].success = account.getInt("success");
				result[j].patientId = Integer.parseInt(account.getString("pid"));
				JSONArray medicines = patient.getJSONArray("medicines"); //获取到一个账单中的一堆药品
				result[j].medicine = new SDAccount.drug[medicines.length()];
				result[j].firstTotal = 0; //初始化未被报销的总费用
				result[j].finalTotal = 0; //初始化报销后的总费用

				
				for(int i = 0; i < medicines.length(); i++){   //获取一堆药品中的每个药品的详细信息
					JSONObject drugs = medicines.getJSONObject(i);
					result[j].medicine[i] = result[j].new drug();
					result[j].medicine[i].medicineId = Integer.parseInt(drugs.getString("mid"));
					result[j].medicine[i].medicineCount = Integer.parseInt(drugs.getString("mcount"));
					result[j].medicine[i].medicineName = drugs.getString("mname");
					result[j].medicine[i].medicinePrice = Double.parseDouble(drugs.getString("mprice"));
					result[j].medicine[i].medicineReimbusement = Double.parseDouble(drugs.getString("mreimbusement"));
					result[j].medicine[i].medicineRatio = Double.parseDouble(drugs.getString("mratio"));
					result[j].medicine[i].medicineUnit = drugs.getString("munit");
					result[j].firstTotal = result[j].firstTotal + result[j].medicine[i].medicineCount * result[j].medicine[i].medicinePrice; //累积计算报销前总费用
					result[j].finalTotal = result[j].finalTotal + result[j].medicine[i].medicineCount * (result[j].medicine[i].medicinePrice - result[j].medicine[i].medicineReimbusement);//累积计算报销后总费用
				}
				result[j].totalRatio = (result[j].firstTotal - result[j].finalTotal) / result[j].firstTotal; 
			}
			return result;
		} 
		catch (JSONException e){
			System.out.println("Json parses error!");
			e.printStackTrace();
			return null;
		}
	}

	
	public SDAccount[] m_parseSimpleAccount(String accountInfo){
		//String accountInfoo = "{\"bills\":[{\"id\":\"2\",\"patient\":\"Äã´óÒ¯µÄ£¡£¡\",\"time\":\"2014-12-04 10:50:23\",\"hospital\":\"Ð£Ò½Ôº\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"È¥ÄãÃÃµÄ£¡£¡£¡\",\"mprice\":\"10\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\"}]},{\"id\":\"3\",\"patient\":\"Äã´óÒ¯µÄ£¡£¡\",\"time\":\"2014-12-04 10:50:23\",\"hospital\":\"Ð£Ò½Ôº\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"È¥ÄãÃÃµÄ£¡£¡£¡\",\"mprice\":\"10\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\"}]}],\"success\":1}";
		SDAccount[] result;
		try{
			JSONObject account = new JSONObject(accountInfo);
			
			JSONArray bills = account.getJSONArray("bills");
			result = new SDAccount[bills.length()];
			
			for(int j = 0; j < bills.length(); j++){
				result[j] = new SDAccount();
				JSONObject patient = bills.getJSONObject(j);
				result[j].accountId = Integer.parseInt(patient.getString("id"));
				result[j].patientName = patient.getString("patient");
				result[j].time = patient.getString("time");
				result[j].hospital = patient.getString("hospital");
				//JSONArray medicines = patient.getJSONArray("medicines");
				
				//result[j].success = account.getInt("success");
				//result[j].medicine = new drug[medicines.length()];
				result[j].firstTotal = patient.getDouble("firstTotal");
				result[j].finalTotal = patient.getDouble("finalTotal");
			}
			return result;
		} 
		catch (JSONException e){
			System.out.println("Json parses error!");
			e.printStackTrace();
			return null;
		}
	}

}
