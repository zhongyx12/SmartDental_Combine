package com.edu.thss.smartdental.model.general;

import java.io.Serializable;


public class SDAccount implements Serializable{
	
	public class drug implements Serializable{
		public int medicineId;
		public String medicineName;
		public double medicinePrice;
		public int medicineCount;
		public double medicineReimbusement;
		public double medicineRatio;
		public String medicineUnit;
		public drug () {
			medicineId = -1;
			medicineName = "hello";
			medicinePrice = -1.0;
			medicineCount = -1;
			medicineReimbusement = -1.0;
			medicineRatio = -1.0;
			medicineUnit = "hello";
		}
	}
	
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
		public SDAccount (){
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
	
}