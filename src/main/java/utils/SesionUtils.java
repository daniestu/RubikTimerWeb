package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.AVG;
import models.Estadisticas;
import models.Solve;

public class SesionUtils {

	public Estadisticas getEstadisticas(List<Solve> tiempos) {
		int total = tiempos.size();
		Solve mejor = mejorTiempo(tiempos);
		Solve peor = peorTiempo(tiempos);
		AVG ao5 = avg(tiempos, 5);
		AVG ao12 = avg(tiempos, 12);
		AVG ao100 = avg(tiempos, 100);
		AVG bestao5 = bestavg(tiempos, 5);
		AVG bestao12 = bestavg(tiempos, 12);
		AVG bestao100 = bestavg(tiempos, 100);
		String media = media(tiempos);
		double desv = calcularDesviacion(tiempos, media);
		
		return new Estadisticas(total, mejor, peor, ao5, ao12, ao100, bestao5, bestao12, bestao100, media, desv);
	}

	private String media(List<Solve> tiempos) {
		String media = "";
		int dnfCount = 0;

	    if (!tiempos.isEmpty()) {
	        int suma = 0;
	        for (Solve tiempo : tiempos) {
	        	String tiempoACalcular = (tiempo.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(tiempo.getTiempo()) + 200) : tiempo.getTiempo();
	        	
	        	if (tiempo.isDnf()) {
	        		dnfCount++;
				}else {
		            suma += convertirTiempoMs(tiempoACalcular);
				}
	        }
	        
	        if ((tiempos.size() - dnfCount) != 0) {
	        	int promedio = suma / (tiempos.size() - dnfCount);
		        media = convertirMsTiempo(promedio);
			}
	    }

	    return media;
	}
	
	private static double calcularDesviacion(List<Solve> solves, String avg) {
        int suma = 0, media, size = 0;
        
        try {
            media = convertirTiempoMs(avg);
        } catch (Exception e) {
            return 0;
        }
        
        double diferencia;
        for (Solve i : solves) {
        	String tiempoACalcular = (i.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(i.getTiempo()) + 200) : i.getTiempo();
            if (!i.isDnf()) {
                size++;
                
                if (media < convertirTiempoMs(tiempoACalcular)) {
                    diferencia = convertirTiempoMs(tiempoACalcular) - media;
                }else{
                    diferencia = media - convertirTiempoMs(tiempoACalcular);
                }

                suma += (diferencia*diferencia);
            }
            
        }
        if (size < 2) {
        	return 0;
		}
        suma = suma / (size - 1);
        diferencia = Math.sqrt(suma)/100;
        return Math.round(diferencia*100.0)/100.0;
    }

	private AVG avg(List<Solve> tiempos, int numero_avg) {
		AVG avgFinal = null;
		
		if (tiempos.size() >= numero_avg) {
			avgFinal = new AVG();
			int dnfCount = 0;
			
			List<Solve> avgSolves = new ArrayList<Solve>();
			Collections.sort(tiempos);
			Solve mejor = tiempos.get(0);
			Solve peor = tiempos.get(0);
			int suma = 0;
			for (int i = 0; i < numero_avg; i++) {
				String mejorATM = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
				String peorATM = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
				String tiempoACalcular = (tiempos.get(i).isMas_2()) ? convertirMsTiempo(convertirTiempoMs(tiempos.get(i).getTiempo()) + 200) : tiempos.get(i).getTiempo();
				
				dnfCount = (tiempos.get(i).isDnf()) ? dnfCount+1 : dnfCount;
				avgSolves.add(tiempos.get(i));
				suma += convertirTiempoMs(tiempoACalcular);
				if (!peor.isDnf() && (tiempos.get(i).isDnf() || esPeorTiempo(peorATM, tiempoACalcular)) ) {
					peor = tiempos.get(i);
				}
				if (mejor.isDnf() || ( !(tiempos.get(i).isDnf()) && esMejorTiempo(mejorATM, tiempoACalcular)) ) {
					mejor = tiempos.get(i);
				}
			}
			
			String tiempoPeor = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
			String tiempoMejor = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
			
			suma = (suma - convertirTiempoMs(tiempoPeor) - convertirTiempoMs(tiempoMejor)) / (numero_avg-2);
			avgFinal.setSolves(avgSolves);
			avgFinal.setDnf(dnfCount >=2);
			avgFinal.setTiempo( (dnfCount >=2) ? "DNF" : convertirMsTiempo(suma) );
		}
		
		return avgFinal;
	}
	
	private AVG bestavg(List<Solve> tiempos, int num) {
		ArrayList <Solve> al = new ArrayList<Solve>();
        ArrayList <Solve> alMejores = new ArrayList<Solve>();
        int suma;
        String mejoravg = "";
        
        for (Solve i : tiempos) {
            int dnfCont = 0;
            boolean dnf=false;
            al.add(i);
            suma=0;
            if (al.size()==num+1) {
                al.remove(0);
                Solve mejor = al.get(0);
                Solve peor = al.get(0);
                for (Solve j : al) {
                    String mejorAtm = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
                    String peorAtm = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
                    String tiempoACalcular = (j.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(j.getTiempo()) + 200) : j.getTiempo();
                    if (dnf) {
                        if (!j.isDnf()) {
                            if (mejor.isDnf() || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                                mejor = j;
                            }
                        }else{
                            dnfCont++;
                        }

                    }else{
                        if (j.isDnf()) {
                            dnfCont++;
                            dnf = true;
                            peor = j;
                        }else{
                            if (mejor.isDnf() || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                                mejor = j;
                            }
                            
                        	if (!peor.isDnf() && esPeorTiempo(peorAtm, tiempoACalcular)) {
                                peor = j;
                            }
                        }
                    }
                    suma += convertirTiempoMs(tiempoACalcular);
                }
                String tiempoPeor = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
    			String tiempoMejor = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
    			
                suma = suma - (convertirTiempoMs(tiempoMejor) + convertirTiempoMs(tiempoPeor) );
                if (dnfCont<2) {
                    if (mejoravg.equals("DNF") || esMejorTiempo(mejoravg, convertirMsTiempo(suma/(num - 2)))) {
                        mejoravg = convertirMsTiempo(suma/(num - 2));
                        alMejores = new ArrayList<Solve>(al);
                    }
                }
            }else{
                if (al.size()==num) {
                    Solve mejor = al.get(0);
                    Solve peor = al.get(0);
                    for (Solve j : al) {
                    	String mejorAtm = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
                        String peorAtm = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
                        String tiempoACalcular = (j.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(j.getTiempo()) + 200) : j.getTiempo();
                        if (dnf) {
                            if (!j.isDnf()) {
                                if (mejor.isDnf() || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                                    mejor = j;
                                }
                            }else{
                                dnfCont++;
                            }
                            
                        }else{
                            if (j.isDnf()) {
                                dnfCont++;
                                dnf = true;
                                peor = j;
                            }else{
                                if (mejor.isDnf() || esMejorTiempo(mejorAtm, tiempoACalcular)) {
                                    mejor = j;
                                }
                                
                            	if (!peor.isDnf() && esPeorTiempo(peorAtm, tiempoACalcular)) {
                                    peor = j;
                                }
                            }
                        }
                        
                        suma += convertirTiempoMs(tiempoACalcular);
                    }
                    String tiempoPeor = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
        			String tiempoMejor = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
        			
                    if (dnfCont>=2) {
                        mejoravg = "DNF";
                    }else{
                        suma = suma - (convertirTiempoMs(tiempoMejor) + convertirTiempoMs(tiempoPeor) );
                        mejoravg = convertirMsTiempo(suma/(num - 2));
                    }
                    alMejores = new ArrayList<Solve>(al);
                }
            }
        }
        AVG avg;
        if (mejoravg.equals("DNF")) {
            avg = new AVG(mejoravg, alMejores, true);
        }else{
            avg = new AVG(mejoravg, alMejores, false);
        }
        return avg;
	}

	private Solve peorTiempo(List<Solve> tiempos) {
		Solve peor = null;
		if (tiempos.size() != 0) {
			peor = tiempos.get(0);
			for (Solve solve : tiempos) {
				if (!solve.isDnf()) {
					String peorAtm = (peor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(peor.getTiempo()) + 200) : peor.getTiempo();
					String tiempoACalcular = (solve.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(solve.getTiempo()) + 200) : solve.getTiempo();
					
					if (peor.isDnf() || esPeorTiempo(peorAtm, tiempoACalcular)) {
						peor = solve;
					}
				}
			}
		}
		
		return peor;
	}

	private Solve mejorTiempo(List<Solve> tiempos) {
		Solve mejor = null;
		if (tiempos.size() != 0) {
			mejor = tiempos.get(0);
			for (Solve solve : tiempos) {
				if (!solve.isDnf()) {
					String mejorAtm = (mejor.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(mejor.getTiempo()) + 200) : mejor.getTiempo();
					String tiempoACalcular = (solve.isMas_2()) ? convertirMsTiempo(convertirTiempoMs(solve.getTiempo()) + 200) : solve.getTiempo();
					
					if (mejor.isDnf() || esMejorTiempo(mejorAtm, tiempoACalcular)) {
						mejor = solve;
					}
				}
			}
		}
		
		return mejor;
	}
	
	public static boolean esMejorTiempo(String mejor, String tiempo) {
        String partsMejor[] = mejor.split(":");
        String partsTiempo[] = tiempo.split(":");

        if (Integer.parseInt(partsMejor[0]) == Integer.parseInt(partsTiempo[0])) {
            if (Integer.parseInt(partsMejor[1]) == Integer.parseInt(partsTiempo[1])) {
                return Integer.parseInt(partsMejor[2]) >= Integer.parseInt(partsTiempo[2]);
            } else {
                return Integer.parseInt(partsMejor[1]) >= Integer.parseInt(partsTiempo[1]);
            }
        } else {
            return Integer.parseInt(partsMejor[0]) >= Integer.parseInt(partsTiempo[0]);
        }
    }

    public static boolean esPeorTiempo(String peor, String tiempo) {
        String[] partsPeor = peor.split(":");
        String partsTiempo[] = tiempo.split(":");

        if (Integer.parseInt(partsPeor[0]) == Integer.parseInt(partsTiempo[0])) {
            if (Integer.parseInt(partsPeor[1]) == Integer.parseInt(partsTiempo[1])) {
                return Integer.parseInt(partsPeor[2]) < Integer.parseInt(partsTiempo[2]);
            } else {
                return Integer.parseInt(partsPeor[1]) < Integer.parseInt(partsTiempo[1]);
            }
        } else {
            return Integer.parseInt(partsPeor[0]) < Integer.parseInt(partsTiempo[0]);
        }
    }
    
    public static int convertirTiempoMs(String tiempo) {
        int m, s, ms;

        String parts[] = tiempo.split(":");

        m = Integer.parseInt(parts[0]);
        s = Integer.parseInt(parts[1]);
        ms = Integer.parseInt(parts[2]);

        s += m * 60;
        ms += s * 100;

        return ms;
    }

    public static String convertirMsTiempo(int ms) {
        int m, s, lms;
        String s_m, s_s, s_ms;

        if (ms >= 100) {
            lms = ms % 100;
            s = (ms - lms) / 100;
            if (s >= 60) {
                m = s / 60;
                s = s % 60;
                if (m < 10) {
                    s_m = "0" + m;
                } else {
                    s_m = "" + m;
                }
                if (s < 10) {
                    s_s = "0" + s;
                } else {
                    s_s = "" + s;
                }
                if (lms < 10) {
                    s_ms = "0" + lms;
                } else {
                    s_ms = "" + lms;
                }
                return s_m + ":" + s_s + ":" + s_ms;
            } else {
                if (s < 10) {
                    s_s = "0" + s;
                } else {
                    s_s = s + "";
                }

                if (lms < 10) {
                    s_ms = "0" + lms;
                } else {
                    s_ms = "" + lms;
                }

                return "00:" + s_s + ":" + s_ms;
            }
        } else {
            if (ms < 10) {
                return "00:00:0" + ms;
            } else {
                return "00:00:" + ms;
            }
        }
    }
}
