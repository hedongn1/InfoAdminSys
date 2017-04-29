/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author lsh
 */
public class AdminUtil {
    
    private JdbcUtil jdbcUtil;

    public AdminUtil() {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }
    
    public <T> List<T> downloadData(String table,Map<String,Object> condition) {
        String cols=null;
        Class<T> cls=null;
        switch(table) {
            case "student":
                cols="id,name,depart,major";
                cls=(Class<T>)Student.class;
                break;
            case "teacher":
                cols="id,name,depart,title";
                cls=(Class<T>)Teacher.class;
                break;
        }
        String sql = "SELECT "+cols+ " FROM "+table+" ";
        List<Object> param = new ArrayList<>();
        if(condition.size()>0) {
            sql+="WHERE ";
            Set set=condition.entrySet();
            int count=0;
            for (Iterator iter=set.iterator();iter.hasNext();)
            {
                Map.Entry entry=(Map.Entry)iter.next();
                String key=(String)entry.getKey();
                Object value=entry.getValue();
                sql+=key+"=?";
                param.add(value);
                if(count+1<condition.size()) sql+=", ";
                System.err.println(key+" "+value.toString());
            }
        }
        sql+=";";
        List<T> result = new ArrayList<>();
        try {
            result = jdbcUtil.getMultipleResult(sql, param, cls);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public void insertData(String table,List<Map<String,Object>> dataList) {
        String sql="INSERT INTO "+table+" ";
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtil != null) {
            jdbcUtil.releaseConn();
            jdbcUtil = null;

        }
    }
}
