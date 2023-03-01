package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import business.SolveService;
import models.Solve;

public class GetSelectedSolveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetSelectedSolveServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id_tiempo = Integer.parseInt(request.getParameter("id"));
		
		SolveService solveService = new SolveService();
		Solve tiempo = null;
		try {
			tiempo = solveService.getById(id_tiempo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json = new Gson().toJson(tiempo);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
