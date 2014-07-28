package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMPosition;
import com.pawineept.ptm.model.TbTStaff;
import com.pawineept.ptm.util.DBUtil;

public class TbTStaffDAO extends BaseDAO {
	public int insert(Connection conn, TbTStaff obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_T_STAFF ");
        sql.append("(TITLE_ID, FIRST_NAME, LAST_NAME, ADDRESS, PHONE, POSITION_ID, JOBTYPE, status, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getTitleId());
	        value(ps,i++,obj.getFirstName());
	        value(ps,i++,obj.getLastName());
	        value(ps,i++,obj.getAddress());
	        value(ps,i++,obj.getPhone());
	        value(ps,i++,obj.getPositionId());
	        value(ps,i++,obj.getJobType());
	        value(ps,i++,obj.getStatus());
	        value(ps,i++,obj.getCreated_by());
	        value(ps,i++,obj.getCreated_date());
	        ps.execute();
	        ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                id = rs.getInt(1);
            }
        }catch(SQLException e){
        	e.printStackTrace();
        	throw e;
        }finally{
            DBUtil.close(ps);
        }
        return id;
    }
	
	public List<TbTStaff> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Staff with condition for search
		List<TbTStaff> lst = new ArrayList<TbTStaff>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select ts.id, ts.FIRST_NAME, ts.LAST_NAME, ts.ADDRESS, ts.PHONE, ct.TITLE_DESC_TH, po.POSTION_NAME  ");
		sql.append(", case when ts.jobtype = 1 then 'ประจำ' else 'ขั่วคราว' end as jobtypename ");
		sql.append(", case when ts.status = 1 then 'ปกติ' else 'ลาออก' end as statusname  ");
		sql.append(" from TB_T_STAFF ts ");
		sql.append("left outer join TB_C_TITLE ct on ts.TITLE_ID = ct.titleid ");
		sql.append("left outer join TB_M_POSITION po on ts.position_id = po.id ");
		
		for(int i=0; i< name.length; i++){
			sql.append(" where ts.FIRST_NAME like ? OR ts.LAST_NAME like ? OR po.POSTION_NAME like ? ");
		}
		sql.append("ORDER BY FIRST_NAME,LAST_NAME ");
		try{
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbTStaff obj2 = new TbTStaff();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setFirstName(rs.getString("FIRST_NAME"));
	            obj2.setLastName(rs.getString("LAST_NAME"));
	            obj2.setAddress(rs.getString("ADDRESS"));
	            obj2.setPhone(rs.getString("PHONE"));
	            obj2.setTitleName(rs.getString("TITLE_DESC_TH"));
	            obj2.setPositionName(rs.getString("POSTION_NAME"));
	            obj2.setJobTypeName(rs.getString("jobtypename"));
	            obj2.setStatus(rs.getString("statusname"));
	            lst.add(obj2);
	        }
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
	        DBUtil.close(ps);
		}
		return lst;		
	}
	
	public void select(Connection conn,TbTStaff obj2) throws SQLException{
		//Method : Mode Update and Show Detail Staff
        String sql = "select ts.*, ct.TITLEID, ct.TITLE_DESC_TH, po.id as poId,  po.POSTION_NAME"
        		+ " from TB_T_STAFF ts "
        		+ " left outer join TB_C_TITLE ct on ts.TITLE_ID = ct.titleid "
        		+ " left outer join TB_M_POSITION po on ts.position_id = po.id "
        		+ " where ts.id = ? ";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("id"));
	            TbCTitle title = new TbCTitle();
	            title.setId(rs.getInt("titleid"));
	            title.setTitleDescTh(rs.getString("title_desc_th"));
	            obj2.setTitle(title);
	            obj2.setFirstName(rs.getString("FIRST_NAME"));
	            obj2.setLastName(rs.getString("LAST_NAME"));
	            obj2.setAddress(rs.getString("ADDRESS"));
	            obj2.setPhone(rs.getString("PHONE"));
	            TbMPosition position = new TbMPosition();
	            position.setId(rs.getInt("poId"));
	            position.setPosition_name(rs.getString("POSTION_NAME"));
	            obj2.setPosition(position);
	            
	            obj2.setJobType(rs.getString("JOBTYPE"));
	            obj2.setStatus(rs.getString("status"));
	        }        
        }catch(SQLException e){
	        e.printStackTrace();
	        throw e;
        }finally{
        	DBUtil.close(rs);
	        DBUtil.close(ps);
        }
    }
	
	public void update(Connection conn,TbTStaff obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_T_STAFF ");
        sql.append("SET TITLE_ID = ? , ");
        sql.append("FIRST_NAME = ? , ");
        sql.append("LAST_NAME = ? , ");
        sql.append("ADDRESS = ?, ");
        sql.append("PHONE = ? , ");
        sql.append("POSITION_ID = ? , ");
        sql.append("JOBTYPE = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getTitleId());
	        value(ps,i++,obj.getFirstName());
	        value(ps,i++,obj.getLastName());
	        value(ps,i++,obj.getAddress());
	        value(ps,i++,obj.getPhone());
	        value(ps,i++,obj.getPositionId());
	        value(ps,i++,obj.getJobType());
	        value(ps,i++,obj.getStatus());
	        value(ps,i++,obj.getUpdated_by());
	        value(ps,i++,obj.getUpdated_date());
	        value(ps,i++,obj.getId());
	        ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
        	DBUtil.close(ps);
        }
        
    }
	
	public List<TbTStaff> findAllList(Connection conn) throws SQLException {
		//Method : Mode Search All Staff 
		List<TbTStaff> lst = new ArrayList<TbTStaff>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select ts.id, ts.FIRST_NAME, ts.LAST_NAME, ts.ADDRESS, ts.PHONE, ct.TITLE_DESC_TH, po.POSTION_NAME  ");
		sql.append(", case when ts.jobtype = 1 then 'ประจำ' else 'ขั่วคราว' end as jobtypename ");
		sql.append(", case when ts.status = 1 then 'ปกติ' else 'ลาออก' end as statusname  ");
		sql.append(" from TB_T_STAFF ts ");
		sql.append("left outer join TB_C_TITLE ct on ts.TITLE_ID = ct.titleid ");
		sql.append("left outer join TB_M_POSITION po on ts.position_id = po.id ");
		sql.append("ORDER BY FIRST_NAME,LAST_NAME ");
		try{
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbTStaff obj2 = new TbTStaff();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setFirstName(rs.getString("FIRST_NAME"));
	            obj2.setLastName(rs.getString("LAST_NAME"));
	            obj2.setAddress(rs.getString("ADDRESS"));
	            obj2.setPhone(rs.getString("PHONE"));
	            obj2.setTitleName(rs.getString("TITLE_DESC_TH"));
	            obj2.setPositionName(rs.getString("POSTION_NAME"));
	            obj2.setJobTypeName(rs.getString("jobtypename"));
	            obj2.setStatus(rs.getString("statusname"));
	            lst.add(obj2);
	        }
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
	        DBUtil.close(ps);
		}
		return lst;		
	}
	
}
