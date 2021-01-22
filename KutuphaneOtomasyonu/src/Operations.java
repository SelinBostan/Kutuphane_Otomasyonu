
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Operations {
    Connection con=null;
    Statement sta =null;
    PreparedStatement psta=null;
    
    
    
     public void bookDelete(int id){
         String sorgu="DELETE FROM `kitaplar_veritabanı` WHERE id=?";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setInt(1, id);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
    public int bookCount(){
        int label=0;
        String sorgu="SELECT COUNT(*) FROM kitaplar_veritabanı";
        
        try {
            sta=con.createStatement();
            ResultSet rs=sta.executeQuery(sorgu);
            rs.next();
            label=rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return label;
    }
    
    public void bookUpdate (int id, String new_name, String new_writer, String new_type, String new_publisher){
        String sorgu = "Update kitaplar_veritabanı SET book_name=?,book_writer=?,book_type=?,book_publisher=? WHERE id=? ";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1, new_name);
            psta.setString(2, new_writer);
            psta.setString(3, new_type);
            psta.setString(4, new_publisher);
            psta.setInt(5, id);
            psta.executeUpdate();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void bookAdd(String name, String writer, String type, String publisher){
        String sorgu="Insert into kitaplar_veritabanı(book_name, book_writer, book_type, book_publisher) VALUES (?,?,?,?)";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1, name);
            psta.setString(2, writer);
            psta.setString(3, type);
            psta.setString(4, publisher);
            psta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public ArrayList<Book> BookCome(){
        ArrayList<Book> list=new ArrayList<>();
        String sorgu="Select * from kitaplar_veritabanı";
        
        try {
            sta=con.createStatement();
            ResultSet rs=sta.executeQuery(sorgu);
            
            while(rs.next()) {
                int id=rs.getInt("id");
                String book_name=rs.getString("book_name");
                String book_writer=rs.getString("book_writer");
                String book_type=rs.getString("book_type");
                String book_publisher=rs.getString("book_publisher");
                list.add(new Book(id, book_name, book_writer, book_type, book_publisher));
            }
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Login(String id, String password){
        String sorgu= "Select * from admin where id=? and password=?";
        try {
            psta=con.prepareStatement(sorgu);
            psta.setString(1, id);
            psta.setString(2, password); 
            ResultSet rs=psta.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public Operations() {
        
        String url="jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection(url,Database.id,Database.password);
            System.out.println("Veritabanı başarıyla bağlandı. ");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalışmadı :/");
        } catch (SQLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection yapılamadı");
        }
        
    }
    
    public static void main(String[] args) {
        Operations op=new Operations();
    }
    
    
    
}
