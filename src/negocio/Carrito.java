package negocio;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    // Lista para almacenar los libros que se agregan al carrito
    private List<Libro> librosEnCarrito;

    // Constructor para inicializar la lista de libros
    public Carrito() {
        librosEnCarrito = new ArrayList<>();
    }

    // Método para agregar un libro al carrito
    public void agregarLibro(Libro libro) {
        librosEnCarrito.add(libro);
    }

    // Método para eliminar un libro del carrito
    public void eliminarLibro(Libro libro) {
        librosEnCarrito.remove(libro);
    }

    // Método para obtener el número total de libros en el carrito
    public int obtenerTotalLibros() {
        return librosEnCarrito.size();
    }

    // Método para mostrar los libros en el carrito
    public void mostrarCarrito() {
        if (librosEnCarrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            System.out.println("Libros en el carrito:");
            for (Libro libro : librosEnCarrito) {
                System.out.println(libro);
            }
        }
    }
    public List<Libro> getLibros() {
        return librosEnCarrito;
    }
}
