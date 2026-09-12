package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlogController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            request.getRequestDispatcher("/WEB-INF/views/blog/index.jsp").forward(request, response);
            return;
        }

        switch (path) {
            case "/que-es-ao5-ao12-ao100":
                request.getRequestDispatcher("/WEB-INF/views/blog/ao5-ao12-ao100.jsp").forward(request, response);
                break;
            case "/como-mejorar-tiempos-cubo-rubik":
                request.getRequestDispatcher("/WEB-INF/views/blog/mejorar-tiempos.jsp").forward(request, response);
                break;
            case "/mejores-cronometros-online-cubo-rubik":
                request.getRequestDispatcher("/WEB-INF/views/blog/mejores-cronometros.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}