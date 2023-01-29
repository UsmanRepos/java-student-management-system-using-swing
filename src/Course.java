

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Usman_Aslam
 */
public class Course {
    Connection conn;
    Statement st; 
    PreparedStatement pst;
    ResultSet rs;
    
    MyConnection myCon;
    Course()
    {
       conn = MyConnection.getConnection();
        try {
            st = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertCour(String c,String t, String ty, Integer hrs)
    {
        try
        {
            pst = conn.prepareStatement("insert into Course (Code,Title,Type,Hours) Values(?,?,?,?)");
            pst.setString(1, c);
            pst.setString(2, t);
            pst.setString(3, ty);
            pst.setInt(4, hrs);
            
            if(pst.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null,"Successfully Add New Course.");
            
        } 
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void updateCour(String c,String t, String ty, Integer hrs)
    {
        try
        {
            pst = conn.prepareStatement("update Course Set  Title = ?,Type = ?, Hours = ? Where code = ?");
            pst.setString(1, t);
            pst.setString(2, ty);
            pst.setInt(3, hrs);
            pst.setString(4, c);
            
            if(pst.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null,"Successfully Update The Course.");
            
        }        
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     public void deleteCour(String c)
     {
         try
        {
            pst = conn.prepareStatement("Delete From Course Where Code = ?");
            pst.setString(1,c);
            
            if(pst.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null,"Successfully Delete the Record.");
        }
        catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    public void fillTable(JTable table, String vs)
    {
        try
        {
            table.setModel(new DefaultTableModel(null,new Object[]{"Code","Title","Type","CreditHours"}));
            st = conn.createStatement();
            String qurrey = "select * from Course where concat(Code,Title,Type,Hours) Like ?";
            pst = conn.prepareStatement(qurrey);
            pst.setString(1, "%" + vs + "%");
            rs = pst.executeQuery();
            
            DefaultTableModel model =  (DefaultTableModel)table.getModel();
            String[] row;
            while(rs.next())
            {
                row = new String[7];
                row[0] = rs.getString("Code");
                row[1] = rs.getString("Title");
                row[2] = rs.getString("Type");
                row[3] = rs.getString("Hours");

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
