/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.*;
import java.util.List;
import java.util.ArrayList;
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
        String sql = "SELECT * FROM student WHERE id=?";
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

    public void uploadData() {
    }

}
