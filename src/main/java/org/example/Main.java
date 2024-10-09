package org.example;

import DAO.DAOException;
import DAO.MySQLDAOManager;
import Model.Autor;
import Model.Libro;
import Model.Prestamo;
import Model.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static public Scanner scanner = new Scanner(System.in);
    private static MySQLDAOManager manager;
    private static ManagerTransacctions managerTransacctions = new ManagerTransacctions();


    public static void main (String[] args) throws SQLException, DAOException {
        Scanner scanner = new Scanner(System.in);
        manager = new MySQLDAOManager("localhost","library_book_manager_jdbc");
        vista();
    }
    public static void vista () throws DAOException {
        System.out.println("Welcome to the Library Book Manager");
        System.out.println("This is an vanilla Java project made without any framework, only using JDBC");
        System.out.println("To start press enter");
        scanner.nextLine();
        tipoDeConsulta();
    }

    public static void tipoDeConsulta() throws DAOException {
        char option = 1;

        while (option != 0) {
            System.out.println("Please enter a number (0-4) to choose an operation:");
            System.out.println("0: Exit");
            System.out.println("1: Work with Author");
            System.out.println("2: Work with Book");
            System.out.println("3: Work with Loan");
            System.out.println("4: Work with User");
            option = scanner.nextLine().charAt(0);


            switch (option) {
                case '0':
                    System.out.println("Exiting the application. Goodbye!");
                    return;  // Salir del método, finaliza la operación
                case '1':
                    System.out.println("You selected to work with Author.");
                    char accionAut = solicitarAccion("Author");
                    managerTransacctions.transacctionAlumno(accionAut,manager);
                    solicitarOtra();
                    // Aquí puedes agregar las operaciones relacionadas con Autor
                    break;
                case '2':
                    System.out.println("You selected to work with Book.");
                    char accionLib = solicitarAccion("Book");
                    managerTransacctions.transacctionLibro(accionLib,manager);
                    solicitarOtra();

                    // Aquí puedes agregar las operaciones relacionadas con Libro
                    break;
                case '3':
                    System.out.println("You selected to work with Loan.");
                    char accionPres = solicitarAccion("Loan");
                    managerTransacctions.transacctionPrestamo(accionPres,manager);
                    solicitarOtra();

                    // Aquí puedes agregar las operaciones relacionadas con Prestamo
                    break;
                case '4':
                    System.out.println("You selected to work with User.");
                    char accionUsuario = solicitarAccion("User");
                    managerTransacctions.transacctionUsuario(accionUsuario,manager);
                    solicitarOtra();

                    // Aquí puedes agregar las operaciones relacionadas con Usuario
                    break;
                default:
                    System.out.println("The value entered is not valid, please enter a number between 0 and 4.");
            }
        }
    }

    public static void solicitarOtra () throws DAOException {
        char option;
        System.out.println("Would you like to make another transaction?");
        while (true) {
            System.out.println("Please enter Y/N");
            option = scanner.nextLine().toUpperCase().charAt(0);

            switch (option) {
                case 'Y':
                    tipoDeConsulta();
                    return;  // Salimos del método después de llamar a tipoDeConsulta()
                case 'N':
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("The value entered is not valid, please enter 'Y' or 'N'.");
                    break;
            }
        }
    }

    private static char solicitarAccion(String modelo) {
        char option = 1;
        while (true) {
            System.out.println("Please enter a number (0-4) to choose an operation:");
            System.out.println("1: Create an new " + modelo);
            System.out.println("2: Change an existing " + modelo);
            System.out.println("3: Delete an " + modelo);
            System.out.println("4: Get an " + modelo);
            System.out.println("5: Get all the " + modelo + "'s");
            option = scanner.nextLine().charAt(0);

            switch (option) {
                case '1':
                    return option;
                case '2':
                    return option;
                case '3':
                    return option;
                case '4':
                    return option;
                case '5':
                    return option;
                default:
                    System.out.println("The value entered is not valid, please enter a number between 0 and 4.");
            }
        }
    }

}