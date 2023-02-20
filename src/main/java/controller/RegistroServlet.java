package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.UsuarioService;
import models.Usuario;

public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public RegistroServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("confirm-password");
        String correo = request.getParameter("correo");
        
        if (!password.equals(passwordConfirmation)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        Usuario usuario = new Usuario(username, password, correo);
        UsuarioService usuarioService = new UsuarioService();
    	
        try {
        	if (usuarioService.usuarioExiste(usuario, 0)) {
            	request.setAttribute("error", "El nombre de usuario ya existe.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
    		}
            
            if (usuarioService.usuarioExiste(usuario, 1)) {
            	request.setAttribute("error", "El correo introducido ya existe.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
    		}
            
			usuario = usuarioService.registrarUsuario(usuario);
			
			if (usuario.getIdUsuario() == -1) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ha ocurrido un error al registrar el usuario.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
		}
    	
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);
        response.sendRedirect("index.jsp");
    }

}
