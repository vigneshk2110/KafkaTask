package com.vicky.Kafka;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Order_Pojo {
	
	
	@Id
	private String Order_ID;
	private double Amount;
	private double Profit;
	private int Quantity;
	private String Category;
	private String Sub_Category;
	
}
