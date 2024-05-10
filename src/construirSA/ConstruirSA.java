/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package construirSA;
 
import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
import org.ini4j.Ini;

public class ConstruirSA {

    public static void main(String[] args) {
        String driver="";
        String db="";
        String user="";
        String pass="";
        //Abrimos conexion.ini para leer los datos de la conexion
        try {
            File archivo = new File("./conexion.ini");
            Ini ini = new Ini(archivo);
            // Leer una sección y una clave
            driver = ini.get("conexion", "driver");
            db = ini.get("conexion", "db");
            user = ini.get("conexion", "user");
            pass = ini.get("conexion", "pass");
        } catch (Exception e) {
            System.out.println("Driver = "+driver+" Base de datos = "+db+" Usuario = "+user+" Password = "+pass);
            System.out.println("Error de lectura .ini" + e);
        }
       
        //System.out.println("Driver = "+driver+" Base de datos = "+db+" Usuario = "+user+" Password = "+pass);
        
        Connection conexion = null;
        
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(db,user,pass);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargard driver"+ex);
        } catch (SQLException ex) {
            int codigoE=ex.getErrorCode();
            
            switch (codigoE) {
                case 1062:
                    JOptionPane.showMessageDialog(null, "Entrada Duplicada");
                    break;
                case 1049:
                    JOptionPane.showMessageDialog(null, "BD Desconocida");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error ");
                    break;
            }
            
            ex.printStackTrace();
            System.out.println("codigo de error "+ex.getErrorCode());
        }
        
        try {
            //Insertamos los 3 empleados
            String sql="INSERT INTO empleado(dni, nombre, apellido, acceso, activo) "
                    + "VALUES (34876444,'Nestor','Becerra',1,1),"
                    + "(24877444,'Mario','Kempes',2,0),"
                    + "(18900435,'Dario','Alcaraz',3,1);";
            
             PreparedStatement ps=conexion.prepareStatement(sql);
             int filas=ps.executeUpdate();
             
             if(filas >0){
                JOptionPane.showMessageDialog(null, "Empleado Agregado");
             }
        } catch (Exception e) {
        }
        
    }
}