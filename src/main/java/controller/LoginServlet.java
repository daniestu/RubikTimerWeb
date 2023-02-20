package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.UsuarioService;
import models.Usuario;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
    	super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Muestra la vista de inicio de sesión
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UsuarioService usuarioService = new UsuarioService();
    	
    	Usuario usuario = null;
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
        	usuario = usuarioService.verificarUsuario(username, password);
        	
        	if (usuario == null || usuario.getIdUsuario() == null) {
    			request.setAttribute("error", "Credenciales inválidas. Intente nuevamente.");
    			request.getRequestDispatcher("login.jsp").forward(request, response);
    		}else {
    			request.getSession().setAttribute("usuario", usuario);
    			response.sendRedirect("index.jsp");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ha ocurrido un error al verificar las credenciales.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
        
    }

}
