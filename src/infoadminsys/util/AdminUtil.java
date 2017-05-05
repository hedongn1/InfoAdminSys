/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;

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

    public <T> List<T> downloadData(Map<String, Object> condition, Class<T> cls) throws Exception {
        String table = cls.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + table + " ";
        List<Object> param = new ArrayList<>();
        if (condition.size() > 0) {
            sql += "WHERE ";
            Set set = condition.entrySet();
            int count = 0;
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                sql += key + " Like ? ";
                param.add("%" + value + "%");
                count++;
                if (count < condition.size()) {
                    sql += " AND ";
                }
            }
        }
        //System.out.println(sql);
        sql += ";";
        List<T> result = new ArrayList<>();
        try {
            result = jdbcUtil.getMultipleResult(sql, param, cls);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据获取失败\n" + e.getMessage(), "提示信息", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public <T> void insertData(List<T> dataList, String[] col, Class<T> cls) throws Exception {
        int n = dataList.size();
        if (n <= 0) {
            return;
        }
        String table = cls.getSimpleName().toLowerCase();
        String sql = "INSERT INTO " + table + "(", duplicate = " ON DUPLICATE KEY UPDATE ";
        for (int i = 0; i < col.length; i++) {
            sql += col[i];
            duplicate += col[i] + "=VALUES(" + col[i] + ")";
            if (i + 1 < col.length) {
                sql += ",";
                duplicate += ",";
            } else {
                sql += ") ";
                duplicate += ";";
            }
        }
        sql += "VALUE ";
        List<Object> param = new ArrayList<Object>();
        for (int i = 0; i < n; i++) {
            T data = dataList.get(i);
            sql += "(";
            for (int j = 0; j < col.length; j++) {
                Object value = cls.getDeclaredField(col[j]).get(data);
                param.add(value);
                sql += "?";
                //if(value!=null) sql += "'" + value.toString() + "'";
                //else sql+="null";
                if (j + 1 < col.length) {
                    sql += ", ";
                }
            }
            sql += ")";
            if (i + 1 < n) {
                sql += ", ";
            }
        }
        sql += duplicate;
        jdbcUtil.updateByPreparedStatement(sql, param);
    }

    public void deleteData(String table, String key, String value) throws Exception {
        String sql = "DELETE FROM " + table + " WHERE " + key + "=?;";
        List<Object> param = new ArrayList<>();
        param.add(value);
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
