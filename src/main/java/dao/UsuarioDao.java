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
import models.Usuario;
import utils.AccesoProperties;

public class UsuarioDao implements Persistencia<Usuario>{

	@Override
	public Usuario add(Usuario usuario) throws IOException{
		String sql = "INSERT INTO usuario (usuario, contrasena, correo) VALUES (?, ?, ?)";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
        
		int generatedId = -1;
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getpassword());
            statement.setString(3, usuario.getCorreo());
            
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (generatedId != -1) {
        	usuario.setIdUsuario(generatedId);
		}
        
		return usuario;
	}

	@Override
	public List<Usuario> getAll() throws IOException, SQLException {
		List<Usuario> usuarios = new ArrayList<>();
	    String query = "SELECT id, usuario, correo FROM usuario";
	    
	    AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String usuario = rs.getString("usuario");
	            String correo = rs.getString("correo");

	            Usuario u = new Usuario(id, usuario, correo);
	            usuarios.add(u);
	        }
	    }

	    return usuarios;
	}
	
	public Usuario getByUsernamePwd(String username, String password) throws IOException, SQLException {
	    AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		Usuario usuario = null;
		
		String sql = "SELECT id, usuario, correo FROM usuario WHERE usuario = ? AND contrasena = ?";
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, username);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	        	usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("id"));
	            usuario.setNombreUsuario(rs.getString("usuario"));
	            usuario.setCorreo(rs.getString("correo"));
	        }
	        rs.close();
	    }

	    return usuario;
	}
	
	public Usuario getByEmail(String correo) throws SQLException {
	    AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		Usuario usuario = null;
		
		String sql = "SELECT id, usuario, correo FROM usuario WHERE correo = ?";
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, correo);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	        	usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("id"));
	            usuario.setNombreUsuario(rs.getString("usuario"));
	            usuario.setCorreo(rs.getString("correo"));
	        }
	        rs.close();
	    }

	    return usuario;
	}

	public boolean restablecerContraseña(Integer usuarioId, String password) {
		String sql = "UPDATE usuario SET contrasena = ? WHERE id = ?";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setString(1, password);
        	statement.setInt(2, usuarioId);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
		return true;
	}

}
