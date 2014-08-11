package com.pawineept.ptm.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TbTPaymentDetail {
	Long paymentDetailId;
	Long paymentid;
	String receiptNo;
	Long payDetailType;
	Integer unit;
	BigDecimal amount;
	String createBy;
	Timestamp createDatetime;
	
	public Long getPaymentDetailId() {
		return paymentDetailId;
	}
	public void setPaymentDetailId(Long paymentDetailId) {
		this.paymentDetailId = paymentDetailId;
	}
	public Long getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Long paymentid) {
		this.paymentid = paymentid;
	}
	public Long getPayDetailType() {
		return payDetailType;
	}
	public void setPayDetailType(Long payDetailType) {
		this.payDetailType = payDetailType;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
}
