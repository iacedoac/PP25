package presentacion;

import negocio.CompraNegocio;
import negocio.Libro;
import negocio.LibroNegocio;
import negocio.Carrito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class GestionCompras extends JInternalFrame {
    private JTable tablaLibros;
    private JButton btnComprar;
    private DefaultTableModel modeloTabla;
    private LibroNegocio libroNegocio;
    private CompraNegocio compraNegocio;
    private JComboBox<String> comboUsuarios;
    private List<negocio.Usuario> listaUsuarios;
    private Carrito carrito;
    private JButton btnAgregarCarrito;

    public GestionCompras() {
        setTitle("Compra de Libros");
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        libroNegocio = new LibroNegocio();
        listaUsuarios = new negocio.UsuarioNegocio().listarUsuarios();
        comboUsuarios = new JComboBox<>();
        compraNegocio = new CompraNegocio();
        carrito = new Carrito();
for (negocio.Usuario u : listaUsuarios) {
    comboUsuarios.addItem(u.getId() + " - " + u.getNombre());
}
        compraNegocio = new CompraNegocio();

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "ISBN"}, 0);
        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        btnAgregarCarrito = new JButton("Agregar al carrito");
        btnAgregarCarrito.addActionListener(e -> agregarAlCarrito());
        JButton btnVerCarrito = new JButton("Ver carrito");
        btnVerCarrito.addActionListener(e -> mostrarCarrito());
        cargarLibros();
        btnComprar = new JButton("Comprar libro seleccionado");
        btnComprar.addActionListener(e -> realizarCompra());

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregarCarrito);
        panelBotones.add(btnVerCarrito);
        

        // Panel inferior (combo usuarios + botones)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(comboUsuarios, BorderLayout.NORTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
        panelInferior.add(panelBotones, BorderLayout.CENTER);
    }

    private void cargarLibros() {
        modeloTabla.setRowCount(0);
        List<Libro> libros = libroNegocio.obtenerTodos();
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn()
            });
        }
    }

    private void realizarCompra() {
        int fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
            int libroId = (int) modeloTabla.getValueAt(fila, 0);
int index = comboUsuarios.getSelectedIndex();
int usuarioId = listaUsuarios.get(index).getId();
compraNegocio.registrarCompra(usuarioId, libroId);
            JOptionPane.showMessageDialog(this, "Compra registrada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para comprar.");
        }
    }
    private void agregarAlCarrito() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada != -1) {
            int libroId = (int) tablaLibros.getValueAt(filaSeleccionada, 0);
            String titulo = (String) tablaLibros.getValueAt(filaSeleccionada, 1);
            String autor = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
            String isbn = (String) tablaLibros.getValueAt(filaSeleccionada, 3);
            Libro libro = new Libro(libroId, titulo, autor, isbn);
            carrito.agregarLibro(libro);
            JOptionPane.showMessageDialog(this, "Libro agregado al carrito.");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para agregar al carrito.");
        }
    }
    private void mostrarCarrito() {
        List<Libro> librosCarrito = carrito.getLibros();
        if (librosCarrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        StringBuilder mensaje = new StringBuilder("Libros en el carrito:\n\n");
        for (Libro libro : librosCarrito) {
            mensaje.append("• ").append(libro.getTitulo())
                   .append(" - ").append(libro.getAutor())
                   .append("\n");
        }

        JOptionPane.showMessageDialog(this, mensaje.toString(), "Carrito", JOptionPane.INFORMATION_MESSAGE);
    }
    
}

