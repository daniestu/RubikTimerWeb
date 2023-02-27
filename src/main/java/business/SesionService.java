package business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dao.SesionDao;
import models.Sesion;
import models.Usuario;

public class SesionService {

	public List<Sesion> getAllByUser(Usuario usuario) throws IOException, SQLException {
        SesionDao sesionDao = new SesionDao();
        List<Sesion> sesiones_todas = sesionDao.getAll();
        
        int id_usuario = usuario.getIdUsuario();
        return sesiones_todas.stream()
                .filter(sesion -> sesion.getUsuario_id() == id_usuario)
                .collect(Collectors.toList());
	}

	public Sesion getByName(String nombre_sesion, Usuario usuario) throws IOException, SQLException {
		SesionDao sesionDao = new SesionDao();
		return sesionDao.getByName(nombre_sesion, usuario.getIdUsuario());
	}

	public Sesion crearSesion(String nombre_sesion, Usuario usuario) throws IOException, SQLException {
		Sesion sesion = new Sesion();
		sesion.setNombre(nombre_sesion);
		sesion.setUsuario_id(usuario.getIdUsuario());
		
		SesionDao sesionDao = new SesionDao();
		sesion = sesionDao.add(sesion);
		return sesion;
	}
	
	public boolean deleteSesion(int id_sesion) {
		SesionDao sesionDao = new SesionDao();
		return sesionDao.remove(id_sesion);
	}
}
