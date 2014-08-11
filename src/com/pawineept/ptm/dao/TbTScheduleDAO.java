package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.util.DBUtil;

public class TbTScheduleDAO extends BaseDAO {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.US);
	SimpleDateFormat sdfshot = new SimpleDateFormat("dd/MM/yyyy",Locale.US);

	public List<TbTSchedule> findByDateTime(Connection conn, Date parse) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TbTSchedule> lst = new ArrayList<TbTSchedule>();
		String sql = "select * from Tb_T_Schedule where datetime = ? ";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,sdf.format(parse));
			rs = ps.executeQuery();
			while(rs.next()){
				TbTSchedule s = new TbTSchedule();
				s.setId(rs.getLong("ID"));
				s.setName(rs.getString("NAME"));
				s.setPatientId(rs.getString("PATIENT_ID"));
				s.setTel(rs.getString("TEL"));
				s.setDatetime(rs.getString("DATETIME"));
				s.setStatus(rs.getString("STATUS"));
				lst.add(s);
			}
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
//		System.out.println(" size:"+lst.size());
		return lst;
	}

	public void insert(TbTSchedule obj, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "insert into Tb_T_Schedule (ID, PATIENT_ID, NAME, TEL, DATETIME, STATUS) select (max(id)+1),?,?,?,?,1 from Tb_T_Schedule";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,obj.getPatientId());
			value(ps,i++,obj.getName());
			value(ps,i++,obj.getTel());
			value(ps,i++,obj.getDatetime());
			ps.executeUpdate();
		}finally{
			DBUtil.close(ps);
		}
		
	}

	public int countAllByDate(Date date, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select COUNT(*) CNT from Tb_T_Schedule where datetime like ? and status in(1,2)";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,sdfshot.format(date)+" %");
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("CNT");
			}
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}
	
	public int countAllByDateAndStatus(Date date, int status, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select COUNT(*) CNT from Tb_T_Schedule where datetime like ? and status = ?";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,sdfshot.format(date)+" %");
			value(ps,i++,status);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getInt("CNT");
			}
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	public void updateStatus(TbTSchedule model, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "update Tb_T_Schedule set STATUS=? where id=?";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,model.getStatus());
			value(ps,i++,model.getId());
			ps.executeUpdate();
		}finally{
			DBUtil.close(ps);
		}
		
	}

	public void updateDateTime(TbTSchedule model, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "update Tb_T_Schedule set datetime=? where id=?";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,model.getDatetime());
			value(ps,i++,model.getId());
			ps.executeUpdate();
		}finally{
			DBUtil.close(ps);
		}
	}
	
	public List<TbTSchedule> findUsed(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<TbTSchedule> lst = new ArrayList<TbTSchedule>();
		String sql = "select ID,NAME,PATIENT_ID from Tb_T_Schedule where datetime like ? and STATUS = ?";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,sdfshot.format(new Date())+" %");
			value(ps,i++,2);
			rs = ps.executeQuery();
			while(rs.next()){
				TbTSchedule s = new TbTSchedule();
				s.setId(rs.getLong("ID"));
				s.setName(rs.getString("NAME"));
				s.setPatientId(rs.getString("PATIENT_ID"));
				lst.add(s);
			}
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return lst;
	}

	public void updateStatusPayment(String PATIENT_ID, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String sql = "update Tb_T_Schedule set status=3 where PATIENT_ID=? and status=2";
		try{
			ps = conn.prepareStatement(sql);
			int i=1;
			value(ps,i++,PATIENT_ID);
			ps.executeUpdate();
		}finally{
			DBUtil.close(ps);
		}
	}

}
