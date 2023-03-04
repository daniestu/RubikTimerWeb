package models;

import java.util.List;

public class AVG {
	private String tiempo;
    private List<Solve> solves;
    
	public AVG(String tiempo, List<Solve> solves) {
		super();
		this.tiempo = tiempo;
		this.solves = solves;
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
    
}
