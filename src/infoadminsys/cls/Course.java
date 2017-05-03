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
public class Course {
    public String id;
    public String name;
    public String teacher_name;
    public String teacher_id;
    public int capacity;
    public int selectedcnt;
    public String commitStatus;
    static public String[] Attr={"id","name","teacher_id","capacity","selectedcnt","commitStatus"};
    //use teacher_id instead of tid, or it would go wrong in database
    //use public instead of private for convenience
    
    public String getProportionString() {
        return Integer.toString(selectedcnt)+"/"+Integer.toString(capacity);
    }
}
