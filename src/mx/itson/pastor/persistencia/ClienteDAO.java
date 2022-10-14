
package mx.itson.pastor.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.pastor.entidades.Cliente;

/**
 *
 * @author ramon
 */

public class ClienteDAO {

    public static List<Cliente> obtenerTodos() {
        List<Cliente> cliente = new ArrayList<>();

        try {
            Connection connection = Conexion.obtener();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, nombre, direccion, telefono, email FROM cliente");
            while (resultSet.next()) {
                Cliente c = new Cliente();
                c.setId(resultSet.getInt(1));
                c.setNombre(resultSet.getString(2));
                c.setDireccion(resultSet.getString(3));
                c.setTelefono(resultSet.getString(4));
                c.setEmail(resultSet.getString(5));
                cliente.add(c);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrio un error " + ex.getMessage());
        }
        return cliente;

    }

    public static boolean guardar(String nombre, String direccion, String telefono, String email) {
        boolean resultado = false;

        try {
            Connection connection = Conexion.obtener();
            String consulta = "INSERT INTO cliente (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, email);
            statement.execute();

            resultado = statement.getUpdateCount() == 1;
        } catch (Exception ex) {
            System.err.println("Ocurrio un error " + ex.getMessage());
        }
        return resultado;
    }

    public static boolean validarCorreo(String email) {

        boolean resultado = false;

        try {
            Connection cone = Conexion.obtener();
            String consulta = "Select email FROM cliente WHERE email = ?";
            PreparedStatement statemnet = cone.prepareStatement(consulta);
            statemnet.setString(1, email);
            ResultSet rs = statemnet.executeQuery();
            resultado = rs.next();
        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e.getMessage());
        }
        return resultado;



    }

public static List<String> nombreCliente() {
        List<String> listadoCliente = new ArrayList();

        try {
            Connection con = Conexion.obtener();
            String consulta = "SELECT nombre FROM cliente WHERE id NOT IN (SELECT idCliente FROM cuenta) ";
            PreparedStatement st = con.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String nombre = new String();
                nombre = rs.getString("nombre");
                listadoCliente.add(nombre);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return listadoCliente;
    }

public static Cliente obtenerCliente(String busqueda) {
        Cliente cliente = new Cliente();
        
        try {
            Connection con = Conexion.obtener();
            String consulta = "SELECT * FROM cliente WHERE id=? OR nombre=? OR direccion=? OR telefono=? OR email=?";
            PreparedStatement st = con.prepareStatement(consulta);
            st.setString(1, busqueda);
            st.setString(2, busqueda);
            st.setString(3, busqueda);
            st.setString(4, busqueda);
            st.setString(5, busqueda);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNombre(rs.getString(2));
                cliente.setDireccion(rs.getString(3));
                cliente.setTelefono(rs.getString(4));
                cliente.setEmail(rs.getString(5));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return cliente;
    }

}
