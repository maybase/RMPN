package com.pawineept.ptm.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class TbTPayment {
	Long paymentid;
	String receiptNo;
	String patientId;
	String patientName;
	String dateTextEn;
	String dateTextTh;
	Long appointmentid;
	Long flagPrint;
	BigDecimal totalAmount;
	String createBy;
	Timestamp createDatetime;
	
	public Long getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Long paymentid) {
		this.paymentid = paymentid;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDateTextEn() {
		return dateTextEn;
	}
	public void setDateTextEn(String dateTextEn) {
		this.dateTextEn = dateTextEn;
	}
	public String getDateTextTh() {
		return dateTextTh;
	}
	public void setDateTextTh(String dateTextTh) {
		this.dateTextTh = dateTextTh;
	}
	public Long getAppointmentid() {
		return appointmentid;
	}
	public void setAppointmentid(Long appointmentid) {
		this.appointmentid = appointmentid;
	}
	public Long getFlagPrint() {
		return flagPrint;
	}
	public void setFlagPrint(Long flagPrint) {
		this.flagPrint = flagPrint;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
}
