package com.rp.cafe.application.model.transaction;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // add an auto increment ID to database table
	private int tranid;
	
	@Column(nullable = false) //not null constraint 
	private LocalDate date = LocalDate.now();
	
	@Column(nullable = false) //not null constraint 
	private LocalTime timing = LocalTime.now();
	
	@Column(nullable = false) //not null constraint 
	private String type;
	
	@Column(nullable = false) //not null constraint 
	private BigDecimal balance;
	
	@Column(nullable = false) //not null constraint 
	private long user_id;
	
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Transaction(int tranid, String type, BigDecimal balance, long user_id) {
		super();
		this.tranid = tranid;
		this.type = type;
		this.balance = balance;
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Transaction [tranid=" + tranid + ", type=" + type + ", balance=" + balance + ", user_id=" + user_id
				+ "]";
	}

	public int getTranid() {
		return tranid;
	}


	public void setTranid(int tranid) {
		this.tranid = tranid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public long getUser_id() {
		return user_id;
	}


	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTiming() {
		return timing;
	}

	public void setTiming(LocalTime timing) {
		this.timing = timing;
	}

	
	
}
