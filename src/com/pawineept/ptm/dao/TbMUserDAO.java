package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.util.DBUtil;

public class TbMUserDAO extends BaseDAO {
	public int insert(Connection conn, TbMUser obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tb_m_t_user ");
        sql.append("(prefix_id, user, pwd, first_name, last_name, last_active, status, created_by, created_date ) ");
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
	
	public List<TbMUser> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All User with condition for search
		List<TbMUser> lst = new ArrayList<TbMUser>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select tu.id , tu.first_name, tu.last_name , tu.user , tu.last_active , ct.TITLE_DESC_TH, ");
		sql.append("case when tu.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  ");
		sql.append("from tb_m_t_user tu ");
		sql.append("left outer join TB_C_TITLE ct on tu.prefix_ID = ct.titleid ");
		
		for(int i=0; i< name.length; i++){
			sql.append("where tu.first_name like ? OR tu.last_name like ? OR tu.user like ? ");
		}
		sql.append("ORDER BY tu.first_name,tu.last_name ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMUser obj2 = new TbMUser();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setFirst_name(rs.getString("first_name"));
	            obj2.setLast_name(rs.getString("last_name"));
	            obj2.setUser(rs.getString("user"));
	            obj2.setLast_active(rs.getString("last_active"));
	            obj2.setStatus(rs.getString("statusname"));
	            obj2.setTitle(new TbCTitle());
	            obj2.getTitle().setTitleDescTh(rs.getString("TITLE_DESC_TH"));
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
	
	public void select(Connection conn,TbMUser obj2) throws SQLException{
		//Method : Mode Update and Show Detail User
        String sql = "select us.*, ti.titleid ,ti.title_desc_th from tb_m_t_user us , tb_c_title ti where us.prefix_id = ti.titleid and us.id = ? ";
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
	            obj2.setFirst_name(rs.getString("first_name"));
	            obj2.setLast_name(rs.getString("last_name"));
	            obj2.setUser(rs.getString("user"));
	            obj2.setPwd(rs.getString("pwd"));
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
	
	public List<TbMUser> findAllList(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All User 
		List<TbMUser> lst = new ArrayList<TbMUser>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select tu.id , tu.first_name, tu.last_name , tu.user , tu.last_active , ct.TITLE_DESC_TH, ");
		sql.append("case when tu.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  ");
		sql.append("from tb_m_t_user tu ");
		sql.append("left outer join TB_C_TITLE ct on tu.prefix_ID = ct.titleid ");
		sql.append("ORDER BY tu.first_name,tu.last_name ");
		try{
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMUser obj2 = new TbMUser();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setFirst_name(rs.getString("first_name"));
	            obj2.setLast_name(rs.getString("last_name"));
	            obj2.setUser(rs.getString("user"));
	            obj2.setLast_active(rs.getString("last_active"));
	            obj2.setStatus(rs.getString("statusname"));
	            obj2.setTitle(new TbCTitle());
	            obj2.getTitle().setTitleDescTh(rs.getString("TITLE_DESC_TH"));
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
