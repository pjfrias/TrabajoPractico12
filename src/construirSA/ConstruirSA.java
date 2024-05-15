
package construirSA;
 
import java.io.File;
import java.sql.*;
import javax.swing.JOptionPane;
// para el correcto funcionamiento de la siguiente linea se debe bajar la libreria desde https://ini4j.sourceforge.net/
import org.ini4j.Ini;

public class ConstruirSA {
    static Connection conexion = null;
    
    public static void main(String[] args) {
        
        conexion("./conexion.ini");
        
        ingresarEmpleados();
        
        ingresarHerramientas();
        
        listarHerramientas();
        
        bajaEmpleado();
    }
    
    public static void conexion(String archivoIni){
        String driver="";
        String db="";
        String user="";
        String pass="";
        //Abrimos conexion.ini para leer los datos de la conexion
        try {
            File archivo = new File(archivoIni);
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
    }
    
    public static void ingresarEmpleados(){
        try {

            String sql="INSERT INTO empleado(dni, nombre, apellido, acceso, activo) "
                    + "VALUES (34876444,'Nestor','Becerra',1,1),"
                    + "(24877444,'Mario','Kempes',2,0),"
                    + "(18900435,'Dario','Alcaraz',3,1);";
            
             PreparedStatement ps=conexion.prepareStatement(sql);
             int filas=ps.executeUpdate();
             
             if(filas == 3){
                JOptionPane.showMessageDialog(null, "Empleados Agregados");
             }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ");
            e.printStackTrace();
        }
    }
    
    public static void ingresarHerramientas(){
        try {
  
            String sql="INSERT INTO herramienta(nombre, descripcion, stock, estado) "
                    + "VALUES ('Malacate','Hata 5 toneladas',4,1),"
                    + "('Prensa','Prensa de mesa hidraulica 2 tons',1,0);";
            
             PreparedStatement ps=conexion.prepareStatement(sql);
             int filas=ps.executeUpdate();
             
             if(filas == 2){
                JOptionPane.showMessageDialog(null, "Herrramientas Agregadas");
             }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ");
            e.printStackTrace();
        }
    }
    
    public static void listarHerramientas(){
        try {
          
            String sql="select nombre, descripcion from herramienta where stock > 4;";
            
             PreparedStatement ps=conexion.prepareStatement(sql);
             ResultSet filas=ps.executeQuery();
             
             System.out.println("\n========= Herramientas con stock mayor a 10 unidades ========");
             while(filas.next()){
                 System.out.println(filas.getString("nombre")+" -- "+filas.getString("descripcion"));
             }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ");
            e.printStackTrace();
        }
    }
    
    public static void bajaEmpleado(){
        try {

            String sql="update empleado set activo = false where id_empleado = 1;";
            
             PreparedStatement ps=conexion.prepareStatement(sql);
             int result=ps.executeUpdate();
             
             if(result == 1){
                 System.out.println("\nEl empleado ahora esta inactivo");
             }
                 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ");
            e.printStackTrace();
        }
    }
}