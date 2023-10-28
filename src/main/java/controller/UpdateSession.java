package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.SesionService;
import models.Sesion;
import models.Usuario;

public class UpdateSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public UpdateSession() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Usuario user = (Usuario) session.getAttribute("usuario");
		String name = request.getParameter("name");
		String newName = request.getParameter("newName");
		
		SesionService sesionService = new SesionService();
		boolean ok = false;
		
		try {
			Sesion sesion = sesionService.getByName(name, user);
			sesion.setNombre(newName);
			
			ok = sesionService.updateSession(sesion);
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write((ok) ? "true" : "false");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
