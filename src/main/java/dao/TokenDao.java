package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import models.Token;
import utils.AccesoProperties;

public class TokenDao {
	
	public Token add(Token token) {
		String sql = "INSERT INTO token (uuid, usuario_id, fecha_creacion, caducado) VALUES (?, ?, ?, 0)";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
        
		int generatedId = -1;
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, token.getUuid());
            statement.setInt(2, token.getUsuarioId());
            statement.setTimestamp(3, token.getFechaCreacion());
            
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (generatedId != -1) {
        	token.setToken_id(generatedId);
		}
        
		return token;
	}

	public Token getByUUID(String uuid) {
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();

		Token token = null;
		
		String sql = "SELECT token_id, uuid, usuario_id, fecha_creacion, caducado FROM token WHERE uuid = ?";
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, uuid);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	        	token = new Token();
	        	token.setToken_id(rs.getInt("token_id"));
	        	token.setUuid(rs.getString("uuid"));
	        	token.setUsuarioId(rs.getInt("usuario_id"));
	        	token.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
	        	token.setCaducado(rs.getBoolean("caducado"));
	        }
	        rs.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		}

	    return token;
	}

	public boolean caducarTokens(Integer idUsuario) {
		String sql = "UPDATE token SET caducado = 1 WHERE usuario_id = ?";
		AccesoProperties accesoBBDD = new AccesoProperties();
		Properties prop = accesoBBDD.cargarFicheroBBDD();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setInt(1, idUsuario);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
		return true;
	}

}
