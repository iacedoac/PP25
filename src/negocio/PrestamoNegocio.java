package negocio;

import datos.PrestamoDAO;
import java.util.Date;
import java.util.List;

public class PrestamoNegocio {
    private PrestamoDAO prestamoDAO;

    public PrestamoNegocio() {
        prestamoDAO = new PrestamoDAO();
    }

    public boolean registrarPrestamo(int idUsuario, int idLibro, Date fechaPrestamo, Date fechaDevolucion) {
        Prestamo prestamo = new Prestamo(0, idUsuario, idLibro, fechaPrestamo, fechaDevolucion, "PENDIENTE");
        return prestamoDAO.insertar(prestamo);
    }

    public List<Prestamo> obtenerPrestamosPorUsuario(int idUsuario) {
        return prestamoDAO.listarPorUsuario(idUsuario);
    }

    public boolean devolverLibro(int idPrestamo) {
        return prestamoDAO.actualizarEstado(idPrestamo);
    }

    public List<Prestamo> obtenerTodosLosPrestamos() {
        return prestamoDAO.listar(); // 🔹 Uso del método "listar" ya existente
    }
}

