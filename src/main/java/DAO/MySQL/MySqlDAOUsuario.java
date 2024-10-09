package DAO.MySQL;

import DAO.DAO;
import DAO.DAOException;
import Model.Usuario;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MySqlDAOUsuario implements DAO <Usuario,Integer> {

    final String INSERT = "INSERT INTO usuario(nombre, apellidos) VALUES(?,?)";
    final String UPDATE = "UPDATE usuario SET nombre = ?, apellidos = ? WHERE id_usuario = ?";
    final String DELETE = "DELETE FROM usuario WHERE id_usuario = ?";
    final String GETALL = "SELECT id_usuario, nombre, apellidos FROM usuario";
    final String GETONE = "SELECT id_usuario, nombre, apellidos FROM usuario WHERE id_usuario = ?";
    private Connection connection;

    public MySqlDAOUsuario(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        this.connection = connection;
    }

    @Override
    public void insertar(Usuario modelo) throws DAOException {
        //"INSERT INTO usuario(nombre, apellidos) VALUES(?,?)";
        PreparedStatement stat = null;
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
    public void modificar(Usuario modelo) throws DAOException {
        //"UPDATE usuario SET nombre = ?, apellidos = ? WHERE id_usuario = ?";
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(UPDATE);
            stat.setString(1,modelo.getNombre());
            stat.setString(2,modelo.getApellidos());
            stat.setInt(4,modelo.getIdUsuario());
            if (stat.executeUpdate()==0){
                throw new DAOException("Puede que el registro no haya sido modificado");
            }
        } catch (SQLException e) {
            throw new DAOException("erro de SQL",e);
        }
        finally {
            closeResources(stat);
        }

    }

    @Override
    public void eliminar(Integer id) throws DAOException {
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
    public List<Usuario> obtenerTodos() throws DAOException {
        List<Usuario> list = new ArrayList<>();
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
    public Usuario obtener(Integer id) throws DAOException {
        //"SELECT id_usuario, nombre FROM usuario WHERE id_usuario = ?";
        Usuario usuario = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = connection.prepareStatement(GETONE);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                usuario = convertirRsAmodelo(rs);
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
        return usuario;
    }

    @Override
    public Usuario convertirRsAmodelo(ResultSet rs) throws DAOException {
        try {
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellidos");
            Usuario usuario = new Usuario(nombre,apellido);
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            return usuario;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
