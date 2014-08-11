package com.pawineept.ptm.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.pawineept.ptm.model.TbTPayment;
import com.pawineept.ptm.util.DBUtil;

public class TbTPaymentDAO extends BaseDAO {

	public Long insert(Connection conn, TbTPayment tbTPayment) throws Exception {
		Long result=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("INSERT INTO TB_T_PAYMENT ");
		sql.append("(RECEIPT_NO, DATE_TEXT_EN, DATE_TEXT_TH, PATIENTID, PATIENTNAME, FLAG_PRINT, CREATE_BY, CREATE_DATETIME) ");
		sql.append(" VALUES(?,?,?,?,?,?,?,?)");
		try{
			int i=1;
			ps = conn.prepareStatement(sql.toString());
			value(ps, i++, tbTPayment.getReceiptNo());
			value(ps, i++, tbTPayment.getDateTextEn());
			value(ps, i++, tbTPayment.getDateTextTh());
			value(ps, i++, tbTPayment.getPatientId());
			value(ps, i++, tbTPayment.getPatientName());
			value(ps, i++, tbTPayment.getFlagPrint());
			value(ps, i++, tbTPayment.getCreateBy());
			value(ps, i++, tbTPayment.getCreateDatetime());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				result = rs.getLong(1);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	public void updateTotalAmount(Connection conn, String receiptNo,
			BigDecimal totalAmount) throws Exception {
		String sql = "UPDATE TB_T_PAYMENT SET TOTAL_AMOUNT = ? WHERE RECEIPT_NO = ?";
		PreparedStatement ps = null;
		try{
			int i=1;
			ps = conn.prepareStatement(sql.toString());
			value(ps, i++, totalAmount);
			value(ps, i++, receiptNo);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(ps);
		}
	}

	
}
