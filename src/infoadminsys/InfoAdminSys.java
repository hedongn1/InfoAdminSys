/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys;

import infoadminsys.ui.LoginUI;
import javax.swing.WindowConstants;

/**
 *
 * @author hed
 */
public class InfoAdminSys {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        LoginUI T = new LoginUI();
        T.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        T.setLocationRelativeTo(null);
        T.setVisible(true);
    }
}
