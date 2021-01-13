package com.syswarp.data.service;

import com.syswarp.data.entity.User;
import com.vaadin.flow.component.Component;

public class AuthService {
	
	//public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
	//}
	
	public class AuthException extends Exception{
		
	}
	private final UserRepository userRepository;
	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public void autenticar(String usuario, String clave) throws AuthException  {
	   User user = userRepository.getByUsername(usuario);
	   if (user != null && user.checkPassword(clave)) {
		   
	   }  else {
		  throw new AuthException();
	   }
	}
}
