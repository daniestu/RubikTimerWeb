package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Estadisticas;
import models.Solve;
import utils.SesionUtils;

public class GetEstadisticasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetEstadisticasServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    String jsonString = sb.toString();
	    
	    Type tiempoListType = new TypeToken<ArrayList<Solve>>() {}.getType();
	    List<Solve> tiempos = new Gson().fromJson(jsonString, tiempoListType);
	    
	    SesionUtils utils = new SesionUtils();
	    Estadisticas estadisticas = utils.getEstadisticas(tiempos);
	    
	    String json = new Gson().toJson(estadisticas);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

}
