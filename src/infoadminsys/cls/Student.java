/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.cls;

import java.sql.Date;

/**
 *
 * @author lsh
 */
public class Student {
    
   public String id,name,sex;
   public String depart,major;
   public String hometown,IDnum;
   public Date birthday;
   public String address;
   public String email,NO;
   final public String type="学生";
   static public String[] Attr={"id","name","sex","depart","major","hometown","IDnum","birthday","address","email","NO"};
}
