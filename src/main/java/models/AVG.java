package models;

import java.util.List;

public class AVG {
	private String tiempo;
    private List<Solve> solves;
    private Boolean dnf;
    
    public AVG(String tiempo, List<Solve> solves, Boolean dnf) {
        this.tiempo = tiempo;
        this.solves = solves;
        this.dnf = dnf;
	}
	
	public AVG() {
	}
	
	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public List<Solve> getSolves() {
		return solves;
	}

	public void setSolves(List<Solve> solves) {
		this.solves = solves;
	}
	
	public Boolean isDnf() {
        return dnf;
    }

    public void setDnf(Boolean dnf) {
        this.dnf = dnf;
    }
    
}
