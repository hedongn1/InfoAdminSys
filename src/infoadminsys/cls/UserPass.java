/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.cls;

/**
 *
 * @author hed
 */
public class UserPass {
    public String username;
    public String password;
    public String type;
    static public String[] Attr={"username","type"};
    
    public UserPass(){
    }
    
    public UserPass(String username, String password) {
        this.password = password;
        this.username = username;
    }
    
    public String getusername(){
        return this.username;
    }
    
    public String getpassword(){
        return this.password;
    }
    
    static public String getTypeCHN(String typeENG)
    {
        String res="";
        switch(typeENG)
        {
            case "student":
                res="学生";
                break;
            case "teacher":
                res="教师";
                break;
            case "admin":
                res="管理员";
                break;
        }
        return res;
    }
    
    static public String getTypeENG(String typeCHN)
    {
        String res="";
        switch(typeCHN)
        {
            case "学生":
                res="student";
                break;
            case "教师":
                res="teacher";
                break;
            case "管理员":
                res="admin";
                break;
        }
        return res;
    }
}
