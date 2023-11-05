package utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.Cubo;

public class CuboUtils {
	
	private static final String[] MOVIMIENTOS = {
		"U", "U2", "U'", "D", "D2", "D'", "R", "R2", "R'", "L", "L2", "L'", "F", "F2", "F'", "B", "B2", "B'"
	};
	
	public Cubo generarCubo (String scramble){
		String blanco = "rgb(" + Color.white.getRed() + ", " + Color.white.getGreen() + ", " + Color.white.getBlue() + ")";
		String rojo = "rgb(" + Color.red.getRed() + ", " + Color.red.getGreen() + ", " + Color.red.getBlue() + ")";
		String naranja = "rgb(" + new java.awt.Color(255, 131, 0).getRed() + ", " + new java.awt.Color(255, 131, 0).getGreen() + ", " + new java.awt.Color(255, 131, 0).getBlue() + ")";
		String amarillo = "rgb(" + Color.yellow.getRed() + ", " + Color.yellow.getGreen() + ", " + Color.yellow.getBlue() + ")";
		String azul = "rgb(" + Color.blue.getRed() + ", " + Color.blue.getGreen() + ", " + Color.blue.getBlue() + ")";
		String verde = "rgb(" + Color.green.getRed() + ", " + Color.green.getGreen() + ", " + Color.green.getBlue() + ")";
		
		Cubo c = new Cubo  (blanco, blanco, blanco, blanco, blanco, blanco, blanco, blanco, blanco, 
				rojo, rojo, rojo, rojo, rojo, rojo, rojo, rojo, rojo, 
				naranja, naranja, naranja, naranja, naranja, naranja, naranja, naranja, naranja,
				amarillo, amarillo, amarillo, amarillo, amarillo, amarillo, amarillo, amarillo, amarillo,
				verde, verde, verde, verde, verde, verde, verde, verde, verde,
				azul, azul, azul, azul, azul, azul, azul, azul, azul);

		String moves [] = scramble.split(" ");
		
		for (String i : moves) {
		c = generarMovimiento(c, i);
		}
		
        return c;
    }
	
	public static String generarScramble() {
	    List<String> movimientos = new ArrayList<>();
	    Random random = new Random();
	    String ultimoMovimiento = "";
	    String penultimoMovimiento = "";

	    for (int i = 0; i < 20; i++) {
	        String movimiento = MOVIMIENTOS[random.nextInt(MOVIMIENTOS.length)];
	        
	        while (movimientoCancela(ultimoMovimiento, penultimoMovimiento, movimiento)) {
	            movimiento = MOVIMIENTOS[random.nextInt(MOVIMIENTOS.length)];
	        }

	        movimientos.add(movimiento);
	        penultimoMovimiento = ultimoMovimiento;
	        ultimoMovimiento = movimiento;
	    }

	    return String.join(" ", movimientos);
	}

	public static boolean movimientoCancela(String ultimoMovimiento, String penultimoMovimiento, String movimiento) {
	    if (ultimoMovimiento.isEmpty()) {
	        return false;
	    }
	    
	    char capaUltimo = ultimoMovimiento.charAt(0);
	    char capaMovimiento = movimiento.charAt(0);
	    
	    if (penultimoMovimiento.isEmpty()) {
	    	if (capaMovimiento == capaUltimo) {
				return true;
			}else {
				return false;
			}
		}
	    
	    char capaPenultimo = penultimoMovimiento.charAt(0);
	    
	    if (capaMovimiento == capaUltimo) {
			return true;
		}
	    
	    if (capaMovimiento == capaPenultimo) {
			if ((capaMovimiento == 'R' && capaUltimo == 'L') || 
				(capaMovimiento == 'L' && capaUltimo == 'R') || 
				(capaMovimiento == 'U' && capaUltimo == 'D') || 
				(capaMovimiento == 'D' && capaUltimo == 'U') || 
				(capaMovimiento == 'B' && capaUltimo == 'F') || 
				(capaMovimiento == 'F' && capaUltimo == 'B')) {
				return true;
			}
		}

	    return false;
	}

	public static Cubo generarMovimiento(Cubo c_ant, String mov){
        String colores[] = new String[21];
        Cubo c_new = c_ant;
        
        
        if (mov.equals("R")) {
            colores[0] = c_ant.getF9();
            colores[1] = c_ant.getF6();
            colores[2] = c_ant.getF3();
            colores[3] = c_ant.getU9();
            colores[4] = c_ant.getU6();
            colores[5] = c_ant.getU3();
            colores[6] = c_ant.getB1();
            colores[7] = c_ant.getB4();
            colores[8] = c_ant.getB7();
            colores[9] = c_ant.getD9();
            colores[10] = c_ant.getD6();
            colores[11] = c_ant.getD3();
            colores[12] = c_ant.getR7();
            colores[13] = c_ant.getR4();
            colores[14] = c_ant.getR1();
            colores[15] = c_ant.getR8();
            colores[16] = c_ant.getR5();
            colores[17] = c_ant.getR2();
            colores[18] = c_ant.getR9();
            colores[19] = c_ant.getR6();
            colores[20] = c_ant.getR3();

            c_new.setU9(colores[0]);
            c_new.setU6(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setB1(colores[3]);
            c_new.setB4(colores[4]);
            c_new.setB7(colores[5]);
            c_new.setD9(colores[6]);
            c_new.setD6(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setF9(colores[9]);
            c_new.setF6(colores[10]);
            c_new.setF3(colores[11]);
            c_new.setR1(colores[12]);
            c_new.setR2(colores[13]);
            c_new.setR3(colores[14]);
            c_new.setR4(colores[15]);
            c_new.setR5(colores[16]);
            c_new.setR6(colores[17]);
            c_new.setR7(colores[18]);
            c_new.setR8(colores[19]);
            c_new.setR9(colores[20]);
        }
        if (mov.equals("R'")) {
            colores[0] = c_ant.getB1();
            colores[1] = c_ant.getB4();
            colores[2] = c_ant.getB7();
            colores[3] = c_ant.getD9();
            colores[4] = c_ant.getD6();
            colores[5] = c_ant.getD3();
            colores[6] = c_ant.getF9();
            colores[7] = c_ant.getF6();
            colores[8] = c_ant.getF3();
            colores[9] = c_ant.getU9();
            colores[10] = c_ant.getU6();
            colores[11] = c_ant.getU3();
            colores[12] = c_ant.getR3();
            colores[13] = c_ant.getR6();
            colores[14] = c_ant.getR9();
            colores[15] = c_ant.getR2();
            colores[16] = c_ant.getR5();
            colores[17] = c_ant.getR8();
            colores[18] = c_ant.getR1();
            colores[19] = c_ant.getR4();
            colores[20] = c_ant.getR7();

            c_new.setU9(colores[0]);
            c_new.setU6(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setB1(colores[3]);
            c_new.setB4(colores[4]);
            c_new.setB7(colores[5]);
            c_new.setD9(colores[6]);
            c_new.setD6(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setF9(colores[9]);
            c_new.setF6(colores[10]);
            c_new.setF3(colores[11]);
            c_new.setR1(colores[12]);
            c_new.setR2(colores[13]);
            c_new.setR3(colores[14]);
            c_new.setR4(colores[15]);
            c_new.setR5(colores[16]);
            c_new.setR6(colores[17]);
            c_new.setR7(colores[18]);
            c_new.setR8(colores[19]);
            c_new.setR9(colores[20]);
        }
        if (mov.equals("R2")) {
            colores[0] = c_ant.getD9();
            colores[1] = c_ant.getD6();
            colores[2] = c_ant.getD3();
            colores[3] = c_ant.getF9();
            colores[4] = c_ant.getF6();
            colores[5] = c_ant.getF3();
            colores[6] = c_ant.getU9();
            colores[7] = c_ant.getU6();
            colores[8] = c_ant.getU3();
            colores[9] = c_ant.getB1();
            colores[10] = c_ant.getB4();
            colores[11] = c_ant.getB7();
            colores[12] = c_ant.getR9();
            colores[13] = c_ant.getR8();
            colores[14] = c_ant.getR7();
            colores[15] = c_ant.getR6();
            colores[16] = c_ant.getR5();
            colores[17] = c_ant.getR4();
            colores[18] = c_ant.getR3();
            colores[19] = c_ant.getR2();
            colores[20] = c_ant.getR1();

            c_new.setU9(colores[0]);
            c_new.setU6(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setB1(colores[3]);
            c_new.setB4(colores[4]);
            c_new.setB7(colores[5]);
            c_new.setD9(colores[6]);
            c_new.setD6(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setF9(colores[9]);
            c_new.setF6(colores[10]);
            c_new.setF3(colores[11]);
            c_new.setR1(colores[12]);
            c_new.setR2(colores[13]);
            c_new.setR3(colores[14]);
            c_new.setR4(colores[15]);
            c_new.setR5(colores[16]);
            c_new.setR6(colores[17]);
            c_new.setR7(colores[18]);
            c_new.setR8(colores[19]);
            c_new.setR9(colores[20]);
        }
        if (mov.equals("L")) {
            colores[0] = c_ant.getB9();
            colores[1] = c_ant.getB6();
            colores[2] = c_ant.getB3();
            colores[3] = c_ant.getD7();
            colores[4] = c_ant.getD4();
            colores[5] = c_ant.getD1();
            colores[6] = c_ant.getF1();
            colores[7] = c_ant.getF4();
            colores[8] = c_ant.getF7();
            colores[9] = c_ant.getU1();
            colores[10] = c_ant.getU4();
            colores[11] = c_ant.getU7();
            colores[12] = c_ant.getL7();
            colores[13] = c_ant.getL4();
            colores[14] = c_ant.getL1();
            colores[15] = c_ant.getL8();
            colores[16] = c_ant.getL5();
            colores[17] = c_ant.getL2();
            colores[18] = c_ant.getL9();
            colores[19] = c_ant.getL6();
            colores[20] = c_ant.getL3();

            c_new.setU1(colores[0]);
            c_new.setU4(colores[1]);
            c_new.setU7(colores[2]);
            c_new.setB3(colores[3]);
            c_new.setB6(colores[4]);
            c_new.setB9(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD4(colores[7]);
            c_new.setD7(colores[8]);
            c_new.setF1(colores[9]);
            c_new.setF4(colores[10]);
            c_new.setF7(colores[11]);
            c_new.setL1(colores[12]);
            c_new.setL2(colores[13]);
            c_new.setL3(colores[14]);
            c_new.setL4(colores[15]);
            c_new.setL5(colores[16]);
            c_new.setL6(colores[17]);
            c_new.setL7(colores[18]);
            c_new.setL8(colores[19]);
            c_new.setL9(colores[20]);
        }
        if (mov.equals("L'")) {
            colores[0] = c_ant.getF1();
            colores[1] = c_ant.getF4();
            colores[2] = c_ant.getF7();
            colores[3] = c_ant.getU7();
            colores[4] = c_ant.getU4();
            colores[5] = c_ant.getU1();
            colores[6] = c_ant.getB9();
            colores[7] = c_ant.getB6();
            colores[8] = c_ant.getB3();
            colores[9] = c_ant.getD1();
            colores[10] = c_ant.getD4();
            colores[11] = c_ant.getD7();
            
            colores[12] = c_ant.getL3();
            colores[13] = c_ant.getL6();
            colores[14] = c_ant.getL9();
            colores[15] = c_ant.getL2();
            colores[16] = c_ant.getL5();
            colores[17] = c_ant.getL8();
            colores[18] = c_ant.getL1();
            colores[19] = c_ant.getL4();
            colores[20] = c_ant.getL7();

            c_new.setU1(colores[0]);
            c_new.setU4(colores[1]);
            c_new.setU7(colores[2]);
            c_new.setB3(colores[3]);
            c_new.setB6(colores[4]);
            c_new.setB9(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD4(colores[7]);
            c_new.setD7(colores[8]);
            c_new.setF1(colores[9]);
            c_new.setF4(colores[10]);
            c_new.setF7(colores[11]);
            c_new.setL1(colores[12]);
            c_new.setL2(colores[13]);
            c_new.setL3(colores[14]);
            c_new.setL4(colores[15]);
            c_new.setL5(colores[16]);
            c_new.setL6(colores[17]);
            c_new.setL7(colores[18]);
            c_new.setL8(colores[19]);
            c_new.setL9(colores[20]);
        }
        if (mov.equals("L2")) {
            colores[0] = c_ant.getD1();
            colores[1] = c_ant.getD4();
            colores[2] = c_ant.getD7();
            colores[3] = c_ant.getF7();
            colores[4] = c_ant.getF4();
            colores[5] = c_ant.getF1();
            colores[6] = c_ant.getU1();
            colores[7] = c_ant.getU4();
            colores[8] = c_ant.getU7();
            colores[9] = c_ant.getB9();
            colores[10] = c_ant.getB6();
            colores[11] = c_ant.getB3();
            colores[12] = c_ant.getL9();
            colores[13] = c_ant.getL8();
            colores[14] = c_ant.getL7();
            colores[15] = c_ant.getL6();
            colores[16] = c_ant.getL5();
            colores[17] = c_ant.getL4();
            colores[18] = c_ant.getL3();
            colores[19] = c_ant.getL2();
            colores[20] = c_ant.getL1();

            c_new.setU1(colores[0]);
            c_new.setU4(colores[1]);
            c_new.setU7(colores[2]);
            c_new.setB3(colores[3]);
            c_new.setB6(colores[4]);
            c_new.setB9(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD4(colores[7]);
            c_new.setD7(colores[8]);
            c_new.setF1(colores[9]);
            c_new.setF4(colores[10]);
            c_new.setF7(colores[11]);
            c_new.setL1(colores[12]);
            c_new.setL2(colores[13]);
            c_new.setL3(colores[14]);
            c_new.setL4(colores[15]);
            c_new.setL5(colores[16]);
            c_new.setL6(colores[17]);
            c_new.setL7(colores[18]);
            c_new.setL8(colores[19]);
            c_new.setL9(colores[20]);
        }
        if (mov.equals("U")) {
            colores[0] = c_ant.getR1();
            colores[1] = c_ant.getR2();
            colores[2] = c_ant.getR3();
            colores[3] = c_ant.getB1();
            colores[4] = c_ant.getB2();
            colores[5] = c_ant.getB3();
            colores[6] = c_ant.getL1();
            colores[7] = c_ant.getL2();
            colores[8] = c_ant.getL3();
            colores[9] = c_ant.getF1();
            colores[10] = c_ant.getF2();
            colores[11] = c_ant.getF3();
            colores[12] = c_ant.getU7();
            colores[13] = c_ant.getU4();
            colores[14] = c_ant.getU1();
            colores[15] = c_ant.getU8();
            colores[16] = c_ant.getU5();
            colores[17] = c_ant.getU2();
            colores[18] = c_ant.getU9();
            colores[19] = c_ant.getU6();
            colores[20] = c_ant.getU3();

            c_new.setF1(colores[0]);
            c_new.setF2(colores[1]);
            c_new.setF3(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR2(colores[4]);
            c_new.setR3(colores[5]);
            c_new.setB1(colores[6]);
            c_new.setB2(colores[7]);
            c_new.setB3(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL2(colores[10]);
            c_new.setL3(colores[11]);
            c_new.setU1(colores[12]);
            c_new.setU2(colores[13]);
            c_new.setU3(colores[14]);
            c_new.setU4(colores[15]);
            c_new.setU5(colores[16]);
            c_new.setU6(colores[17]);
            c_new.setU7(colores[18]);
            c_new.setU8(colores[19]);
            c_new.setU9(colores[20]);
        }
        if (mov.equals("U'")) {
            colores[0] = c_ant.getL1();
            colores[1] = c_ant.getL2();
            colores[2] = c_ant.getL3();
            colores[3] = c_ant.getF1();
            colores[4] = c_ant.getF2();
            colores[5] = c_ant.getF3();
            colores[6] = c_ant.getR1();
            colores[7] = c_ant.getR2();
            colores[8] = c_ant.getR3();
            colores[9] = c_ant.getB1();
            colores[10] = c_ant.getB2();
            colores[11] = c_ant.getB3();
            colores[12] = c_ant.getU3();
            colores[13] = c_ant.getU6();
            colores[14] = c_ant.getU9();
            colores[15] = c_ant.getU2();
            colores[16] = c_ant.getU5();
            colores[17] = c_ant.getU8();
            colores[18] = c_ant.getU1();
            colores[19] = c_ant.getU4();
            colores[20] = c_ant.getU7();

            c_new.setF1(colores[0]);
            c_new.setF2(colores[1]);
            c_new.setF3(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR2(colores[4]);
            c_new.setR3(colores[5]);
            c_new.setB1(colores[6]);
            c_new.setB2(colores[7]);
            c_new.setB3(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL2(colores[10]);
            c_new.setL3(colores[11]);
            c_new.setU1(colores[12]);
            c_new.setU2(colores[13]);
            c_new.setU3(colores[14]);
            c_new.setU4(colores[15]);
            c_new.setU5(colores[16]);
            c_new.setU6(colores[17]);
            c_new.setU7(colores[18]);
            c_new.setU8(colores[19]);
            c_new.setU9(colores[20]);
        }
        if (mov.equals("U2")) {
            colores[0] = c_ant.getB1();
            colores[1] = c_ant.getB2();
            colores[2] = c_ant.getB3();
            colores[3] = c_ant.getL1();
            colores[4] = c_ant.getL2();
            colores[5] = c_ant.getL3();
            colores[6] = c_ant.getF1();
            colores[7] = c_ant.getF2();
            colores[8] = c_ant.getF3();
            colores[9] = c_ant.getR1();
            colores[10] = c_ant.getR2();
            colores[11] = c_ant.getR3();
            colores[12] = c_ant.getU9();
            colores[13] = c_ant.getU8();
            colores[14] = c_ant.getU7();
            colores[15] = c_ant.getU6();
            colores[16] = c_ant.getU5();
            colores[17] = c_ant.getU4();
            colores[18] = c_ant.getU3();
            colores[19] = c_ant.getU2();
            colores[20] = c_ant.getU1 ();

            c_new.setF1(colores[0]);
            c_new.setF2(colores[1]);
            c_new.setF3(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR2(colores[4]);
            c_new.setR3(colores[5]);
            c_new.setB1(colores[6]);
            c_new.setB2(colores[7]);
            c_new.setB3(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL2(colores[10]);
            c_new.setL3(colores[11]);
            c_new.setU1(colores[12]);
            c_new.setU2(colores[13]);
            c_new.setU3(colores[14]);
            c_new.setU4(colores[15]);
            c_new.setU5(colores[16]);
            c_new.setU6(colores[17]);
            c_new.setU7(colores[18]);
            c_new.setU8(colores[19]);
            c_new.setU9(colores[20]);
        }
        if (mov.equals("D")) {
            colores[0] = c_ant.getL7();
            colores[1] = c_ant.getL8();
            colores[2] = c_ant.getL9();
            colores[3] = c_ant.getF7();
            colores[4] = c_ant.getF8();
            colores[5] = c_ant.getF9();
            colores[6] = c_ant.getR7();
            colores[7] = c_ant.getR8();
            colores[8] = c_ant.getR9();
            colores[9] = c_ant.getB7();
            colores[10] = c_ant.getB8();
            colores[11] = c_ant.getB9();
            colores[12] = c_ant.getD7();
            colores[13] = c_ant.getD4();
            colores[14] = c_ant.getD1();
            colores[15] = c_ant.getD8();
            colores[16] = c_ant.getD5();
            colores[17] = c_ant.getD2();
            colores[18] = c_ant.getD9();
            colores[19] = c_ant.getD6();
            colores[20] = c_ant.getD3();


            c_new.setF7(colores[0]);
            c_new.setF8(colores[1]);
            c_new.setF9(colores[2]);
            c_new.setR7(colores[3]);
            c_new.setR8(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setB7(colores[6]);
            c_new.setB8(colores[7]);
            c_new.setB9(colores[8]);
            c_new.setL7(colores[9]);
            c_new.setL8(colores[10]);
            c_new.setL9 (colores[11]);
            c_new.setD1(colores[12]);
            c_new.setD2(colores[13]);
            c_new.setD3(colores[14]);
            c_new.setD4(colores[15]);
            c_new.setD5(colores[16]);
            c_new.setD6(colores[17]);
            c_new.setD7(colores[18]);
            c_new.setD8(colores[19]);
            c_new.setD9(colores[20]);
        }
        if (mov.equals("D'")) {
            colores[0] = c_ant.getR7();
            colores[1] = c_ant.getR8();
            colores[2] = c_ant.getR9();
            colores[3] = c_ant.getB7();
            colores[4] = c_ant.getB8();
            colores[5] = c_ant.getB9();
            colores[6] = c_ant.getL7();
            colores[7] = c_ant.getL8();
            colores[8] = c_ant.getL9();
            colores[9] = c_ant.getF7();
            colores[10] = c_ant.getF8();
            colores[11] = c_ant.getF9();
            colores[12] = c_ant.getD3();
            colores[13] = c_ant.getD6();
            colores[14] = c_ant.getD9();
            colores[15] = c_ant.getD2();
            colores[16] = c_ant.getD5();
            colores[17] = c_ant.getD8();
            colores[18] = c_ant.getD1();
            colores[19] = c_ant.getD4();
            colores[20] = c_ant.getD7();

            c_new.setF7(colores[0]);
            c_new.setF8(colores[1]);
            c_new.setF9(colores[2]);
            c_new.setR7(colores[3]);
            c_new.setR8(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setB7(colores[6]);
            c_new.setB8(colores[7]);
            c_new.setB9(colores[8]);
            c_new.setL7(colores[9]);
            c_new.setL8(colores[10]);
            c_new.setL9 (colores[11]);
            c_new.setD1(colores[12]);
            c_new.setD2(colores[13]);
            c_new.setD3(colores[14]);
            c_new.setD4(colores[15]);
            c_new.setD5(colores[16]);
            c_new.setD6(colores[17]);
            c_new.setD7(colores[18]);
            c_new.setD8(colores[19]);
            c_new.setD9(colores[20]);
        }
        if (mov.equals("D2")) {
            colores[0] = c_ant.getB7();
            colores[1] = c_ant.getB8();
            colores[2] = c_ant.getB9();
            colores[3] = c_ant.getL7();
            colores[4] = c_ant.getL8();
            colores[5] = c_ant.getL9();
            colores[6] = c_ant.getF7();
            colores[7] = c_ant.getF8();
            colores[8] = c_ant.getF9();
            colores[9] = c_ant.getR7();
            colores[10] = c_ant.getR8();
            colores[11] = c_ant.getR9();
            colores[12] = c_ant.getD9();
            colores[13] = c_ant.getD8();
            colores[14] = c_ant.getD7();
            colores[15] = c_ant.getD6();
            colores[16] = c_ant.getD5();
            colores[17] = c_ant.getD4();
            colores[18] = c_ant.getD3();
            colores[19] = c_ant.getD2();
            colores[20] = c_ant.getD1();

            c_new.setF7(colores[0]);
            c_new.setF8(colores[1]);
            c_new.setF9(colores[2]);
            c_new.setR7(colores[3]);
            c_new.setR8(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setB7(colores[6]);
            c_new.setB8(colores[7]);
            c_new.setB9(colores[8]);
            c_new.setL7(colores[9]);
            c_new.setL8(colores[10]);
            c_new.setL9 (colores[11]);
            c_new.setD1(colores[12]);
            c_new.setD2(colores[13]);
            c_new.setD3(colores[14]);
            c_new.setD4(colores[15]);
            c_new.setD5(colores[16]);
            c_new.setD6(colores[17]);
            c_new.setD7(colores[18]);
            c_new.setD8(colores[19]);
            c_new.setD9(colores[20]);
        }
        if (mov.equals("F")) {
            colores[0] = c_ant.getL9();
            colores[1] = c_ant.getL6();
            colores[2] = c_ant.getL3();
            colores[3] = c_ant.getU7();
            colores[4] = c_ant.getU8();
            colores[5] = c_ant.getU9();
            colores[6] = c_ant.getR7();
            colores[7] = c_ant.getR4();
            colores[8] = c_ant.getR1();
            colores[9] = c_ant.getD1();
            colores[10] = c_ant.getD2();
            colores[11] = c_ant.getD3();
            colores[12] = c_ant.getF7();
            colores[13] = c_ant.getF4();
            colores[14] = c_ant.getF1();
            colores[15] = c_ant.getF8();
            colores[16] = c_ant.getF5();
            colores[17] = c_ant.getF2();
            colores[18] = c_ant.getF9();
            colores[19] = c_ant.getF6();
            colores[20] = c_ant.getF3();


            c_new.setU7(colores[0]);
            c_new.setU8(colores[1]);
            c_new.setU9(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR4(colores[4]);
            c_new.setR7(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD2(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setL3(colores[9]);
            c_new.setL6(colores[10]);
            c_new.setL9(colores[11]);
            c_new.setF1(colores[12]);
            c_new.setF2(colores[13]);
            c_new.setF3(colores[14]);
            c_new.setF4(colores[15]);
            c_new.setF5(colores[16]);
            c_new.setF6(colores[17]);
            c_new.setF7(colores[18]);
            c_new.setF8(colores[19]);
            c_new.setF9(colores[20]);
        }
        if (mov.equals("F'")) {
            colores[0] = c_ant.getR1();
            colores[1] = c_ant.getR4();
            colores[2] = c_ant.getR7();
            colores[3] = c_ant.getD3();
            colores[4] = c_ant.getD2();
            colores[5] = c_ant.getD1();
            colores[6] = c_ant.getL3();
            colores[7] = c_ant.getL6();
            colores[8] = c_ant.getL9();
            colores[9] = c_ant.getU9();
            colores[10] = c_ant.getU8();
            colores[11] = c_ant.getU7();
            colores[12] = c_ant.getF3();
            colores[13] = c_ant.getF6();
            colores[14] = c_ant.getF9();
            colores[15] = c_ant.getF2();
            colores[16] = c_ant.getF5();
            colores[17] = c_ant.getF8();
            colores[18] = c_ant.getF1();
            colores[19] = c_ant.getF4();
            colores[20] = c_ant.getF7();

            c_new.setU7(colores[0]);
            c_new.setU8(colores[1]);
            c_new.setU9(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR4(colores[4]);
            c_new.setR7(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD2(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setL3(colores[9]);
            c_new.setL6(colores[10]);
            c_new.setL9(colores[11]);
            c_new.setF1(colores[12]);
            c_new.setF2(colores[13]);
            c_new.setF3(colores[14]);
            c_new.setF4(colores[15]);
            c_new.setF5(colores[16]);
            c_new.setF6(colores[17]);
            c_new.setF7(colores[18]);
            c_new.setF8(colores[19]);
            c_new.setF9(colores[20]);
        }
        if (mov.equals("F2")) {
            colores[0] = c_ant.getD3();
            colores[1] = c_ant.getD2();
            colores[2] = c_ant.getD1();
            colores[3] = c_ant.getL9();
            colores[4] = c_ant.getL6();
            colores[5] = c_ant.getL3();
            colores[6] = c_ant.getU9();
            colores[7] = c_ant.getU8();
            colores[8] = c_ant.getU7();
            colores[9] = c_ant.getR7();
            colores[10] = c_ant.getR4();
            colores[11] = c_ant.getR1();
            colores[12] = c_ant.getF9();
            colores[13] = c_ant.getF8();
            colores[14] = c_ant.getF7();
            colores[15] = c_ant.getF6();
            colores[16] = c_ant.getF5();
            colores[17] = c_ant.getF4();
            colores[18] = c_ant.getF3();
            colores[19] = c_ant.getF2();
            colores[20] = c_ant.getF1();

            c_new.setU7(colores[0]);
            c_new.setU8(colores[1]);
            c_new.setU9(colores[2]);
            c_new.setR1(colores[3]);
            c_new.setR4(colores[4]);
            c_new.setR7(colores[5]);
            c_new.setD1(colores[6]);
            c_new.setD2(colores[7]);
            c_new.setD3(colores[8]);
            c_new.setL3(colores[9]);
            c_new.setL6(colores[10]);
            c_new.setL9(colores[11]);
            c_new.setF1(colores[12]);
            c_new.setF2(colores[13]);
            c_new.setF3(colores[14]);
            c_new.setF4(colores[15]);
            c_new.setF5(colores[16]);
            c_new.setF6(colores[17]);
            c_new.setF7(colores[18]);
            c_new.setF8(colores[19]);
            c_new.setF9(colores[20]);
        }
        if (mov.equals("B")) {
            colores[0] = c_ant.getR3();
            colores[1] = c_ant.getR6();
            colores[2] = c_ant.getR9();
            colores[3] = c_ant.getD9();
            colores[4] = c_ant.getD8();
            colores[5] = c_ant.getD7();
            colores[6] = c_ant.getL1();
            colores[7] = c_ant.getL4();
            colores[8] = c_ant.getL7();
            colores[9] = c_ant.getU3();
            colores[10] = c_ant.getU2();
            colores[11] = c_ant.getU1();
            colores[12] = c_ant.getB7();
            colores[13] = c_ant.getB4();
            colores[14] = c_ant.getB1();
            colores[15] = c_ant.getB8();
            colores[16] = c_ant.getB5();
            colores[17] = c_ant.getB2();
            colores[18] = c_ant.getB9();
            colores[19] = c_ant.getB6();
            colores[20] = c_ant.getB3();

            c_new.setU1(colores[0]);
            c_new.setU2(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setR3(colores[3]);
            c_new.setR6(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setD7(colores[6]);
            c_new.setD8(colores[7]);
            c_new.setD9(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL4(colores[10]);
            c_new.setL7(colores[11]);
            c_new.setB1(colores[12]);
            c_new.setB2(colores[13]);
            c_new.setB3(colores[14]);
            c_new.setB4(colores[15]);
            c_new.setB5(colores[16]);
            c_new.setB6(colores[17]);
            c_new.setB7(colores[18]);
            c_new.setB8(colores[19]);
            c_new.setB9(colores[20]);
        }
        if (mov.equals("B'")) {
            colores[0] = c_ant.getL7();
            colores[1] = c_ant.getL4();
            colores[2] = c_ant.getL1();
            colores[3] = c_ant.getU1();
            colores[4] = c_ant.getU2();
            colores[5] = c_ant.getU3();
            colores[6] = c_ant.getR9();
            colores[7] = c_ant.getR6();
            colores[8] = c_ant.getR3();
            colores[9] = c_ant.getD7();
            colores[10] = c_ant.getD8();
            colores[11] = c_ant.getD9();
            
            colores[12] = c_ant.getB3();
            colores[13] = c_ant.getB6();
            colores[14] = c_ant.getB9();
            colores[15] = c_ant.getB2();
            colores[16] = c_ant.getB5();
            colores[17] = c_ant.getB8();
            colores[18] = c_ant.getB1();
            colores[19] = c_ant.getB4();
            colores[20] = c_ant.getB7();

            c_new.setU1(colores[0]);
            c_new.setU2(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setR3(colores[3]);
            c_new.setR6(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setD7(colores[6]);
            c_new.setD8(colores[7]);
            c_new.setD9(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL4(colores[10]);
            c_new.setL7(colores[11]);
            c_new.setB1(colores[12]);
            c_new.setB2(colores[13]);
            c_new.setB3(colores[14]);
            c_new.setB4(colores[15]);
            c_new.setB5(colores[16]);
            c_new.setB6(colores[17]);
            c_new.setB7(colores[18]);
            c_new.setB8(colores[19]);
            c_new.setB9(colores[20]);
        }
        if (mov.equals("B2")) {
            colores[0] = c_ant.getD9();
            colores[1] = c_ant.getD8();
            colores[2] = c_ant.getD7();
            colores[3] = c_ant.getL7();
            colores[4] = c_ant.getL4();
            colores[5] = c_ant.getL1();
            colores[6] = c_ant.getU3();
            colores[7] = c_ant.getU2();
            colores[8] = c_ant.getU1();
            colores[9] = c_ant.getR9();
            colores[10] = c_ant.getR6();
            colores[11] = c_ant.getR3();
            
            colores[12] = c_ant.getB9();
            colores[13] = c_ant.getB8();
            colores[14] = c_ant.getB7();
            colores[15] = c_ant.getB6();
            colores[16] = c_ant.getB5();
            colores[17] = c_ant.getB4();
            colores[18] = c_ant.getB3();
            colores[19] = c_ant.getB2();
            colores[20] = c_ant.getB1();

            c_new.setU1(colores[0]);
            c_new.setU2(colores[1]);
            c_new.setU3(colores[2]);
            c_new.setR3(colores[3]);
            c_new.setR6(colores[4]);
            c_new.setR9(colores[5]);
            c_new.setD7(colores[6]);
            c_new.setD8(colores[7]);
            c_new.setD9(colores[8]);
            c_new.setL1(colores[9]);
            c_new.setL4(colores[10]);
            c_new.setL7(colores[11]);
            
            c_new.setB1(colores[12]);
            c_new.setB2(colores[13]);
            c_new.setB3(colores[14]);
            c_new.setB4(colores[15]);
            c_new.setB5(colores[16]);
            c_new.setB6(colores[17]);
            c_new.setB7(colores[18]);
            c_new.setB8(colores[19]);
            c_new.setB9(colores[20]);
        }
        return c_new;
    }

}
