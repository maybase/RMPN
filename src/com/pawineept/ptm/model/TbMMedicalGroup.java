package com.pawineept.ptm.model;

public class TbMMedicalGroup {
	private Integer id;
	private Integer branch_id;
	private String medical_group_name;
	private String prefix;
	private String status;
	private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String chkStatus;
	private String branch_name;
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	public String getMedical_group_name() {
		return medical_group_name;
	}
	public void setMedical_group_name(String medical_group_name) {
		this.medical_group_name = medical_group_name;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	
}
