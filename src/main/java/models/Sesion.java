package models;

public class Sesion {

	private Integer id;
	private String nombre;
	private Integer usuario_id;
	private boolean default_sesion;
	
	public Sesion(Integer id, String nombre, Integer usuario_id, boolean default_sesion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.usuario_id = usuario_id;
		this.default_sesion = default_sesion;
	}
	
	public Sesion() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getUsuario_id() {
		return usuario_id;
	}
	
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public boolean isDefault_sesion() {
		return default_sesion;
	}

	public void setDefault_sesion(boolean default_sesion) {
		this.default_sesion = default_sesion;
	}
	
}
