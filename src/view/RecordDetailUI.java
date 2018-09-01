package view;

import dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class RecordDetailUI extends JFrame {
    private JLabel readerId     = new JLabel("读者id:");
    private JLabel bookId       = new JLabel("书籍id:");
    private JLabel borrowDate   = new JLabel("借书时间:");
    private JLabel returnDate   = new JLabel("还书时间:");
    private JTextField readerF  = new JTextField(10);
    private JTextField bookF    = new JTextField(10);
    private JTextField borrowF  = new JTextField(10);
    private JTextField returnF  = new JTextField(10);
    private JButton newButton   = new JButton("新建");
    private JButton backButton  = new JButton("返回");
    private JPanel jp1          = new JPanel();
    private JPanel jp2          = new JPanel();
    private JPanel jp3          = new JPanel();

    public RecordDetailUI(){
        this.setTitle("Record");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addComponent();
        addEvent();

        pack();
        this.setVisible(true);
    }

    public void addComponent(){
        this.setLayout(new GridLayout(3,1));

        jp1.add(readerId);
        jp1.add(readerF);
        jp1.add(bookId);
        jp1.add(bookF);

        jp2.add(borrowDate);
        jp2.add(borrowF);
        jp2.add(returnDate);
        jp2.add(returnF);

        jp3.add(newButton);
        jp3.add(backButton);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
    }

    public void addEvent(){
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHelper dbh = new DBHelper();
                int readerId = 0,bookId = 0;
                try{
                    readerId = Integer.valueOf(readerF.getText());
                    bookId = Integer.valueOf(bookF.getText());
                    Date borrowDate = java.sql.Date.valueOf(borrowF.getText());
                    Date returnDate = java.sql.Date.valueOf(returnF.getText());
                    dbh.insertRecord(readerId,bookId,borrowDate,returnDate);
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"请按正确格式录入信息！\n日期格式为：XXXX-XX-XX","错误",JOptionPane.ERROR_MESSAGE);
                    dbh.close();
                    return;
                }catch (SQLException se){
                    JOptionPane.showMessageDialog(null,"请确认书籍id或读者id是否正确！","错误",JOptionPane.ERROR_MESSAGE);
                    dbh.close();
                    return;
                }
                try{
                    dbh.borrow(readerId,bookId);
                }catch (SQLException se){
                    JOptionPane.showMessageDialog(null,"无剩余书籍,仍然借出。","提示",JOptionPane.INFORMATION_MESSAGE);
                }
                JOptionPane.showMessageDialog(null,"录入记录成功！","提示",JOptionPane.INFORMATION_MESSAGE);
                dbh.close();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
