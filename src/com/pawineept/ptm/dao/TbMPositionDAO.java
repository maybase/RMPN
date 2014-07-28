package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMPosition;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.util.DBUtil;

public class TbMPositionDAO extends BaseDAO {
	public int insert(Connection conn, TbMPosition obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_M_POSITION ");
        sql.append("(POSTION_NAME, REMARK, status, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ? ) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPosition_name());
	        value(ps,i++,obj.getRemark());
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
	
	public List<TbMPosition> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Position with condition for search
		List<TbMPosition> lst = new ArrayList<TbMPosition>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select id , POSTION_NAME, REMARK, case when status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  from TB_M_POSITION ");
		for(int i=0; i< name.length; i++){
			sql.append("where POSTION_NAME like ? ");
		}
		sql.append("ORDER BY POSTION_NAME ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMPosition obj2 = new TbMPosition();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setPosition_name(rs.getString("POSTION_NAME"));
	            obj2.setRemark(rs.getString("REMARK"));
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
	
	public void select(Connection conn,TbMPosition obj2) throws SQLException{
		//Method : Mode Update and Show Detail position
        String sql = "select po.* from TB_M_POSITION po where po.id = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("id"));
	            obj2.setPosition_name(rs.getString("POSTION_NAME"));
	            obj2.setRemark(rs.getString("REMARK"));
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
	
	public void update(Connection conn,TbMPosition obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_M_POSITION ");
        sql.append("SET POSTION_NAME = ? , ");
        sql.append("REMARK = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPosition_name());
	        value(ps,i++,obj.getRemark());
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
	
	public List<String> findActiveList(Connection conn) throws SQLException{
		//Method : Show List of Active Position
		List<String> result = new ArrayList<String>();
		String sql = "select id, POSTION_NAME from TB_M_POSITION where status = 1 order by POSTION_NAME asc ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result.add(rs.getString("POSTION_NAME"));
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
	
	public int findIdForNameTH(Connection conn, String name) throws SQLException{
		Integer result = null;
		String sql = "select id from TB_M_POSITION where POSTION_NAME = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			value(ps,1, name);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result = rs.getInt("id");
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
	
	public List<TbMPosition> findAllList(Connection conn) throws SQLException {
		//Method : Mode Search All Position 
		List<TbMPosition> lst = new ArrayList<TbMPosition>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select id , POSTION_NAME, REMARK, case when status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  from TB_M_POSITION ");
		sql.append("ORDER BY POSTION_NAME ");
		try{
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMPosition obj2 = new TbMPosition();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setPosition_name(rs.getString("POSTION_NAME"));
	            obj2.setRemark(rs.getString("REMARK"));
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
