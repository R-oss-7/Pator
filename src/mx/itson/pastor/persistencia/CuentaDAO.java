
package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;
import mx.itson.pastor.entidades.Cuenta;

/**
 *
 * @author ramon
 */

public class CuentaDAO {
    
    public static boolean agregar(String numero, Cliente cliente) {
        boolean resultado = false;
        
        try {
            Connection connection = Conexion.obtener();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO cuenta (numero, idCliente) VALUES (?,?)");
            PreparedStatement st = connection.prepareCall(sql.toString());
            st.setString(1, numero);
            st.setInt(2, cliente.getId());
            resultado = st.execute();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultado;
    }
    
    public static List<Cuenta> obtenerTodos() {
        List<Cuenta> cuentas = new ArrayList<>();
        
        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT cu.id, cu.numero, cl.id, cl.nombre, cl.direccion, cl.telefono, cl.email FROM cuenta cu INNER JOIN cliente cl ON cu.idCliente = cl.id");
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setId(resultSet.getInt(1));
                cuenta.setNumero(resultSet.getString(2));
                
                Cliente c = new Cliente();
                c.setId(resultSet.getInt(3));
                c.setNombre(resultSet.getString(4));
                c.setDireccion(resultSet.getString(5));
                c.setTelefono(resultSet.getString(6));
                c.setEmail(resultSet.getString(7));
                
                cuenta.setCliente(c);
                
                cuentas.add(cuenta);
            }
        } catch (Exception ex) {
            System.err.print("Ocurrió un error: " + ex.getMessage());
        }
        return cuentas;
    }
    
    public static List<Cuenta> obtenerCuenta() {
        List<Cuenta> listadoCuentas = new ArrayList();
        
        try {
            Connection connexion = Conexion.obtener();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT numero, nombre, direccion, telefono, email FROM pastordb1.cuenta INNER JOIN cliente ON cuenta.idCliente=cliente.id");
            PreparedStatement st = connexion.prepareStatement(sql.toString());
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next());
            Cuenta c = new Cuenta();
            Cliente cliente = ClienteDAO.obtenerCliente(resultSet.getString("nombre"));
            c.setCliente(cliente);
            c.setNumero(resultSet.getString("numero"));
            
            listadoCuentas.add(c);
            
        } catch (Exception e) {
            System.err.println(e);
        }
        return listadoCuentas;
    }
    
    public static Cuenta ObtenerPorNumero(String numero) {
        Cuenta cuenta = new Cuenta();
        
        try {
            Connection con = Conexion.obtener();
            String consulta = "SELECT * FROM cuenta Where numero=? ";
            PreparedStatement st = con.prepareStatement(consulta);
            st.setString(1, numero);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                cuenta.setId(rs.getInt("id"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("idCliente"));
                cuenta.setCliente(cliente);
                cuenta.setNumero(rs.getString("numero"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cuenta;
    }
    
}
