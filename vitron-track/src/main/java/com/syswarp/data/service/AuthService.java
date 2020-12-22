package com.syswarp.data.service;

public class AuthService {
    //private final OperariosRepository operariosRepository;
    // todo por ahora solo registro con un usuario clavado aca.

	public class AuthException extends Exception{
		
	}
	
	public void autenticar(String usuario, String clave) throws AuthException {
		if (usuario.equalsIgnoreCase("ADMIN") && clave.equalsIgnoreCase("ADMIN") ) {
			
		} else {
			throw new AuthException();
			
		}
			
	}
}
