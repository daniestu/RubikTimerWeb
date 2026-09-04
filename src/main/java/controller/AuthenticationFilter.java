package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.UsuarioService;
import models.Usuario;
import utils.CoockieHandler;
import utils.UserUtils;

public class AuthenticationFilter implements Filter {

	private List<String> allowedPages;

	public AuthenticationFilter() {
		super();
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		allowedPages = new ArrayList<>();

		allowedPages.add("/user/login");
		allowedPages.add("/user/register");
		allowedPages.add("/user/checkAuthentication");
		allowedPages.add("/user/forgotPassword");
		allowedPages.add("/user/resetPassword");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();

		// Extraemos la ruta relativa omitiendo el context path
		String path = uri.substring(contextPath.length());

		// Comprobamos si la ruta o recurso estatico esta permitido
		if (allowedPages.contains(path) || isStaticResource(path)) {
			chain.doFilter(request, response);
			return;
		}

		// Comprobamos sesion activa
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("usuario") != null) {
			chain.doFilter(request, response);
			return;
		}

		// Si no hay sesion, intentamos autenticacion por cookies
		String username = CoockieHandler.findCookie(req, res, "RubikTimerUsername");
		String password = CoockieHandler.findCookie(req, res, "RubikTimerPassword");

		if (username != null && password != null) {
			try {
				UsuarioService usuarioService = new UsuarioService();
				Usuario usuario = usuarioService.verificarUsuario(username, UserUtils.encryptPassword(password));

				if (usuario != null && usuario.getIdUsuario() != null) {
					req.getSession().setAttribute("usuario", usuario);
					chain.doFilter(request, response);
					return;
				}
			} catch (Exception e) {
				// Falla en verificación de usuario por cookie
			}
		}

		res.sendRedirect(contextPath + "/user/login");
		*/

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// Comprobamos sesion activa
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("usuario") != null) {
			chain.doFilter(request, response);
			return;
		}

		// Si no hay sesion, intentamos autenticacion por cookies
		String username = CoockieHandler.findCookie(req, res, "RubikTimerUsername");
		String password = CoockieHandler.findCookie(req, res, "RubikTimerPassword");

		if (username != null && password != null) {
			try {
				UsuarioService usuarioService = new UsuarioService();
				Usuario usuario = usuarioService.verificarUsuario(username, UserUtils.encryptPassword(password));

				if (usuario != null && usuario.getIdUsuario() != null) {
					req.getSession().setAttribute("usuario", usuario);
					chain.doFilter(request, response);
					return;
				}
			} catch (Exception e) {
				// Falla en verificación de usuario por cookie
			}
		}

		chain.doFilter(request, response);
	}

	private boolean isStaticResource(String path) {
		return path.startsWith("/css/") ||
				path.startsWith("/js/") ||
				path.startsWith("/images/") ||
				path.startsWith("/bootstrap/") ||
				path.endsWith(".css") ||
				path.endsWith(".js") ||
				path.endsWith(".ico") ||
				path.endsWith(".png") ||
				path.endsWith(".jpg");
	}

	@Override
	public void destroy() {
	}
}