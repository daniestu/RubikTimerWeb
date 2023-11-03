package business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.SolveDao;
import models.Sesion;
import models.Solve;

public class SolveService {
	public Solve registrarSolve(Solve solve) throws IOException {
		SolveDao solveDao = new SolveDao();
		return solveDao.add(solve);
	}

	public List<Solve> getAll(Sesion sesion) throws SQLException {
		SolveDao solveDao = new SolveDao();
		return solveDao.getAllBySesion(sesion.getId());
	}

	public boolean deleteBySesion(Integer id_sesion) {
		SolveDao solveDao = new SolveDao();
		return solveDao.deleteBySesion(id_sesion);
	}

	public Solve getById(int id_tiempo) throws SQLException {
		SolveDao solveDao = new SolveDao();
		return solveDao.getById(id_tiempo);
	}

	public boolean eliminar(int id) {
		SolveDao solveDao = new SolveDao();
		return solveDao.delete(id);
	}
	
	public boolean updateDnf(int id, int action) {
		SolveDao solveDao = new SolveDao();
		return solveDao.updateDnf(id, action);
	}
	
	public boolean updateMas2(int id, int action) {
		SolveDao solveDao = new SolveDao();
		return solveDao.updateMas2(id, action);
	}
}
