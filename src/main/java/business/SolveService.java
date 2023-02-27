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
}
