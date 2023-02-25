package models;

import java.util.Date;

public class Solve implements Comparable<Solve>{
	
	private Integer id;
    private String scramble;
    private Date fecha;
    private String tiempo;
    private boolean mas_2;
    private boolean dnf;
    private Integer usuario_id;
    private Integer sesion_id;
    
	public Solve(Integer id, String scramble, Date fecha, String tiempo, boolean mas_2, boolean dnf, Integer usuario_id, Integer sesion_id) {
		super();
		this.id = id;
		this.scramble = scramble;
		this.fecha = fecha;
		this.tiempo = tiempo;
		this.mas_2 = mas_2;
		this.dnf = dnf;
		this.usuario_id = usuario_id;
		this.sesion_id = sesion_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getScramble() {
		return scramble;
	}

	public void setScramble(String scramble) {
		this.scramble = scramble;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public boolean isMas_2() {
		return mas_2;
	}

	public void setMas_2(boolean mas_2) {
		this.mas_2 = mas_2;
	}

	public boolean isDnf() {
		return dnf;
	}

	public void setDnf(boolean dnf) {
		this.dnf = dnf;
	}

	public Integer getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Integer getSesion_id() {
		return sesion_id;
	}

	public void setSesion_id(Integer sesion_id) {
		this.sesion_id = sesion_id;
	}
	
	@Override
    public int compareTo(Solve o) {
        return o.id.compareTo(this.id);
    }
}
