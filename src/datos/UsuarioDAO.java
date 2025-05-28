package datos;

import negocio.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String INSERTAR = "INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)";
    private static final String SELECCIONAR_POR_EMAIL = "SELECT * FROM usuarios WHERE email = ?";
    private static final String ACTUALIZAR = "UPDATE usuarios SET nombre = ?, telefono = ? WHERE email = ?";
    private static final String ELIMINAR = "DELETE FROM usuarios WHERE email = ?";
    private static final String LISTAR = "SELECT * FROM usuarios";

    //  Insertar usuario en la BD
    public boolean insertar(Usuario usuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(INSERTAR)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Buscar usuario por email
    public Usuario buscarPorEmail(String email) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECCIONAR_POR_EMAIL)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  Actualizar usuario
    public boolean actualizar(Usuario usuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getTelefono());
            stmt.setString(3, usuario.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Eliminar usuario
    public boolean eliminar(String email) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(ELIMINAR)) {

            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Listar todos los usuarios
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(LISTAR);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"), rs.getString("telefono")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
