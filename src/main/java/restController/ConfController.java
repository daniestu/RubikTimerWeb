package restController;

import business.UsuarioService;
import models.Conf;
import models.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

                    if (session != null && session.getAttribute("usuario") != null) {
                        UsuarioService usuarioService = new UsuarioService();
                        Usuario usuario = (Usuario) session.getAttribute("usuario");

                        int tema = (request.getParameter("config-theme") != null) ? Integer.parseInt(request.getParameter("config-theme")) : 1;
                        int idioma = (request.getParameter("config-lang") != null) ? Integer.parseInt(request.getParameter("config-lang")) : 1;
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
                    }

                    // Redireccion dinamica segun el contextPath de la aplicacion
                    String contextPath = request.getContextPath();
                    response.sendRedirect(contextPath.isEmpty() ? "/" : contextPath);
                    break;

                default:
                    break;
            }
        }
    }
}