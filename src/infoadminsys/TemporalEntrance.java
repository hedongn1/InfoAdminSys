/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys;

import infoadminsys.ui.*;
import javax.swing.WindowConstants;

/**
 *
 * @author lsh
 */
public class TemporalEntrance {
    
    public static void main(String args[]) {
        /*
        AdminUI AD=new AdminUI();
        AD.setLocationRelativeTo(null);
        AD.setVisible(true);
*/
        TeacherUI T = new TeacherUI("123");
        T.setLocationRelativeTo(null);
        T.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        T.setVisible(true);
    }
    
}
