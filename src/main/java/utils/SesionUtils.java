package utils;

import java.util.Collections;
import java.util.List;

import models.Estadisticas;
import models.Solve;

public class SesionUtils {

	public Estadisticas getEstadisticas(List<Solve> tiempos) {
		int total = tiempos.size();
		String mejor = mejorTiempo(tiempos);
		String peor = peorTiempo(tiempos);
		String ao5 = avg(tiempos, 5);
		String ao12 = avg(tiempos, 12);
		String ao100 = avg(tiempos, 100);
		String media = media(tiempos);
		
		return new Estadisticas(total, mejor, peor, ao5, ao12, ao100, media);
	}

	private String media(List<Solve> tiempos) {
		String media = "";
		
		if (tiempos.size() > 0) {
			int suma = tiempos.stream()
			    .mapToInt(tiempo -> convertirTiempoMs(tiempo.getTiempo()))
			    .sum();
			    
			suma = suma/tiempos.size();
			media = convertirMsTiempo(suma);
		}
		
		return media;
	}

	private String avg(List<Solve> tiempos, int numero_avg) {
		String avgFinal = "";
		
		if (tiempos.size() >= numero_avg) {
			Collections.sort(tiempos);
			String mejor = tiempos.get(0).getTiempo();
			String peor = tiempos.get(0).getTiempo();
			int suma = 0;
			for (int i = 0; i < numero_avg; i++) {
				suma += convertirTiempoMs(tiempos.get(i).getTiempo());
				if (esPeorTiempo(peor, tiempos.get(i).getTiempo())) {
					peor = tiempos.get(i).getTiempo();
				}
				if (esMejorTiempo(mejor, tiempos.get(i).getTiempo())) {
					mejor = tiempos.get(i).getTiempo();
				}
			}
			
			suma = (suma - convertirTiempoMs(peor) - convertirTiempoMs(mejor)) / (numero_avg-2);
			avgFinal = convertirMsTiempo(suma);
		}
		
		return avgFinal;
	}

	private String peorTiempo(List<Solve> tiempos) {
		String peor = "";
		if (tiempos.size() != 0) {
			peor = tiempos.get(0).getTiempo();
			for (Solve solve : tiempos) {
				if (esPeorTiempo(peor, solve.getTiempo())) {
					peor = solve.getTiempo();
				}
			}
		}
		
		return peor;
	}

	private String mejorTiempo(List<Solve> tiempos) {
		String mejor = "";
		if (tiempos.size() != 0) {
			mejor = tiempos.get(0).getTiempo();
			for (Solve solve : tiempos) {
				if (esMejorTiempo(mejor, solve.getTiempo())) {
					mejor = solve.getTiempo();
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
