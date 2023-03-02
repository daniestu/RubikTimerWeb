package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Cubo;
import utils.CuboUtils;

public class EstablecerCuboServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EstablecerCuboServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String scramble = request.getParameter("scramble");
		
		CuboUtils cuboUtils = new CuboUtils();
		Cubo cubo = cuboUtils.generarCubo(scramble);
		
		String json = new Gson().toJson(cubo);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
