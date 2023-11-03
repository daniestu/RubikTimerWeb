package restController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import business.SesionService;
import business.SolveService;
import models.Sesion;
import models.Usuario;

public class SolveController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SolveController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		String nombre_sesion = request.getParameter("sesion");
		
		SesionService sesionService = new SesionService();
		SolveService solveService = new SolveService();
		
		Map<String, Boolean> resultado = new HashMap<>();
		
		Sesion sesion = null;
		String json;
		int id;
		int action;
		boolean ok;
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/delete":
			id = Integer.parseInt(request.getParameter("id"));
			boolean eliminado = solveService.eliminar(id);
			
			resultado.put("eliminado", eliminado);
			
			json = new Gson().toJson(resultado);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
			
		case "/get":
			List<models.Solve> solves = null;
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				solves = solveService.getAll(sesion);
			} catch (Exception e) {
				if (usuario != null) {
					e.printStackTrace();
				}
			}
			
			if (usuario == null) {
				json = "{\"usuario\":\"nulo\"}";
			}else {
				json = new Gson().toJson(solves);
			}
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case "/save":
			try {
				String tiempo = request.getParameter("tiempo");
				String scramble = request.getParameter("scramble");
				String sesionName = request.getParameter("sesion");
				Date fecha = new Date();
				boolean mas_dos = false;
				boolean dnf = false;
				
				sesion =sesionService.getByName(sesionName, usuario);
				
				models.Solve solve = new models.Solve(null, scramble, fecha, tiempo, mas_dos, dnf, usuario.getIdUsuario(), sesion.getId());
				
				solveService.registrarSolve(solve);
				
				response.setContentType("text/plain");
				response.getWriter().write("El tiempo se ha guardado correctamente.");
			}catch (Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.setContentType("text/plain");
				response.getWriter().write("Ha ocurrido un error al guardar el tiempo.");
			}
			break;
		case "/updateMas2":
			id = Integer.parseInt(request.getParameter("id"));
			action = Integer.parseInt(request.getParameter("action"));
			
			ok = solveService.updateMas2(id, action);
			resultado.put("actualizado", ok);
			
			json = new Gson().toJson(resultado);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case "/updateDnf":
			id = Integer.parseInt(request.getParameter("id"));
			action = Integer.parseInt(request.getParameter("action"));
			
			ok = solveService.updateDnf(id, action);	
			resultado.put("actualizado", ok);
			
			json = new Gson().toJson(resultado);
			
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
