package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Usuario;
import utils.AccesoBBDD;

public class GuardaTiempoServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	//HttpServletRequest req = (HttpServletRequest) request;

	HttpSession session = request.getSession(false);
	Usuario usuario = (Usuario) session.getAttribute("usuario");
      
  	Date date = new Date();
  	SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
  	SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
  	
    String tiempo = request.getParameter("tiempo");
    String scramble = request.getParameter("scramble");
    String fecha = formatoFecha.format(date);
    String hora = formatoHora.format(date);
    boolean mas_dos = false;
    boolean dnf = false;
    
    AccesoBBDD accesoBBDD = new AccesoBBDD();
	Properties prop = accesoBBDD.cargarFichero();
	
    String url = prop.getProperty("url");
    String user = prop.getProperty("username");
    String password = prop.getProperty("password");
    Connection con = null;
    try {
      con = DriverManager.getConnection(url, user, password);

      // Inserta el tiempo en la base de datos
      String query = "INSERT INTO tiempos (tiempo, scramble, fecha, hora, mas_dos, dnf, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(query);
      ps.setString(1, tiempo);
      ps.setString(2, scramble);
      ps.setDate(3, java.sql.Date.valueOf(fecha));
      ps.setTime(4, java.sql.Time.valueOf(hora));
      ps.setBoolean(5, mas_dos);
      ps.setBoolean(6, dnf);
      ps.setInt(7, usuario.getIdUsuario());
      ps.executeUpdate();

      // Envía una respuesta al cliente
      response.setContentType("text/plain");
      response.getWriter().write("El tiempo se ha guardado correctamente.");

    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.setContentType("text/plain");
      response.getWriter().write("Ha ocurrido un error al guardar el tiempo.");
    } finally {
      try {
        if (con != null) {
          con.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}