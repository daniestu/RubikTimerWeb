package utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import business.TokenService;
import models.Token;
import models.Usuario;

public class TokenUtils {
	
	public static Token generateToken(Usuario usuario) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replaceAll("-", "");
        Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
        
        Token token = new Token(uuidString, usuario.getIdUsuario(), fechaCreacion, false);
        
        return token;
    }

	public static boolean verificarCaducidad(String uuid) {
		TokenService tokenService = new TokenService();
		Token token = tokenService.getByUUID(uuid);
		if (token == null || token.getToken_id() == null || token.getToken_id() == 0 || token.isCaducado()) {
            return true;
        }
		
        Timestamp fechaActual = new Timestamp(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.MINUTE, -30);
        Timestamp fechaHace30Minutos = new Timestamp(calendar.getTimeInMillis());

        Timestamp fechaCreacionToken = token.getFechaCreacion();
        return fechaCreacionToken.before(fechaHace30Minutos);
	}

}
