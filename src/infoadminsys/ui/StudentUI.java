/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.ui;

import javax.swing.JOptionPane;
import infoadminsys.cls.*;
import infoadminsys.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.TableModel;
import java.util.*;
import java.awt.Font;
import java.text.ParseException;

/**
 *
 * @author lsh
 */
public class StudentUI extends javax.swing.JFrame {

    /**
     * Creates new form StudentUI
     */
    private String id;
    private Student student;
    final private StudentUtil studentUtil = new StudentUtil();
    private List<SelectedCourse> SCList = new ArrayList<>();
    final private SelectedCourseUtil SCUtil = new SelectedCourseUtil();

    private void setText() {
        jLabel_hello.setText("您好, " + student.name);
        jTextField_name.setText(student.name);
        jTextField_sex.setText(student.sex);
        jComboBox_sex.setSelectedItem(student.sex);
        jTextField_id.setText(student.id);
        jTextField_depart.setText(student.depart);
        jTextField_major.setText(student.major);
        jTextField_hometown.setText(student.hometown);
        jTextField_birthday.setText(student.birthday.toString());
        jTextField_IDnum.setText(student.IDnum);
        jTextField_address.setText(student.address);
        jTextField_email.setText(student.email);
        jTextField_NO.setText(student.NO);
    }

    private void setEdit(boolean edit) {
        jTextField_name.setEditable(false);
        jTextField_sex.setEditable(edit);
        jTextField_sex.setVisible(!edit);
        jComboBox_sex.setVisible(edit);
        jTextField_id.setEditable(false);
        jTextField_depart.setEditable(false);
        jTextField_major.setEditable(false);
        jTextField_hometown.setEditable(edit);
        jTextField_birthday.setEditable(edit);
        jTextField_IDnum.setEditable(false);
        jTextField_address.setEditable(edit);;
        jTextField_email.setEditable(edit);
        jTextField_NO.setEditable(edit);
    }

    private void buttonModify() {
        jButton_modify.setVisible(false);
        jButton_save.setVisible(true);
        jButton_back.setVisible(true);
    }

    private void buttonReadonly() {
        jButton_modify.setVisible(true);
        jButton_save.setVisible(false);
        jButton_back.setVisible(false);
    }

    private void saveData() throws ParseException,Exception {
        student.name = jTextField_name.getText();
        student.sex = jComboBox_sex.getSelectedItem().toString();
        student.id = jTextField_id.getText();
        student.depart = jTextField_depart.getText();
        student.major = jTextField_major.getText();
        student.hometown = jTextField_hometown.getText();
        student.birthday= studentUtil.StringToDate(jTextField_birthday.getText());
        student.IDnum = jTextField_IDnum.getText();
        student.address = jTextField_address.getText();
        student.email = jTextField_email.getText();
        student.NO = jTextField_NO.getText();
        studentUtil.uploadData(student);
    }

    private void displayInfo(boolean readDB) {
        if (readDB) {
            student = studentUtil.downloadData(id);
        }
        setText();
        setEdit(false);
        buttonReadonly();
    }
    
    private Object[][] getCourses(){
        int rowNum=SCList.size();
        Object[][] courses=new Object[rowNum][3];
        for(int i=0;i<rowNum;i++) {
            courses[i][0]=SCList.get(i).course_id;
            courses[i][1]=SCList.get(i).course_name;
            courses[i][2]=SCList.get(i).teacher_name;
        }
        return courses;
    }
    
    private TableModel SCTableModel() {
        return new javax.swing.table.DefaultTableModel(getCourses(),
                new String[]{
                    "课程号", "课程名称", "教师"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    private Object[][] getScores(){
        int rowNum=SCList.size();
        Object[][] scores=new Object[rowNum][3];
        for(int i=0;i<rowNum;i++) {
            scores[i][0]=SCList.get(i).course_id;
            scores[i][1]=SCList.get(i).course_name;
            scores[i][2]=SCList.get(i).score;
        }
        return scores;
    }
    
    private TableModel scoreTableModel() {
        return new javax.swing.table.DefaultTableModel(getScores(),
                new String[]{
                    "课程号", "课程名称", "成绩"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    public StudentUI() {
    }

    public StudentUI(String username) {
        id = username;
        SCList=SCUtil.downloadData(id);
        initComponents();
        jTable_courses.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_scores.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel_name = new javax.swing.JLabel();
        jTextField_name = new javax.swing.JTextField();
        jLabel_id = new javax.swing.JLabel();
        jTextField_id = new javax.swing.JTextField();
        jLabel_major = new javax.swing.JLabel();
        jTextField_major = new javax.swing.JTextField();
        jButton_save = new javax.swing.JButton();
        jButton_back = new javax.swing.JButton();
        jButton_modify = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel_birthday = new javax.swing.JLabel();
        jTextField_birthday = new javax.swing.JTextField();
        jLabel_email = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jLabel_hometown = new javax.swing.JLabel();
        jTextField_hometown = new javax.swing.JTextField();
        jLabel_NO = new javax.swing.JLabel();
        jTextField_NO = new javax.swing.JTextField();
        jLabel_depart = new javax.swing.JLabel();
        jTextField_depart = new javax.swing.JTextField();
        jComboBox_sex = new javax.swing.JComboBox<>();
        jLabel_address = new javax.swing.JLabel();
        jTextField_address = new javax.swing.JTextField();
        jTextField_sex = new javax.swing.JTextField();
        jLabel_IDnum = new javax.swing.JLabel();
        jTextField_IDnum = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_courses = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_scores = new javax.swing.JTable();
        jLabel_account = new javax.swing.JLabel();
        jLabel_hello = new javax.swing.JLabel();
        jLabel_logOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 577));
        jTabbedPane1.setRequestFocusEnabled(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 522));

        jLabel_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_name.setText("姓名：");

        jTextField_name.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTextField_name.setToolTipText("");

        jLabel_id.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_id.setText("学号：");

        jTextField_id.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jLabel_major.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_major.setText("专业：");

        jTextField_major.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jButton_save.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton_save.setText("保存");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });

        jButton_back.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton_back.setText("返回");
        jButton_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_backActionPerformed(evt);
            }
        });

        jButton_modify.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jButton_modify.setText("修改");
        jButton_modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modifyActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel1.setText("性别：");

        jLabel_birthday.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_birthday.setText("出生日期：");

        jTextField_birthday.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTextField_birthday.setToolTipText("");

        jLabel_email.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_email.setText("e-mail：");

        jTextField_email.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jLabel_hometown.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_hometown.setText("籍贯：");

        jTextField_hometown.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jLabel_NO.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_NO.setText("联系电话：");

        jTextField_NO.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jLabel_depart.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_depart.setText("院系：");

        jTextField_depart.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jComboBox_sex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "男", "女" }));
        jComboBox_sex.setEditor(null);

        jLabel_address.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_address.setText("地址：");

        jTextField_address.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        jTextField_sex.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTextField_sex.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel_IDnum.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel_IDnum.setText("身份证号码：");

        jTextField_IDnum.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_email, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_address, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_hometown, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_id, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_name, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel_NO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_NO, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton_modify)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_save)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jButton_back)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField_address, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_hometown, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_id, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_birthday, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_depart, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextField_sex, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox_sex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextField_depart, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(jLabel_major)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField_birthday, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel_IDnum)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_IDnum, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_major, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(31, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel_name)
                    .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox_sex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_sex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField_depart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_id)
                        .addComponent(jTextField_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_depart))
                    .addComponent(jLabel_major)
                    .addComponent(jTextField_major, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_birthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_birthday)
                        .addComponent(jLabel_IDnum)
                        .addComponent(jTextField_IDnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_hometown)
                        .addComponent(jTextField_hometown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_address)
                    .addComponent(jTextField_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_email)
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_NO)
                    .addComponent(jTextField_NO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_back)
                    .addComponent(jButton_modify)
                    .addComponent(jButton_save))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel_email, jTextField_email});

        jTextField_birthday.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("个人信息", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(800, 576));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 576));
        jScrollPane2.setViewportView(null);

        jTable_courses.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTable_courses.setModel(SCTableModel());
        jTable_courses.setColumnSelectionAllowed(true);
        jTable_courses.setGridColor(new java.awt.Color(102, 102, 102));
        jTable_courses.setShowGrid(true);
        jTable_courses.setSurrendersFocusOnKeystroke(true);
        jScrollPane2.setViewportView(jTable_courses);
        jTable_courses.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("选课查询", jPanel2);

        jPanel3.setPreferredSize(new java.awt.Dimension(800, 576));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 576));

        jTable_scores.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTable_scores.setModel(scoreTableModel());
        jTable_scores.setGridColor(new java.awt.Color(102, 102, 102));
        jScrollPane1.setViewportView(jTable_scores);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("成绩查询", jPanel3);

        jLabel_account.setText("账号管理");
        jLabel_account.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel_account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_accountMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_accountMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_accountMouseEntered(evt);
            }
        });

        jLabel_hello.setText("您好,");

        jLabel_logOut.setText("注销");
        jLabel_logOut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel_logOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_logOutMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_logOutMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_logOutMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_hello)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_account, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_logOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel_logOut)
                    .addComponent(jLabel_account, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_hello, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel_account, jLabel_logOut});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modifyActionPerformed
        // TODO add your handling code here:
        setEdit(true);
        buttonModify();
    }//GEN-LAST:event_jButton_modifyActionPerformed

    private void jButton_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_backActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "确定退出？未保存的内容将丢失！", "提示信息", JOptionPane.YES_NO_OPTION);
        if (result == 0) { //YES
            displayInfo(false);
        }
    }//GEN-LAST:event_jButton_backActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        // TODO add your handling code here:
        try {
            saveData();
        } catch(ParseException e) {
            JOptionPane.showMessageDialog(this, "请按照yyyy-MM-dd的格式输入日期！\n如：1996-01-01", "提示信息", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "保存失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "保存成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jLabel_accountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_accountMouseClicked
        // TODO add your handling code here:
        setEnabled(false);
        AccountManageUI AM = new AccountManageUI(student.id, student.type);
        AM.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        AM.setLocationRelativeTo(this);
        AM.setAlwaysOnTop(true);
        AM.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
        AM.setVisible(true);
    }//GEN-LAST:event_jLabel_accountMouseClicked

    private void jLabel_accountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_accountMouseEntered
        // TODO add your handling code here:
        jLabel_account.setText("<html><u><b>账号管理</b></u></html>");
    }//GEN-LAST:event_jLabel_accountMouseEntered

    private void jLabel_accountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_accountMouseExited
        // TODO add your handling code here:
        jLabel_account.setText("账号管理");
    }//GEN-LAST:event_jLabel_accountMouseExited

    private void jLabel_logOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_logOutMouseEntered
        // TODO add your handling code here:
        jLabel_logOut.setText("<html><u><b>注销</b><u></html>");
    }//GEN-LAST:event_jLabel_logOutMouseEntered

    private void jLabel_logOutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_logOutMouseExited
        // TODO add your handling code here:
        jLabel_logOut.setText("注销");
    }//GEN-LAST:event_jLabel_logOutMouseExited

    private void jLabel_logOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_logOutMouseClicked
        // TODO add your handling code here:
        LoginUI T = new LoginUI();
        T.setLocationRelativeTo(null);
        T.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel_logOutMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_back;
    private javax.swing.JButton jButton_modify;
    private javax.swing.JButton jButton_save;
    private javax.swing.JComboBox<String> jComboBox_sex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_IDnum;
    private javax.swing.JLabel jLabel_NO;
    private javax.swing.JLabel jLabel_account;
    private javax.swing.JLabel jLabel_address;
    private javax.swing.JLabel jLabel_birthday;
    private javax.swing.JLabel jLabel_depart;
    private javax.swing.JLabel jLabel_email;
    private javax.swing.JLabel jLabel_hello;
    private javax.swing.JLabel jLabel_hometown;
    private javax.swing.JLabel jLabel_id;
    private javax.swing.JLabel jLabel_logOut;
    private javax.swing.JLabel jLabel_major;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_courses;
    private javax.swing.JTable jTable_scores;
    private javax.swing.JTextField jTextField_IDnum;
    private javax.swing.JTextField jTextField_NO;
    private javax.swing.JTextField jTextField_address;
    private javax.swing.JTextField jTextField_birthday;
    private javax.swing.JTextField jTextField_depart;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_hometown;
    private javax.swing.JTextField jTextField_id;
    private javax.swing.JTextField jTextField_major;
    private javax.swing.JTextField jTextField_name;
    private javax.swing.JTextField jTextField_sex;
    // End of variables declaration//GEN-END:variables
}
