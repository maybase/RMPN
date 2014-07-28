package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.util.DBUtil;

public class TbCTitleDAO extends BaseDAO {
	final String TABLE = "tb_c_title";
	public List<String> findAllList(Connection conn) throws SQLException{
		List<String> result = new ArrayList<String>();
		String sql = "select titleid, title_desc_th from  "+TABLE+" where status = 1 order by titleid asc";
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
	
	public int findIdForNameTH(Connection conn, String name) throws SQLException{
		Integer result = null;
		String sql = "select titleid from "+TABLE+" where title_desc_th = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			value(ps,1, name);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result = rs.getInt("titleid");
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
	
	public int insert(Connection conn, TbCTitle model) throws SQLException{
		String sql = "insert into "+TABLE+" (title_desc_th, title_desc_en, status, create_by, create_datetime) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		int id = 0;
		try {
			ps = conn.prepareStatement(sql);

			value(ps,1, model.getTitleDescTh());
			value(ps,2, model.getTitleDescEn());
			value(ps,3, model.getStatus());
			value(ps,4, model.getCreated_by());
			value(ps,5, model.getCreated_date());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                id = rs.getInt(1);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(ps);
		}
		return id;
	}
	
	public void update(Connection conn,TbCTitle obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tb_c_title ");
        sql.append("SET title_desc_th = ? , ");
        sql.append("title_desc_en = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE titleid = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getTitleDescTh());
	        value(ps,i++,obj.getTitleDescEn());
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
	
	public void select(Connection conn,TbCTitle obj2) throws SQLException{
		//Method : Mode Update and Show Detail Title
        String sql = "select ti.* from tb_c_title ti where ti.titleid = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("titleid"));
	            obj2.setTitleDescTh(rs.getString("title_desc_th"));
	            obj2.setTitleDescEn(rs.getString("title_desc_en"));
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
	
	public List<TbCTitle> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Title with condition for search
		List<TbCTitle> lst = new ArrayList<TbCTitle>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select titleid, title_desc_th, title_desc_en, case when status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  from tb_c_title  ");
		for(int i=0; i< name.length; i++){
			sql.append("where title_desc_th like ? OR title_desc_en like ? ");
		}
		sql.append("ORDER BY title_desc_th ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbCTitle obj2 = new TbCTitle();
	        	obj2.setId(rs.getInt("titleid"));
	            obj2.setTitleDescTh(rs.getString("title_desc_th"));
	            obj2.setTitleDescEn(rs.getString("title_desc_en"));
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
	
	public List<TbCTitle> findAllDetailList(Connection conn) throws SQLException {
		//Method : Mode Search All Title 
		List<TbCTitle> lst = new ArrayList<TbCTitle>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select titleid, title_desc_th, title_desc_en, case when status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  from tb_c_title ");
		sql.append("ORDER BY title_desc_th ");
		try{
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbCTitle obj2 = new TbCTitle();
	        	obj2.setId(rs.getInt("titleid"));
	            obj2.setTitleDescTh(rs.getString("title_desc_th"));
	            obj2.setTitleDescEn(rs.getString("title_desc_en"));
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
