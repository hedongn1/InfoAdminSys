/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.ui;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import javax.swing.JOptionPane;
import infoadminsys.cls.*;
import infoadminsys.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.TableModel;
import java.util.*;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lsh
 */
public class AdminUI extends javax.swing.JFrame {

    /**
     * Creates new form StudentUI
     */
    private String id;
    private Administrator admin;
    private AdminUtil adminUtil = new AdminUtil();
    private List<Student> stuList = new ArrayList<>();
    private List<Teacher> teaList = new ArrayList<>();
    private List<SelectedCourse> SCList = new ArrayList<>();
    final private SelectedCourseUtil SCUtil = new SelectedCourseUtil();

    private Object[][] getCourses() {
        int rowNum = SCList.size();
        Object[][] courses = new Object[rowNum][3];
        for (int i = 0; i < rowNum; i++) {
            courses[i][0] = SCList.get(i).course_id;
            courses[i][1] = SCList.get(i).course_name;
            courses[i][2] = SCList.get(i).teacher_name;
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

    private Object[][] getScores() {
        int rowNum = SCList.size();
        Object[][] scores = new Object[rowNum][3];
        for (int i = 0; i < rowNum; i++) {
            scores[i][0] = SCList.get(i).course_id;
            scores[i][1] = SCList.get(i).course_name;
            scores[i][2] = SCList.get(i).score;
        }
        return scores;
    }

    private void setSearchIcon() {
        ImageIcon icon = new ImageIcon("/Users/lsh/Desktop/Workspace/NetBeansProjects/InfoAdminSys/image/search.png");
        icon.setImage(icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        jLabel_search1.setIcon(icon);
        jLabel_search2.setIcon(icon);
        jLabel_search3.setIcon(icon);
    }

    private void adjust() {
        jPanel_student.setLayout(null);
        jScrollPane1.setBounds(6,27,767,490);
        System.out.println(jTable_student.bounds());
        System.out.println(jTable_course.bounds());
        System.out.println(jPanel_student.size());
        jLabel_search1.setBounds(20, 0, 24, 24);
        jTextField_stuID.setBounds(54, 3, 133, 20);
        jTextField_stuName.setBounds(184, 3, 138, 20);
        jComboBox_stuDepart.setBounds(318, 4, 231, 20);
        jTextField_stuMajor.setBounds(543, 3, 232, 20);

        jPanel_teacher.setLayout(null);
        jScrollPane2.setBounds(6,27,767,490);
        System.out.println(jScrollPane2.size());
        jLabel_search2.setBounds(20, 0, 24, 24);
        jTextField_teaID.setBounds(54, 3, 133, 20);
        jTextField_teaName.setBounds(184, 3, 138, 20);
        jComboBox_teaDepart.setBounds(318, 4, 231, 20);
        jTextField_teaTitle.setBounds(543, 3, 232, 20);
        
        jPanel_course.setLayout(null);
        jScrollPane3.setBounds(6,27,767,490);
        jLabel_search3.setBounds(20, 0, 24, 24);
        jTextField_courID.setBounds(54, 3, 133, 20);
        jTextField_courName.setBounds(184, 3, 228, 20);
        jTextField_courTea.setBounds(409,3,228,20);

        jTable_student.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_teacher.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_course.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
    }
    
    private <T> void Export(List<T> objList, Class<T> cls) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv文件", "csv");
        chooser.setFileFilter(filter);
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                T obj=cls.newInstance();
                String[] Attr = (String[])cls.getDeclaredField("Attr").get(obj);
                String[] row = new String[Attr.length];
                CsvWriter writer = new CsvWriter(file.getPath(), ',', Charset.defaultCharset());
                writer.writeRecord(Attr);
                Field field;
                int n = objList.size();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < Attr.length; j++) {
                        field = cls.getDeclaredField(Attr[j]);
                        System.out.println(field.get(objList.get(i)));
                        Object value = field.get(objList.get(i));
                        if (value != null) {
                            row[j] = field.get(objList.get(i)).toString();
                        } else {
                            row[j] = "";
                        }
                    }
                    writer.writeRecord(row);
                }
                writer.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "导出失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private <T> void Import(List<T> objList, Class<T> cls) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv文件", "csv");
        chooser.setFileFilter(filter);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                CsvReader reader = new CsvReader(file.getPath(), ',', Charset.defaultCharset());
                reader.readHeaders();
                String[] columnName = reader.getHeaders();
                objList.clear();
                Field field;
                while (reader.readRecord()) {
                    T obj=cls.newInstance();
                    for (int i = 0; i < columnName.length; i++) {
                        String col = columnName[i];
                        field = cls.getDeclaredField(col);
                        if (field.getType().equals(java.sql.Date.class)) {
                            field.set(obj, StudentUtil.StringToDate(reader.get(col)));
                        } else {
                            field.set(obj, reader.get(columnName[i]));
                        }
                    }
                    objList.add(obj);
                }
                adminUtil.insertData(objList, columnName, cls);
                reader.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "导入失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
    }
    
    private void studentDisplay() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_student.getModel();
        tableModel.setRowCount(stuList.size());
        for (int i = 0; i < stuList.size(); i++) {
            Student student = stuList.get(i);
            jTable_student.setValueAt(i + 1, i, 0);
            jTable_student.setValueAt(student.id, i, 1);
            jTable_student.setValueAt(student.name, i, 2);
            jTable_student.setValueAt(student.depart, i, 3);
            jTable_student.setValueAt(student.major, i, 4);
        }
    }

    private void teacherDisplay() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_teacher.getModel();
        tableModel.setRowCount(teaList.size());
        for (int i = 0; i < teaList.size(); i++) {
            Teacher teacher = teaList.get(i);
            jTable_teacher.setValueAt(i + 1, i, 0);
            jTable_teacher.setValueAt(teacher.id, i, 1);
            jTable_teacher.setValueAt(teacher.name, i, 2);
            jTable_teacher.setValueAt(teacher.depart, i, 3);
            jTable_teacher.setValueAt(teacher.title, i, 4);
        }
    }
    
    public AdminUI() {
        //SCList=SCUtil.downloadData(id);
        initComponents();
        setSearchIcon();
        adjust();
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
        jPanel_student = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_student = new javax.swing.JTable();
        jTextField_stuID = new javax.swing.JTextField();
        jTextField_stuName = new javax.swing.JTextField();
        jTextField_stuMajor = new javax.swing.JTextField();
        jComboBox_stuDepart = new javax.swing.JComboBox<>();
        jLabel_search1 = new javax.swing.JLabel();
        jButton_stuExport = new javax.swing.JButton();
        jButton_stuImport = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel_teacher = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_teacher = new javax.swing.JTable();
        jTextField_teaID = new javax.swing.JTextField();
        jTextField_teaName = new javax.swing.JTextField();
        jTextField_teaTitle = new javax.swing.JTextField();
        jComboBox_teaDepart = new javax.swing.JComboBox<>();
        jLabel_search2 = new javax.swing.JLabel();
        jButton_teaExport = new javax.swing.JButton();
        jButton_teaImport = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel_course = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton_teaImport1 = new javax.swing.JButton();
        jButton_teaExport1 = new javax.swing.JButton();
        jTextField_courID = new javax.swing.JTextField();
        jTextField_courName = new javax.swing.JTextField();
        jTextField_courTea = new javax.swing.JTextField();
        jLabel_search3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_course = new javax.swing.JTable();
        jPanel_account = new javax.swing.JPanel();
        jLabel_account = new javax.swing.JLabel();
        jLabel_hello = new javax.swing.JLabel();
        jLabel_logOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 577));
        jTabbedPane1.setRequestFocusEnabled(false);

        jPanel_student.setPreferredSize(new java.awt.Dimension(800, 522));

        jTable_student.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable_student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "学号", "姓名", "学院", "专业"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_student.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_student.setAutoscrolls(false);
        jTable_student.setColumnSelectionAllowed(true);
        jTable_student.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_student);
        jTable_student.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTable_student.getColumnModel().getColumnCount() > 0) {
            jTable_student.getColumnModel().getColumn(0).setResizable(false);
            jTable_student.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable_student.getColumnModel().getColumn(1).setResizable(false);
            jTable_student.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable_student.getColumnModel().getColumn(2).setResizable(false);
            jTable_student.getColumnModel().getColumn(2).setPreferredWidth(134);
            jTable_student.getColumnModel().getColumn(3).setResizable(false);
            jTable_student.getColumnModel().getColumn(3).setPreferredWidth(225);
            jTable_student.getColumnModel().getColumn(4).setResizable(false);
            jTable_student.getColumnModel().getColumn(4).setPreferredWidth(224);
        }

        jLabel_search1.setToolTipText("");
        jLabel_search1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search1MouseClicked(evt);
            }
        });
        jLabel_search1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel_search1KeyPressed(evt);
            }
        });

        jButton_stuExport.setText("导出");
        jButton_stuExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_stuExportActionPerformed(evt);
            }
        });

        jButton_stuImport.setText("导入");
        jButton_stuImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_stuImportMouseClicked(evt);
            }
        });

        jButton3.setText("删除选中");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("查看详细/修改");

        javax.swing.GroupLayout jPanel_studentLayout = new javax.swing.GroupLayout(jPanel_student);
        jPanel_student.setLayout(jPanel_studentLayout);
        jPanel_studentLayout.setHorizontalGroup(
            jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_studentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel_studentLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField_stuID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_stuName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jComboBox_stuDepart, 0, 223, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_stuMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_studentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_stuImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_stuExport)
                .addGap(20, 20, 20))
        );
        jPanel_studentLayout.setVerticalGroup(
            jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_studentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField_stuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_stuName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_stuDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_stuMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton_stuImport)
                    .addComponent(jButton_stuExport))
                .addContainerGap())
        );

        jTabbedPane1.addTab("学生信息", jPanel_student);

        jPanel_teacher.setPreferredSize(new java.awt.Dimension(800, 522));

        jTable_teacher.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable_teacher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "工号", "姓名", "学院", "职称"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_teacher.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_teacher.setAutoscrolls(false);
        jTable_teacher.setColumnSelectionAllowed(true);
        jTable_teacher.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable_teacher);
        jTable_teacher.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTable_teacher.getColumnModel().getColumnCount() > 0) {
            jTable_teacher.getColumnModel().getColumn(0).setResizable(false);
            jTable_teacher.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable_teacher.getColumnModel().getColumn(1).setResizable(false);
            jTable_teacher.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable_teacher.getColumnModel().getColumn(2).setResizable(false);
            jTable_teacher.getColumnModel().getColumn(2).setPreferredWidth(134);
            jTable_teacher.getColumnModel().getColumn(3).setResizable(false);
            jTable_teacher.getColumnModel().getColumn(3).setPreferredWidth(225);
            jTable_teacher.getColumnModel().getColumn(4).setResizable(false);
            jTable_teacher.getColumnModel().getColumn(4).setPreferredWidth(224);
        }

        jLabel_search2.setToolTipText("");
        jLabel_search2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search2MouseClicked(evt);
            }
        });

        jButton_teaExport.setText("导出");
        jButton_teaExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_teaExportActionPerformed(evt);
            }
        });

        jButton_teaImport.setText("导入");
        jButton_teaImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_teaImportActionPerformed(evt);
            }
        });

        jButton7.setText("删除选中");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("查看详细/修改");

        javax.swing.GroupLayout jPanel_teacherLayout = new javax.swing.GroupLayout(jPanel_teacher);
        jPanel_teacher.setLayout(jPanel_teacherLayout);
        jPanel_teacherLayout.setHorizontalGroup(
            jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_teacherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel_teacherLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField_teaID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_teaName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jComboBox_teaDepart, 0, 223, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_teaTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_teacherLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaExport)
                .addGap(20, 20, 20))
        );
        jPanel_teacherLayout.setVerticalGroup(
            jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_teacherLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField_teaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_teaDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton8)
                    .addComponent(jButton7)
                    .addComponent(jButton_teaImport)
                    .addComponent(jButton_teaExport))
                .addContainerGap())
        );

        jTabbedPane1.addTab("教师信息", jPanel_teacher);

        jPanel_course.setPreferredSize(new java.awt.Dimension(800, 576));

        jButton9.setText("查看详细/修改");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("删除选中");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton_teaImport1.setText("导入");
        jButton_teaImport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_teaImport1ActionPerformed(evt);
            }
        });

        jButton_teaExport1.setText("导出");
        jButton_teaExport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_teaExport1ActionPerformed(evt);
            }
        });

        jLabel_search3.setToolTipText("");
        jLabel_search3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search3MouseClicked(evt);
            }
        });
        jLabel_search3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel_search3KeyPressed(evt);
            }
        });

        jTable_course.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "课程号", "课程名称", "教师", "人数"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_course.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(jTable_course);
        if (jTable_course.getColumnModel().getColumnCount() > 0) {
            jTable_course.getColumnModel().getColumn(0).setResizable(false);
            jTable_course.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable_course.getColumnModel().getColumn(1).setResizable(false);
            jTable_course.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable_course.getColumnModel().getColumn(2).setResizable(false);
            jTable_course.getColumnModel().getColumn(2).setPreferredWidth(225);
            jTable_course.getColumnModel().getColumn(3).setResizable(false);
            jTable_course.getColumnModel().getColumn(3).setPreferredWidth(224);
            jTable_course.getColumnModel().getColumn(4).setResizable(false);
            jTable_course.getColumnModel().getColumn(4).setPreferredWidth(134);
        }

        javax.swing.GroupLayout jPanel_courseLayout = new javax.swing.GroupLayout(jPanel_course);
        jPanel_course.setLayout(jPanel_courseLayout);
        jPanel_courseLayout.setHorizontalGroup(
            jPanel_courseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_courseLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel_search3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField_courID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField_courName, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField_courTea, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_courseLayout.createSequentialGroup()
                .addContainerGap(451, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaImport1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaExport1)
                .addGap(20, 20, 20))
            .addGroup(jPanel_courseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel_courseLayout.setVerticalGroup(
            jPanel_courseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_courseLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel_courseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel_search3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_courID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_courName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_courTea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel_courseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton_teaImport1)
                    .addComponent(jButton_teaExport1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("课程信息", jPanel_course);

        javax.swing.GroupLayout jPanel_accountLayout = new javax.swing.GroupLayout(jPanel_account);
        jPanel_account.setLayout(jPanel_accountLayout);
        jPanel_accountLayout.setHorizontalGroup(
            jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        jPanel_accountLayout.setVerticalGroup(
            jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("账号信息", jPanel_account);

        jLabel_account.setText("账号管理");
        jLabel_account.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        jLabel_logOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel_account, jLabel_logOut});

        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_accountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_accountMouseClicked
        // TODO add your handling code here:
        setEnabled(false);
        AccountManageUI AM = new AccountManageUI(admin.id, admin.type);
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel_search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseClicked
        // TODO add your handling code here:
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_stuID.getText().length() > 0) {
            condition.put("id", jTextField_stuID.getText());
        }
        if (jTextField_stuName.getText().length() > 0) {
            condition.put("name", jTextField_stuName.getText());
        }
        if (jComboBox_stuDepart.getSelectedItem() != null) {
            condition.put("depart", jComboBox_stuDepart.getSelectedItem());
        }
        if (jTextField_stuMajor.getText().length() > 0) {
            condition.put("major", jTextField_stuMajor.getText());
        }
        try {
            stuList = adminUtil.downloadData(condition, Student.class);
            studentDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "数据获取失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jLabel_search1MouseClicked

    private void jLabel_search2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search2MouseClicked
        // TODO add your handling code here:
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_teaID.getText().length() > 0) {
            condition.put("id", jTextField_teaID.getText());
        }
        if (jTextField_teaName.getText().length() > 0) {
            condition.put("name", jTextField_teaName.getText());
        }
        if (jComboBox_stuDepart.getSelectedItem() != null) {
            condition.put("depart", jComboBox_teaDepart.getSelectedItem());
        }
        if (jTextField_teaTitle.getText().length() > 0) {
            condition.put("title", jTextField_teaTitle.getText());
        }
        try {
            teaList = adminUtil.downloadData(condition, Teacher.class);
            teacherDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "数据获取失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jLabel_search2MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jLabel_search1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_search1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_search1KeyPressed

    private void jButton_teaImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaImportActionPerformed
        // TODO add your handling code here:
        Import(teaList,Teacher.class);
        teacherDisplay();
    }//GEN-LAST:event_jButton_teaImportActionPerformed

    private void jButton_stuImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_stuImportMouseClicked
        // TODO add your handling code here:
        Import(stuList,Student.class);
        studentDisplay();
    }//GEN-LAST:event_jButton_stuImportMouseClicked

    private void jButton_stuExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_stuExportActionPerformed
        // TODO add your handling code here:
        Export(stuList,Student.class);
    }//GEN-LAST:event_jButton_stuExportActionPerformed

    private void jButton_teaExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaExportActionPerformed
        // TODO add your handling code here:
        Export(teaList,Teacher.class);
    }//GEN-LAST:event_jButton_teaExportActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton_teaImport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaImport1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_teaImport1ActionPerformed

    private void jButton_teaExport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaExport1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_teaExport1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel_search3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_search3MouseClicked

    private void jLabel_search3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_search3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_search3KeyPressed
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_stuExport;
    private javax.swing.JButton jButton_stuImport;
    private javax.swing.JButton jButton_teaExport;
    private javax.swing.JButton jButton_teaExport1;
    private javax.swing.JButton jButton_teaImport;
    private javax.swing.JButton jButton_teaImport1;
    private javax.swing.JComboBox<String> jComboBox_stuDepart;
    private javax.swing.JComboBox<String> jComboBox_teaDepart;
    private javax.swing.JLabel jLabel_account;
    private javax.swing.JLabel jLabel_hello;
    private javax.swing.JLabel jLabel_logOut;
    private javax.swing.JLabel jLabel_search1;
    private javax.swing.JLabel jLabel_search2;
    private javax.swing.JLabel jLabel_search3;
    private javax.swing.JPanel jPanel_account;
    private javax.swing.JPanel jPanel_course;
    private javax.swing.JPanel jPanel_student;
    private javax.swing.JPanel jPanel_teacher;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_course;
    private javax.swing.JTable jTable_student;
    private javax.swing.JTable jTable_teacher;
    private javax.swing.JTextField jTextField_courID;
    private javax.swing.JTextField jTextField_courName;
    private javax.swing.JTextField jTextField_courTea;
    private javax.swing.JTextField jTextField_stuID;
    private javax.swing.JTextField jTextField_stuMajor;
    private javax.swing.JTextField jTextField_stuName;
    private javax.swing.JTextField jTextField_teaID;
    private javax.swing.JTextField jTextField_teaName;
    private javax.swing.JTextField jTextField_teaTitle;
    // End of variables declaration//GEN-END:variables
}
