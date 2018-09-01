package dao;

import java.sql.*;

public class DBHelper {
    public static final String url = "jdbc:mysql://127.0.0.1/librarySys?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String mysql = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "123456";

    public static Connection conn = null;
    private PreparedStatement psmt = null;

    public DBHelper() {
        try {
            Class.forName(mysql);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBook(String name,String author,String pub,int reser,String descp){
        String sql = "INSERT INTO book(name,author,publisher,reserve,description)" +
                     "VALUES (?,?,?,?,?)";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,name);
            psmt.setString(2,author);
            psmt.setString(3,pub);
            psmt.setInt(4,reser);
            psmt.setString(5,descp);
            psmt.executeUpdate();

        }catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                psmt.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    public void updateBook(int id,String name,String author,String pub,int reser,String descp)throws SQLException{
        String sql = "UPDATE book SET name=?,author=?,publisher=?,reserve=?,description=?" +
                     "WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,name);
            psmt.setString(2,author);
            psmt.setString(3,pub);
            psmt.setInt(4,reser);
            psmt.setString(5,descp);
            psmt.setInt(6,id);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

    public ResultSet searchBookName(String name){
        String sql = "SELECT * FROM book WHERE name LIKE ?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            name = "%"+name+"%";
            psmt.setString(1,name);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchBookId(int id){
        String sql = "SELECT * FROM book WHERE id=?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchAllBook(){
        String sql = "SELECT * FROM book";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public void deleteBook(int id)throws SQLException{
        String sql = "DELETE FROM book WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

    // -------------------------------------------------------------

    public void insertReader(String name,int phone)throws SQLException{
        String sql = "INSERT INTO reader(name,phone)" +
                     "VALUES (?,?)";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,name);
            psmt.setInt(2,phone);
            psmt.executeUpdate();

        }catch (SQLException se){
            throw se;
        }
    }

    public void updateReader(int id,String name,int phone)throws SQLException{
        String sql = "UPDATE reader SET name=?,phone=? WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1,name);
            psmt.setInt(2,phone);
            psmt.setInt(3,id);

            psmt.executeUpdate();

        }catch (SQLException se){
            throw se;
        }
    }

    public ResultSet searchReaderName(String name){
        String sql = "SELECT * FROM reader WHERE name LIKE ?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            name = "%"+name+"%";
            psmt.setString(1,name);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }finally {

        }
        return rs;
    }

    public ResultSet searchReaderId(int id){
        String sql = "SELECT * FROM reader WHERE id=?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }finally {

        }
        return rs;
    }

    public ResultSet searchAllReader(){
        String sql = "SELECT * FROM reader";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public void deleteReader(int id)throws SQLException{
        String sql = "DELETE FROM reader WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

    //---------------------------------------------------------------------------------

    public void insertRecord(int readerId, int bookId, Date borrowDate,Date returnDate)throws SQLException{
        String sql = "INSERT INTO record(readerId,bookId,borrowDate,returnDate)" +
                     "VALUES (?,?,?,?)";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,readerId);
            psmt.setInt(2,bookId);
            psmt.setDate(3,borrowDate);
            psmt.setDate(4,returnDate);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

    public void updateRecord(int id, int readerId, int bookId, Date borrowDate,Date returnDate)throws SQLException{
        String sql = "UPDATE record SET readerId=?,bookId=?,borrowDate=?,returnDate=?" +
                     "WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,readerId);
            psmt.setInt(2,bookId);
            psmt.setDate(3,borrowDate);
            psmt.setDate(4,returnDate);
            psmt.setInt(5,id);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

    public ResultSet searchRecordFrom(java.sql.Date date){
        String sql = "SELECT * FROM record WHERE borrowDate>=?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setDate(1,date);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchRecordByBookId(int id){
        String sql = "SELECT * FROM record WHERE bookId=?";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchAllRecord(){
        String sql = "SELECT * FROM record";
        ResultSet rs = null;
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return rs;
    }

    public void deleteRecord(int id)throws SQLException{
        String sql = "DELETE FROM record WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1,id);
            psmt.executeUpdate();
        }catch (SQLException se){
            throw se;
        }
    }

// ------------------------------------------------------------------------------------------

    public void borrow(int readerId,int bookId)throws SQLException {
        String sql = "UPDATE reader SET borrowNum=borrowNum+1 WHERE id=?";
        String sql2 = "UPDATE book SET reserve=reserve-1 WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, readerId);
            psmt.executeUpdate();
            psmt = conn.prepareStatement(sql2);
            psmt.setInt(1, bookId);
            psmt.executeUpdate();
        } catch (SQLException se) {
            throw se;
        }
    }

    public void returnBook(int readerId,int bookId)throws SQLException{
        String sql = "UPDATE reader SET borrowNum=borrowNum-1 WHERE id=?";
        String sql2 = "UPDATE book SET reserve=reserve+1 WHERE id=?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, readerId);
            psmt.executeUpdate();
            psmt = conn.prepareStatement(sql2);
            psmt.setInt(1, bookId);
            psmt.executeUpdate();
        } catch (SQLException se) {
            throw se;
        }
    }
}
