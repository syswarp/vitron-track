package com.syswarp;

public class Utiles {

	public java.util.Date hoy(){
		return new java.util.Date();
	}
	
	public java.sql.Date convert(java.util.Date uDate){
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		System.out.println("La fecha de hoy es: " +sDate);
		return sDate; 
	}
	
}
