package com.models;
import java.sql.Date;

public class TipModel {
	String tipText ;
	int tipID ;
	Date tipDate ;
	private int placeId;
	private int brandId;

	public String getTipText() {
		return tipText;
	}
	public void setTipText(String tipText) {
		this.tipText = tipText;
	}
	public int getTipID() {
		return tipID;
	}
	public void setTipID(int tipID) {
		this.tipID = tipID;
	}
	public Date getTipDate() {
		return tipDate;
	}
	public void setTipDate(Date tipDate) {
		this.tipDate = tipDate;
	}

	public void addTip(){

	}

	public void removeTip(int id){

	}
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

}