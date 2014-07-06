/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.util.DBUtil;

/**
 *
 * @author MAY
 */
public class TbTPatientDAO extends BaseDAO {
    public void insert(Connection conn, TbTPatient obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tb_t_patient ");
        sql.append("(PATIENTID,TITLEID, FIRSTNAME, LASTNAME, AGE, BIRTH_DATE,Occupation,POSITION,ADDRESS,TELEPHONE,MOBILEPHONE,EMAIL,IDCARD, ");
        sql.append("FLAG_NOUSE_PATIENTID,CREATE_BY,CREATE_DATETIME,NICKNAME) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, datetime('now','localtime'),?)");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getPatientid());
	        value(ps,i++,obj.getTitle().getTitleid());
	        value(ps,i++,obj.getFirstname());
	        value(ps,i++,obj.getLastname());
	        value(ps,i++,obj.getAge());
	        value(ps,i++,obj.getBirthDate());
	        value(ps,i++,obj.getOccupation());
	        value(ps,i++,obj.getPosition());
	        value(ps,i++,obj.getAddress());
	        value(ps,i++,obj.getTelephone());
	        value(ps,i++,obj.getMobilephone());
	        value(ps,i++,obj.getEmail());
	        value(ps,i++,obj.getIdcard());
	        value(ps,i++,obj.getFlagNousePatientid());
	        value(ps,i++,obj.getCreateBy());
	        value(ps,i++,obj.getNickname());
	        ps.execute();
        }catch(SQLException e){
        	e.printStackTrace();
        	throw e;
        }finally{
            DBUtil.close(ps);
        }
    }

    public void update(Connection conn,TbTPatient obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TB_T_PATIENT ");
        sql.append("SET TITLEID = ? , ");
        sql.append("FIRSTNAME = ? , ");
        sql.append("LASTNAME = ? , ");
        sql.append("NICKNAME = ?, ");
        sql.append("AGE = ? , ");
        sql.append("BIRTH_DATE = ? , ");
        sql.append("POSITION = ? , ");
        sql.append("ADDRESS = ? , ");
        sql.append("TELEPHONE = ? , ");
        sql.append("MOBILEPHONE = ? , ");
        sql.append("EMAIL = ? , ");
        sql.append("IDCARD = ? , ");
        sql.append("FLAG_NOUSE_PATIENTID = ? , ");
        sql.append("MODIFIED_BY = ? , ");
        sql.append("MODIFIED_DATETIME = datetime('now','localtime') ");
        sql.append("WHERE PATIENTID = ? ");
        PreparedStatement ps = null;
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj.getTitle().getTitleid());
	        value(ps,i++,obj.getFirstname());
	        value(ps,i++,obj.getLastname());
	        value(ps,i++,obj.getNickname());
	        value(ps,i++,obj.getAge());
	        value(ps,i++,obj.getBirthDate());
	        value(ps,i++,obj.getPosition());
	        value(ps,i++,obj.getAddress());
	        value(ps,i++,obj.getTelephone());
	        value(ps,i++,obj.getMobilephone());
	        value(ps,i++,obj.getEmail());
	        value(ps,i++,obj.getIdcard());
	        value(ps,i++,obj.getFlagNousePatientid());
	        value(ps,i++,obj.getCreateBy());
	        value(ps,i++,obj.getPatientid());
	        ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }finally{
        	DBUtil.close(ps);
        }
        
    }
    
    public void select(Connection conn,TbTPatient obj2) throws SQLException{
        String sql = "SELECT PA.*,TI.TITLE_DESC_TH FROM TB_T_PATIENT PA , TB_C_TITLE TI WHERE PA.TITLEID = TI.TITLEID AND PA.PATIENTID = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,obj2.getPatientid());
	        rs = ps.executeQuery();
	        if(rs.next()){
	            obj2.setPatientid(rs.getString("PATIENTID"));
	            TbCTitle title = new TbCTitle();
	            title.setTitleid(getLong(rs.getLong("TITLEID")));
	            title.setTitleDescTh(rs.getString("TITLE_DESC_TH"));
	            obj2.setTitle(title);
	            obj2.setFirstname(rs.getString("FIRSTNAME"));
	            obj2.setLastname(rs.getString("LASTNAME"));
	            obj2.setAge(getLong(rs.getLong("AGE")));
	            obj2.setBirthDate(rs.getTimestamp("BIRTH_DATE"));
	            obj2.setOccupation(rs.getString("occupation"));
	            obj2.setAddress(rs.getString("ADDRESS"));
	            obj2.setTelephone(rs.getString("TELEPHONE"));
	            obj2.setMobilephone(rs.getString("MOBILEPHONE"));
	            obj2.setEmail(rs.getString("EMAIL"));
	            obj2.setIdcard(rs.getString("IDCARD"));
	            obj2.setFlagNousePatientid(rs.getString("FLAG_NOUSE_PATIENTID"));
	        }        
        }catch(SQLException e){
	        e.printStackTrace();
	        throw e;
        }finally{
        	DBUtil.close(rs);
	        DBUtil.close(ps);
        }
    }

    public List<TbTPatient> findByFullName(Connection conn,String text) throws SQLException {
        String sql = "SELECT PA.PATIENTID,PA.FIRSTNAME,PA.LASTNAME,TI.TITLE_DESC_TH FROM TB_T_PATIENT PA, TB_C_TITLE TI WHERE PA.TITLEID  = TI.TITLEID  AND FIRSTNAME Like ? ORDER BY 1,2";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TbTPatient> lst = new ArrayList<TbTPatient>();
        try{
	        ps = conn.prepareStatement(sql.toString());
	        int i=1;
	        value(ps,i++,text+"%");
	        rs = ps.executeQuery();
	        while(rs.next()){
	        	TbTPatient obj2 = new TbTPatient();
	            obj2.setPatientid(rs.getString("PATIENTID"));
	            obj2.setFirstname(rs.getString("FIRSTNAME"));
	            obj2.setLastname(rs.getString("LASTNAME"));
	            TbCTitle title = new TbCTitle();
	            title.setTitleDescTh(rs.getString("TITLE_DESC_TH"));
	            obj2.setTitle(title);
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

	public List<TbTPatient> findAllName(Connection conn, String[] name) throws SQLException {
		List<TbTPatient> lst = new ArrayList<TbTPatient>();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select patientid, firstname, lastname, nickname, mobilephone, telephone,lastservice from tb_t_patient where 1=2 ");
		for(int i=0; i< name.length; i++){
			sql.append("OR firstname like ? OR lastname like ? OR nickname like ? ");
		}
		sql.append("ORDER BY 2,3 ");
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
	        	TbTPatient obj2 = new TbTPatient();
	            obj2.setPatientid(rs.getString("PATIENTID"));
	            obj2.setFirstname(rs.getString("FIRSTNAME"));
	            obj2.setLastname(rs.getString("LASTNAME"));
	            obj2.setNickname(rs.getString("NICKNAME"));
	            obj2.setLastservice(rs.getTimestamp("LASTSERVICE"));
	            obj2.setTelephone(rs.getString("telephone"));
	            obj2.setMobilephone(rs.getString("mobilephone"));
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

	public TbTPatient findById(String ptid,Connection conn) throws SQLException {
		TbTPatient obj2 = new TbTPatient();
		StringBuilder sql = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		sql.append("select patientid, firstname, lastname, address, lastservice, age from tb_t_patient where ");
		sql.append("patientid=?");
		try{
			ps = conn.prepareStatement(sql.toString());
			value(ps,1,ptid);
			rs = ps.executeQuery();
	        if(rs.next()){
	        	
	            obj2.setPatientid(rs.getString("PATIENTID"));
	            obj2.setFirstname(rs.getString("FIRSTNAME"));
	            obj2.setLastname(rs.getString("LASTNAME"));
	            obj2.setLastservice(rs.getTimestamp("LASTSERVICE"));
	            obj2.setAddress(rs.getString("ADDRESS"));
	            obj2.setAge(rs.getLong("age"));
	        }
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			DBUtil.close(rs);
	        DBUtil.close(ps);
		}
		return obj2;
	}

    
}
