package DAO.MySQL;

import DAO.DAO;
import DAO.DAOException;
import Model.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDAOAutor implements DAO<Autor, Integer> {

    final String INSERT = "INSERT INTO autor(nombre, apellidos) VALUES(?,?)";
    final String UPDATE = "UPDATE autor SET nombre = ?, apellidos = ? WHERE id_autor = ?";
    final String DELETE = "DELETE FROM autor WHERE id_autor = ?";
    final String GETALL = "SELECT id_autor, nombre, apellidos FROM autor";
    final String GETONE = "SELECT id_autor, nombre, apellidos FROM autor WHERE id_autor = ?";
    private Connection connection;

    public MySqlDAOAutor(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        this.connection = connection;
    }

    @Override
    public void insertar(Autor modelo) throws DAOException {
        PreparedStatement stat = null;
        //"INSERT INTO autor(nombre, apellidos) VALUES(?,?)";
        try {
            stat = connection.prepareStatement(INSERT);
            stat.setString(1,modelo.getNombre());
            stat.setString(2,modelo.getApellidos());
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
    public void modificar(Autor modelo) throws DAOException {
    //"UPDATE autor SET nombre = ?, apellidos = ? WHERE id_autor = ?";
    PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(UPDATE);
            stat.setString(1,modelo.getNombre());
            stat.setString(2,modelo.getApellidos());
            stat.setLong(3,modelo.getIdAutor());
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
    public void eliminar(Integer id) throws DAOException {
    //"DELETE FROM autor WHERE id_autor = ?";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(DELETE);
            stat.setLong(1,id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        try {
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Puede que el registro "+ id + " no se halla borrado");
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
    public List<Autor> obtenerTodos() throws DAOException {
        //SELECT id_autor, nombre, apellidos FROM autor";
        List<Autor> list = new ArrayList<>();
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
    public Autor obtener(Integer id) throws DAOException {
        //SELECT id_autor, nombre, apellidos FROM autor";
        Autor autor = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                autor = convertirRsAmodelo(rs);
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
        return autor;
    }

    @Override
    public Autor convertirRsAmodelo(ResultSet rs) throws DAOException {
        try {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellidos");
            Autor autor = new Autor(apellido,nombre);
            autor.setIdAutor(rs.getInt("id_autor"));
            return autor;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
