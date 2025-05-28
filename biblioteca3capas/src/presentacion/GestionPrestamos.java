package presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import negocio.Prestamo;
import negocio.PrestamoNegocio;
import negocio.UsuarioNegocio;
import negocio.LibroNegocio;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class GestionPrestamos extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtIdUsuario, txtIdLibro;
    private JButton btnPrestar, btnDevolver, btnBuscar, btnListarTodos;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private PrestamoNegocio prestamoNegocio;
    private UsuarioNegocio usuarioNegocio;
    private LibroNegocio libroNegocio;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;

    public GestionPrestamos() {
        initComponents();
    }

    private void initComponents() {
        prestamoNegocio = new PrestamoNegocio();
        usuarioNegocio = new UsuarioNegocio();
        libroNegocio = new LibroNegocio();

        setTitle("Gestión de Préstamos");
        setSize(600, 400);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Préstamo"));

        JLabel label = new JLabel("ID Usuario:");
        panelFormulario.add(label);
        txtIdUsuario = new JTextField();
        panelFormulario.add(txtIdUsuario);

        panelFormulario.add(new JLabel(""));
        
        lblNewLabel = new JLabel("ID Libro:");
        panelFormulario.add(lblNewLabel);
        txtIdLibro = new JTextField();
        panelFormulario.add(txtIdLibro);
        
        lblNewLabel_1 = new JLabel("");
        panelFormulario.add(lblNewLabel_1);

        btnPrestar = new JButton("Registrar Préstamo");
        panelFormulario.add(btnPrestar);

        btnBuscar = new JButton("Ver Préstamos");
        panelFormulario.add(btnBuscar);
        
        btnListarTodos = new JButton("Listar Todos los Préstamos");
        panelFormulario.add(btnListarTodos);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Usuario", "Libro", "Fecha Préstamo", "Fecha Devolución", "Estado"}, 0);
        tablaPrestamos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);

        btnDevolver = new JButton("Devolver Libro");

        getContentPane().add(panelFormulario, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(btnDevolver, BorderLayout.SOUTH);

        btnPrestar.addActionListener(e -> registrarPrestamo());
        btnBuscar.addActionListener(e -> cargarPrestamos());
        btnListarTodos.addActionListener(e -> cargarTodosLosPrestamos());
        btnDevolver.addActionListener(e -> devolverLibro());
    }

    private void registrarPrestamo() {
        try {
            int idUsuario = Integer.parseInt(txtIdUsuario.getText());
            int idLibro = Integer.parseInt(txtIdLibro.getText());

            if (usuarioNegocio.buscarUsuarioPorId(idUsuario) == null) {
                JOptionPane.showMessageDialog(this, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (libroNegocio.buscarLibroPorId(idLibro) == null) {
                JOptionPane.showMessageDialog(this, "El libro no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date fechaPrestamo = new Date();
            Date fechaDevolucion = new Date(fechaPrestamo.getTime() + (7L * 24 * 60 * 60 * 1000));
            boolean exito = prestamoNegocio.registrarPrestamo(idUsuario, idLibro, fechaPrestamo, fechaDevolucion);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Préstamo registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPrestamos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos para el ID de usuario y libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTodosLosPrestamos() {
        modeloTabla.setRowCount(0);
        List<Prestamo> prestamos = prestamoNegocio.obtenerTodosLosPrestamos();

        for (Prestamo prestamo : prestamos) {
            modeloTabla.addRow(new Object[]{prestamo.getId(), prestamo.getIdUsuario(), prestamo.getIdLibro(),
                    prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getEstado()});
        }
    }

    private void cargarPrestamos() {
        modeloTabla.setRowCount(0);
        try {
            int idUsuario = Integer.parseInt(txtIdUsuario.getText());
            List<Prestamo> prestamos = prestamoNegocio.obtenerPrestamosPorUsuario(idUsuario);

            for (Prestamo prestamo : prestamos) {
                modeloTabla.addRow(new Object[]{prestamo.getId(), prestamo.getIdUsuario(), prestamo.getIdLibro(),
                        prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getEstado()});
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de usuario válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverLibro() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un préstamo para devolver.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPrestamo = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean exito = prestamoNegocio.devolverLibro(idPrestamo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Libro devuelto correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarPrestamos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al devolver el libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
