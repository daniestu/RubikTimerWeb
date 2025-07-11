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
import models.Conf;
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
	
	public Usuario getByUsernamePwd(String username, String password) throws SQLException {
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
	
	public Usuario getByEmailPwd(String email, String password) throws SQLException {
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		Usuario usuario = null;
		
		String sql = "SELECT id, usuario, correo FROM usuario WHERE correo = ? AND contrasena = ?";
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, email);
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

	public Conf getConfiguracionUsuario(Usuario usuario) throws SQLException {
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		Conf conf = null;

		String sql = "SELECT conf_tema, conf_idioma, conf_ocultar_elementos, conf_ocultar_visualizacion, conf_pulsacion_larga, conf_cronometro_raton, conf_tiempo_inspeccion, conf_segundos_inspeccion FROM usuario WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, usuario.getIdUsuario());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				conf = new Conf(rs.getInt("conf_tema"), rs.getInt("conf_idioma"),
						rs.getInt("conf_ocultar_elementos"), rs.getInt("conf_ocultar_visualizacion"),
						rs.getInt("conf_pulsacion_larga"), rs.getInt("conf_cronometro_raton"),
						rs.getInt("conf_tiempo_inspeccion"), rs.getInt("conf_segundos_inspeccion"));
			}
			rs.close();
		}

		return conf;
	}

	public boolean actualizarConfiguracionUsuario(Usuario usuario, Conf conf) {
		String sql = "UPDATE usuario SET conf_tema = ?, conf_idioma = ?, conf_ocultar_elementos = ?, " +
				"conf_ocultar_visualizacion = ?, conf_pulsacion_larga = ?, conf_cronometro_raton = ?, " +
				"conf_tiempo_inspeccion = ?, conf_segundos_inspeccion = ? WHERE id = ?";

		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, conf.getTema());
			statement.setInt(2, conf.getIdioma());
			statement.setInt(3, conf.getOcultarElementos());
			statement.setInt(4, conf.getOcultarVisualizacion());
			statement.setInt(5, conf.getPulsacionLarga());
			statement.setInt(6, conf.getCronometroRaton());
			statement.setInt(7, conf.getTiempoInspeccion());
			statement.setInt(8, conf.getSegundosInspeccion());
			statement.setInt(9, usuario.getIdUsuario());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
