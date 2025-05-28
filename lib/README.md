Aplicación de Gestión de Biblioteca (Arquitectura en 3 Capas)

Descripción

Esta aplicación permite la gestión de una biblioteca utilizando la arquitectura de 3 capas:

Capa de Presentación: Interfaz gráfica desarrollada en Java Swing.

Capa de Negocio: Manejo de la lógica de gestión de libros, usuarios y préstamos.

Capa de Acceso a Datos: Persistencia con base de datos H2.

Requisitos

Eclipse IDE

JDK 11 o superior

Base de datos H2 (se ejecuta en memoria o archivo local)

Instalación y Ejecución

Clonar el repositorio:

git clone <URL_DEL_REPOSITORIO>
cd biblioteca-3capas

Abrir en Eclipse:

Importar como un proyecto Java.

Ejecutar la aplicación:

Ejecutar la clase principal en presentacion.Main.

Estructura del Proyecto

biblioteca-3capas/
│── src/
│   ├── presentacion/     # Interfaz gráfica (UI)
│   ├── negocio/          # Lógica de negocio
│   ├── datos/            # Acceso a datos (Base de Datos H2)
│── lib/                  # Dependencias (si es necesario)
│── README.md             # Documentación básica
│── biblioteca.mv.db      # Archivo de la base de datos H2

Funcionalidades

Libros: Agregar, editar, eliminar y buscar libros por título, autor o ISBN.

Usuarios: Registrar y gestionar usuarios.

Préstamos: Registrar préstamos, devoluciones de libros y listado general.

Tecnologías Utilizadas

Java (Swing para la UI)

Base de datos H2 (Modo archivo/local)

JDBC para conexión a la base de datos

Autor



