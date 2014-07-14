package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMBranch;
import com.pawineept.ptm.model.TbMMedicalGroup;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.util.DBUtil;

public class TbMMedicalGroupDAO extends BaseDAO {
	public int insert(Connection conn, TbMMedicalGroup obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_M_MEDICAL_GROUP ");
        sql.append("(BRANCH_ID, MEDICAL_GROUP_NAME, PREFIX, status, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ?) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getBranch_id());
	        value(ps,i++,obj.getMedical_group_name());
	        value(ps,i++,obj.getPrefix());
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
	
	public List<TbMMedicalGroup> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Medical Group
		List<TbMMedicalGroup> lst = new ArrayList<TbMMedicalGroup>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select mg.id , mb.BRANCHNAME, mg.MEDICAL_GROUP_NAME , mg.prefix , case when mg.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname from TB_M_MEDICAL_GROUP mg , TB_M_BRANCH mb where mg.branch_id = mb.id ");
		for(int i=0; i< name.length; i++){
			sql.append("and ( mg.MEDICAL_GROUP_NAME like ? OR mb.BRANCHNAME like ? ) ");
		}
		sql.append("ORDER BY 2,3 ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMMedicalGroup obj2 = new TbMMedicalGroup();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setBranch_name(rs.getString("BRANCHNAME"));
	            obj2.setMedical_group_name(rs.getString("MEDICAL_GROUP_NAME"));
	            obj2.setPrefix(rs.getString("prefix"));
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
	
	public void select(Connection conn,TbMMedicalGroup obj2) throws SQLException{
		//Method : Mode Update and Show Detail Medical Group
        String sql = "select mg.*, mb.id as mid ,mb.branchname from TB_M_MEDICAL_GROUP mg , TB_M_BRANCH mb where mg.branch_id = mb.id and mg.id = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("id"));
	            obj2.setBranch_name(rs.getString("branchname"));
	            obj2.setMedical_group_name(rs.getString("MEDICAL_GROUP_NAME"));
	            obj2.setPrefix(rs.getString("PREFIX"));
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
	
	public void update(Connection conn,TbMMedicalGroup obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_M_MEDICAL_GROUP ");
        sql.append("SET BRANCH_ID = ? , ");
        sql.append("MEDICAL_GROUP_NAME = ? , ");
        sql.append("PREFIX = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getBranch_id());
	        value(ps,i++,obj.getMedical_group_name());
	        value(ps,i++,obj.getPrefix());
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
