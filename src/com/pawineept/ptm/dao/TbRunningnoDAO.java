package com.pawineept.ptm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.pawineept.ptm.model.TbRunningno;
import com.pawineept.ptm.util.DBUtil;

public class TbRunningnoDAO extends BaseDAO{
    public Integer selectMaxRunNo(Connection conn,TbRunningno obj) throws SQLException{
         
        String sql = "select * from tb_runningno where prefix = ?  and year = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i=1;
        value(ps,i++,obj.getPrefix());
        value(ps,i++,obj.getYear());
        ResultSet rs = ps.executeQuery();
        Integer runNum = null;
        if(rs.next()){
            runNum = rs.getInt("RUN_NUM");
        }
        
        DBUtil.close(rs);
        DBUtil.close(ps);
        return runNum;
    }
    
    public void runNoplus(Connection conn,TbRunningno obj) throws SQLException{
        String sql = "update tb_runningno set run_num = run_num+1 where prefix = ?  and year = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        int i=1;
        value(ps,i++,obj.getPrefix());
        value(ps,i++,obj.getYear());
        ps.execute();

        DBUtil.close(ps);
    }
    
    public void insertRunNo(Connection conn,TbRunningno obj) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("insert into tb_runningno ");
        sql.append("values (? , ? , ?)");
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        int i=1;
        value(ps,i++,obj.getPrefix());
        value(ps,i++,obj.getYear());
        value(ps,i++,obj.getRunNum());
        ps.execute();
        
        DBUtil.close(ps);        
    }
   
}
