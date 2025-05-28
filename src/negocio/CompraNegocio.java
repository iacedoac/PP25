package negocio;

import java.util.Date;
import java.util.List;
import datos.CompraDAO;

public class CompraNegocio {
    private CompraDAO compraDAO;

    public CompraNegocio() {
        compraDAO = new CompraDAO();
    }

    public void registrarCompra(int usuarioId, int libroId) {
        Compra compra = new Compra(usuarioId, libroId, new Date());
        compraDAO.registrarCompra(compra);
    }

    public List<Compra> obtenerHistorial(int usuarioId) {
        return compraDAO.obtenerComprasPorUsuario(usuarioId);
    }
}
