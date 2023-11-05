package business;

import dao.TokenDao;
import models.Token;

public class TokenService {
	public Token add (Token token) {
		TokenDao tokenDao = new TokenDao();
		return tokenDao.add(token);
	}

	public Token getByUUID(String uuid) {
		TokenDao tokenDao = new TokenDao();
		return tokenDao.getByUUID(uuid);
	}

	public boolean caducarTokens(Integer idUsuario) {
		TokenDao tokenDao = new TokenDao();
		return tokenDao.caducarTokens(idUsuario);
	}
}
