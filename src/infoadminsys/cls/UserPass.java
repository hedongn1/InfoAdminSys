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
    private String username;
    private String password;
    private String type;
    
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
}

