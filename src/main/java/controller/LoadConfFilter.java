package controller;

import business.UsuarioService;
import models.Conf;
import models.TemaConfig;
import models.Usuario;
import utils.IdiomaHelper;
import utils.TemaHelper;
import utils.UserUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LoadConfFilter implements Filter {

    public LoadConfFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        UsuarioService usuarioService = new UsuarioService();

        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean esRaiz = path.equals("/") || path.equals("");
        boolean esBlog = path.equals("/blog") || path.startsWith("/blog/");

        if (esRaiz || path.equals("/timer") || esBlog) {
            Usuario usuario = null;
            if (session != null) {
                usuario = (Usuario) session.getAttribute("usuario");
            }

            if (esRaiz && usuario != null) {
                ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/timer");
                return;
            }

            Conf conf;
            try {
                conf = usuarioService.getConfiguracionUsuario(usuario);
                if (usuario == null) {
                    conf = aplicarPreferenciasInvitado(req, resp, conf);
                }
            } catch (Exception e) {
                conf = UserUtils.getDefaultConf();
            }

            Integer idiomaForzado = (Integer) request.getAttribute("idiomaForzado");
            if (idiomaForzado != null) {
                conf.setIdioma(idiomaForzado);
            }
            TemaConfig config = TemaHelper.getConfig(conf.getTema());
            Locale locale = IdiomaHelper.getLocale(conf.getIdioma());
            int idiomaNavegador = IdiomaHelper.getIdioma(request.getLocale());
            request.setAttribute("idiomaNavegador", idiomaNavegador);
            request.setAttribute("codigoIdioma", IdiomaHelper.getCodigoUrl(conf.getIdioma()));
            request.setAttribute("locale", locale);
            request.setAttribute("temaConfig", config);
            request.setAttribute("conf", conf);
            request.setAttribute("esInvitado", usuario == null);
        }

        chain.doFilter(request, response);
    }

    private Conf aplicarPreferenciasInvitado(HttpServletRequest request, HttpServletResponse response, Conf conf) {
        Cookie[] cookies = request.getCookies();
        boolean existeCookieIdioma = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("guestIdioma".equals(cookie.getName())) {
                    try {
                        conf.setIdioma(Integer.parseInt(cookie.getValue()));
                        existeCookieIdioma = true;
                    } catch (NumberFormatException e) {
                        // Si la cookie es inválida, se ignorará para recrearla
                    }
                } else if ("guestTema".equals(cookie.getName())) {
                    try {
                        conf.setTema(Integer.parseInt(cookie.getValue()));
                    } catch (NumberFormatException e) {
                        // Ignorar si no es numérico
                    }
                }
            }
        }

        // Si no se encuientra la cookie "guestIdioma", la creamos con el idioma del navegador
        if (!existeCookieIdioma) {
            Locale localeNavegador = request.getLocale();
            int idiomaDetectado = 1;

            if (localeNavegador != null && localeNavegador.getLanguage() != null) {
                idiomaDetectado = IdiomaHelper.getIdiomaDesdeCodigoUrl(localeNavegador.getLanguage());
            }

            conf.setIdioma(idiomaDetectado);

            Cookie cookieIdioma = new Cookie("guestIdioma", String.valueOf(idiomaDetectado));
            cookieIdioma.setPath("/");
            cookieIdioma.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(cookieIdioma);
        }

        return conf;
    }

    @Override
    public void destroy() {}
}