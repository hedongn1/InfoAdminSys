/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    //获得所有课程信息
    public List<Map<String,Object>> findAllCourse() {
        String sql = "select c.id as id, c.name, " +
                "teacher_id,t.name, commitStatus from course as c, teacher as t where teacher_id = t.id";
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            list = jdbcUtil.getCertainResultList(sql,null);
           // System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据教师id获得所有课程信息
    public List<Map<String,Object>> findAllCoursesByTeacherId(int teacherId) {
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

    public boolean removeCourse(int id) {
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
    public List<Map<String,Object>> findAllStudentWithGradeByCourseId(int courseId) {
        String sql = "select s.id as id, s.name, c.id from student as s, " +
                " selectedcourse as c where s.id = c.student_id and course_id = ? order by s.id asc";

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

    //根据课程的id查询所有选择该课程的学生
    public List<Map<String,Object>> findAllStudentWithGradeDraftByCourseId(int courseId) {
        String sql = "select s.id as id, studentCode ,name ,courseId from student as s ," +
                " chooseCourse as c where s.id = c.studentId and courseId = ? order by s.id asc";

        List<Object> params = new ArrayList<>();
        params.add(courseId);
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            list = jdbcUtil.getCertainResultList(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 添加暂存表中的成绩信息
        String sql2 = "select * from gradeDraft where courseId = ? order by id asc";
        List<Map<String, Object>> draftGrades = new ArrayList<>();

        try {
            draftGrades = jdbcUtil.getCertainResultList(sql2, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> willReturnMap = list.get(i);
            if (draftGrades.size() == 0) {
                willReturnMap.put("score", null);
            } else {
                for (int j = 0; j < draftGrades.size(); j++) {
                    Map<String, Object> map = draftGrades.get(j);
                    if (map != null && map.get("studentId").equals(willReturnMap.get("id"))) {
                        willReturnMap.put("score", map.get("score"));
                    }
                }
            }

        }

        return list;
    }

    //根据课程序号提交课程
    public boolean commitCourseByCourseId(int courseId) {
        String sql = "update course set commitStatus = '已提交' where id = ?";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        boolean flag = false;
        try {
            flag = jdbcUtil.updateByPreparedStatement(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //暂存课程
    public boolean draftCourseByCourseId(int courseId) {
        String sql = "update course set commitStatus = '已暂存' where id = ?";
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


    public void clearCommitStautsByCourseId(int courseId) {

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

