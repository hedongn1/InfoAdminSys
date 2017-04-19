/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.Teacher;
import java.util.ArrayList;
import java.util.List;
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
            teacher = jdbcUtil.getSingleResult(sql, param, Teacher.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return teacher;
    }

    public void uploadData() {
    }
}
