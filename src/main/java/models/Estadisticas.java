package models;

public class Estadisticas {
	private int total;
    private Solve mejor;
    private Solve peor;
    private AVG ao5;
    private AVG ao12;
    private AVG ao100;
    private String media;

	public Estadisticas(int total, Solve mejor, Solve peor, AVG ao5, AVG ao12, AVG ao100, String media) {
		super();
		this.total = total;
		this.mejor = mejor;
		this.peor = peor;
		this.ao5 = ao5;
		this.ao12 = ao12;
		this.ao100 = ao100;
		this.media = media;
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
}
