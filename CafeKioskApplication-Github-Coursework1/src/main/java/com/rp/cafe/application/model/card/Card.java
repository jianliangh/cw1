package com.rp.cafe.application.model.card;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Table;

import java.time.YearMonth;

@Entity
public class Card {
	
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // add an auto increment ID to database table
	private int cardid;
	private long cardnumber;
	private String cardname;
	private YearMonth validuntil;
	private Integer cvv;
	private BigDecimal cardbalance;
	private int userid;
	
	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Card(int cardid, long cardnumber, String cardname, YearMonth validuntil, Integer cvv, BigDecimal cardbalance,
			int userid) {
		super();
		this.cardid = cardid;
		this.cardnumber = cardnumber;
		this.cardname = cardname;
		this.validuntil = validuntil;
		this.cvv = cvv;
		this.cardbalance = cardbalance;
		this.userid = userid;
	}
	
	@Override
	public String toString() {
		return "Card [cardid=" + cardid + ", cardnumber=" + cardnumber + ", cardname=" + cardname + ", validuntil="
				+ validuntil + ", cvv=" + cvv + ", cardbalance=" + cardbalance + ", userid=" + userid + "]";
	}

	public int getCardid() {
		return cardid;
	}

	public void setCardid(int cardid) {
		this.cardid = cardid;
	}

	public long getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(long cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public YearMonth getValiduntil() {
		return validuntil;
	}

	public void setValiduntil(YearMonth validuntil) {
		this.validuntil = validuntil;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public BigDecimal getCardbalance() {
		return cardbalance;
	}
	
	public String yourCardBalance() {
		return "Your Card Balance: $" + cardbalance;
	}
	
	public String yourCardBalanceInDashboard() {
		return "Your Card Balance: <br> $" + cardbalance;
	}

	public void setCardbalance(BigDecimal cardbalance) {
		this.cardbalance = cardbalance;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

		
}
