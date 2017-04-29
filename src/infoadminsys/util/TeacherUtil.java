/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.Teacher;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author hed
 */
public class TeacherUtil {
    
    private JdbcUtil jdbcUtil;

    public TeacherUtil() {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }

    public Teacher downloadData(String id) {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        List<Object> param = new ArrayList<>();
        param.add(id);
        Teacher teacher = null;
        try {
            teacher = jdbcUtil.getSingleResult(sql,param,Teacher.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return teacher;
    }
    
    public void uploadData(Teacher teacher) throws NoSuchFieldException, IllegalAccessException, SQLException {
        String sql = "UPDATE teacher SET ";
        Class<Teacher> cls = Teacher.class;
        int itemNum = Teacher.Attr.length;
        List<Object> param = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            String attrName = teacher.Attr[i];
            sql = sql + attrName + "=?";
            if(i+1<itemNum) sql+=", "; else sql+=" ";
            if (!attrName.equals("birthday")) {
                Field field = cls.getDeclaredField(attrName);
                param.add(field.get(teacher));
            } else {
                param.add(teacher.birthday.toString());
            }
        }
        sql += "WHERE id=?;";
        param.add(teacher.id);
        jdbcUtil.updateByPreparedStatement(sql,param);
    }
    

    public List<Map<String,Object>> findAllTeachers() {
        String sql = "select * from teacher order by id asc";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcUtil.getCertainResultList(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public Teacher findTeacherById(int id) {
        String sql = "select * from teacher where id = ?";
        List<Object> param = new ArrayList<>();
        param.add(id);
        Teacher teacher = null;
        try {
            teacher = jdbcUtil.getSingleResult(sql, param, Teacher.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;

    }

    public Map<String,Object> findTeacherMapById(int id) {
        String sql = "select * from teacher where id = ?";
        List<Object> param = new ArrayList<>();
        param.add(id);
        Map<String, Object> map = null;
        try {
            map = jdbcUtil.getCertainResult(sql,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public boolean removeTeacher(int id) {
        String sql = "delete from teacher where id = ?";
        List<Object> param = new ArrayList<>();
        param.add(id);
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
        
        //!!!also need remove in the UserPass table
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtil != null) {
            jdbcUtil.releaseConn();
            jdbcUtil = null;

        }
    }
}
