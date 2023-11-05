package utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class AccesoProperties {
	
	public Properties cargarFicheroBBDD() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ClassLoader cl = getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream("CFG.INI");

			if (is == null) {
				throw new IllegalArgumentException("CGF.INI no encontrado!");
			}else {
				Properties prop=new Properties();
				prop.load(new InputStreamReader(is));
				return prop;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error inesperado");
		}
		
	}
	
	public Properties cargarFicheroEmail() {
		try {
			ClassLoader cl = getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream("CFG.INI");

			if (is == null) {
				throw new IllegalArgumentException("CGF.INI no encontrado!");
			}else {
				Properties prop=new Properties();
				prop.load(new InputStreamReader(is));
				return prop;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Error inesperado");
		}
		
	}
}
