package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.SesionService;
import business.SolveService;
import models.Sesion;
import models.Usuario;

public class BorrarSesionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BorrarSesionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String nombre_sesion = request.getParameter("sesion");
		boolean solvesBorrados = false;
		boolean sesionBorrada = false;
		boolean ok = false;
		
		SesionService sesionService = new SesionService();
		SolveService solveService = new SolveService();
		
		Map<String, Boolean> resultado = new HashMap<>();
		
		try {
			Sesion sesion = sesionService.getByName(nombre_sesion, usuario);
			solvesBorrados = solveService.deleteBySesion(sesion.getId());
			sesionBorrada = sesionService.deleteSesion(sesion.getId());
			
			if (solvesBorrados && sesionBorrada) {
				ok = true;
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		resultado.put("eliminado", ok);
	
		String json = new Gson().toJson(resultado);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
