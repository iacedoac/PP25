package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import negocio.Compra;

public class CompraDAO {
    private Connection conexion;

    public CompraDAO() {
    	conexion = Conexion.conectar();
    }

    public void registrarCompra(Compra compra) {
        String sql = "INSERT INTO compras (usuario_id, libro_id, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, compra.getUsuarioId());
            stmt.setInt(2, compra.getLibroId());
            stmt.setDate(3, new java.sql.Date(compra.getFecha().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> obtenerComprasPorUsuario(int usuarioId) {
        List<Compra> lista = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Compra compra = new Compra(
                    rs.getInt("usuario_id"),
                    rs.getInt("libro_id"),
                    rs.getDate("fecha")
                );
                lista.add(compra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
