package restController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Cubo;
import utils.CuboUtils;

public class CubeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String[] MOVIMIENTOS = {
		"U", "U2", "U'", "D", "D2", "D'", "R", "R2", "R'", "L", "L2", "L'", "F", "F2", "F'", "B", "B2", "B'"
	};

    public CubeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		String scramble;
		
		switch (path) {
		case "/generateScramble":
			scramble = generarScramble();
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
	
	private String generarScramble() {
		List<String> movimientos = new ArrayList<>();
		Random random = new Random();
		String ultimoMovimiento = "";
		
		for (int i = 0; i < 20; i++) {
			String movimiento = MOVIMIENTOS[random.nextInt(MOVIMIENTOS.length)];
			
			while (movimiento.equals(ultimoMovimiento) ||
				(movimiento.startsWith("U") && ultimoMovimiento.startsWith("U")) ||
				(movimiento.startsWith("D") && ultimoMovimiento.startsWith("D")) ||
				(movimiento.startsWith("R") && ultimoMovimiento.startsWith("R")) ||
				(movimiento.startsWith("L") && ultimoMovimiento.startsWith("L")) ||
				(movimiento.startsWith("F") && ultimoMovimiento.startsWith("F")) ||
				(movimiento.startsWith("B") && ultimoMovimiento.startsWith("B"))) {
					movimiento = MOVIMIENTOS[random.nextInt(MOVIMIENTOS.length)];
			}
			
			movimientos.add(movimiento);
			ultimoMovimiento = movimiento;
		}
		
		return String.join(" ", movimientos);
	}

}
