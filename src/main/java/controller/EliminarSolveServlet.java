package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import business.SolveService;

public class EliminarSolveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EliminarSolveServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		SolveService solveService = new SolveService();
		boolean eliminado = solveService.eliminar(id);
		
		Map<String, Boolean> resultado = new HashMap<>();
		resultado.put("eliminado", eliminado);
		
		String json = new Gson().toJson(resultado);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
