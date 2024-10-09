package DAO.MySQL;

import DAO.DAO;
import DAO.DAOException;
import Model.Prestamo;
import Model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDAOPrestamo implements DAO<Prestamo,Integer> {


    final String INSERT = "INSERT INTO prestamo(id_usuario, id_libro, fecha_prestamo) VALUES(?,?,?)";
    final String UPDATE = "UPDATE prestamo SET id_usuario = ?, id_libro = ?, fecha_prestamo = ? WHERE id_prestamo = ?";
    final String DELETE = "DELETE FROM prestamo WHERE id_prestamo = ?";
    final String GETALL = "SELECT id_prestamo, id_usuario, id_libro, fecha_prestamo FROM prestamo";
    final String GETONE = "SELECT id_prestamo, id_usuario, id_libro, fecha_prestamo FROM prestamo WHERE id_prestamo = ?";
    private Connection connection;

    public MySqlDAOPrestamo(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        this.connection = connection;
    }

    @Override
    public void insertar(Prestamo modelo) throws DAOException {
        //"INSERT INTO prestamo(id_usuario, id_libro, fecha_prestamo) VALUES(?,?,?)";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INSERT);
            stat.setInt(1, modelo.getIdUsuario());
            stat.setInt(2, modelo.getIdLibro());
            stat.setDate(3, modelo.getFechaPrestamo());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Puede que no se haya guardado");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            closeResources(stat);
        }
    }

    @Override
    public void modificar(Prestamo modelo) throws DAOException {
        //"UPDATE prestamo SET id_usuario = ?, id_libro = ?, fecha_prestamo = ? WHERE id_prestamo = ?";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(UPDATE);
            stat.setInt(1, modelo.getIdUsuario());
            stat.setInt(2, modelo.getIdLibro());
            stat.setDate(3, modelo.getFechaPrestamo());
            stat.setInt(4,modelo.getIdPrestamo());
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Puede que no se haya actualizado el registro");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            closeResources(stat);
        }
    }

    @Override
    public void eliminar(Integer id) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(DELETE);
            stat.setLong(1, id);
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Puede que el registro " + id + " no se haya eliminado");
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            closeResources(stat);
        }
    }

    @Override
    public List<Prestamo> obtenerTodos() throws DAOException {
        List<Prestamo> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                list.add(convertirRsAmodelo(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            closeResources(rs, stat);
        }
        return list;
    }

    @Override
    public Prestamo obtener(Integer id) throws DAOException {
        Prestamo prestamo = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETONE);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                prestamo = convertirRsAmodelo(rs);
            }
            else {
                throw new DAOException("No se encontro ningun registro con ese ID");
            }

        } catch (SQLException e) {
            throw new DAOException("error en SQL",e);
        }
        finally {
            closeResources(rs,stat);
        }
        return prestamo;

    }

    @Override
    public Prestamo convertirRsAmodelo(ResultSet rs) throws DAOException {
        //"SELECT id_prestamo, id_usuario, id_libro, fecha_prestamo FROM prestamo";
        try {
            int idUsuario = rs.getInt("id_usuario");
            int idLibro = rs.getInt("id_libro");
            Date fechaPrestamo = rs.getDate("fecha_prestamo");
            Prestamo prestamo = new Prestamo(idUsuario, idLibro, fechaPrestamo);
            prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
            return prestamo;
        } catch (SQLException e) {
            throw new DAOException("error al convertir el registro",e);
        }

    }
}
