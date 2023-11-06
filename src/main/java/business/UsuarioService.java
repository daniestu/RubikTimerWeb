package business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.UsuarioDao;
import models.Usuario;

public class UsuarioService {

	public Usuario verificarUsuario(String nombre, String pwd) throws IOException, SQLException{
		UsuarioDao usuarioDao = new UsuarioDao();
		
		Usuario usuario = usuarioDao.getByUsernamePwd(nombre, pwd);
		if (usuario == null || usuario.getIdUsuario() == null) {
			usuario = usuarioDao.getByEmailPwd(nombre, pwd);
		}
		
		return usuario;
	}
	
	public boolean usuarioExiste(Usuario usuario, int accion) throws IOException, SQLException{
		UsuarioDao usuarioDao = new UsuarioDao();
		List <Usuario> usuarios = usuarioDao.getAll();
		
		boolean existe = false;
		
		for (Usuario user : usuarios) {
			if (accion == 0) {
				if (usuario.getNombreUsuario().toUpperCase().equals(user.getNombreUsuario().toUpperCase())) {
					existe = true;
				}
			}else if (accion == 1) {
				if (usuario.getCorreo().toUpperCase().equals(user.getCorreo().toUpperCase())) {
					existe = true;
				}
			}
		}
		return existe;
	}
	
	public Usuario registrarUsuario(Usuario usuario) throws IOException {
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.add(usuario);
	}
	
	public Usuario getByEmail(String correo) throws SQLException {
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.getByEmail(correo);
	}

	public boolean restablecerContraseña(Integer usuarioId, String password) {
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.restablecerContraseña(usuarioId, password);
	}
}
