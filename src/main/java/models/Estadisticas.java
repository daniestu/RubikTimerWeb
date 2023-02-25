package models;

public class Estadisticas {
	private int total;
    private String mejor;
    private String peor;
    private String ao5;
    private String ao12;
    private String ao100;
    private String media;

	public Estadisticas(int total, String mejor, String peor, String ao5, String ao12, String ao100, String media) {
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
	
	public String getMejor() {
		return mejor;
	}
	
	public void setMejor(String mejor) {
		this.mejor = mejor;
	}
	
	public String getPeor() {
		return peor;
	}
	
	public void setPeor(String peor) {
		this.peor = peor;
	}
	
	public String getAo5() {
		return ao5;
	}
	
	public void setAo5(String ao5) {
		this.ao5 = ao5;
	}
	
	public String getAo12() {
		return ao12;
	}
	
	public void setAo12(String ao12) {
		this.ao12 = ao12;
	}
	
	public String getAo100() {
		return ao100;
	}
	
	public void setAo100(String ao100) {
		this.ao100 = ao100;
	}
	
	public String getMedia() {
		return media;
	}
	
	public void setMedia(String media) {
		this.media = media;
	}
}
