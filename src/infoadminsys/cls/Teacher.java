/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.cls;

import java.sql.Date;

/**
 *
 * @author hed
 */
public class Teacher {
    public String id;
    public String name;
    public String sex;
    public String depart;
    public String title;
    public String hometown;
    public Date birthday;
    public String email;
    public String address;
    public String cell;
    public String IDnum;
    final public String type = "teacher";
    static public String[] Attr={"id","name","sex","depart","title","hometown","birthday","email","address","cell","IDnum"};
}
