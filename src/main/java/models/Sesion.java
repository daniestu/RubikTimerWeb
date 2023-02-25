package models;

public class Sesion {

	private Integer id;
	private String nombre;
	private Integer usuario_id;
	
	public Sesion(Integer id, String nombre, Integer usuario_id) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.usuario_id = usuario_id;
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
}
