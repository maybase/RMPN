package com.pawineept.ptm.model;

public class TbMPaymentType {
	private Integer id;

	private Integer medical_group_id;
	private String pay_type_name;
	private String owner;
	private String status;
	private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String chkStatus;
	private String medical_group_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMedical_group_id() {
		return medical_group_id;
	}
	public void setMedical_group_id(Integer medical_group_id) {
		this.medical_group_id = medical_group_id;
	}
	public String getPay_type_name() {
		return pay_type_name;
	}
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
	public String getMedical_group_name() {
		return medical_group_name;
	}
	public void setMedical_group_name(String medical_group_name) {
		this.medical_group_name = medical_group_name;
	}

	
}
