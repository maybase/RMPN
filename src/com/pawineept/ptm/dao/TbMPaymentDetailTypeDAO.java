package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbMPaymentDetailType;
import com.pawineept.ptm.model.TbMPaymentType;
import com.pawineept.ptm.util.DBUtil;

public class TbMPaymentDetailTypeDAO extends BaseDAO {
	public int insert(Connection conn, TbMPaymentDetailType obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_M_PATMENT_DETAIL_TYPE ");
        sql.append("(PAY_TYPE_ID, PAY_DETAIL_TYPE_NAME, TOTAL_NUM, COST, STATUS, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ?) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPay_type_id());
	        value(ps,i++,obj.getPay_detail_type_name());
	        value(ps,i++,obj.getTotal_num());
	        value(ps,i++,obj.getCost());
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
	
	public List<TbMPaymentDetailType> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Payment Detail Type with condition for search
		List<TbMPaymentDetailType> lst = new ArrayList<TbMPaymentDetailType>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select pdt.id , pt.PAY_TYPE_NAME, pdt.PAY_DETAIL_TYPE_NAME , pdt.total_num , pdt.cost ,  ");
		sql.append("case when pdt.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname ");
		sql.append("from TB_M_PATMENT_DETAIL_TYPE pdt ");
		sql.append("left outer join TB_M_PAYMENT_TYPE pt on pdt.PAY_TYPE_ID = pt.id ");
		for(int i=0; i< name.length; i++){
			sql.append("where ( pt.PAY_TYPE_NAME like ? OR pdt.PAY_DETAIL_TYPE_NAME like ? ) ");
		}
		sql.append("ORDER BY pt.PAY_TYPE_NAME, pdt.PAY_DETAIL_TYPE_NAME ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMPaymentDetailType obj2 = new TbMPaymentDetailType();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setPay_type_name(rs.getString("PAY_TYPE_NAME"));
	            obj2.setPay_detail_type_name(rs.getString("PAY_DETAIL_TYPE_NAME"));
	            obj2.setTotal_num(rs.getInt("total_num"));
	            obj2.setCost(rs.getInt("cost"));
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
	
	public void select(Connection conn,TbMPaymentDetailType obj2) throws SQLException{
		//Method : Mode Update and Show Detail Payment Detail Type
        String sql = "select pdt.*, pt.id as mid , pt.PAY_TYPE_NAME from TB_M_PATMENT_DETAIL_TYPE pdt left outer join TB_M_PAYMENT_TYPE pt on pdt.PAY_TYPE_ID = pt.id where pdt.id = ? ";
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
	            obj2.setPay_detail_type_name(rs.getString("PAY_DETAIL_TYPE_NAME"));
	            obj2.setTotal_num(rs.getInt("TOTAL_NUM"));
	            obj2.setCost(rs.getInt("COST"));
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
	
	public void update(Connection conn,TbMPaymentDetailType obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_M_PATMENT_DETAIL_TYPE ");
        sql.append("SET PAY_TYPE_ID = ? , ");
        sql.append("PAY_DETAIL_TYPE_NAME = ? , ");
        sql.append("TOTAL_NUM = ? , ");
        sql.append("COST = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPay_type_id());
	        value(ps,i++,obj.getPay_detail_type_name());
	        value(ps,i++,obj.getTotal_num());
	        value(ps,i++,obj.getCost());
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
	
	public List<TbMPaymentDetailType> findAllList(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Payment Detail Type 
		List<TbMPaymentDetailType> lst = new ArrayList<TbMPaymentDetailType>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select pdt.id , pt.PAY_TYPE_NAME, pdt.PAY_DETAIL_TYPE_NAME , pdt.total_num , pdt.cost ,  ");
		sql.append("case when pdt.status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname ");
		sql.append("from TB_M_PATMENT_DETAIL_TYPE pdt ");
		sql.append("left outer join TB_M_PAYMENT_TYPE pt on pdt.PAY_TYPE_ID = pt.id ");
		sql.append("ORDER BY pt.PAY_TYPE_NAME, pdt.PAY_DETAIL_TYPE_NAME ");
		try{
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMPaymentDetailType obj2 = new TbMPaymentDetailType();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setPay_type_name(rs.getString("PAY_TYPE_NAME"));
	            obj2.setPay_detail_type_name(rs.getString("PAY_DETAIL_TYPE_NAME"));
	            obj2.setTotal_num(rs.getInt("total_num"));
	            obj2.setCost(rs.getInt("cost"));
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
