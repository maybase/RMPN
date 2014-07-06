package com.pawineept.ptm.model;

import java.sql.Timestamp;

public class TbCTitle {
	private Long titleid;
	private String titleDescTh;
	private String titleDescEn;
	private String createBy;
	private Timestamp createDate;
	
	public Long getTitleid() {
		return titleid;
	}
	public void setTitleid(Long titleid) {
		this.titleid = titleid;
	}
	public String getTitleDescTh() {
		return titleDescTh;
	}
	public void setTitleDescTh(String titleDescTh) {
		this.titleDescTh = titleDescTh;
	}
	public String getTitleDescEn() {
		return titleDescEn;
	}
	public void setTitleDescEn(String titleDescEn) {
		this.titleDescEn = titleDescEn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
