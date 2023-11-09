package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.contracts.Persistencia;
import models.Sesion;
import utils.AccesoProperties;

public class SesionDao implements Persistencia<Sesion>{

	@Override
	public Sesion add(Sesion sesion) throws IOException, SQLException {
		String sql = "INSERT INTO sesion (nombre, usuario_id, default_sesion) VALUES (?, ?, 0)";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
		int generatedId = -1;
		ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
        	statement.setString(1, sesion.getNombre());
        	statement.setInt(2, sesion.getUsuario_id());
            statement.executeUpdate();
            
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
	    
	    AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	         Statement stmt = conn.createStatement();
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
	    
	    AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
		Sesion sesion = null;
		ResultSet rs = null;
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
    		PreparedStatement statement = conn.prepareStatement(query)) {
	    	
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
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_sesion);
            statement.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean update(Sesion sesion) {
        String sql = "UPDATE sesion SET nombre = ? WHERE id = ? AND usuario_id = ?";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setString(1, sesion.getNombre());
        	statement.setInt(2, sesion.getId());
        	statement.setInt(3, sesion.getUsuario_id());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
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
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setString(1, nombre_sesion);
        	statement.setInt(2, usuario_id);
        	statement.setString(3, nombre_sesion);
        	statement.setInt(4, usuario_id);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
}
