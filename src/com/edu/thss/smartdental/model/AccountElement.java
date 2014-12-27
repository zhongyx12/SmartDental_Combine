package com.edu.thss.smartdental.model;

public class AccountElement {
	
	class drug {
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
	
	public String patientName;
	public int patientId;
	public String time;
	public String hospital;
	public drug[] medicine;
	public int success;
	public double firstTotal;
	public double finalTotal;
	public double totalRatio;
	public AccountElement (){
		patientName = "hello";
		patientId = -1;
		time = "hello";
		hospital = "hello";
		
		success = -1;
		firstTotal = -1.0;
		finalTotal = -1.0;
		totalRatio = -1.0;
	}
	
}