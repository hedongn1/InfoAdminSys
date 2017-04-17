    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.util;
import infoadminsys.cls.UserPass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hed
 */
public class LoginUtil {
    
    private JdbcUtil jdbcUtil;
    
    public LoginUtil(){
        jdbcUtil = new JdbcUtil();
        jdbcUtil.getConnection();
    }
    
    public UserPass Login(UserPass userpass){
        UserPass ret = null;
        String username = userpass.getusername();
        String password = userpass.getpassword();
        String sql = "select * from userpass where username = ? and password = ?";
        List<Object> param = new ArrayList<>();
        param.add(username);
        param.add(password);
        try {
            UserPass dbuser = jdbcUtil.getSingleResult(sql,param,UserPass.class);
            if (dbuser != null) {
                ret = dbuser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
}
