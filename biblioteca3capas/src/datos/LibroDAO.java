package datos;

import negocio.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private static final String INSERTAR = "INSERT INTO libros (titulo, autor, isbn) VALUES (?, ?, ?)";
    private static final String SELECCIONAR_POR_ISBN = "SELECT * FROM libros WHERE isbn = ?";
    private static final String ACTUALIZAR = "UPDATE libros SET titulo = ?, autor = ? WHERE isbn = ?";
    private static final String ELIMINAR = "DELETE FROM libros WHERE isbn = ?";
    private static final String LISTAR = "SELECT * FROM libros";

    //  Insertar un nuevo libro en la base de datos
    public boolean insertar(Libro libro) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(INSERTAR)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Buscar un libro por ISBN
    public Libro buscarPorISBN(String isbn) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECCIONAR_POR_ISBN)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No se encontró el libro
    }

    //  Actualizar un libro por ISBN
    public boolean actualizar(Libro libro) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(ACTUALIZAR)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getIsbn());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Eliminar un libro por ISBN
    public boolean eliminar(String isbn) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(ELIMINAR)) {

            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  Listar todos los libros
    public List<Libro> listar() {
        List<Libro> libros = new ArrayList<>();

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(LISTAR);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                libros.add(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("isbn")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}
