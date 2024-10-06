package DAO;

import DAO.MySQL.MySqlDAOAutor;
import DAO.MySQL.MySqlDAOLibro;
import DAO.MySQL.MySqlDAOPrestamo;
import DAO.MySQL.MySqlDAOUsuario;
import Model.Autor;
import Model.Libro;
import Model.Prestamo;
import Model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLDAOManager {

    private Connection connection;
    private MySqlDAOAutor DAOAutor;
    private MySqlDAOUsuario DAOUsuario;
    private MySqlDAOLibro DAOLibro;
    private MySqlDAOPrestamo DAOPrestamo;

    public MySQLDAOManager(String host, String database) throws DAOException {
//        host: localhost | database: escuela_jdbc
//        username: root| password: 7431
        String jdbc = "jdbc:mysql://" + host + ":3306/" + database;
        connection = null;
        try {
            this.connection = DriverManager.getConnection(jdbc,"root", "7431");
        }
        catch (SQLException e) {
            throw new DAOException("Error al establecer conexion con la BD", e);
        }
    }

    public MySqlDAOAutor getDAOAutor() {
        if (DAOAutor == null) {
            this.DAOAutor = new MySqlDAOAutor(connection);
        }
        return DAOAutor;
    }

    public MySqlDAOUsuario getDAOUsuario() {
        if (DAOUsuario == null) {
            this.DAOUsuario = new MySqlDAOUsuario(connection);
        }
        return DAOUsuario;
    }

    public MySqlDAOLibro getDAOLibro() {
        if (DAOLibro == null) {
            this.DAOLibro = new MySqlDAOLibro(connection);
        }
        return DAOLibro;
    }

    public MySqlDAOPrestamo getDAOPrestamo() {
        if (DAOPrestamo == null) {
            this.DAOPrestamo = new MySqlDAOPrestamo(connection);
        }
        return DAOPrestamo;
    }

    public static void main (String[] args) throws SQLException, DAOException {
        MySQLDAOManager manager = new MySQLDAOManager("localhost","library_book_manager_jdbc");
        List <Libro> libros = manager.getDAOLibro().obtenerTodos();
        System.out.println(libros);

        List <Autor> autors = manager.getDAOAutor().obtenerTodos();
        System.out.println(autors);

        List <Prestamo> prestamos = manager.getDAOPrestamo().obtenerTodos();
        System.out.println(prestamos);

        List <Usuario> usuarios = manager.getDAOUsuario().obtenerTodos();
        System.out.println(usuarios);
    }
}
