/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.ui;

import infoadminsys.cls.*;
import infoadminsys.util.*;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

/**
 *
 * @author lsh
 */
public class AdminUI extends javax.swing.JFrame {

    /**
     * Creates new form StudentUI
     */
    private Administrator admin = new Administrator();
    private AdminUtil adminUtil = new AdminUtil();
    private List<Student> stuList = new ArrayList<>();
    private List<Teacher> teaList = new ArrayList<>();
    private List<Course> courList = new ArrayList<>();
    private List<UserPass> accList = new ArrayList<>();
    private CourseUtil courseUtil = new CourseUtil();

    private void setSearchIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/search.png"));
        icon.setImage(icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        jLabel_search1.setIcon(icon);
        jLabel_search2.setIcon(icon);
        jLabel_search3.setIcon(icon);
        jLabel_search4.setIcon(icon);
    }

    private void adjust() {
        jLabel_hello.setText("您好，" + admin.id);
        jPanel_student.setLayout(null);
        jScrollPane1.setBounds(6, 27, 767, 480);
        jTable_student.setBounds(6, 27, 767, 440);
        jLabel_search1.setBounds(20, 0, 24, 24);
        jTextField_stuID.setBounds(54, 3, 133, 20);
        jTextField_stuName.setBounds(184, 3, 138, 20);
        jTextField_stuDepart.setBounds(319, 3, 227, 20);
        jTextField_stuMajor.setBounds(543, 3, 232, 20);

        jPanel_teacher.setLayout(null);
        jScrollPane2.setBounds(6, 27, 767, 480);
        jLabel_search2.setBounds(20, 0, 24, 24);
        jTextField_teaID.setBounds(54, 3, 133, 20);
        jTextField_teaName.setBounds(184, 3, 138, 20);
        jTextField_teaDepart.setBounds(319, 3, 227, 20);
        jTextField_teaTitle.setBounds(543, 3, 232, 20);

        jPanel_course.setLayout(null);
        jScrollPane3.setBounds(6, 27, 767, 480);
        jLabel_search3.setBounds(20, 0, 24, 24);
        jTextField_courID.setBounds(54, 3, 133, 20);
        jTextField_courName.setBounds(184, 3, 228, 20);
        jTextField_courTea.setBounds(409, 3, 228, 20);

        jPanel_account.setLayout(null);
        jScrollPane4.setBounds(6, 27, 767, 480);
        jLabel_search4.setBounds(20, 0, 24, 24);
        jTextField_accUsername.setBounds(54, 3, 360, 20);
        jComboBox_accType.setBounds(410, 4, 365, 20);

        DefaultTableCellRenderer hr=(DefaultTableCellRenderer) jTable_student.getTableHeader().getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        jTable_student.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_teacher.getTableHeader().setDefaultRenderer(hr);
        jTable_teacher.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_teacher.getTableHeader().setDefaultRenderer(hr);
        jTable_course.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_course.getTableHeader().setDefaultRenderer(hr);
        jTable_account.getTableHeader().setFont(new Font("Lucida Grande", 0, 13));
        jTable_account.getTableHeader().setDefaultRenderer(hr);
    }

    private <T> void Export(List<T> objList, Class<T> cls) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv文件", "csv");
        chooser.setFileFilter(filter);
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                T obj = cls.newInstance();
                String[] Attr = (String[]) cls.getDeclaredField("Attr").get(obj);
                String[] row = new String[Attr.length];
                CsvWriter writer = new CsvWriter(file.getPath(), ',', Charset.defaultCharset());
                writer.writeRecord(Attr);
                Field field;
                int n = objList.size();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < Attr.length; j++) {
                        field = cls.getDeclaredField(Attr[j]);
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
                    T obj = cls.newInstance();
                    for (int i = 0; i < columnName.length; i++) {
                        String col = columnName[i];
                        field = cls.getDeclaredField(col);
                        Class<?> t = field.getType();
                        String value = reader.get(columnName[i]).trim();
                        if (t.equals(java.sql.Date.class)) {
                            field.set(obj, Utility.StringToDate(value));
                        } else if (t.equals(int.class)) {
                            field.set(obj, Utility.StringToInt(value));
                        } else if (t.equals(double.class)) {
                            field.set(obj, Utility.StringToDouble(value));
                        } else {
                            field.set(obj, value);
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

    private void courseDisplay() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_course.getModel();
        tableModel.setRowCount(courList.size());
        for (int i = 0; i < courList.size(); i++) {
            Course course = courList.get(i);
            jTable_course.setValueAt(i + 1, i, 0);
            jTable_course.setValueAt(course.id, i, 1);
            jTable_course.setValueAt(course.name, i, 2);
            jTable_course.setValueAt(course.teacher_name, i, 3);
            jTable_course.setValueAt(course.getProportionString(), i, 4);
        }
    }

    private void accountDisplay() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_account.getModel();
        tableModel.setRowCount(accList.size());
        for (int i = 0; i < accList.size(); i++) {
            UserPass account = accList.get(i);
            jTable_account.setValueAt(i + 1, i, 0);
            jTable_account.setValueAt(account.getusername(), i, 1);
            jTable_account.setValueAt(account.getTypeCHN(account.type), i, 2);
        }
    }

    private void studentSearch() {
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_stuID.getText().length() > 0) {
            condition.put("id", jTextField_stuID.getText());
        }
        if (jTextField_stuName.getText().length() > 0) {
            condition.put("name", jTextField_stuName.getText());
        }
        if (jTextField_stuDepart.getText().length()>0) {
            condition.put("depart", jTextField_stuDepart.getText());
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
    }

    private void teacherSearch() {
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_teaID.getText().length() > 0) {
            condition.put("id", jTextField_teaID.getText());
        }
        if (jTextField_teaName.getText().length() > 0) {
            condition.put("name", jTextField_teaName.getText());
        }
        if (jTextField_stuDepart.getText().length()>0) {
            condition.put("depart", jTextField_teaDepart.getText().length());
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
    }

    private void courseSearch() {
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_courID.getText().length() > 0) {
            condition.put("c.id", "%"+jTextField_courID.getText()+"%");
        }
        if (jTextField_courName.getText().length() > 0) {
            condition.put("c.name", "%"+jTextField_courName.getText()+"%");
        }
        if (jTextField_courTea.getText().length() > 0) {
            condition.put("t.name", "%"+jTextField_courTea.getText()+"%");
        }
        try {
            courList.clear();
            List<Map<String, Object>> mapList = courseUtil.findAllCourse(condition);
            int n = mapList.size();
            for (int i = 0; i < n; i++) {
                courList.add(Utility.MapToClass(mapList.get(i), Course.class));
            }
            courseDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "数据获取失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void accountSearch() {
        Map<String, Object> condition = new HashMap<>();
        if (jTextField_accUsername.getText().length() > 0) {
            condition.put("username", jTextField_accUsername.getText());
        }
        if (jComboBox_accType.getSelectedIndex() > 0) {
            condition.put("type", UserPass.getTypeENG(jComboBox_accType.getSelectedItem().toString()));
        }
        try {
            accList = adminUtil.downloadData(condition, UserPass.class);
            accountDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "数据获取失败！\n" + e.getMessage(), "提示信息", JOptionPane.WARNING_MESSAGE);
        }
    }

    public AdminUI() {
    }

    public AdminUI(String username) {
        admin.id = username;
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
        jLabel_search1 = new javax.swing.JLabel();
        jButton_stuExport = new javax.swing.JButton();
        jButton_stuImport = new javax.swing.JButton();
        jButton_stuDelete = new javax.swing.JButton();
        jButton_stuModify = new javax.swing.JButton();
        jTextField_stuDepart = new javax.swing.JTextField();
        jPanel_teacher = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_teacher = new javax.swing.JTable();
        jTextField_teaID = new javax.swing.JTextField();
        jTextField_teaName = new javax.swing.JTextField();
        jTextField_teaTitle = new javax.swing.JTextField();
        jLabel_search2 = new javax.swing.JLabel();
        jButton_teaExport = new javax.swing.JButton();
        jButton_teaImport = new javax.swing.JButton();
        jButton_teaDelete = new javax.swing.JButton();
        jButton_teaModify = new javax.swing.JButton();
        jTextField_teaDepart = new javax.swing.JTextField();
        jPanel_course = new javax.swing.JPanel();
        jButton_courModify = new javax.swing.JButton();
        jButton_courDelete = new javax.swing.JButton();
        jButton_courImport = new javax.swing.JButton();
        jButton_courExport = new javax.swing.JButton();
        jTextField_courID = new javax.swing.JTextField();
        jTextField_courName = new javax.swing.JTextField();
        jTextField_courTea = new javax.swing.JTextField();
        jLabel_search3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_course = new javax.swing.JTable();
        jPanel_account = new javax.swing.JPanel();
        jLabel_search4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_account = new javax.swing.JTable();
        jButton_accModify = new javax.swing.JButton();
        jButton_accDelete = new javax.swing.JButton();
        jButton_accImport = new javax.swing.JButton();
        jButton_accExport = new javax.swing.JButton();
        jTextField_accUsername = new javax.swing.JTextField();
        jComboBox_accType = new javax.swing.JComboBox<>();
        jLabel_account = new javax.swing.JLabel();
        jLabel_hello = new javax.swing.JLabel();
        jLabel_logOut = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 580));
        jTabbedPane1.setRequestFocusEnabled(false);

        jPanel_student.setPreferredSize(new java.awt.Dimension(800, 500));

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
        jTable_student.getTableHeader().setResizingAllowed(false);
        jTable_student.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable_student);
        jTable_student.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        jTextField_stuID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_stuIDKeyPressed(evt);
            }
        });

        jTextField_stuName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_stuNameKeyPressed(evt);
            }
        });

        jTextField_stuMajor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_stuMajorKeyPressed(evt);
            }
        });

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

        jButton_stuDelete.setText("删除选中");
        jButton_stuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_stuDeleteActionPerformed(evt);
            }
        });

        jButton_stuModify.setText("查看详细/修改");
        jButton_stuModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_stuModifyMouseClicked(evt);
            }
        });

        jTextField_stuDepart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_stuDepartKeyPressed(evt);
            }
        });

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
                        .addComponent(jTextField_stuDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_stuMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_studentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_stuModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_stuDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_stuImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_stuExport)
                .addGap(20, 20, 20))
        );
        jPanel_studentLayout.setVerticalGroup(
            jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_studentLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField_stuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_stuName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_stuMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_stuDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel_studentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_stuModify)
                    .addComponent(jButton_stuDelete)
                    .addComponent(jButton_stuImport)
                    .addComponent(jButton_stuExport))
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("学生信息", jPanel_student);

        jPanel_teacher.setPreferredSize(new java.awt.Dimension(800, 500));

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
        jTable_teacher.getTableHeader().setResizingAllowed(false);
        jTable_teacher.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable_teacher);
        jTable_teacher.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        jTextField_teaID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_teaIDKeyPressed(evt);
            }
        });

        jTextField_teaName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_teaNameKeyPressed(evt);
            }
        });

        jTextField_teaTitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_teaTitleKeyPressed(evt);
            }
        });

        jLabel_search2.setToolTipText("");
        jLabel_search2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search2MouseClicked(evt);
            }
        });
        jLabel_search2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel_search2KeyPressed(evt);
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

        jButton_teaDelete.setText("删除选中");
        jButton_teaDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_teaDeleteActionPerformed(evt);
            }
        });

        jButton_teaModify.setText("查看详细/修改");
        jButton_teaModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_teaModifyMouseClicked(evt);
            }
        });

        jTextField_teaDepart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_teaDepartKeyPressed(evt);
            }
        });

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
                        .addComponent(jTextField_teaDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField_teaTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_teacherLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_teaModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_teaExport)
                .addGap(20, 20, 20))
        );
        jPanel_teacherLayout.setVerticalGroup(
            jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_teacherLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextField_teaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_teaDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_teacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_teaModify)
                    .addComponent(jButton_teaDelete)
                    .addComponent(jButton_teaImport)
                    .addComponent(jButton_teaExport))
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("教师信息", jPanel_teacher);

        jPanel_course.setPreferredSize(new java.awt.Dimension(800, 500));

        jButton_courModify.setText("查看详细/修改");
        jButton_courModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_courModifyMouseClicked(evt);
            }
        });

        jButton_courDelete.setText("删除选中");
        jButton_courDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_courDeleteMouseClicked(evt);
            }
        });

        jButton_courImport.setText("导入");
        jButton_courImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_courImportMouseClicked(evt);
            }
        });

        jButton_courExport.setText("导出");
        jButton_courExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_courExportMouseClicked(evt);
            }
        });

        jTextField_courID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_courIDKeyPressed(evt);
            }
        });

        jTextField_courName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_courNameKeyPressed(evt);
            }
        });

        jTextField_courTea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_courTeaKeyPressed(evt);
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

        jTable_course.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
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
        jTable_course.setColumnSelectionAllowed(true);
        jTable_course.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable_course);
        jTable_course.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTable_course.getColumnModel().getColumnCount() > 0) {
            jTable_course.getColumnModel().getColumn(0).setResizable(false);
            jTable_course.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable_course.getColumnModel().getColumn(1).setResizable(false);
            jTable_course.getColumnModel().getColumn(1).setPreferredWidth(130);
            jTable_course.getColumnModel().getColumn(1).setHeaderValue("课程号");
            jTable_course.getColumnModel().getColumn(2).setResizable(false);
            jTable_course.getColumnModel().getColumn(2).setPreferredWidth(225);
            jTable_course.getColumnModel().getColumn(2).setHeaderValue("课程名称");
            jTable_course.getColumnModel().getColumn(3).setResizable(false);
            jTable_course.getColumnModel().getColumn(3).setPreferredWidth(224);
            jTable_course.getColumnModel().getColumn(3).setHeaderValue("教师");
            jTable_course.getColumnModel().getColumn(4).setResizable(false);
            jTable_course.getColumnModel().getColumn(4).setPreferredWidth(134);
            jTable_course.getColumnModel().getColumn(4).setHeaderValue("人数");
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
                .addComponent(jButton_courModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_courDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_courImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_courExport)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel_courseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_courModify)
                    .addComponent(jButton_courDelete)
                    .addComponent(jButton_courExport)
                    .addComponent(jButton_courImport))
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("课程信息", jPanel_course);

        jPanel_account.setPreferredSize(new java.awt.Dimension(800, 500));

        jLabel_search4.setToolTipText("");
        jLabel_search4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_search4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_search4MouseClicked(evt);
            }
        });

        jTable_account.setFont(new java.awt.Font("Lucida Grande", 0, 13)); // NOI18N
        jTable_account.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "用户名", "类型"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_account.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_account.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable_account);
        jTable_account.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (jTable_account.getColumnModel().getColumnCount() > 0) {
            jTable_account.getColumnModel().getColumn(0).setResizable(false);
            jTable_account.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable_account.getColumnModel().getColumn(1).setResizable(false);
            jTable_account.getColumnModel().getColumn(1).setPreferredWidth(356);
            jTable_account.getColumnModel().getColumn(2).setResizable(false);
            jTable_account.getColumnModel().getColumn(2).setPreferredWidth(357);
        }

        jButton_accModify.setText("重置密码");
        jButton_accModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_accModifyMouseClicked(evt);
            }
        });

        jButton_accDelete.setText("删除选中");
        jButton_accDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_accDeleteMouseClicked(evt);
            }
        });

        jButton_accImport.setText("导入");
        jButton_accImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_accImportMouseClicked(evt);
            }
        });

        jButton_accExport.setText("导出");
        jButton_accExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_accExportMouseClicked(evt);
            }
        });

        jTextField_accUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_accUsernameKeyPressed(evt);
            }
        });

        jComboBox_accType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "学生", "教师", "管理员" }));
        jComboBox_accType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox_accTypeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_accountLayout = new javax.swing.GroupLayout(jPanel_account);
        jPanel_account.setLayout(jPanel_accountLayout);
        jPanel_accountLayout.setHorizontalGroup(
            jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_accountLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel_search4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField_accUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jComboBox_accType, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(jPanel_accountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_accountLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_accModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_accDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_accImport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_accExport)
                .addGap(20, 20, 20))
        );
        jPanel_accountLayout.setVerticalGroup(
            jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_accountLayout.createSequentialGroup()
                .addGroup(jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_search4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_accType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_accUsername))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton_accModify)
                    .addComponent(jButton_accDelete)
                    .addComponent(jButton_accExport)
                    .addComponent(jButton_accImport))
                .addGap(12, 12, 12))
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
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel_account, jLabel_logOut});

        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_accountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_accountMouseClicked
        // TODO add your handling code here:
        setEnabled(false);
        AccountManageUI AM = new AccountManageUI(admin.id, admin.type, false);
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

    private void jButton_stuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_stuDeleteActionPerformed
        // TODO add your handling code here:
        int rows[] = jTable_student.getSelectedRows();
        int n = rows.length;
        if (n > 0) {
            int res = JOptionPane.showConfirmDialog(this, "确定删除？此操作不可撤回!", "提示信息", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) {
                return;
            }
            Arrays.sort(rows);
            int count = 0;
            for (int i = 0; i < n; i++) {
                String id = jTable_student.getValueAt(rows[i], 1).toString();
                try {
                    adminUtil.deleteData("student", "id", id);
                } catch (Exception e) {
                    count++;
                    rows[i] = -1;
                }
            }
            JOptionPane.showMessageDialog(this, "删除完毕，共" + Integer.toString(n) + "个，失败" + Integer.toString(count) + "个!", "提示信息", JOptionPane.WARNING_MESSAGE);
            for (int i = n - 1; i >= 0; i--) {
                if (rows[i] >= 0) {
                    stuList.remove(rows[i]);
                }
            }
            studentDisplay();
        }
    }//GEN-LAST:event_jButton_stuDeleteActionPerformed

    private void jLabel_search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search1MouseClicked
        // TODO add your handling code here:
        studentSearch();
    }//GEN-LAST:event_jLabel_search1MouseClicked

    private void jLabel_search2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search2MouseClicked
        // TODO add your handling code here:
        teacherSearch();
    }//GEN-LAST:event_jLabel_search2MouseClicked

    private void jButton_teaDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaDeleteActionPerformed
        // TODO add your handling code here:
        int rows[] = jTable_teacher.getSelectedRows();
        int n = rows.length;
        if (n > 0) {
            int res = JOptionPane.showConfirmDialog(this, "确定删除？此操作不可撤回!", "提示信息", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) {
                return;
            }
            Arrays.sort(rows);
            int count = 0;
            for (int i = 0; i < n; i++) {
                String id = jTable_teacher.getValueAt(rows[i], 1).toString();
                try {
                    adminUtil.deleteData("teacher", "id", id);
                } catch (Exception e) {
                    count++;
                    rows[i] = -1;
                }
            }
            JOptionPane.showMessageDialog(this, "删除完毕，共" + Integer.toString(n) + "个，失败" + Integer.toString(count) + "个!", "提示信息", JOptionPane.WARNING_MESSAGE);
            for (int i = n - 1; i >= 0; i--) {
                if (rows[i] >= 0) {
                    teaList.remove(rows[i]);
                }
            }
            teacherDisplay();
        }
    }//GEN-LAST:event_jButton_teaDeleteActionPerformed

    private void jLabel_search1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_search1KeyPressed
        // TODO add your handling code here:
        studentSearch();
    }//GEN-LAST:event_jLabel_search1KeyPressed

    private void jButton_teaImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaImportActionPerformed
        // TODO add your handling code here:
        Import(teaList, Teacher.class);
        teacherDisplay();
    }//GEN-LAST:event_jButton_teaImportActionPerformed

    private void jButton_stuImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_stuImportMouseClicked
        // TODO add your handling code here:
        Import(stuList, Student.class);
        studentDisplay();
    }//GEN-LAST:event_jButton_stuImportMouseClicked

    private void jButton_stuExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_stuExportActionPerformed
        // TODO add your handling code here:
        Export(stuList, Student.class);
    }//GEN-LAST:event_jButton_stuExportActionPerformed

    private void jButton_teaExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_teaExportActionPerformed
        // TODO add your handling code here:
        Export(teaList, Teacher.class);
    }//GEN-LAST:event_jButton_teaExportActionPerformed

    private void jLabel_search3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search3MouseClicked
        // TODO add your handling code here:
        courseSearch();
    }//GEN-LAST:event_jLabel_search3MouseClicked

    private void jLabel_search3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_search3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_search3KeyPressed

    private void jButton_stuModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_stuModifyMouseClicked
        // TODO add your handling code here:
        int row = jTable_student.getSelectedRow();
        if (row >= 0) {
            setEnabled(false);
            String username = jTable_student.getValueAt(row, 1).toString();
            StudentUI S = new StudentUI(username, true);
            S.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            S.setLocationRelativeTo(this);
            S.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    setEnabled(true);
                }
            });
            S.setVisible(true);
        }
    }//GEN-LAST:event_jButton_stuModifyMouseClicked

    private void jLabel_search2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel_search2KeyPressed
        // TODO add your handling code here:
        teacherSearch();
    }//GEN-LAST:event_jLabel_search2KeyPressed

    private void jTextField_stuIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_stuIDKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            studentSearch();
        }
    }//GEN-LAST:event_jTextField_stuIDKeyPressed

    private void jTextField_stuNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_stuNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            studentSearch();
        }
    }//GEN-LAST:event_jTextField_stuNameKeyPressed

    private void jTextField_stuMajorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_stuMajorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            studentSearch();
        }
    }//GEN-LAST:event_jTextField_stuMajorKeyPressed

    private void jTextField_teaIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_teaIDKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            teacherSearch();
        }
    }//GEN-LAST:event_jTextField_teaIDKeyPressed

    private void jTextField_teaNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_teaNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            teacherSearch();
        }
    }//GEN-LAST:event_jTextField_teaNameKeyPressed

    private void jTextField_teaTitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_teaTitleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            teacherSearch();
        }
    }//GEN-LAST:event_jTextField_teaTitleKeyPressed

    private void jTextField_courIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_courIDKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            courseSearch();
        }
    }//GEN-LAST:event_jTextField_courIDKeyPressed

    private void jTextField_courNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_courNameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            courseSearch();
        }
    }//GEN-LAST:event_jTextField_courNameKeyPressed

    private void jTextField_courTeaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_courTeaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            courseSearch();
        }
    }//GEN-LAST:event_jTextField_courTeaKeyPressed

    private void jLabel_search4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_search4MouseClicked
        // TODO add your handling code here:
        accountSearch();
    }//GEN-LAST:event_jLabel_search4MouseClicked

    private void jTextField_accUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_accUsernameKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            accountSearch();
        }
    }//GEN-LAST:event_jTextField_accUsernameKeyPressed

    private void jComboBox_accTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_accTypeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            accountSearch();
        }
    }//GEN-LAST:event_jComboBox_accTypeKeyPressed

    private void jButton_accDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_accDeleteMouseClicked
        // TODO add your handling code here:
        int rows[] = jTable_account.getSelectedRows();
        int n = rows.length;
        if (n > 0) {
            int res = JOptionPane.showConfirmDialog(this, "确定删除？此操作不可撤回!", "提示信息", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) {
                return;
            }
            Arrays.sort(rows);
            int count = 0;
            for (int i = 0; i < n; i++) {
                String username = jTable_account.getValueAt(rows[i], 1).toString();
                try {
                    adminUtil.deleteData("userpass", "username", username);
                } catch (Exception e) {
                    count++;
                    rows[i] = -1;
                }
            }
            JOptionPane.showMessageDialog(this, "删除完毕，共" + Integer.toString(n) + "个，失败" + Integer.toString(count) + "个!", "提示信息", JOptionPane.WARNING_MESSAGE);
            for (int i = n - 1; i >= 0; i--) {
                if (rows[i] >= 0) {
                    accList.remove(rows[i]);
                }
            }
            accountDisplay();
        }
    }//GEN-LAST:event_jButton_accDeleteMouseClicked

    private void jButton_teaModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_teaModifyMouseClicked
        // TODO add your handling code here:
        int row = jTable_teacher.getSelectedRow();
        if (row >= 0) {
            setEnabled(false);
            String username = jTable_teacher.getValueAt(row, 1).toString();
            TeacherUI T = new TeacherUI(username, true);
            T.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            T.setLocationRelativeTo(this);
            T.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    setEnabled(true);
                }
            });
            T.setVisible(true);
        }
    }//GEN-LAST:event_jButton_teaModifyMouseClicked

    private void jButton_courImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_courImportMouseClicked
        // TODO add your handling code here:
        Import(courList, Course.class);
        courseDisplay();
    }//GEN-LAST:event_jButton_courImportMouseClicked

    private void jButton_courExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_courExportMouseClicked
        // TODO add your handling code here:
        Export(courList, Course.class);
    }//GEN-LAST:event_jButton_courExportMouseClicked

    private void jButton_courDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_courDeleteMouseClicked
        // TODO add your handling code here:
        int rows[] = jTable_course.getSelectedRows();
        int n = rows.length;
        if (n > 0) {
            int res = JOptionPane.showConfirmDialog(this, "确定删除？此操作不可撤回!", "提示信息", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.NO_OPTION) {
                return;
            }
            Arrays.sort(rows);
            int count = 0;
            for (int i = 0; i < n; i++) {
                String id = jTable_course.getValueAt(rows[i], 1).toString();
                try {
                    adminUtil.deleteData("course", "id", id);
                } catch (Exception e) {
                    count++;
                    rows[i] = -1;
                }
            }
            JOptionPane.showMessageDialog(this, "删除完毕，共" + Integer.toString(n) + "个，失败" + Integer.toString(count) + "个!", "提示信息", JOptionPane.WARNING_MESSAGE);
            for (int i = n - 1; i >= 0; i--) {
                if (rows[i] >= 0) {
                    courList.remove(rows[i]);
                }
            }
            courseDisplay();
        }
    }//GEN-LAST:event_jButton_courDeleteMouseClicked

    private void jButton_accImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_accImportMouseClicked
        // TODO add your handling code here:
        Import(accList, UserPass.class);
        accountDisplay();
    }//GEN-LAST:event_jButton_accImportMouseClicked

    private void jButton_accExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_accExportMouseClicked
        // TODO add your handling code here:
        Export(accList, UserPass.class);
    }//GEN-LAST:event_jButton_accExportMouseClicked

    private void jButton_accModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_accModifyMouseClicked
        // TODO add your handling code here:
        int row = jTable_account.getSelectedRow();
        if (row >= 0) {
            setEnabled(false);
            String username = jTable_account.getValueAt(row, 1).toString();
            String type = jTable_account.getValueAt(row, 2).toString();
            AccountManageUI AM = new AccountManageUI(username, type, true);
            AM.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            AM.setLocationRelativeTo(this);
            AM.setAlwaysOnTop(true);
            AM.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    setEnabled(true);
                }
            });
            AM.setVisible(true);
        }
    }//GEN-LAST:event_jButton_accModifyMouseClicked

    private void jButton_courModifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_courModifyMouseClicked
        // TODO add your handling code here:
        int row = jTable_course.getSelectedRow();
        if (row >= 0) {
            setEnabled(false);
            String id = jTable_course.getValueAt(row, 1).toString();
            CourseUI C = new CourseUI(id);
            C.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            C.setLocationRelativeTo(this);
            C.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    setEnabled(true);
                }
            });
            C.setVisible(true);
        }
    }//GEN-LAST:event_jButton_courModifyMouseClicked

    private void jTextField_stuDepartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_stuDepartKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            studentSearch();
        }
    }//GEN-LAST:event_jTextField_stuDepartKeyPressed

    private void jTextField_teaDepartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_teaDepartKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            teacherSearch();
        }
    }//GEN-LAST:event_jTextField_teaDepartKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_accDelete;
    private javax.swing.JButton jButton_accExport;
    private javax.swing.JButton jButton_accImport;
    private javax.swing.JButton jButton_accModify;
    private javax.swing.JButton jButton_courDelete;
    private javax.swing.JButton jButton_courExport;
    private javax.swing.JButton jButton_courImport;
    private javax.swing.JButton jButton_courModify;
    private javax.swing.JButton jButton_stuDelete;
    private javax.swing.JButton jButton_stuExport;
    private javax.swing.JButton jButton_stuImport;
    private javax.swing.JButton jButton_stuModify;
    private javax.swing.JButton jButton_teaDelete;
    private javax.swing.JButton jButton_teaExport;
    private javax.swing.JButton jButton_teaImport;
    private javax.swing.JButton jButton_teaModify;
    private javax.swing.JComboBox<String> jComboBox_accType;
    private javax.swing.JLabel jLabel_account;
    private javax.swing.JLabel jLabel_hello;
    private javax.swing.JLabel jLabel_logOut;
    private javax.swing.JLabel jLabel_search1;
    private javax.swing.JLabel jLabel_search2;
    private javax.swing.JLabel jLabel_search3;
    private javax.swing.JLabel jLabel_search4;
    private javax.swing.JPanel jPanel_account;
    private javax.swing.JPanel jPanel_course;
    private javax.swing.JPanel jPanel_student;
    private javax.swing.JPanel jPanel_teacher;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_account;
    private javax.swing.JTable jTable_course;
    private javax.swing.JTable jTable_student;
    private javax.swing.JTable jTable_teacher;
    private javax.swing.JTextField jTextField_accUsername;
    private javax.swing.JTextField jTextField_courID;
    private javax.swing.JTextField jTextField_courName;
    private javax.swing.JTextField jTextField_courTea;
    private javax.swing.JTextField jTextField_stuDepart;
    private javax.swing.JTextField jTextField_stuID;
    private javax.swing.JTextField jTextField_stuMajor;
    private javax.swing.JTextField jTextField_stuName;
    private javax.swing.JTextField jTextField_teaDepart;
    private javax.swing.JTextField jTextField_teaID;
    private javax.swing.JTextField jTextField_teaName;
    private javax.swing.JTextField jTextField_teaTitle;
    // End of variables declaration//GEN-END:variables
}
