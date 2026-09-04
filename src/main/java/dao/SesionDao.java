package dao;

import dao.contracts.Persistencia;
import models.Sesion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.AccesoProperties;
import utils.EmailUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionDao implements Persistencia<Sesion>{
	private static final Logger _log = LogManager.getLogger(SesionDao.class);

	@Override
	public Sesion add(Sesion sesion) throws IOException, SQLException {
		
		String sql = "INSERT INTO sesion (nombre, usuario_id, default_sesion) VALUES (?, ?, 0)";
		int generatedId = -1;
		ResultSet rs = null;

        try (Connection connection = AccesoProperties.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
        	statement.setString(1, sesion.getNombre());
        	statement.setInt(2, sesion.getUsuario_id());
            statement.executeUpdate();
            
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
			_log.error(e.getMessage(), e);
        } finally {
	    	if (rs != null) {
	            rs.close();
	        }
    	    
        }
        if (generatedId != -1) {
        	sesion.setId(generatedId);
		}
        
		return sesion;
	}

	@Override
	public List<Sesion> getAll() throws IOException, SQLException {
		List<Sesion> sesiones = new ArrayList<>();
	    String query = "SELECT id, nombre, usuario_id, default_sesion FROM sesion";

	    try (Connection connection = AccesoProperties.getDBConnection();
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String nombre = rs.getString("nombre");
	            int usuario_id = rs.getInt("usuario_id");
	            boolean default_sesion = rs.getBoolean("default_sesion");

	            Sesion s = new Sesion(id, nombre, usuario_id, default_sesion);
	            sesiones.add(s);
	        }
	    }

	    return sesiones;
	}
	
	public Sesion getByName(String nombreSesion, int id_usuario) throws IOException, SQLException {
	    String query = "SELECT id, nombre, usuario_id, default_sesion FROM sesion WHERE nombre = ? AND usuario_id = ?";
		
		Sesion sesion = null;
		ResultSet rs = null;
	    try (Connection connection = AccesoProperties.getDBConnection();
    		PreparedStatement statement = connection.prepareStatement(query)) {
	    	
	    	statement.setString(1, nombreSesion);
	    	statement.setInt(2, id_usuario);
	    	rs = statement.executeQuery();
	        if (rs.next()) {
	            int id = rs.getInt("id");
	            String nombre = rs.getString("nombre");
	            int usuario_id = rs.getInt("usuario_id");
	            boolean default_sesion = rs.getBoolean("default_sesion");

	            sesion = new Sesion(id, nombre, usuario_id, default_sesion);
	        }
	    }finally {
	    	if (rs != null) {
	            rs.close();
	        }
	    }

	    return sesion;
	}

	public boolean remove(int id_sesion) {
		String sql = "DELETE FROM sesion WHERE id = ?";
		
        try (Connection connection = AccesoProperties.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_sesion);
            statement.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
			_log.error(e.getMessage(), e);
            return false;
        }
	}
	
	public boolean update(Sesion sesion) {
        String sql = "UPDATE sesion SET nombre = ? WHERE id = ? AND usuario_id = ?";
		
        try (Connection connection = AccesoProperties.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setString(1, sesion.getNombre());
        	statement.setInt(2, sesion.getId());
        	statement.setInt(3, sesion.getUsuario_id());
            statement.executeUpdate();
            
        } catch (SQLException e) {
			_log.error(e.getMessage(), e);
            return false;
        }
        
		return true;
	}

	public void updateDefault(String nombre_sesion, int usuario_id) {
		String sql = "UPDATE sesion "
					+ "SET default_Sesion = "
					+ "CASE WHEN nombre = ? AND usuario_id = ? THEN 1 "
					+ "WHEN nombre <> ? AND usuario_id = ? THEN 0 "
					+ "ELSE default_Sesion "
					+ "END";
		
        try (Connection connection = AccesoProperties.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setString(1, nombre_sesion);
        	statement.setInt(2, usuario_id);
        	statement.setString(3, nombre_sesion);
        	statement.setInt(4, usuario_id);
            statement.executeUpdate();
            
        } catch (SQLException e) {
			_log.error(e.getMessage(), e);
        }
	}
	
}
