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
        HttpSession session = req.getSession(false);
        UsuarioService usuarioService = new UsuarioService();

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.equals("/") || path.equals("")) {
            Usuario usuario = null;
            Conf conf;
            try {
                if (session != null) {
                    usuario = (Usuario) session.getAttribute("usuario");
                }

                conf = usuarioService.getConfiguracionUsuario(usuario);

                if (usuario == null) {
                    conf = aplicarPreferenciasInvitado(req, conf);
                }
            } catch (Exception e) {
                conf = UserUtils.getDefaultConf();
            }

            TemaConfig config = TemaHelper.getConfig(conf.getTema());
            Locale locale = IdiomaHelper.getLocale(conf.getIdioma());
            int idiomaNavegador = IdiomaHelper.getIdioma(request.getLocale());
            request.setAttribute("idiomaNavegador", idiomaNavegador);
            request.setAttribute("locale", locale);
            request.setAttribute("temaConfig", config);
            request.setAttribute("conf", conf);
            request.setAttribute("esInvitado", usuario == null);
        }

        chain.doFilter(request, response);
    }

    private Conf aplicarPreferenciasInvitado(HttpServletRequest request, Conf conf) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return conf;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("guestIdioma")) {
                conf.setIdioma(Integer.parseInt(cookie.getValue()));
            } else if (cookie.getName().equals("guestTema")) {
                conf.setTema(Integer.parseInt(cookie.getValue()));
            }
        }

        return conf;
    }

    @Override
    public void destroy() {}
}
