package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.UsuarioService;
import models.Usuario;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/login":
			request.getRequestDispatcher("../login.jsp").forward(request, response);
			break;
		case "/logout":
			HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();
		    }
		    
		    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Expires", "0");

		    response.sendRedirect("login");
			break;
		case "/register":
			request.getRequestDispatcher("../register.jsp").forward(request, response);
			break;
		case "/checkAuthentication":
			boolean isAuthenticated = (request.getSession().getAttribute("usuario") != null);
			
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write("{\"authenticated\":" + isAuthenticated + "}");
	        
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Usuario usuario;
        UsuarioService usuarioService = new UsuarioService();
        
		String path = request.getPathInfo();
		
		switch (path) {
		case "/login":
	    	usuario = null;
	        
	        try {
	        	usuario = usuarioService.verificarUsuario(username, password);
	        	
	        	if (usuario == null || usuario.getIdUsuario() == null) {
	    			request.setAttribute("error", "Credenciales inválidas. Intente nuevamente.");
	    			request.getRequestDispatcher("../login.jsp").forward(request, response);
	    		}else {
	    			request.getSession().setAttribute("usuario", usuario);
	    			
	    			Cookie usernameCookie = new Cookie("RubikTimerUsername", username);
	            	usernameCookie.setMaxAge(7 * 24 * 60 * 60);
	            	response.addCookie(usernameCookie);

	            	Cookie passwordCookie = new Cookie("RubikTimerPassword", password);
	            	passwordCookie.setMaxAge(7 * 24 * 60 * 60);
	            	response.addCookie(passwordCookie);
	            	
	            	response.sendRedirect("../");
	    		}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Ha ocurrido un error al verificar las credenciales.");
				request.getRequestDispatcher("../login.jsp").forward(request, response);
			}
			break;
		case "/logout":
			doGet(request, response);
			break;
		case "/register":
	        String passwordConfirmation = request.getParameter("confirm-password");
	        String correo = request.getParameter("correo");
	        
	        if (!password.equals(passwordConfirmation)) {
	            request.setAttribute("error", "Las contraseñas no coinciden.");
	            request.getRequestDispatcher("../register.jsp").forward(request, response);
	            return;
	        }
	        
	        usuario = new Usuario(username, password, correo);
	    	
	        try {
	        	if (usuarioService.usuarioExiste(usuario, 0)) {
	            	request.setAttribute("error", "El nombre de usuario ya existe.");
	                request.getRequestDispatcher("../register.jsp").forward(request, response);
	                return;
	    		}
	            
	            if (usuarioService.usuarioExiste(usuario, 1)) {
	            	request.setAttribute("error", "El correo introducido ya existe.");
	                request.getRequestDispatcher("../register.jsp").forward(request, response);
	                return;
	    		}
	            
				usuario = usuarioService.registrarUsuario(usuario);
				
				if (usuario.getIdUsuario() == -1) {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Ha ocurrido un error al registrar el usuario.");
	            request.getRequestDispatcher("../register.jsp").forward(request, response);
	            return;
			}
	    	
	        HttpSession session = request.getSession();
	        session.setAttribute("usuario", usuario);
	        response.sendRedirect("../");
			break;
		default:
			break;
		}
	}

}
