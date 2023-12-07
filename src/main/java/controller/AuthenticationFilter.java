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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.UsuarioService;
import models.Usuario;
import utils.UserUtils;

public class AuthenticationFilter implements Filter {
	
	private List<String> allowedPages;

    public AuthenticationFilter() {
        super();
    }
    
	public void destroy() {
		
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        
        String url = req.getRequestURI();
        
        boolean isAllowed = allowedPages.contains(url);
        
        if (!isAllowed) {
        	if (session == null || session.getAttribute("usuario") == null) {
        		
        		HttpServletRequest httpRequest = (HttpServletRequest) request;
        		HttpServletResponse httpResponse = (HttpServletResponse) response;
        		
        		Cookie[] cookies = httpRequest.getCookies();
        		String username = null;
        		String password = null;
        		if (cookies != null) {
        		    for (Cookie cookie : cookies) {
        		    	//System.out.println("dominio: " + cookie.getDomain() + ", nombre: " + cookie.getName() + ", Valor: " + cookie.getValue() + ", Path: " + cookie.getPath() + ", version: " + cookie.getVersion() + ", Max age: " + cookie.getMaxAge() + ", Comment: " + cookie.getComment());
        		        if (cookie.getName().equals("RubikTimerUsername")) {
        		            username = cookie.getValue();
        		        } else if (cookie.getName().equals("RubikTimerPassword")) {
        		            password = cookie.getValue();
        		        }
        		    }
        		}
        		
        		if (username != null && password != null) {
					
        			Usuario usuario = null;
        			UsuarioService usuarioService = new UsuarioService();
        			
        	        try {
        	        	usuario = usuarioService.verificarUsuario(username, UserUtils.encryptPassword(password));
        	        	
        	        	if (usuario != null && usuario.getIdUsuario() != null) {
        	    			httpRequest.getSession().setAttribute("usuario", usuario);
        	            	chain.doFilter(request, response);
        	    		}else {
        	    			res.sendRedirect("/rubikTimerWeb/user/login");
        	    		}
        			} catch (Exception e) {
        				res.sendRedirect("/rubikTimerWeb/user/login");
        			}
        	        
				}else {
	            	res.sendRedirect("/rubikTimerWeb/user/login");
				}
        		
            } else {
                chain.doFilter(request, response);
            }
		}else {
			chain.doFilter(request, response);
		}
    }

	public void init(FilterConfig fConfig) throws ServletException {
		allowedPages = new ArrayList<String>();
		
		allowedPages.add("/rubikTimerWeb/user/login");
		allowedPages.add("/rubikTimerWeb/user/register");
		allowedPages.add("/rubikTimerWeb/js/login.js");
		allowedPages.add("/rubikTimerWeb/css/loginStyles.css");
		allowedPages.add("/rubikTimerWeb/user/images/favicon.ico");
		allowedPages.add("/rubikTimerWeb/images/favicon.ico");
		allowedPages.add("/rubikTimerWeb/user/checkAuthentication");
		allowedPages.add("/rubikTimerWeb/user/forgotPassword");
		allowedPages.add("/rubikTimerWeb/user/resetPassword");
	}

}
