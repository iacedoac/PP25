package negocio;

import java.util.Date;

public class Compra {
    private int usuarioId;
    private int libroId;
    private Date fecha;

    public Compra(int usuarioId, int libroId, Date fecha) {
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.fecha = fecha;
    }

    public int getUsuarioId() { return usuarioId; }
    public int getLibroId() { return libroId; }
    public Date getFecha() { return fecha; }
}
