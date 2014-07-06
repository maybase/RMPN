package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.util.DBUtil;

public class TbMUserDAO extends BaseDAO {
	public int insert(Connection conn, TbMUser obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tb_m_t_user ");
        sql.append("(PREFIX_ID, USER, PWD, FIRST_NAME, LAST_NAME, LAST_ACTIVE, STATUS, CREATED_BY, CREATED_DATE ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPrefixId());
	        value(ps,i++,obj.getUser());
	        value(ps,i++,obj.getPwd());
	        value(ps,i++,obj.getFirst_name());
	        value(ps,i++,obj.getLast_name());
	        value(ps,i++,obj.getLast_active());
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
	
	public void update(Connection conn,TbMUser obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tb_m_t_user ");
        sql.append("SET PREFIX_ID = ? , ");
        sql.append("USER = ? , ");
        sql.append("PWD = ? , ");
        sql.append("FIRST_NAME = ?, ");
        sql.append("LAST_NAME = ? , ");
        sql.append("LAST_ACTIVE = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPrefixId());
	        value(ps,i++,obj.getUser());
	        value(ps,i++,obj.getPwd());
	        value(ps,i++,obj.getFirst_name());
	        value(ps,i++,obj.getLast_name());
	        value(ps,i++,obj.getLast_active());
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
}
