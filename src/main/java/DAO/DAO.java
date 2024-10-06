package DAO;

import java.sql.ResultSet;
import java.util.List;

public interface DAO <T,K>{
    //T es el tipo de modelo que vamos a devolver. (Alumno, asignatura, etc) y K el tipo de clave que devolvemos
    void insertar (T modelo) throws DAOException;
    void modificar (T modelo) throws DAOException;
    void eliminar (T modelo) throws DAOException;
    List<T> obtenerTodos() throws DAOException;
    T obtener (K id) throws DAOException;

    default void  closeResources(AutoCloseable... resources) throws DAOException {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    throw new DAOException("Error al cerrar los recursos", e);
                }
            }
        }
    }

    Object convertirRsAmodelo (ResultSet rs) throws DAOException;

}
