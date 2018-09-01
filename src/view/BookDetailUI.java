package view;

import dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookDetailUI extends JFrame {
    private JLabel nameLabel        = new JLabel("书名:");
    private JLabel authorLabel      = new JLabel("作者:");
    private JLabel publisherLabel   = new JLabel("出版社:");
    private JLabel reserveLabel     = new JLabel("库存:");
    private JLabel descriptionLabel = new JLabel("描述:");
    private JTextField nameF        = new JTextField(10);
    private JTextField authorF      = new JTextField(10);
    private JTextField publisherF   = new JTextField(10);
    private JTextField reserveF     = new JTextField(3);
    private JTextArea  descriptionArea = new JTextArea(5,25);
    private JButton newButton       = new JButton("新建");
    private JButton backButton      = new JButton("返回");
    private JPanel jp2              = new JPanel();
    private JPanel jp3              = new JPanel();
    private JPanel jp4              = new JPanel();
    private JPanel jp5              = new JPanel();

    public BookDetailUI(){
        this.setTitle("book");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addComponent();
        addEvent();

        pack();
        this.setVisible(true);
    }

    public void addComponent(){
        this.setLayout(new GridLayout(4,1));

        jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp2.add(nameLabel);
        jp2.add(nameF);
        jp2.add(authorLabel);
        jp2.add(authorF);

        jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp3.add(publisherLabel);
        jp3.add(publisherF);
        jp3.add(reserveLabel);
        jp3.add(reserveF);

        jp4.setLayout(new FlowLayout(FlowLayout.LEFT));
        descriptionArea.setLineWrap(true);
        jp4.add(descriptionLabel);
        jp4.add(new JScrollPane(descriptionArea));

        jp5.add(newButton);
        jp5.add(backButton);

        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
    }

    public void addEvent(){

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHelper dbh = new DBHelper();
                try {
                    String name = nameF.getText();
                    String author = authorF.getText();
                    String pub = publisherF.getText();
                    int reserve = Integer.valueOf(reserveF.getText());
                    String descp = descriptionArea.getText();
                    dbh.insertBook(name,author,pub,reserve,descp);
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null,"请按正确格式录入信息！","错误",JOptionPane.ERROR_MESSAGE);
                    dbh.close();
                    return;
                }
                JOptionPane.showMessageDialog(null,"录入书籍成功！","提示",JOptionPane.INFORMATION_MESSAGE);
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
