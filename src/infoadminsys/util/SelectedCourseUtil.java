/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.SelectedCourse;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author lsh
 */
public class SelectedCourseUtil {

    private JdbcUtil jdbcUtil;

    public SelectedCourseUtil() {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }

    public List<SelectedCourse> downloadData(String id) {
        String sql = "SELECT C.id as course_id, C.name as course_name, T.name as teacher_name, score "
                   + "FROM (selectedcourse SC INNER JOIN course C ON SC.course_id=C.id),teacher T "
                   + "WHERE C.teacher_id=T.id AND student_id=?";
        List<Object> param = new ArrayList<>();
        param.add(id);
        List<SelectedCourse> results = new ArrayList<>();
        try {
            results = jdbcUtil.getMultipleResult(sql, param, SelectedCourse.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return results;
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtil != null) {
            jdbcUtil.releaseConn();
            jdbcUtil = null;

        }
    }
}
