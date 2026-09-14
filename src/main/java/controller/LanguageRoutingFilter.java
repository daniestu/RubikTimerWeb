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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageRoutingFilter implements Filter {

    private static final Pattern LANG_PATTERN = Pattern.compile("^/(en|es|fr|de|it|pt|zh|ar|ru|ja|ko)(/.*)?$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        Matcher matcher = LANG_PATTERN.matcher(path);
        if (matcher.matches()) {
            String codigoDetectado = matcher.group(1);
            String subPath = matcher.group(2);
            String resto = (subPath == null || subPath.isEmpty()) ? "/" : subPath;

            if (resto.equals("/") || resto.equals("/timer")) {
                request.setAttribute("idiomaForzado", IdiomaHelper.getIdiomaDesdeCodigoUrl(codigoDetectado));
                req.getRequestDispatcher(resto).forward(request, response);
                return;
            }
        }

        if (path.equals("/") || path.equals("") || path.equals("/timer")) {
            int idiomaSugerido = adivinarIdioma(req);
            String codigo = IdiomaHelper.getCodigoUrl(idiomaSugerido);
            res.sendRedirect(req.getContextPath() + "/" + codigo + (path.equals("/timer") ? "/timer" : ""));
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
            // seguimos con el resto de señales
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
        if (localeNavegador != null && localeNavegador.getLanguage() != null) {
            return IdiomaHelper.getIdiomaDesdeCodigoUrl(localeNavegador.getLanguage());
        }

        return 1;
    }

    @Override
    public void destroy() {}
}