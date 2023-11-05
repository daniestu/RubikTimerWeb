package restController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Cubo;
import utils.CuboUtils;

public class CubeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public CubeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		String scramble;
		
		switch (path) {
		case "/generateScramble":
			scramble = CuboUtils.generarScramble();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(scramble);
			break;
		case "/generateCube":
			scramble = request.getParameter("scramble");
			
			CuboUtils cuboUtils = new CuboUtils();
			Cubo cubo = cuboUtils.generarCubo(scramble);
			
			String json = new Gson().toJson(cubo);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		default:
			break;

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
