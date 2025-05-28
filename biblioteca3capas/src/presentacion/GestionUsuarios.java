package presentacion;

import javax.swing.*;
import negocio.Usuario;
import negocio.UsuarioNegocio;
import java.awt.*;

public class GestionUsuarios extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre, txtEmail, txtTelefono;
    private JButton btnAgregar, btnBuscar, btnEditar, btnEliminar;
    private UsuarioNegocio usuarioNegocio;

    public GestionUsuarios() {
        usuarioNegocio = new UsuarioNegocio(); // ✅ Ahora la variable se usa
        setTitle("Gestión de Usuarios");
        setSize(400, 300);
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        btnAgregar = new JButton("Agregar");
        btnBuscar = new JButton("Buscar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        add(btnAgregar);
        add(btnBuscar);
        add(btnEditar);
        add(btnEliminar);

        // ✅ Agregar eventos a los botones
        btnAgregar.addActionListener(e -> agregarUsuario());
        btnBuscar.addActionListener(e -> buscarUsuario());
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
    }
    
    //  Método para agregar un usuario
    private void agregarUsuario() {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario(0, nombre, email, telefono);
        boolean agregado = usuarioNegocio.agregarUsuario(usuario);

        if (agregado) {
            JOptionPane.showMessageDialog(this, "Usuario agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el usuario (posible email duplicado)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para buscar un usuario por email
    private void buscarUsuario() {
        String email = txtEmail.getText();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un email para buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioNegocio.buscarUsuario(email);
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtTelefono.setText(usuario.getTelefono());
            JOptionPane.showMessageDialog(this, "Usuario encontrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para editar un usuario
    private void editarUsuario() {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = new Usuario(0, nombre, email, telefono);
        boolean editado = usuarioNegocio.editarUsuario(usuario);

        if (editado) {
            JOptionPane.showMessageDialog(this, "Usuario editado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para eliminar un usuario por email
    private void eliminarUsuario() {
        String email = txtEmail.getText();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un email para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean eliminado = usuarioNegocio.eliminarUsuario(email);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //  Método para limpiar los campos
    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}
