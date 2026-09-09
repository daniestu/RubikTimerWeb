package restController;

import business.UsuarioService;
import models.Conf;
import models.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ConfController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/save":
                    HttpSession session = request.getSession(false);
                    Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

                    int tema = (request.getParameter("config-theme") != null) ? Integer.parseInt(request.getParameter("config-theme")) : 1;
                    int idioma = (request.getParameter("config-lang") != null) ? Integer.parseInt(request.getParameter("config-lang")) : 1;

                    if (usuario != null) {
                        UsuarioService usuarioService = new UsuarioService();

                        boolean ocultarElementos = request.getParameter("config-hide-elements") != null;
                        boolean ocultarPreview = request.getParameter("config-hide-preview") != null;
                        boolean pulsacionLarga = request.getParameter("config-long-pulse") != null;
                        boolean cronometroRaton = request.getParameter("config-mouse-timer") != null;
                        boolean tiempoInspeccion = request.getParameter("config-inspect-time") != null;
                        int segundosInspeccion = (request.getParameter("config-inspect-sec") != null && !request.getParameter("config-inspect-sec").isEmpty())
                                ? Integer.parseInt(request.getParameter("config-inspect-sec")) : 0;

                        Conf conf = new Conf(tema, idioma, (ocultarElementos) ? 1 : 0, (ocultarPreview) ? 1 : 0,
                                (pulsacionLarga) ? 1 : 0, (cronometroRaton) ? 1 : 0,
                                (tiempoInspeccion) ? 1 : 0, segundosInspeccion);

                        usuarioService.actualizarConfiguracionUsuario(usuario, conf);
                    } else {
                        // invitado: idioma y tema van en cookie (el servidor los necesita para renderizar),
                        // el resto de preferencias las gestiona el propio navegador
                        String path_cookie = request.getContextPath().isEmpty() ? "/" : request.getContextPath();

                        Cookie cookieIdioma = new Cookie("guestIdioma", String.valueOf(idioma));
                        cookieIdioma.setPath(path_cookie);
                        cookieIdioma.setMaxAge(60 * 60 * 24 * 365); // 1 año
                        response.addCookie(cookieIdioma);

                        Cookie cookieTema = new Cookie("guestTema", String.valueOf(tema));
                        cookieTema.setPath(path_cookie);
                        cookieTema.setMaxAge(60 * 60 * 24 * 365);
                        response.addCookie(cookieTema);
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                    break;

                default:
                    break;
            }
        }
    }
}