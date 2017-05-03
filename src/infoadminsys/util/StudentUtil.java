/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author lsh
 */
public class StudentUtil {

    private JdbcUtil jdbcUtil;

    public StudentUtil() {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }

    public Student downloadData(String id) {
        String sql = "SELECT * FROM student WHERE id=?;";
        List<Object> param = new ArrayList<>();
        param.add(id);
        Student stu = null;
        try {
            stu = jdbcUtil.getSingleResult(sql, param, Student.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return stu;
    }

    public void uploadData(Student student) throws NoSuchFieldException, IllegalAccessException, SQLException {
        String sql = "UPDATE student SET ";
        Class<Student> cls = Student.class;
        int itemNum = Student.Attr.length;
        List<Object> param = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            String attrName = Student.Attr[i];
            sql = sql + attrName + "=?";
            if(i+1<itemNum) sql+=", "; else sql+=" ";
            if (!attrName.equals("birthday")) {
                Field field = cls.getDeclaredField(attrName);
                param.add(field.get(student));
            } else {
                if(student.birthday!=null)
                    param.add(student.birthday.toString());
                else
                    param.add(null);
            }
        }
        sql += "WHERE id=?;";
        param.add(student.id);
        jdbcUtil.updateByPreparedStatement(sql, param);
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtil != null) {
            jdbcUtil.releaseConn();
            jdbcUtil = null;

        }
    }
}
