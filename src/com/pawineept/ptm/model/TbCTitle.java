package com.pawineept.ptm.model;

import java.sql.Timestamp;

public class TbCTitle {
	private Integer id;
	private String titleDescTh;
	private String titleDescEn;
    private String status;
    private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String chkStatus;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public String getChkStatus() {
		return chkStatus;
	}
	public void setChkStatus(String chkStatus) {
		this.chkStatus = chkStatus;
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
}
