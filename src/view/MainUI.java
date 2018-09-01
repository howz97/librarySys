package view;

import dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainUI extends JFrame{

    private JTextField searchTxt    = new JTextField(10);
    private JButton searchButton    = new JButton("搜索");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton book       = new JRadioButton("书籍",true);
    private JRadioButton reader     = new JRadioButton("读者",false);
    private JRadioButton record     = new JRadioButton("借书记录",false);

    private JButton addButton       = new JButton("添加");
    private JPanel jp1 = new JPanel();
    private JPanel jp2 = new JPanel();
    private JPanel jp4 = new JPanel();

    public MainUI(){
        this.setTitle("查询界面");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponent();
        addEvent();

        this.setVisible(true);
    }
    public void addComponent(){
        this.setLayout(new GridLayout(4, 1));
        jp1.add(searchTxt);
        jp1.add(searchButton);
        buttonGroup.add(book);
        buttonGroup.add(reader);
        buttonGroup.add(record);
        jp2.add(book);
        jp2.add(reader);
        jp2.add(record);

        jp4.add(addButton);
        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
    }
    public void addEvent(){
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHelper dbh = new DBHelper();
                if (book.isSelected()){
                    try {
                        String query = searchTxt.getText();
                        ResultSet rs;

                        if (query.isEmpty()){
                            rs = dbh.searchAllBook();
                        }else {
                            try{
                                int id = Integer.parseInt(query);
                                rs = dbh.searchBookId(id);
                            }catch (NumberFormatException nfe){
                                rs = dbh.searchBookName(query);
                            }
                        }

                        Object[] columnNames = {"编号", "书名", "作者", "出版社", "剩余","描述"};
                        rs.last();
                        Object[][] rowData = new Object[rs.getRow()][6];
                        rs.beforeFirst();
                        while (rs.next()){
                            rowData[rs.getRow()-1][0] = rs.getInt("id");
                            rowData[rs.getRow()-1][1] = rs.getString("name");
                            rowData[rs.getRow()-1][2] = rs.getString("author");
                            rowData[rs.getRow()-1][3] = rs.getString("publisher");
                            rowData[rs.getRow()-1][4] = rs.getInt("reserve");
                            rowData[rs.getRow()-1][5] = rs.getString("description");
                        }
                        JTable table = new JTable(rowData,columnNames);
                        new Query(Query.kindBook,table);
                    }catch (SQLException se){
                        se.printStackTrace();
                    }
                }else if (reader.isSelected()){
                    try {
                        String query = searchTxt.getText();
                        ResultSet rs;

                        if (query.isEmpty()){
                            rs = dbh.searchAllReader();
                        }else {
                            try{
                                int id = Integer.parseInt(query);
                                rs = dbh.searchReaderId(id);
                            }catch (NumberFormatException nfe){
                                rs = dbh.searchReaderName(query);
                            }
                        }

                        Object[] columnNames = {"编号", "姓名", "已借数量", "电话"};
                        rs.last();
                        Object[][] rowData = new Object[rs.getRow()][4];
                        rs.beforeFirst();
                        while (rs.next()){
                            rowData[rs.getRow()-1][0] = rs.getInt("id");
                            rowData[rs.getRow()-1][1] = rs.getString("name");
                            rowData[rs.getRow()-1][2] = rs.getInt("borrowNum");
                            rowData[rs.getRow()-1][3] = rs.getInt("phone");
                        }
                        JTable table = new JTable(rowData,columnNames);
                        new Query(Query.kindReader,table);
                    }catch (SQLException se){
                        se.printStackTrace();
                    }
                }else {
                    ResultSet rs;
                    String query = searchTxt.getText();
                    if (query.isEmpty()){
                        rs = dbh.searchAllRecord();
                    }else{
                        try{
                            int id = Integer.parseInt(query);
                            rs = dbh.searchRecordByBookId(id);
                        }catch (NumberFormatException nfe){
                            java.sql.Date date = java.sql.Date.valueOf(query); //todo: IllegalArgumentException
                            rs = dbh.searchRecordFrom(date);
                        }
                    }

                    try {
                        Object[] columnNames = {"记录编号","读者编号", "书籍编号", "借书日期", "归还日期"};
                        rs.last();
                        Object[][] rowData = new Object[rs.getRow()][5];
                        rs.beforeFirst();
                        while (rs.next()){
                            rowData[rs.getRow()-1][0] = rs.getInt("id");
                            rowData[rs.getRow()-1][1] = rs.getInt("readerId");
                            rowData[rs.getRow()-1][2] = rs.getInt("bookId");
                            rowData[rs.getRow()-1][3] = rs.getDate("borrowDate");
                            rowData[rs.getRow()-1][4] = rs.getDate("returnDate");
                        }
                        JTable table = new JTable(rowData,columnNames);
                        new Query(Query.kindRecord,table);
                    }catch (SQLException se){
                        se.printStackTrace();
                    }
                }
            }
        });

       addButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (book.isSelected()){
                   new BookDetailUI();
               }else if (reader.isSelected()){
                   new ReaderDetailUI();
               }else {
                   new RecordDetailUI();
               }
           }
       });
    }
}