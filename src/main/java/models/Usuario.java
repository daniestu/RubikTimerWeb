package models;

@SuppressWarnings("serial")
public class Usuario implements java.io.Serializable {

	private Integer idUsuario;
	private String nombreUsuario;
	private String password;
	private String correo;

	public Usuario() {
	}

	public Usuario(String nombreUsuario, String password, String correo) {
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.correo = correo;
	}
	
	public Usuario(Integer idUsuario, String nombreUsuario, String correo) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getpassword() {
		return this.password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
