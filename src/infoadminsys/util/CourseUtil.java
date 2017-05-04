/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import infoadminsys.cls.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author hed
 */

public class CourseUtil {
    private JdbcUtil jdbcUtil;

    public CourseUtil() {
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }
    
    public void uploadData(Course course) throws Exception {
        String sql = "UPDATE course SET ";
        Class<Course> cls = Course.class;
        int itemNum = Course.Attr.length;
        List<Object> param = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            String attrName = Course.Attr[i];
            sql = sql + attrName + "=?";
            if(i+1<itemNum) sql+=", "; else sql+=" ";
            Field field = cls.getDeclaredField(attrName);
            param.add(field.get(course));
        }
        sql += "WHERE id=?;";
        param.add(course.id);
        jdbcUtil.updateByPreparedStatement(sql, param);
    }
    
    //获得所有课程信息
    public List<Map<String,Object>> findAllCourse(Map<String, Object> condition) {
        String sql = "select c.id as id, c.name as name, teacher_id, t.name as teacher_name, capacity, selectedcnt, commitStatus "
               +"from course as c, teacher as t where teacher_id = t.id";
        List<Object> param=new ArrayList<>();
        if (condition.size() > 0) {
            Set set = condition.entrySet();
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                sql += " AND "+key + "=?";
                param.add(value);
            }
        }
        sql+=";";
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            list = jdbcUtil.getCertainResultList(sql,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据教师id获得所有课程信息
    public List<Map<String,Object>> findAllCoursesByTeacherId(String teacherId) {
        String sql = "select * from course where teacher_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(teacherId);
        List<Map<String, Object>> list = null;
        try {
            list = jdbcUtil.getCertainResultList(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    

    public boolean saveCourse(Map<String,Object> map) {
        String sql = "";
        if (map.containsKey("id")) {
            sql = "update course set name = ? ,teacher_id = ? where id = ?";
        } else {
            sql = "insert into course(name,teacher_id) values(?,?)";
        }

        List<Object> param = new ArrayList<>();
        param.add(map.get("courseName"));
        param.add(map.get("teacher_id"));
        if (map.containsKey("id")) {
            param.add(map.get("id"));
        }
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean removeCourse(String id) {
        String sql = "delete from course where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    //根据课程的id查询所有选择该课程的学生及成绩
    public List<Map<String,Object>> findAllStudentWithGradeByCourseId(String courseId) {
        String sql = "select s.id as id, s.name, c.course_id, c.score from student as s, " +
                " selectedcourse as c where id = student_id and course_id = ? order by id asc";

        List<Object> param = new ArrayList<>();
        param.add(courseId);
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            list = jdbcUtil.getCertainResultList(sql,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据课程序号提交课程
    public boolean commitCourseByCourseId(String courseId) {
        String sql = "update course set status = '已提交' where id = ?";
        List<Object> param = new ArrayList<>();
        param.add(courseId);
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //暂存课程
    public boolean draftCourseByCourseId(String courseId) {
        String sql = "update course set status = '已暂存' where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public void clearCommitStautsByCourseId(String courseId) {

        String sql = "update course set commitStatus = '' where id = ?";
        String sql2 = "delete from gradeDraft where courseId = ?";
        String sql3 = "delete from grade where courseId = ?";

        List<Object> params = new ArrayList<>();
        params.add(courseId);

        try {
            jdbcUtil.updateByPreparedStatement(sql, params);
            jdbcUtil.updateByPreparedStatement(sql2, params);
            jdbcUtil.updateByPreparedStatement(sql3, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (jdbcUtil != null) {
            jdbcUtil.releaseConn();
            jdbcUtil = null;

        }
        //System.out.println(this.getClass().toString() + "销毁了");
    }
}

