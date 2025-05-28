package negocio;

import datos.LibroDAO;
import java.util.List;

public class LibroNegocio {
    private LibroDAO libroDAO;

    public LibroNegocio() {
        libroDAO = new LibroDAO();
    }

    //  Agregar un libro con validaciones
    public boolean agregarLibro(Libro libro) {
        if (libro.getTitulo().isEmpty() || libro.getAutor().isEmpty() || libro.getIsbn().isEmpty()) {
            return false;  // No se permiten datos vacíos
        }

        // Validar que el ISBN no exista en la base de datos
        if (libroDAO.buscarPorISBN(libro.getIsbn()) != null) {
            return false;  // ISBN ya registrado
        }

        return libroDAO.insertar(libro);
    }
    public Libro buscarLibroPorId(int id) {
        List<Libro> libros = libroDAO.listar(); // Obtener todos los libros
        for (Libro libro : libros) {
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null; // No encontrado
    }

    //  Buscar un libro por ISBN
    public Libro buscarPorISBN(String isbn) {
        if (isbn.isEmpty()) {
            return null;  // No se permite buscar sin ISBN
        }
        return libroDAO.buscarPorISBN(isbn);
    }

    //  Editar un libro
    public boolean editarLibro(Libro libro) {
        if (libro.getTitulo().isEmpty() || libro.getAutor().isEmpty() || libro.getIsbn().isEmpty()) {
            return false;  // No se permiten datos vacíos
        }

        return libroDAO.actualizar(libro);
    }

    //  Eliminar un libro por ISBN
    public boolean eliminarLibro(String isbn) {
        if (isbn.isEmpty()) {
            return false;  // No se puede eliminar sin ISBN
        }

        return libroDAO.eliminar(isbn);
    }

    //  Listar todos los libros
    public List<Libro> listarLibros() {
        return libroDAO.listar();
    }


public static void main(String[] args) {
    LibroNegocio negocio = new LibroNegocio();

    // Prueba rápida: Agregar un libro
    Libro libro = new Libro(0, "El Principito", "Antoine de Saint-Exupéry", "123456789");
    boolean agregado = negocio.agregarLibro(libro);
    System.out.println("Libro agregado: " + agregado);

    // Prueba rápida: Buscar por ISBN
    Libro encontrado = negocio.buscarPorISBN("123456789");
    System.out.println("Libro encontrado: " + encontrado);

    // Prueba rápida: Listar todos los libros
    System.out.println("Lista de libros en la BD:");
    negocio.listarLibros().forEach(System.out::println);

    // Prueba rápida: Eliminar libro
    boolean eliminado = negocio.eliminarLibro("123456789");
    System.out.println("Libro eliminado: " + eliminado);
}
}
