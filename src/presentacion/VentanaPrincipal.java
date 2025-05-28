package presentacion;

import javax.swing.*;
import java.awt.FlowLayout;
import presentacion.GestionCompras;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JDesktopPane escritorio;

    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el escritorio (para ventanas internas)
        escritorio = new JDesktopPane();
        getContentPane().add(escritorio);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Libros
        JMenu menuLibros = new JMenu("Libros");
        JMenuItem itemGestionLibros = new JMenuItem("Gestionar Libros");
        itemGestionLibros.addActionListener(e -> abrirGestionLibros());
        menuLibros.add(itemGestionLibros);
                
        // Menú Usuarios
        JMenu menuUsuarios = new JMenu("Usuarios");
        JMenuItem itemGestionUsuarios = new JMenuItem("Gestionar Usuarios");
        itemGestionUsuarios.addActionListener(e -> abrirGestionUsuarios());
        menuUsuarios.add(itemGestionUsuarios);
        
        // Menú Préstamos
        JMenu menuPrestamos = new JMenu("Préstamos");
        JMenuItem itemGestionPrestamos = new JMenuItem("Gestionar Préstamos");
        itemGestionPrestamos.addActionListener(e -> abrirGestionPrestamos());
        menuPrestamos.add(itemGestionPrestamos);
        
     // Menú E-commerce
        JMenu menuEcommerce = new JMenu("E-commerce");
        JMenuItem itemGestionCompras = new JMenuItem("Comprar libros");

        itemGestionCompras.addActionListener(e -> {
            GestionCompras compras = new GestionCompras();
            escritorio.add(compras);
            compras.setVisible(true);
        });

        
        
        // Agregar menús a la barra
        menuBar.add(menuLibros);
        menuBar.add(menuUsuarios);
        menuBar.add(menuPrestamos);
        menuEcommerce.add(itemGestionCompras);
        menuBar.add(menuEcommerce);
        
        setJMenuBar(menuBar);
    }

    private void abrirGestionLibros() {
        GestionLibros ventanaLibros = new GestionLibros();
        ventanaLibros.setSize(468, 626);
        ventanaLibros.setLocation(0, 0);
        escritorio.add(ventanaLibros);
        ventanaLibros.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        ventanaLibros.setVisible(true);
    }

    private void abrirGestionUsuarios() {
        GestionUsuarios ventanaUsuarios = new GestionUsuarios();
        ventanaUsuarios.setLocation(0, 0);
        escritorio.add(ventanaUsuarios);
        ventanaUsuarios.setVisible(true);
    }

    private void abrirGestionPrestamos() {
        GestionPrestamos ventanaPrestamos = new GestionPrestamos();
        ventanaPrestamos.setSize(468, 626);
        ventanaPrestamos.setLocation(0, 0);
        escritorio.add(ventanaPrestamos);
        ventanaPrestamos.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
