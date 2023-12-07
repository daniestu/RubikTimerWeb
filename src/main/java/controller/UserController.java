package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.TokenService;
import business.UsuarioService;
import models.Token;
import models.Usuario;
import utils.TokenUtils;
import utils.UserUtils;

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
		case "/forgotPassword":
			request.getRequestDispatcher("../forgotPassword.jsp").forward(request, response);
			break;
		case "/resetPassword":
			String token = request.getParameter("token");
			request.setAttribute("token", token);
			
			boolean caducado = TokenUtils.verificarCaducidad(token);
			
			request.setAttribute("caducado", (caducado) ? true : false);
			
			request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm-password");
        String correo = request.getParameter("correo");
        
        Usuario usuario;
        UsuarioService usuarioService = new UsuarioService();
        
		String path = request.getPathInfo();
		
		switch (path) {
		case "/login":
			
	    	usuario = null;
	        
	        try {
	        	usuario = usuarioService.verificarUsuario(username, UserUtils.encryptPassword(password));
	        	
	        	if (usuario == null || usuario.getIdUsuario() == null) {
	    			request.setAttribute("error", "Credenciales inválidas. Intente nuevamente.");
	    			request.getRequestDispatcher("../login.jsp").forward(request, response);
	    		}else {
	    			request.getSession().setAttribute("usuario", usuario);
	    			
	    			Cookie usernameCookie = new Cookie("RubikTimerUsername", username);
	            	usernameCookie.setMaxAge(7 * 24 * 60 * 60);
	            	usernameCookie.setComment("user to rubikTimerWeb");
	            	usernameCookie.setPath("/");
	            	//usernameCookie.setDomain("localhost:8080");
	            	response.addCookie(usernameCookie);

	            	Cookie passwordCookie = new Cookie("RubikTimerPassword", password);
	            	passwordCookie.setMaxAge(7 * 24 * 60 * 60);
	            	passwordCookie.setComment("password to rubikTimerWeb");
	            	passwordCookie.setPath("/");
	            	//passwordCookie.setDomain("localhost:8080");
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
	        if (!password.equals(passwordConfirmation)) {
	            request.setAttribute("error", "Las contraseñas no coinciden.");
	            request.getRequestDispatcher("../register.jsp").forward(request, response);
	            return;
	        }
	        
	        try {
	        	usuario = new Usuario(username, UserUtils.encryptPassword(password), correo);
	        	
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
		case "/forgotPassword":
			try {
	        	usuario = usuarioService.getByEmail(correo);
	        	
	            if (usuario == null || usuario.getIdUsuario() == null || usuario.getIdUsuario() == 0) {
	            	request.setAttribute("error", "El correo introducido no existe.");
	                request.getRequestDispatcher("../forgotPassword.jsp").forward(request, response);
	                return;
	    		}
	            
	            if (UserUtils.enviarMailConfirmacionReset(usuario)) {
	            	request.setAttribute("confirmation", "ok");
				}else {
					request.setAttribute("error", "Ha ocurrido un error al enviar el Correo.");
				}
	            
                request.getRequestDispatcher("../forgotPassword.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Ha ocurrido un error al enviar el Correo.");
	            request.getRequestDispatcher("../forgotPassword.jsp").forward(request, response);
	            return;
			}
			break;
		case "/resetPassword":
			try {
				String uuid = request.getParameter("token");
				request.setAttribute("token", uuid);
				boolean caducado = TokenUtils.verificarCaducidad(uuid);
				
				if (caducado) {
					request.setAttribute("caducado", true);
					request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
					return;
				}else {
					request.setAttribute("caducado", false);
				}
				
				if (!password.equals(passwordConfirmation)) {
		            request.setAttribute("error", "Las contraseñas no coinciden.");
		            request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
		            return;
		        }
				
				TokenService tokenService = new TokenService();
				Token token = tokenService.getByUUID(uuid);
				
				if (!usuarioService.restablecerContraseña(token.getUsuarioId(), UserUtils.encryptPassword(password))) {
					request.setAttribute("error", "Ha ocurrido un error al restablecer la contraseña.");
		            request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
		            return;
				}

				tokenService.caducarTokens(token.getUsuarioId());
				
				request.setAttribute("confirmation", "ok");
				request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Ha ocurrido un error al restablecer la contraseña.");
				request.setAttribute("caducado", false);
	            request.getRequestDispatcher("../resetPassword.jsp").forward(request, response);
	            return;
			}
			
			break;
		default:
			break;
		}
	}

}
