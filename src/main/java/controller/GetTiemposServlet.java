package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.SesionService;
import business.SolveService;
import models.Sesion;
import models.Solve;
import models.Usuario;


public class GetTiemposServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetTiemposServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String nombre_sesion = request.getParameter("sesion");
		
		SesionService sesionService = new SesionService();
		SolveService solveService = new SolveService();
		Sesion sesion = null;
		List<Solve> solves = null;
		try {
			sesion = sesionService.getByName(nombre_sesion, usuario);
			solves = solveService.getAll(sesion);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		String json = new Gson().toJson(solves);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
