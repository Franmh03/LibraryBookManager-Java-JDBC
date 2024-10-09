package org.example;

import DAO.DAOException;
import DAO.MySQL.MySqlDAOAutor;
import DAO.MySQL.MySqlDAOLibro;
import DAO.MySQL.MySqlDAOPrestamo;
import DAO.MySQL.MySqlDAOUsuario;
import DAO.MySQLDAOManager;
import Model.Autor;
import Model.Libro;
import Model.Prestamo;
import Model.Usuario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerTransacctions {

    public void transacctionAlumno (char option, MySQLDAOManager manager) throws DAOException {

        MySqlDAOAutor autorManager = manager.getDAOAutor();

        switch (option) {
            case '1':
                autorManager.insertar(solicitarAutor());
                System.out.println("Transaccion completada correctamente");
                break;
            case '2':
                Autor autorMod = solicitarAutor();
                autorMod.setIdAutor(solicitarID());
                autorManager.modificar(autorMod);
                System.out.println("Transaccion completada correctamente");
                break;
            case '3':
                autorManager.eliminar(solicitarID());
                System.out.println("Transaccion completada correctamente");

                break;
            case '4':
                Autor autorObt = autorManager.obtener(solicitarID());
                System.out.println(autorObt.toString());
                System.out.println("Transaccion completada correctamente");

                break;
            case '5':
                List<Autor> autors = autorManager.obtenerTodos();
                for (Autor autor : autors) {
                    System.out.println(autor.toString());
                }
                System.out.println("Transaccion completada correctamente");

                break;
        }
    }

    public Autor solicitarAutor (){
        String nombre, apellido;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the author's last name:");
        apellido = scanner.nextLine().trim();
        System.out.println("Please enter the author's first name:");
        nombre = scanner.nextLine().trim();
        return new Autor(apellido,nombre);
    }

    public void transacctionLibro(char option, MySQLDAOManager manager) throws DAOException {

        MySqlDAOLibro libroManager = manager.getDAOLibro();

        switch (option) {
            case '1':
                libroManager.insertar(solicitarLibro());
                System.out.println("Transaccion completada correctamente");
                break;
            case '2':
                Libro libroMod = solicitarLibro();
                libroMod.setIdLibro(solicitarID());
                libroManager.modificar(libroMod);
                System.out.println("Transaccion completada correctamente");
                break;
            case '3':
                libroManager.eliminar(solicitarID());
                System.out.println("Transaccion completada correctamente");
                break;
            case '4':
                Libro libroObt = libroManager.obtener(solicitarID());
                System.out.println(libroObt.toString());
                System.out.println("Transaccion completada correctamente");
                break;
            case '5':
                List<Libro> libros = libroManager.obtenerTodos();
                for (Libro libro : libros) {
                    System.out.println(libro.toString());
                }
                System.out.println("Transaccion completada correctamente");
                break;
        }
    }

    public Libro solicitarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the book title:");
        String titulo = scanner.nextLine().trim();

        System.out.println("Please enter the author's ID:");
        int idAutor = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Please enter the publication year:");
        Long year = Long.parseLong(scanner.nextLine().trim());

        return new Libro(titulo, idAutor, year);
    }

    public void transacctionPrestamo (char option, MySQLDAOManager manager) throws DAOException {

        MySqlDAOPrestamo prestamoManager = manager.getDAOPrestamo();

        switch (option) {
            case '1':
                prestamoManager.insertar(solicitarPrestamo());
                System.out.println("Transaccion completada correctamente");
                break;
            case '2':
                Prestamo prestamoMod = solicitarPrestamo();
                prestamoMod.setIdPrestamo(solicitarID());
                prestamoManager.modificar(prestamoMod);
                System.out.println("Transaccion completada correctamente");
                break;
            case '3':
                prestamoManager.eliminar(solicitarID());
                System.out.println("Transaccion completada correctamente");
                break;
            case '4':
                Prestamo prestamoObt = prestamoManager.obtener(solicitarID());
                System.out.println(prestamoObt.toString());
                System.out.println("Transaccion completada correctamente");
                break;
            case '5':
                List<Prestamo> prestamos = prestamoManager.obtenerTodos();
                for (Prestamo prestamo : prestamos) {
                    System.out.println(prestamo.toString());
                }
                System.out.println("Transaccion completada correctamente");
                break;
        }
    }

    public Prestamo solicitarPrestamo() {
        int idUsuario, idLibro;
        Date fechaPrestamo;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the user's ID:");
        idUsuario = scanner.nextInt();
        System.out.println("Please enter the book's ID:");
        idLibro = scanner.nextInt();
        fechaPrestamo = solicitarFechaPrestamo();
        return new Prestamo(idUsuario, idLibro, fechaPrestamo);
    }

    public Date solicitarFechaPrestamo() {
        Scanner scanner = new Scanner(System.in);
        int year = 0;
        int month = 0;
        int day = 0;
        boolean fechaValida = false;

        while (!fechaValida) {
            try {
                // Solicitar el año
                System.out.println("Please enter the loan year (yyyy):");
                year = Integer.parseInt(scanner.nextLine());

                // Solicitar el mes
                System.out.println("Please enter the loan month (1-12):");
                month = Integer.parseInt(scanner.nextLine());
                if (month < 1 || month > 12) {
                    throw new IllegalArgumentException("Month must be between 1 and 12.");
                }

                // Solicitar el día
                System.out.println("Please enter the loan day (1-31):");
                day = Integer.parseInt(scanner.nextLine());

                // Validar la fecha
                Date fechaPrestamo = new Date(year,month,day);
                fechaValida = true; // Salimos del bucle

                return fechaPrestamo;
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid integers for year, month, and day.");
            }
        }

        return null; // Esto no debería alcanzarse, pero se debe retornar algo.
    }

    public void transacctionUsuario (char option, MySQLDAOManager manager) throws DAOException {

        MySqlDAOUsuario usuarioManager = manager.getDAOUsuario();

        switch (option) {
            case '1':
                usuarioManager.insertar(solicitarUsuario());
                System.out.println("Transaccion completada correctamente");
                break;
            case '2':
                Usuario usuarioMod = solicitarUsuario();
                usuarioMod.setIdUsuario(solicitarID());
                usuarioManager.modificar(usuarioMod);
                System.out.println("Transaccion completada correctamente");
                break;
            case '3':
                usuarioManager.eliminar(solicitarID());
                System.out.println("Transaccion completada correctamente");
                break;
            case '4':
                Usuario usuarioObt = usuarioManager.obtener(solicitarID());
                System.out.println(usuarioObt.toString());
                System.out.println("Transaccion completada correctamente");
                break;
            case '5':
                List<Usuario> usuarios = usuarioManager.obtenerTodos();
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario.toString());
                }
                System.out.println("Transaccion completada correctamente");
                break;
        }
    }

    public Usuario solicitarUsuario() {
        String nombre, apellidos;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the user's first name:");
        nombre = scanner.nextLine().trim();
        System.out.println("Please enter the user's last name:");
        apellidos = scanner.nextLine().trim();
        return new Usuario(nombre, apellidos);
    }


    private static int solicitarID() {
        Scanner scanner = new Scanner(System.in);
        int id = -1;
        boolean valid = false;

        while (!valid) {
            System.out.println("Please enter a valid ID (integer):");
            try {
                id = Integer.parseInt(scanner.nextLine().trim());
                valid = true;  // Si el ID es válido, salimos del bucle
            } catch (NumberFormatException e) {
                System.out.println("The value entered is not a valid integer. Please try again.");
            }
        }

        return id;
    }

}
