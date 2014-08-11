package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.pawineept.ptm.model.TbTPaymentDetail;
import com.pawineept.ptm.util.DBUtil;

public class TbTPaymentDetailDAO extends BaseDAO {

	public int insert(Connection conn, TbTPaymentDetail detail) throws Exception {
		int result = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("INSERT INTO TB_T_PAYMENT_DETAIL ");
		sql.append("(RECEIPT_NO,PAY_DETAIL_TYPE,UNIT,AMOUNT,CREATE_BY,CREATE_DATETIME) ");
		sql.append(" VALUES(?,?,?,?,?,?)");
		try{
			int i=1;
			ps = conn.prepareStatement(sql.toString());
			value(ps, i++, detail.getReceiptNo());
			value(ps, i++, detail.getPayDetailType());
			value(ps, i++, detail.getUnit());
			value(ps, i++, detail.getAmount());
			value(ps, i++, detail.getCreateBy());
			value(ps, i++, detail.getCreateDatetime());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	

}
