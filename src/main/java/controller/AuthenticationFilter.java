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
        
//        System.out.println(url + " " + ((isAllowed) ? "true" : "false"));
        
        if (!isAllowed) {
        	if (session == null || session.getAttribute("usuario") == null) {
            	res.sendRedirect("user/login");
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
	}

}
