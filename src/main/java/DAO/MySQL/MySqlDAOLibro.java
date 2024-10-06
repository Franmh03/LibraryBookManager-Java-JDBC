package DAO.MySQL;

import DAO.DAO;
import DAO.DAOException;
import Model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDAOLibro implements DAO<Libro,Integer> {

    final String INSERT = "INSERT INTO libro(titulo, id_autor, anio_publicacion) VALUES(?,?,?)";
    final String UPDATE = "UPDATE libro SET titulo = ?, id_autor = ?, anio_publicacion = ? WHERE id_libro = ?";
    final String DELETE = "DELETE FROM libro WHERE id_alumno = ?";
    final String GETALL = "SELECT id_libro, titulo, id_autor, anio_publicacion FROM libro";
    final String GETONE = "SELECT id_libro, titulo, id_autor, anio_publicacion FROM libro WHERE id_libro = ?";
    private final Connection connection;

    public MySqlDAOLibro(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        this.connection = connection;
    }

    @Override
    public void insertar(Libro modelo) throws DAOException {
        //"INSERT INTO libro(titulo, id_autor, anio_publicacion) VALUES(?,?,?)";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INSERT);
            stat.setString(1,modelo.getTitulo());
            stat.setInt(2,modelo.getIdAutor());
            stat.setLong(3,modelo.getYear());
            if (stat.executeUpdate() ==0) {
                throw new DAOException("Puede que no se haya guardado, tu sabras campeon");
            }
            if (!connection.getAutoCommit()){
                connection.commit();
            }
        } catch (Exception e) {
            throw new DAOException("Error en SQL" , e);
        }
        finally {
            closeResources(stat);
        }
    }

    @Override
    public void modificar(Libro modelo) throws DAOException {
        //"UPDATE libro SET titulo = ?, id_autor = ?, anio_publicacion = ? WHERE id_libro = ?";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(UPDATE);
            stat.setString(1,modelo.getTitulo());
            stat.setInt(2,modelo.getIdAutor());
            stat.setLong(3,modelo.getYear());
            stat.setInt(4,modelo.getIdLibro());
            if (stat.executeUpdate()==0){
                throw new DAOException("Puede que el registro no haya sido modificado");
            }
        } catch (SQLException e) {
            throw new DAOException("error de SQL",e);
        }
        finally {
            closeResources(stat);
        }

    }

    @Override
    public void eliminar(Libro modelo) throws DAOException {
        //"DELETE FROM libro WHERE id_alumno = ?";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(DELETE);
            stat.setLong(1,modelo.getIdLibro());
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        try {
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Puede que el registro "+ modelo.getIdLibro() + " no se halla borrado");
            }
        }
        catch (SQLException e) {
            throw  new DAOException("Error de SQL");
        }
        finally {
            closeResources(stat);
        }
    }

    @Override
    public List<Libro> obtenerTodos() throws DAOException {
        //"SELECT id_libro, titulo, id_autor, anio_publicacion FROM libro";
        List<Libro> list = new ArrayList<>();
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                list.add(convertirRsAmodelo(rs));
            }

        } catch (SQLException e) {
            throw new DAOException("erro en SQL",e);
        }
        finally {
            closeResources(rs,stat);
        }
        return list;
    }

    @Override
    public Libro obtener(Integer id) throws DAOException {
        Libro libro = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETONE);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                libro = convertirRsAmodelo(rs);
            }
            else {
                throw new DAOException("No se encontro ningun registro con ese ID");
            }

        } catch (SQLException e) {
            throw new DAOException("erro en SQL",e);
        }
        finally {
            closeResources(rs,stat);
        }
        return libro;
    }

    @Override
    public Libro convertirRsAmodelo(ResultSet rs) throws DAOException {
        //"SELECT id_libro, titulo, id_autor, anio_publicacion FROM libro";
        try {
            String titulo = rs.getString("titulo");
            int idAutor = rs.getInt("id_autor");
            Long year= rs.getLong("anio_publicacion");
            Libro libro = new Libro(titulo, idAutor, year);
            libro.setIdLibro(rs.getInt("id_libro"));
            return libro;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
