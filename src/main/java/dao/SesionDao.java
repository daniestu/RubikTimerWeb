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
import utils.AccesoBBDD;

public class SesionDao implements Persistencia<Sesion>{

	@Override
	public Sesion add(Sesion sesion) throws IOException, SQLException {
		String sql = "INSERT INTO sesion (nombre, usuario_id) VALUES (?, ?)";
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
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
	    String query = "SELECT id, nombre, usuario_id FROM sesion";
	    
	    AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();

	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String nombre = rs.getString("nombre");
	            int usuario_id = rs.getInt("usuario_id");

	            Sesion s = new Sesion(id, nombre, usuario_id);
	            sesiones.add(s);
	        }
	    }

	    return sesiones;
	}
	
	public Sesion getByName(String nombreSesion, int id_usuario) throws IOException, SQLException {
	    String query = "SELECT id, nombre, usuario_id FROM sesion WHERE nombre = ? AND usuario_id = ?";
	    
	    AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
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

	            sesion = new Sesion(id, nombre, usuario_id);
	        }
	    }finally {
	    	if (rs != null) {
	            rs.close();
	        }
	    }

	    return sesion;
	}
	
}
