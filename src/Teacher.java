
import java.sql.Connection;
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
public class Teacher {
    Connection conn;
    Statement st; 
    PreparedStatement pst;
    ResultSet rs;
    
    MyConnection myCon;
    Teacher()
    {
       conn = MyConnection.getConnection();
    }
    
    public void insertStu(String fname,String lname,String desig,String speciality, String sex, String cnic, String phNo, String add)
    {
        try
        {
            pst = conn.prepareStatement("insert into Teacher (First_Name,Last_Name,Desigination,Speciality,Sex,Cnic,"
                                        + "PhoneNo, Address) Values(?,?,?,?,?,?,?,?)");
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, desig);
            pst.setString(4, speciality);
            pst.setString(5, sex);
            pst.setString(6, phNo);
            pst.setString(7, cnic);
            pst.setString(8, add);
            
            if(pst.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null,"Successfully Add New Student.");
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateStu(String fname,String lname,String desig,String speciality,String sex, String cnic, String phNo, String add, Integer id)
    {
        try
        {
            pst = conn.prepareStatement("UPDATE Teacher SET First_Name = ?, Last_Name = ?, Desigination = ?,"
                    + " Speciality = ?,Sex = ?,Cnic = ?, PhoneNo = ?, Address = ? WHERE ID = ?");
            pst.setString(1, fname);
            pst.setString(2, lname);
            pst.setString(3, desig);
            pst.setString(4, speciality);
            pst.setString(5, sex);
            pst.setString(6, phNo);
            pst.setString(7, cnic);
            pst.setString(8, add);
            pst.setInt(9,id);
            
            if(pst.executeUpdate() > 0)
                JOptionPane.showMessageDialog(null,"Successfully Update the Data of Student.");
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteStu(Integer id)
    {
        try
        {
            pst = conn.prepareStatement("Delete From Teacher Where ID = ?");
            pst.setInt(1,id);
            
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
            table.setModel(new DefaultTableModel(null,new Object[]{"ID","First_Name","Last_Name","Speciality","Desigination","Sex","Cnic","PhoneNo","Address"}));
            st = conn.createStatement();
            //String qurrey = "select * from Student";
            pst = conn.prepareStatement("select * from Teacher where concat (First_Name, Last_Name,Desigination,Speciality,Sex, Cnic,PhoneNo,Address) Like ?");
            pst.setString(1,"%" + vs + "%");
            rs = pst.executeQuery();
            
            DefaultTableModel model =  (DefaultTableModel)table.getModel();
            String[] row;
            while(rs.next())
            {
                row = new String[9];
                row[0] = rs.getString("ID");
                row[1] = rs.getString("First_Name");
                row[2] = rs.getString("Last_Name");
                row[3] = rs.getString("Desigination");
                row[4] = rs.getString("Speciality");
                row[5] = rs.getString("Sex");
                row[6] = rs.getString("Cnic");
                row[7] = rs.getString("PhoneNo");
                row[8] = rs.getString("Address");
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
