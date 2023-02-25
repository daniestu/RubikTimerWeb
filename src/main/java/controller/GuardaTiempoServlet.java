package controller;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.SesionService;
import business.SolveService;
import models.Sesion;
import models.Solve;
import models.Usuario;

public class GuardaTiempoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		  
		try {
			String tiempo = request.getParameter("tiempo");
			String scramble = request.getParameter("scramble");
			String sesion = request.getParameter("sesion");
			Date fecha = new Date();
			boolean mas_dos = false;
			boolean dnf = false;
			
			SesionService sesionService = new SesionService();
			Sesion s =sesionService.getByName(sesion, usuario);
			
			Solve solve = new Solve(null, scramble, fecha, tiempo, mas_dos, dnf, usuario.getIdUsuario(), s.getId());
			
			SolveService solveService = new SolveService();
			solveService.registrarSolve(solve);
			
			response.setContentType("text/plain");
			response.getWriter().write("El tiempo se ha guardado correctamente.");
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().write("Ha ocurrido un error al guardar el tiempo.");
		}
	}
}