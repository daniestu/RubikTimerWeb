package controller;

import business.UsuarioService;
import models.Conf;
import models.Usuario;
import utils.IdiomaHelper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LanguageRoutingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Se procesan todas las rutas de los 11 idiomas soportados
        if (path.matches("^/(en|es|fr|de|it|pt|zh|ar|ru|ja|ko)$")) {
            String codigo = path.substring(1);
            request.setAttribute("idiomaForzado", IdiomaHelper.getIdiomaDesdeCodigoUrl(codigo));
            req.getRequestDispatcher("/").forward(request, response);
            return;
        }

        // La raíz sin prefijo -> redirige al idioma sugerido según el visitante
        if (path.equals("/") || path.equals("")) {
            int idiomaSugerido = adivinarIdioma(req);
            String codigo = IdiomaHelper.getCodigoUrl(idiomaSugerido);
            res.sendRedirect(req.getContextPath() + "/" + codigo);
            return;
        }

        chain.doFilter(request, response);
    }

    private int adivinarIdioma(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                if (usuario != null) {
                    Conf conf = new UsuarioService().getConfiguracionUsuario(usuario);
                    return conf.getIdioma();
                }
            }
        } catch (Exception e) {
            // si falla, seguimos probando con el resto de señales
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("guestIdioma")) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        break;
                    }
                }
            }
        }

        Locale localeNavegador = request.getLocale();
        if (localeNavegador != null && localeNavegador.getLanguage().equals("es")) {
            return 2;
        }

        return 1;
    }

    @Override
    public void destroy() {}
}