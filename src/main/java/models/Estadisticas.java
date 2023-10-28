package models;

public class Estadisticas {
	private int total;
    private Solve mejor;
    private Solve peor;
    private AVG ao5;
    private AVG ao12;
    private AVG ao100;
    private AVG bestao5;
    private AVG bestao12;
    private AVG bestao100;
    private String media;
    private double desv;

	public Estadisticas(int total, Solve mejor, Solve peor, AVG ao5, AVG ao12, AVG ao100, AVG bestao5, AVG bestao12, AVG bestao100, String media, double desv) {
		super();
		this.total = total;
		this.mejor = mejor;
		this.peor = peor;
		this.ao5 = ao5;
		this.ao12 = ao12;
		this.ao100 = ao100;
		this.bestao5 = bestao5;
		this.bestao12 = bestao12;
		this.bestao100 = bestao100;
		this.media = media;
		this.desv = desv;
	}

	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public Solve getMejor() {
		return mejor;
	}
	
	public void setMejor(Solve mejor) {
		this.mejor = mejor;
	}
	
	public Solve getPeor() {
		return peor;
	}
	
	public void setPeor(Solve peor) {
		this.peor = peor;
	}
	
	public AVG getAo5() {
		return ao5;
	}
	
	public void setAo5(AVG ao5) {
		this.ao5 = ao5;
	}
	
	public AVG getAo12() {
		return ao12;
	}
	
	public void setAo12(AVG ao12) {
		this.ao12 = ao12;
	}
	
	public AVG getAo100() {
		return ao100;
	}
	
	public void setAo100(AVG ao100) {
		this.ao100 = ao100;
	}
	
	public String getMedia() {
		return media;
	}
	
	public void setMedia(String media) {
		this.media = media;
	}

	public AVG getBestao5() {
		return bestao5;
	}

	public void setBestao5(AVG bestao5) {
		this.bestao5 = bestao5;
	}

	public AVG getBestao12() {
		return bestao12;
	}

	public void setBestao12(AVG bestao12) {
		this.bestao12 = bestao12;
	}

	public AVG getBestao100() {
		return bestao100;
	}

	public void setBestao100(AVG bestao100) {
		this.bestao100 = bestao100;
	}

	public double getDesv() {
		return desv;
	}

	public void setDesv(double desv) {
		this.desv = desv;
	}
	
}
