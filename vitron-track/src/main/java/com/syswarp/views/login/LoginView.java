package com.syswarp.views.login;

import com.vaadin.flow.component.Text;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.Version;



import com.syswarp.data.entity.Operarios;
import com.syswarp.data.service.AuthService;
//import com.syswarp.data.service.AuthService;
//import com.syswarp.data.service.AuthService.AuthException;


@Route(value = "Login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
//@RouteAlias(value="")
public class LoginView extends Div {
	private final VerticalLayout layout = new VerticalLayout();
	
	
	public LoginView(AuthService authService) {
    	//setId("login-view");
     
	 TextField username = new TextField("Username");
     PasswordField password = new PasswordField("Password");		
	 add(new H1("Vitron"),username, password
			 , new Button("Login", event -> { 
				 try {
				 authService.autenticar(username.getValue(), password.getValue());
				 UI.getCurrent().navigate("operaciones");
				 } catch(AuthService.AuthException e) {
					 Notification.show("Credenciales Invalidas");
				 }
			 } 
					 
					 ) 
			 
			 );	
	/*	
    	LoginForm component = new LoginForm();
    	component.setI18n(SetEspanol());
    	component.addLoginListener(e -> {
    	    boolean isAuthenticated = Autenticar(e);
    	    if (isAuthenticated) {
    	    	// set el usuario 
    	    	//VaadinSession.getCurrent().setAttribute(usuario, value);
    	    	
    	    	 navigateToMainPage();
    	    	
    	    } else {
    	    	Notification.show("Credenciales Invalidas");
    	        component.setError(true);
    	       
    	    }
    	});
    	
    	add(component);
    	
    	*/
    	
    	/*
        TextField usuario = new TextField("Usuario");
        PasswordField clave = new PasswordField("Clave");
        
        add(new H1("Bienvenido"), usuario, clave, new Button("Acceder", 
        		event->{Autenticar( usuario.getValue(), clave.getValue() );
				} ) );
        */
        
    }

	private void navigateToMainPage() {
		// TODO Auto-generated method stub
		UI.getCurrent().navigate("Contenedor");
		// guardar datos del usuario con esto
		//VaadinSession.getCurrent()
	}

	private boolean Autenticar(LoginEvent e) {
		return true;
	}

	private LoginI18n SetEspanol() {
	    final LoginI18n i18n = LoginI18n.createDefault();

	    i18n.setHeader(new LoginI18n.Header());
	    i18n.getHeader().setTitle("Vitron Track");
	    i18n.getHeader().setDescription("Vitron Track");
	    i18n.getForm().setUsername("Usuario");
	    i18n.getForm().setTitle("Bienvenido a Vitron Track");
	    i18n.getForm().setSubmit("Entrar");
	    i18n.getForm().setPassword("Clave");
	    i18n.getForm().setForgotPassword("Olvide mi clave");
	    i18n.getErrorMessage().setTitle("Usuario/Clave inválidos");
	    i18n.getErrorMessage()
	        .setMessage("Vuelva a cargar usuario y contraseña para intentar de nuevo.");
	    i18n.setAdditionalInformation(
	        "Espacio para informacion adicional"
	            + "... ");
	    return i18n;
	}
	
	
}
