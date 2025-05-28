package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:h2:./data/biblioteca;AUTO_SERVER=TRUE";
    private static final String USUARIO = "sa";
    private static final String PASSWORD = "";

    // Script para crear la tabla LIBROS si no existe
    private static final String CREAR_TABLA_LIBROS = "CREATE TABLE IF NOT EXISTS libros ("
            + "id IDENTITY PRIMARY KEY, "
            + "titulo VARCHAR(255), "
            + "autor VARCHAR(255), "
            + "isbn VARCHAR(20) UNIQUE)";

    //  Script para crear la tabla USUARIOS si no existe
    private static final String CREAR_TABLA_USUARIOS = "CREATE TABLE IF NOT EXISTS usuarios ("
            + "id IDENTITY PRIMARY KEY, "
            + "nombre VARCHAR(255), "
            + "email VARCHAR(255) UNIQUE, " // 🔹 Garantiza que los emails sean únicos
            + "telefono VARCHAR(20))";
    //  Script para crear la tabla PRESTAMOS si no existe
    private static final String CREAR_TABLA_PRESTAMOS = "CREATE TABLE IF NOT EXISTS prestamos ("
            + "id IDENTITY PRIMARY KEY, "
            + "id_usuario INT, "
            + "id_libro INT, "
            + "fecha_prestamo DATE, "
            + "fecha_devolucion DATE, "
            + "estado VARCHAR(20) DEFAULT 'PENDIENTE', "
            + "FOREIGN KEY (id_usuario) REFERENCES usuarios(id), "
            + "FOREIGN KEY (id_libro) REFERENCES libros(id))";

    public static Connection conectar() {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);

            //  Crear las tablas si no existen
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(CREAR_TABLA_LIBROS);
                stmt.execute(CREAR_TABLA_USUARIOS); // 🔹 Se asegura de crear la tabla usuarios
                stmt.execute(CREAR_TABLA_PRESTAMOS); // 🔹 Se asegura de crear la tabla préstamos
            }

            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver JDBC H2 no encontrado.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Error de conexión con H2.");
            e.printStackTrace();
            return null;
        }
    }
}

