package com.pawineept.ptm.model;

public class TbMPaymentDetailType {
	private Integer id;

	private Integer pay_type_id;
	private String pay_detail_type_name;
	private Integer total_num;
	private Integer cost;
	private String status;
	private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String chkStatus;
	private String pay_type_name;
	
	public String getPay_type_name() {
		return pay_type_name;
	}
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPay_type_id() {
		return pay_type_id;
	}
	public void setPay_type_id(Integer pay_type_id) {
		this.pay_type_id = pay_type_id;
	}
	public String getPay_detail_type_name() {
		return pay_detail_type_name;
	}
	public void setPay_detail_type_name(String pay_detail_type_name) {
		this.pay_detail_type_name = pay_detail_type_name;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
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
