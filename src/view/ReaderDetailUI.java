package view;

import dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ReaderDetailUI extends JFrame{
    private JLabel nameLabel        = new JLabel("名字:");
    private JLabel phoneLabel       = new JLabel("电话:");
    private JTextField nameF        = new JTextField(10);
    private JTextField phoneF       = new JTextField(10);
    private JButton newButton       = new JButton("新建");
    private JButton backButton      = new JButton("返回");
    private JPanel jp1              = new JPanel();
    private JPanel jp1_1            = new JPanel();
    private JPanel jp2              = new JPanel();

    public ReaderDetailUI(){
        this.setTitle("Reader");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addComponent();
        addEvent();

        pack();
        this.setVisible(true);
    }

    public void addComponent(){
        this.setLayout(new GridLayout(3,1));

        jp1.add(nameLabel);
        jp1.add(nameF);
        jp1.add(phoneLabel);
        jp1.add(phoneF);

        jp2.add(newButton);
        jp2.add(backButton);

        this.add(jp1);
        this.add(jp1_1);
        this.add(jp2);
    }

    public void addEvent(){
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHelper dbh = new DBHelper();
                try{
                    String name = nameF.getText();
                    int phone = Integer.valueOf(phoneF.getText());
                    dbh.insertReader(name,phone);
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"请按正确格式录入信息！","错误",JOptionPane.ERROR_MESSAGE);
                    dbh.close();
                    return;
                }catch (SQLException se){
                    JOptionPane.showMessageDialog(null,"请检查电话号码！","错误",JOptionPane.ERROR_MESSAGE);
                    dbh.close();
                    return;
                }
                JOptionPane.showMessageDialog(null,"录入读者成功！","提示",JOptionPane.INFORMATION_MESSAGE);
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
