package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoProperties {

	public static Connection getDBConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver MySQL no encontrado", e);
		}

		String host = getRequiredEnv("DB_HOST");
		String port = getRequiredEnv("DB_PORT");
		String dbName = getRequiredEnv("DB_NAME");
		String user = getRequiredEnv("DB_USER");
		String password = getRequiredEnv("DB_PASSWORD");

		String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName
				+ "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		return DriverManager.getConnection(jdbcUrl, user, password);
	}

	public static String getRequiredEnv(String varName) {
		String value = System.getenv(varName);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException("Error de configuracion de correo: La variable de entorno '" + varName + "' no esta definida.");
		}
		return value;
	}
}
