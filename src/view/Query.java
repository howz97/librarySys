package view;

import dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class Query extends JFrame {
    public static final int kindBook = 1;
    public static final int kindReader = 2;
    public static final int kindRecord = 3;
    int kind = 0;
    JTable table    = null;
    JButton updateB = new JButton("更新");
    JButton deleteB = new JButton("删除");
    JButton backB   = new JButton("返回");
    JPanel jp       = new JPanel();

    public Query(int k,JTable t){
        kind  = k;
        table = t;
        this.setTitle("查询结果");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(table.getTableHeader(),BorderLayout.NORTH);
        this.add(table,BorderLayout.CENTER);
        this.setSize(1500,800);

        jp.add(updateB);
        jp.add(deleteB);
        jp.add(backB);
        this.add(jp,BorderLayout.SOUTH);

        this.addEvent();

        this.setVisible(true);
    }

    void addEvent(){
        updateB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (kind){
                    case kindBook:
                        DBHelper dbh = new DBHelper();
                        try{
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            String name = table.getValueAt(table.getSelectedRow(),1).toString();
                            String author = table.getValueAt(table.getSelectedRow(),2).toString();
                            String pub = table.getValueAt(table.getSelectedRow(),3).toString();
                            int reser = Integer.parseInt(table.getValueAt(table.getSelectedRow(),4).toString());
                            String descp = table.getValueAt(table.getSelectedRow(),5).toString();
                            dbh.updateBook(id,name,author,pub,reser,descp);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"请按正确格式录入信息！","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"更新失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"更新书籍信息成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                    case kindReader:
                        dbh = new DBHelper();
                        try {
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            String name = table.getValueAt(table.getSelectedRow(),1).toString();
                            int phone = Integer.parseInt(table.getValueAt(table.getSelectedRow(),3).toString());
                            dbh.updateReader(id,name,phone);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"请按正确格式录入信息！","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"更新失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"更新读者信息成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                    case kindRecord:
                        dbh = new DBHelper();
                        try{
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            int bid = Integer.parseInt(table.getValueAt(table.getSelectedRow(),1).toString());
                            int rid = Integer.parseInt(table.getValueAt(table.getSelectedRow(),2).toString());
                            Date bdate = Date.valueOf(table.getValueAt(table.getSelectedRow(),3).toString());
                            Date rdate = Date.valueOf(table.getValueAt(table.getSelectedRow(),4).toString());
                            dbh.updateRecord(id,bid,rid,bdate,rdate);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"请按正确格式录入信息！\n日期格式为：XXXX-XX-XX","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"更新失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"更新记录信息成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                }
            }
        });
        deleteB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"确认删除？","确认",JOptionPane.YES_NO_OPTION);

                if (result == 1){
                    return;
                }

                switch (kind){
                    case kindBook:
                        DBHelper dbh = new DBHelper();
                        try {
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            dbh.deleteBook(id);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"删除失败，id 格式错误","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"删除失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"书籍删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                    case kindReader:
                        dbh = new DBHelper();
                        try {
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            dbh.deleteReader(id);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"删除失败，id 格式错误","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"删除失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"读者删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                    case kindRecord:
                        dbh = new DBHelper();
                        int readerId = 0,bookId = 0;
                        try {
                            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                            readerId = Integer.parseInt(table.getValueAt(table.getSelectedRow(),1).toString());
                            bookId = Integer.parseInt(table.getValueAt(table.getSelectedRow(),2).toString());
                            dbh.deleteRecord(id);
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(null,"删除失败，id 格式错误","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"删除失败，确认id是否存在","错误",JOptionPane.ERROR_MESSAGE);
                            dbh.close();
                            break;
                        }
                        try{
                            dbh.returnBook(readerId,bookId);
                        }catch (SQLException se){
                            JOptionPane.showMessageDialog(null,"归还出现未知异常","提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                        JOptionPane.showMessageDialog(null,"记录删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        dbh.close();
                        break;
                }
            }
        });
        backB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
