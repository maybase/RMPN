package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.util.DBUtil;

public class TbCTitleDAO extends BaseDAO {
	final String TABLE = "tb_c_title";
	public List<String> findAllList(Connection conn) throws SQLException{
		List<String> result = new ArrayList<String>();
		String sql = "select titleid, title_desc_th from  "+TABLE+" order by titleid asc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result.add(rs.getString("TITLE_DESC_TH"));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
	        DBUtil.close(rs);
	        DBUtil.close(ps);
		}
		return result;		
	}
	
	public Long findIdForNameTH(Connection conn, String name) throws SQLException{
		Long result = null;
		String sql = "select titleid from "+TABLE+" where title_desc_th = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			value(ps,1, name);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result = getLong(rs.getLong("titleid"));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
	        DBUtil.close(ps);
		}
		return result;
	}
	
	public boolean insert(Connection conn, TbCTitle model) throws SQLException{
		String sql = "insert into "+TABLE+" (titleid, title_desc_th, title_desc_en, create_by, create_datetime) values(?,?,?,?,datetime('now','localtime'))";
		PreparedStatement ps = null;
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			value(ps,1, model.getTitleid());
			value(ps,2, model.getTitleDescTh());
			value(ps,3, model.getTitleDescEn());
			value(ps,4, model.getCreateBy());
			result = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(ps);
		}
		return result;
	}
}
