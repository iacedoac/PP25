package datos;

import negocio.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private static final String INSERTAR = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECCIONAR_POR_USUARIO = "SELECT * FROM prestamos WHERE id_usuario = ?";
    private static final String ACTUALIZAR_ESTADO = "UPDATE prestamos SET estado = 'DEVUELTO' WHERE id = ?";
    private static final String LISTAR = "SELECT * FROM prestamos";

    public boolean insertar(Prestamo prestamo) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(INSERTAR)) {

            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
            stmt.setString(5, prestamo.getEstado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prestamo> listarPorUsuario(int idUsuario) {
        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECCIONAR_POR_USUARIO)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                prestamos.add(new Prestamo(rs.getInt("id"), rs.getInt("id_usuario"), rs.getInt("id_libro"),
                        rs.getDate("fecha_prestamo"), rs.getDate("fecha_devolucion"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public boolean actualizarEstado(int idPrestamo) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR_ESTADO)) {

            stmt.setInt(1, idPrestamo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 //  Método para listar todos los préstamos
    public List<Prestamo> listar() {
        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(LISTAR);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                prestamos.add(new Prestamo(rs.getInt("id"), rs.getInt("id_usuario"), rs.getInt("id_libro"),
                        rs.getDate("fecha_prestamo"), rs.getDate("fecha_devolucion"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
}
