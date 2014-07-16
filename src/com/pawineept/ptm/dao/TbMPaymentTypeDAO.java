package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbMMedicalGroup;
import com.pawineept.ptm.model.TbMPaymentType;
import com.pawineept.ptm.util.DBUtil;

public class TbMPaymentTypeDAO extends BaseDAO {
	public int insert(Connection conn, TbMPaymentType obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_M_PAYMENT_TYPE ");
        sql.append("(MEDICAL_GROUP_ID, PAY_TYPE_NAME, OWNER, status, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ?) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getMedical_group_id());
	        value(ps,i++,obj.getPay_type_name());
	        value(ps,i++,obj.getOwner());
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
	
	public List<TbMPaymentType> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Payment Type
		List<TbMPaymentType> lst = new ArrayList<TbMPaymentType>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select pt.id , mg.MEDICAL_GROUP_NAME, pt.PAY_TYPE_NAME , case when pt.owner = 1 then 'ภา' else 'บริษัท' end as ownername , case when mg.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname from TB_M_PAYMENT_TYPE pt , TB_M_MEDICAL_GROUP mg where pt.MEDICAL_GROUP_ID = mg.id ");
		for(int i=0; i< name.length; i++){
			sql.append("and ( mg.MEDICAL_GROUP_NAME like ? OR pt.PAY_TYPE_NAME like ? ) ");
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
	        	TbMPaymentType obj2 = new TbMPaymentType();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setMedical_group_name(rs.getString("MEDICAL_GROUP_NAME"));
	            obj2.setPay_type_name(rs.getString("PAY_TYPE_NAME"));
	            obj2.setOwner(rs.getString("ownername"));
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
	
	public void select(Connection conn,TbMPaymentType obj2) throws SQLException{
		//Method : Mode Update and Show Detail Payment Type
        String sql = "select pt.*, mg.id as mid ,mg.MEDICAL_GROUP_NAME from TB_M_PAYMENT_TYPE pt , TB_M_MEDICAL_GROUP mg where pt.MEDICAL_GROUP_ID = mg.id and pt.id = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("id"));
	            obj2.setPay_type_name(rs.getString("PAY_TYPE_NAME"));
	            obj2.setMedical_group_name(rs.getString("MEDICAL_GROUP_NAME"));
	            obj2.setOwner(rs.getString("owner"));
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
	
	public void update(Connection conn,TbMPaymentType obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_M_PAYMENT_TYPE ");
        sql.append("SET MEDICAL_GROUP_ID = ? , ");
        sql.append("PAY_TYPE_NAME = ? , ");
        sql.append("OWNER = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getMedical_group_id());
	        value(ps,i++,obj.getPay_type_name());
	        value(ps,i++,obj.getOwner());
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
		//Method : Show List of Active Payment Type
		List<String> result = new ArrayList<String>();
		String sql = "select id, PAY_TYPE_NAME from TB_M_PAYMENT_TYPE where status = 1 order by PAY_TYPE_NAME asc ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result.add(rs.getString("PAY_TYPE_NAME"));
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
		String sql = "select id from TB_M_PAYMENT_TYPE where PAY_TYPE_NAME = ?";
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
}
