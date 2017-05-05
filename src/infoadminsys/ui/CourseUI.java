/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.ui;

import infoadminsys.cls.*;
import infoadminsys.util.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author lsh
 */
public class CourseUI extends javax.swing.JFrame {

    /**
     * Creates new form CourseUI
     */
    String id;
    Course course;
    boolean EDIT = false;
    List<SelectedCourse> SCList = new ArrayList<>();
    CourseUtil courseUtil = new CourseUtil();
    SelectedCourseUtil SCUtil = new SelectedCourseUtil();
    AdminUtil adminUtil = new AdminUtil();
    DefaultTableModel tableModel;
    List<String> deletedRows = new ArrayList<>();

    private void setText() {
        jTextField_id.setText(course.id);
        jTextField_name.setText(course.name);
        jTextField_teaID.setText(course.teacher_id);
        jTextField_teaName.setText(course.teacher_name);
        jTextField_capacity.setText(Integer.toString(course.capacity));
        jTextField_selectedcnt.setText(Integer.toString(course.selectedcnt));
    }

    private void setEdit(boolean edit) {
        EDIT = edit;
        jTextField_id.setEditable(false);
        jTextField_name.setEditable(edit);
        jTextField_teaID.setEditable(edit);
        jTextField_teaName.setEnabled(false);
        jTextField_capacity.setEditable(edit);
        jTextField_selectedcnt.setEditable(edit);
    }

    private void buttonVisible(boolean vis) {
        jButton_modify.setVisible(!vis);
        jButton_insert.setVisible(vis);
        jButton_delete.setVisible(vis);
        jButton_save.setVisible(vis);
        jButton_back.setVisible(vis);
    }

    private void saveData() throws Exception {
        course.id = jTextField_id.getText();
        course.name = jTextField_name.getText();
        course.teacher_id = jTextField_teaID.getText();
        course.teacher_name = jTextField_name.getText();
        course.capacity = Utility.StringToInt(jTextField_capacity.getText());
        course.selectedcnt = Utility.StringToInt(jTextField_selectedcnt.getText());
        courseUtil.uploadData(course);
    }

    private void selectedcntInc(int x) {
        course.selectedcnt = Utility.StringToInt(jTextField_selectedcnt.getText());
        course.selectedcnt += x;
        jTextField_selectedcnt.setText(Integer.toString(course.selectedcnt));
    }

    Object[][] getStudents() {
        List<Map<String, Object>> mapList = courseUtil.findAllStudentWithGradeByCourseId(course.id);
        int n = mapList.size();
        Object[][] stuList = new Object[n][4];
        for (int i = 0; i < n; i++) {
            Map<String, Object> stuMap = mapList.get(i);
            stuList[i][0] = i + 1;
            stuList[i][1] = stuMap.get("id");
            stuList[i][2] = stuMap.get("name");
            stuList[i][3] = stuMap.get("score");
        }
        return stuList;
    }

    private TableModel SCTableModel() {
        return (new javax.swing.table.DefaultTableModel(getStudents(),
                new String[]{
                    "序号", "学号", "姓名", "成绩"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex] & EDIT;
            }
        });

    }

    void tableLoad() {
        jTable_select.setModel(SCTableModel());
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) jTable_select.getTableHeader().getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        jTable_select.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        if (jTable_select.getColumnModel().getColumnCount() > 0) {
            jTable_select.getColumnModel().getColumn(0).setResizable(false);
            jTable_select.getColumnModel().getColumn(0).setPreferredWidth(65);
            jTable_select.getColumnModel().getColumn(1).setResizable(false);
            jTable_select.getColumnModel().getColumn(1).setPreferredWidth(155);
            jTable_select.getColumnModel().getColumn(2).setResizable(false);
            jTable_select.getColumnModel().getColumn(2).setPreferredWidth(155);
            jTable_select.getColumnModel().getColumn(3).setResizable(false);
            jTable_select.getColumnModel().getColumn(3).setPreferredWidth(75);
        }
    }

    private void displayInfo(boolean readDB) {
        if (readDB) {
            Map<String, Object> condition = new HashMap<>();
            condition.put("c.id", id);
            try {
                course = Utility.MapToClass(courseUtil.findAllCourse(condition).get(0), Course.class);
            } catch (Exception e) {
                return;
            }
        }
        setText();
        setEdit(false);
        buttonVisible(false);
        tableLoad();
        jTable_select.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }

    public CourseUI() {
    }

    public CourseUI(String course_id) {
        id = course_id;
        initComponents();
        displayInfo(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_select = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_id = new javax.swing.JTextField();
        jTextField_name = new javax.swing.JTextField();
        jTextField_teaID = new javax.swing.JTextField();
        jTextField_capacity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_teaName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_selectedcnt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton_save = new javax.swing.JButton();
        jButton_delete = new javax.swing.JButton();
        jButton_insert = new javax.swing.JButton();
        jButton_modify = new javax.swing.JButton();
        jButton_back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable_select.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable_select.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable_select.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_select.setColumnSelectionAllowed(true);
        jTable_select.setGridColor(new java.awt.Color(102, 102, 102));
        jTable_select.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_select);
        jTable_select.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jLabel1.setText("课程编号：");

        jLabel2.setText("课程名称：");

        jLabel3.setText("教师工号：");

        jLabel4.setText("课程容量：");

        jTextField_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nameActionPerformed(evt);
            }
        });

        jTextField_teaID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_teaIDActionPerformed(evt);
            }
        });

        jLabel5.setText("教师姓名：");

        jTextField_teaName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_teaNameActionPerformed(evt);
            }
        });

        jLabel6.setText("选课人数：");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 16)); // NOI18N
        jLabel7.setText("选课学生");

        jButton_save.setText("保存");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });

        jButton_delete.setText("删除");
        jButton_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteActionPerformed(evt);
            }
        });

        jButton_insert.setText("插入");
        jButton_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_insertActionPerformed(evt);
            }
        });

        jButton_modify.setText("修改");
        jButton_modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modifyActionPerformed(evt);
            }
        });

        jButton_back.setText("返回");
        jButton_back.setToolTipText("");
        jButton_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_teaName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_id, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_selectedcnt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton_modify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_insert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_back))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(252, 252, 252))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(jTextField_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel2)
                            .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_teaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_teaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel6)
                            .addComponent(jTextField_selectedcnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_modify)
                    .addComponent(jButton_insert)
                    .addComponent(jButton_delete)
                    .addComponent(jButton_save)
                    .addComponent(jButton_back))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nameActionPerformed

    private void jTextField_teaIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_teaIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_teaIDActionPerformed

    private void jTextField_teaNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_teaNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_teaNameActionPerformed

    private void jButton_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_insertActionPerformed
        // TODO add your handling code here:
        if (course.selectedcnt >= course.capacity) {
            JOptionPane.showMessageDialog(this, "选课人数已达上限！\n", "提示信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int n = tableModel.getRowCount() + 1;
        tableModel.addRow(new Object[]{n, null, null, null});
        selectedcntInc(1);
    }//GEN-LAST:event_jButton_insertActionPerformed

    private void jButton_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteActionPerformed
        // TODO add your handling code here:
        int rows[] = jTable_select.getSelectedRows();
        int n = rows.length;
        if (n > 0) {
            int res = JOptionPane.showConfirmDialog(this, "确定删除？此操作在点击保存前可以撤回!", "提示信息", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) {
                return;
            }
            Arrays.sort(rows);
            for (int i = n - 1; i >= 0; i--) {
                Object obj = jTable_select.getValueAt(rows[i], 1);
                if (obj != null) {
                    String student_id = obj.toString();
                    deletedRows.add(student_id);
                }
                tableModel.removeRow(rows[i]);
            }
            selectedcntInc(-n);
        }
    }//GEN-LAST:event_jButton_deleteActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        // TODO add your handling code here:
        try {
            int n = deletedRows.size();
            for (int i = 0; i < n; i++) {
                SCUtil.deleteData(deletedRows.get(i), course.id);
            }
            SCList.clear();
            n = tableModel.getRowCount();
            for (int i = 0; i < n; i++) {
                SelectedCourse SC = new SelectedCourse();
                SC.course_id = course.id;
                Object obj = jTable_select.getValueAt(i, 1);
                if (obj == null) {
                    selectedcntInc(-1);
                    continue;
                }
                SC.student_id = obj.toString().trim();
                if (SC.student_id.length() <= 0) {
                    selectedcntInc(-1);
                    continue;
                }
                obj = jTable_select.getValueAt(i, 3);
                if (obj != null) {
                    SC.score = Utility.StringToDouble(obj.toString());
                } else {
                    SC.score = 0.0;
                }
                SCList.add(SC);
            }
            String[] col = {"student_id", "course_id", "score"};
            adminUtil.insertData(SCList, col, SelectedCourse.class);
            saveData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "保存失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "保存成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
        displayInfo(true);
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jButton_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_backActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "确定退出？未保存的内容将丢失！", "提示信息", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            displayInfo(true);
        }
    }//GEN-LAST:event_jButton_backActionPerformed

    private void jButton_modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modifyActionPerformed
        // TODO add your handling code here:
        buttonVisible(true);
        setEdit(true);
        tableModel = (DefaultTableModel) jTable_select.getModel();
    }//GEN-LAST:event_jButton_modifyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_back;
    private javax.swing.JButton jButton_delete;
    private javax.swing.JButton jButton_insert;
    private javax.swing.JButton jButton_modify;
    private javax.swing.JButton jButton_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_select;
    private javax.swing.JTextField jTextField_capacity;
    private javax.swing.JTextField jTextField_id;
    private javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_selectedcnt;
    private javax.swing.JTextField jTextField_teaID;
    private javax.swing.JTextField jTextField_teaName;
    // End of variables declaration//GEN-END:variables
}
