package restController;

import java.io.IOException;
import java.sql.SQLException;
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
import com.google.gson.GsonBuilder;

import business.SesionService;
import business.SolveService;
import models.Sesion;
import models.Solve;
import models.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.EmailUtils;

public class SolveController extends HttpServlet {
	private static final Logger _log = LogManager.getLogger(SolveController.class);
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
		
		Map<String, Object> resultado = new HashMap<>();
		
		Sesion sesion = null;
		String json;
		int id;
		int action;
		boolean ok;
		boolean eliminado;
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/delete":
			id = Integer.parseInt(request.getParameter("id"));
			eliminado = solveService.eliminar(id);
			
			resultado.put("eliminado", eliminado);
			
			json = new Gson().toJson(resultado);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case "/delete_last":
			eliminado = false;
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				Solve solve = solveService.getLastSolveBySesion(sesion);
				eliminado = solveService.eliminar(solve.getId());
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}

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
				_log.error(e.getMessage(), e);
			}

			if (usuario == null) {
				json = "{\"usuario\":\"nulo\"}";
			}else {
				Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy").create();
				json = gson.toJson(solves);
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
				_log.error(e.getMessage(), e);
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
		case "/updateMas2_last":
			action = Integer.parseInt(request.getParameter("action"));

			ok = false;
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				Solve solve = solveService.getLastSolveBySesion(sesion);
				ok = solveService.updateMas2(solve.getId(), action);
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}

			resultado.put("actualizado", ok);

			json = new Gson().toJson(resultado);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			break;
		case "/updateDnf_last":
			action = Integer.parseInt(request.getParameter("action"));

			ok = false;
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				Solve solve = solveService.getLastSolveBySesion(sesion);
				resultado.put("tiempo_original", solve.getTiempo());
				ok = solveService.updateDnf(solve.getId(), action);
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}

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
