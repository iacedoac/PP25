package negocio;

import datos.UsuarioDAO;
import java.util.List;

public class UsuarioNegocio {
    private UsuarioDAO usuarioDAO;

    public UsuarioNegocio() {
        usuarioDAO = new UsuarioDAO();
    }

    //  Agregar usuario con validaciones
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario.getNombre().isEmpty() || usuario.getEmail().isEmpty() || usuario.getTelefono().isEmpty()) {
            return false;
        }
        if (usuarioDAO.buscarPorEmail(usuario.getEmail()) != null) {
            return false; // El email ya existe
        }
        return usuarioDAO.insertar(usuario);
    }
    public Usuario buscarUsuarioPorId(int id) {
        List<Usuario> usuarios = usuarioDAO.listar(); // Obtener todos los usuarios
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null; // No encontrado
    }

    //  Buscar usuario por email
    public Usuario buscarUsuario(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    //  Editar usuario
    public boolean editarUsuario(Usuario usuario) {
        return usuarioDAO.actualizar(usuario);
    }

    //  Eliminar usuario por email
    public boolean eliminarUsuario(String email) {
        return usuarioDAO.eliminar(email);
    }

    //  Listar usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listar();
    }
}

