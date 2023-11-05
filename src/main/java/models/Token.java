package models;

import java.sql.Timestamp;

public class Token {
	
	private Integer token_id;
	private String uuid;
	private Integer usuario_id;
	private Timestamp fechaCreacion;
	private boolean caducado;
	
	public Token() {}
	
	public Token(String uuid, Integer usuario_id, Timestamp fechaCreacion, boolean caducado) {
		this.uuid = uuid;
		this.usuario_id = usuario_id;
		this.fechaCreacion = fechaCreacion;
		this.caducado = caducado;
	}

	public Integer getToken_id() {
		return token_id;
	}
	
	public void setToken_id(Integer token_id) {
		this.token_id = token_id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getUsuarioId() {
		return usuario_id;
	}
	
	public void setUsuarioId(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isCaducado() {
		return caducado;
	}

	public void setCaducado(boolean caducado) {
		this.caducado = caducado;
	}
	
	
}
