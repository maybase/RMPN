package com.pawineept.ptm.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public class BaseDAO {
	public Long getLong(long num){
		return new Long(num);	
	}
	
	protected void value(PreparedStatement ps, int i, String obj) throws SQLException {
        if(obj==null){
            ps.setNull(i, Types.VARCHAR);
        }else{
            ps.setString(i, obj);
        }
    }
    protected void value(PreparedStatement ps, int i, Integer obj) throws SQLException {
        if(obj==null){
            ps.setNull(i, Types.INTEGER);
        }else{
            ps.setInt(i, obj);
        }
    }
    protected void value(PreparedStatement ps, int i, Long obj) throws SQLException {
        if(obj==null){
            ps.setNull(i, Types.INTEGER);
        }else{
            ps.setLong(i, obj);
        }
    }
    
    protected void value(PreparedStatement ps, int i, Timestamp obj) throws SQLException {
        if(obj==null){
            ps.setNull(i, Types.TIMESTAMP);
        }else{
            ps.setTimestamp(i, obj);
        }
    }
    
    protected void value(PreparedStatement ps, int i, BigDecimal obj) throws SQLException {
    	if(obj==null){
            ps.setNull(i, Types.DECIMAL);
        }else{
            ps.setDouble(i, obj.doubleValue());
        }
	}
}
