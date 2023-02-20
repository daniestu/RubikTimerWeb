package business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.UsuarioDao;
import models.Usuario;

public class UsuarioService {

	public Usuario verificarUsuario(String nombre, String pwd) throws IOException, SQLException{
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.getByUsernamePwd(nombre, pwd);
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
}
