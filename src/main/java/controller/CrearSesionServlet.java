package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.SesionService;
import models.Sesion;
import models.Usuario;

public class CrearSesionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CrearSesionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String nombre_sesion = request.getParameter("sesion");
		
		SesionService sesionService = new SesionService();
		Sesion sesion = null;
		try {
			sesion = sesionService.crearSesion(nombre_sesion, usuario);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		String json = new Gson().toJson(sesion);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
