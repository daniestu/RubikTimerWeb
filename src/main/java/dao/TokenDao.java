package dao;

import models.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.AccesoProperties;
import utils.EmailUtils;

import java.sql.*;

public class TokenDao {
	private static final Logger _log = LogManager.getLogger(TokenDao.class);
	
	public Token add(Token token) {
		String sql = "INSERT INTO token (uuid, usuario_id, fecha_creacion, caducado) VALUES (?, ?, ?, 0)";
        
		int generatedId = -1;
        try (Connection connection = AccesoProperties.getDBConnection();
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
			_log.error(e.getMessage(), e);
        }
        if (generatedId != -1) {
        	token.setToken_id(generatedId);
		}
        
		return token;
	}

	public Token getByUUID(String uuid) {

		Token token = null;
		
		String sql = "SELECT token_id, uuid, usuario_id, fecha_creacion, caducado FROM token WHERE uuid = ?";
	    try (Connection connection = AccesoProperties.getDBConnection();
	    		PreparedStatement stmt = connection.prepareStatement(sql)) {
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
			_log.error(e.getMessage(), e);
		}

	    return token;
	}

	public boolean caducarTokens(Integer idUsuario) {
		String sql = "UPDATE token SET caducado = 1 WHERE usuario_id = ?";
		
        try (Connection connection = AccesoProperties.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
        	statement.setInt(1, idUsuario);
            statement.executeUpdate();
            
        } catch (SQLException e) {
			_log.error(e.getMessage(), e);
            return false;
        }
        
		return true;
	}

}
