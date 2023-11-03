package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import dao.contracts.Persistencia;
import models.Solve;
import utils.AccesoBBDD;

public class SolveDao implements Persistencia<Solve>{

	@Override
	public Solve add(Solve solve) throws IOException {
		String sql = "INSERT INTO tiempos (tiempo, scramble, fecha, mas_dos, dnf, usuario_id, sesion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		
		int generatedId = -1;
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
        	statement.setString(1, solve.getTiempo());
        	statement.setString(2, solve.getScramble());
            statement.setDate(3, java.sql.Date.valueOf(formatoFecha.format(solve.getFecha())));
            statement.setBoolean(4, solve.isMas_2());
            statement.setBoolean(5, solve.isDnf());
            statement.setInt(6, solve.getUsuario_id());
            statement.setInt(7, solve.getSesion_id());
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (generatedId != -1) {
        	solve.setId(generatedId);
		}
        
		return solve;
	}

	@Override
	public List<Solve> getAll() throws IOException, SQLException {
		List<Solve> solves = new ArrayList<>();
	    String query = "SELECT * FROM tiempos";
		
	    AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String tiempo = rs.getString("tiempo");
	            String scramble = rs.getString("scramble");
	            Date fecha = rs.getDate("fecha");
	            boolean mas_2 = rs.getBoolean("mas_dos");
	            boolean dnf = rs.getBoolean("dnf");
	            int usuario_id = rs.getInt("usuario_id");
	            int sesion_id = rs.getInt("sesion_id");

	            Solve s = new Solve(id, scramble, fecha, tiempo, mas_2, dnf, usuario_id, sesion_id);
	            solves.add(s);
	        }
	    }
	    return solves;
	}
	
	public List<Solve> getAllBySesion(int id_sesion) throws SQLException {
		List<Solve> solves = new ArrayList<>();
	    String query = "SELECT * FROM tiempos WHERE sesion_id = ?";
		
	    AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
		ResultSet rs = null;
		try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
			PreparedStatement statement = conn.prepareStatement(query)){
			
			statement.setInt(1, id_sesion);
	    	rs = statement.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String tiempo = rs.getString("tiempo");
	            String scramble = rs.getString("scramble");
	            Date fecha = rs.getDate("fecha");
	            boolean mas_2 = rs.getBoolean("mas_dos");
	            boolean dnf = rs.getBoolean("dnf");
	            int usuario_id = rs.getInt("usuario_id");
	            int sesion_id = rs.getInt("sesion_id");

	            Solve s = new Solve(id, scramble, fecha, tiempo, mas_2, dnf, usuario_id, sesion_id);
	            solves.add(s);
	        }
	    } finally {
	    	if (rs != null) {
	            rs.close();
	        }
	    }
	    return solves;
	}

	public boolean deleteBySesion(Integer id_sesion) {
		String sql = "DELETE FROM tiempos WHERE sesion_id = ?";
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
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

	public Solve getById(int id_tiempo) throws SQLException {
		String query = "SELECT * FROM tiempos WHERE id = ?";
	    
	    AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
		Solve solve = null;
		ResultSet rs = null;
	    try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
    		PreparedStatement statement = conn.prepareStatement(query)) {
	    	
	    	statement.setInt(1, id_tiempo);
	    	rs = statement.executeQuery();
	        if (rs.next()) {
	            solve = new Solve(rs.getInt("id"), rs.getString("scramble"), rs.getDate("fecha"), rs.getString("tiempo"), rs.getBoolean("mas_dos"), rs.getBoolean("dnf"), rs.getInt("usuario_id"), rs.getInt("sesion_id"));
	        }
	    }finally {
	    	if (rs != null) {
	            rs.close();
	        }
	    }

	    return solve;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM tiempos WHERE id = ?";
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateMas2(int id, int action) {
		String sql = "UPDATE tiempos SET mas_dos = " + action + ", dnf = 0 WHERE id = ?";
		
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateDnf(int id, int action) {
		String sql = "UPDATE tiempos SET dnf = " + action + ", mas_dos = 0 WHERE id = ?";
		
		AccesoBBDD accesoBBDD = new AccesoBBDD();
		Properties prop = accesoBBDD.cargarFichero();
		
        try (Connection connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
