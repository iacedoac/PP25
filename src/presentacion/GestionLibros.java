package presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import negocio.LibroNegocio;
import negocio.Libro;

public class GestionLibros extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtTitulo, txtAutor, txtISBN;
    private JButton btnAgregar, btnBuscar, btnEditar, btnEliminar;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private LibroNegocio libroNegocio;

    public GestionLibros() {
        libroNegocio = new LibroNegocio();
        setTitle("Gestión de Libros");
        setSize(600, 500);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setLayout(new BorderLayout(10, 10)); // Margen entre componentes

        //  Panel superior con formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Información del Libro"));

        JLabel label = new JLabel("Título:");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        panelFormulario.add(label);
        txtTitulo = new JTextField();
        txtTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
        panelFormulario.add(txtTitulo);

        JLabel label_1 = new JLabel("Autor:");
        label_1.setHorizontalAlignment(SwingConstants.LEFT);
        panelFormulario.add(label_1);
        txtAutor = new JTextField();
        txtAutor.setHorizontalAlignment(SwingConstants.RIGHT);
        panelFormulario.add(txtAutor);

        JLabel label_2 = new JLabel("ISBN:");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        panelFormulario.add(label_2);
        txtISBN = new JTextField();
        txtISBN.setHorizontalAlignment(SwingConstants.RIGHT);
        panelFormulario.add(txtISBN);

        //  Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnBuscar = new JButton("Buscar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        //  Crear modelo de tabla con columnas dinámicas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN"}, 0);
        tablaLibros = new JTable(modeloTabla);
        scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setPreferredSize(new Dimension(580, 200)); // Tamaño de la tabla

        //  Panel central con la tabla debajo de los botones
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER); // La tabla se coloca debajo

        //  Agregar componentes a la ventana
        getContentPane().add(panelFormulario, BorderLayout.NORTH);
        getContentPane().add(panelCentral, BorderLayout.CENTER);

        //  Eventos de botones
        btnAgregar.addActionListener(e -> agregarLibro());
        btnBuscar.addActionListener(e -> buscarLibro());
        btnEditar.addActionListener(e -> editarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());

        //  Cargar libros en la tabla al abrir la ventana
        cargarLibrosEnTabla();
    }

    //  Método para agregar un libro
    private void agregarLibro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String isbn = txtISBN.getText();

        if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Libro libro = new Libro(0, titulo, autor, isbn);
        boolean agregado = libroNegocio.agregarLibro(libro);

        if (agregado) {
            JOptionPane.showMessageDialog(this, "Libro agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarLibrosEnTabla(); //  Actualiza la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para buscar un libro por ISBN
    private void buscarLibro() {
        String isbn = txtISBN.getText();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ISBN para buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Libro libro = libroNegocio.buscarPorISBN(isbn);
        if (libro != null) {
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            JOptionPane.showMessageDialog(this, "Libro encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Libro no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para editar un libro
    private void editarLibro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String isbn = txtISBN.getText();

        if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Libro libro = new Libro(0, titulo, autor, isbn);
        boolean editado = libroNegocio.editarLibro(libro);

        if (editado) {
            JOptionPane.showMessageDialog(this, "Libro editado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarLibrosEnTabla(); //  Actualiza la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar el libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para eliminar un libro por ISBN
    private void eliminarLibro() {
        String isbn = txtISBN.getText();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ISBN para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean eliminado = libroNegocio.eliminarLibro(isbn);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Libro eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarLibrosEnTabla(); //  Actualiza la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para limpiar los campos
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtISBN.setText("");
    }

    //  Método para cargar los libros en la tabla
    private void cargarLibrosEnTabla() {
        modeloTabla.setRowCount(0); // Borra filas anteriores
        List<Libro> libros = libroNegocio.listarLibros();
        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getIsbn()});
        }
    }
}
