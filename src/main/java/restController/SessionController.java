package restController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import business.SesionService;
import business.SolveService;
import models.Estadisticas;
import models.Sesion;
import models.Solve;
import models.Usuario;
import utils.SesionUtils;

public class SessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SessionController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String nombre_sesion = request.getParameter("sesion");
		
		SesionService sesionService = new SesionService();
		SolveService solveService = new SolveService();
		
		Sesion sesion = null;
		String json;
		boolean ok = false;
		
		Map<String, Boolean> resultado = new HashMap<>();
		
		String path = request.getPathInfo();
		
		switch (path) {
		case "/add":
			try {
				sesion = sesionService.crearSesion(nombre_sesion, usuario);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			json = new Gson().toJson(sesion);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
			break;
		case "/delete":
			boolean solvesBorrados = false;
			boolean sesionBorrada = false;
			
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				solvesBorrados = solveService.deleteBySesion(sesion.getId());
				sesionBorrada = sesionService.deleteSesion(sesion.getId());
				
				if (solvesBorrados && sesionBorrada) {
					ok = true;
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			
			resultado.put("eliminado", ok);
		
			json = new Gson().toJson(resultado);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
			break;
		case "/get":
			List<Sesion> sesiones = null;
			try {
				sesiones = sesionService.getAllByUser(usuario);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			
			json = new Gson().toJson(sesiones);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
			break;
		case "/update":
			String name = request.getParameter("name");
			String newName = request.getParameter("newName");
			
			try {
				sesion = sesionService.getByName(name, usuario);
				sesion.setNombre(newName);
				
				ok = sesionService.updateSession(sesion);
				
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write((ok) ? "true" : "false");
			
			break;
		case "/getData":
		case "/import":
			doPost(request, response);
			break;
		case "/updateDefault":
			sesionService.updateDefault(nombre_sesion, usuario);
			break;
		case "/export":
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
				List<Solve> solves = solveService.getAll(sesion);
				
		        response.setContentType("text/csv");
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + sesion.getNombre() + ".csv\"");
		        
		        StringBuilder csvContent = new StringBuilder();
		        int i = 0;
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        for (Solve solve : solves) {
		        	i++;
		        	
		        	csvContent.append(i + ";" + solve.getScramble() + ";" + sdf.format(solve.getFecha()) + ";" + solve.getTiempo() + ";" + ((solve.isMas_2()) ? "1" : "0") + ";" + ((solve.isDnf()) ? "1" : "0"));
		        	csvContent.append("\n");
				}
	        	
		        try (PrintWriter writer = response.getWriter()) {
		            writer.write(csvContent.toString());
		        }
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		String line;
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		switch (path) {
		case "/add":
		case "/delete":
		case "/get":
		case "/update":
		case "/updateDefault":
		case "/export":
			doGet(request, response);
			break;
		case "/getData":
			BufferedReader reader = request.getReader();
		    StringBuilder sb = new StringBuilder();
		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		    String jsonString = sb.toString();
		    
		    Type tiempoListType = new TypeToken<ArrayList<Solve>>() {}.getType();
		    List<Solve> tiempos = new Gson().fromJson(jsonString, tiempoListType);
		    
		    SesionUtils utils = new SesionUtils();
		    Estadisticas estadisticas = utils.getEstadisticas(tiempos);
		    
		    String json = new Gson().toJson(estadisticas);
			
			response.getWriter().write(json);
			break;
		case "/import":
	        Part filePart = request.getPart("importFile");
	        HttpSession session = request.getSession(false);
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			String nombre_sesion = request.getParameter("sesion");
			
			SesionService sesionService = new SesionService();
			Sesion sesion;
			
			Map<String, Boolean> resultado = new HashMap<>();
			try {
				sesion = sesionService.getByName(nombre_sesion, usuario);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
				return;
			}
			
			SolveService solveService = new SolveService();
			
			if (sesion == null || sesion.getId() == null || sesion.getId() == 0) {
				resultado.put("importado", false);
				json = new Gson().toJson(resultado);
				response.getWriter().write(json);
				return;
			}
			
			boolean formatoCorrecto = SesionUtils.verificarFicheroImportacion(filePart);
			
			if (!formatoCorrecto) {
				resultado.put("importado", false);
				json = new Gson().toJson(resultado);
				response.getWriter().write(json);
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
	        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
	            while ((line = bfr.readLine()) != null) {
	            	String[] parts = line.split(";");
	                Solve solve = new Solve();
	                solve.setScramble(parts[1]);
	                solve.setFecha(sdf.parse(parts[2]));
	                solve.setTiempo(parts[3]);
	                solve.setMas_2( (parts[4].equals("0")) ? false : true );
	                solve.setDnf( (parts[5].equals("0")) ? false : true );
	                solve.setUsuario_id(usuario.getIdUsuario());
	                solve.setSesion_id(sesion.getId());
	                
	                solveService.registrarSolve(solve);
	            }
	        } catch (ParseException e) {
				e.printStackTrace();
				resultado.put("importado", false);
				json = new Gson().toJson(resultado);
				response.getWriter().write(json);
				return;
			}
	        
	        resultado.put("importado", true);
			json = new Gson().toJson(resultado);
			response.getWriter().write(json);
	        
			break;
		default:
			break;
		}
	}

}
