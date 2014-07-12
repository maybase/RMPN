package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbMBranch;
import com.pawineept.ptm.util.DBUtil;

public class TbMBranchDAO extends BaseDAO {
	public int insert(Connection conn, TbMBranch obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TB_M_BRANCH ");
        sql.append("(BRANCHNAME, BRANCHADDRESS, BRANCHPHONE, status, created_by, created_date ) ");
        sql.append("VALUES ( ?, ?, ?, ?, ?, ? ) ");
        PreparedStatement ps = null;
        int id = 0;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getBranchName());
	        value(ps,i++,obj.getBranchAddress());
	        value(ps,i++,obj.getBranchPhone());
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
	
	public List<TbMBranch> findAllName(Connection conn, String[] name) throws SQLException {
		//Method : Mode Search All Branch
		List<TbMBranch> lst = new ArrayList<TbMBranch>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select id , BRANCHNAME, BRANCHADDRESS, BRANCHPHONE , case when status = 1 then 'ใช้งาน' else 'ไม่ใช้งาน' end as statusname  from TB_M_BRANCH where 1=2 ");
		for(int i=0; i< name.length; i++){
			sql.append("OR BRANCHNAME like ? OR BRANCHADDRESS like ? ");
		}
		sql.append("ORDER BY 1,2 ");
		try{
			ps = conn.prepareStatement(sql.toString());
	        int num=1;
			for(int i=0; i< name.length; i++){
				value(ps,num++,"%"+name[i]+"%");
				value(ps,num++,"%"+name[i]+"%");
			}
			rs = ps.executeQuery();
	        while(rs.next()){
	        	TbMBranch obj2 = new TbMBranch();
	        	obj2.setId(rs.getInt("id"));
	            obj2.setBranchName(rs.getString("BRANCHNAME"));
	            obj2.setBranchAddress(rs.getString("BRANCHADDRESS"));
	            obj2.setBranchPhone(rs.getString("BRANCHPHONE"));
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
	
	public void select(Connection conn,TbMBranch obj2) throws SQLException{
		//Method : Mode Update and Show Detail Branch
        String sql = "select br.* from TB_M_BRANCH br where br.id = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getId());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setId(rs.getInt("id"));
	            obj2.setBranchName(rs.getString("BRANCHNAME"));
	            obj2.setBranchAddress(rs.getString("BRANCHADDRESS"));
	            obj2.setBranchPhone(rs.getString("BRANCHPHONE"));
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
	
	public void update(Connection conn,TbMBranch obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_M_BRANCH ");
        sql.append("SET BRANCHNAME = ? , ");
        sql.append("BRANCHADDRESS = ? , ");
        sql.append("BRANCHPHONE = ? , ");
        sql.append("STATUS = ? , ");
        sql.append("UPDATED_BY = ? , ");
        sql.append("UPDATED_DATE = ?  ");
        sql.append("WHERE ID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getBranchName());
	        value(ps,i++,obj.getBranchAddress());
	        value(ps,i++,obj.getBranchPhone());
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
		//Method : Show List of Active Branch
		List<String> result = new ArrayList<String>();
		String sql = "select id, branchname from TB_M_BRANCH where status = 1 order by branchname asc ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
	        while(rs.next()){
	        	result.add(rs.getString("branchname"));
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
