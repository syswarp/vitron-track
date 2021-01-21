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

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.syswarp.data.entity.Operarios;
import com.syswarp.data.service.OperacionesService;
//import com.syswarp.data.service.AuthService;
//import com.syswarp.data.service.AuthService.AuthException;
import com.syswarp.data.service.OperariosRepository;


@Route(value = "Login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
//@RouteAlias(value="")
public class LoginView extends Div {
	private final VerticalLayout layout = new VerticalLayout();
	 @Autowired
	 OperariosRepository ore;
	
	public LoginView() {
    	//setId("login-view");
		
		
    	LoginForm component = new LoginForm();
    	
    	component.setI18n(SetEspanol());
    	component.addLoginListener(e -> {
    	    boolean isAuthenticated = Autenticar(e);
    	    if (isAuthenticated) {
                  	    	
    	    	 navigateToMainPage();
    	    	
    	    } else {
    	    	Notification.show("Credenciales Invalidas");
    	        component.setError(true);
    	       
    	    }
    	});
    	
    	add(component);
    	
    	
         
    }

	private void navigateToMainPage() {
		// TODO Auto-generated method stub
		UI.getCurrent().navigate("Contenedor");
		// guardar datos del usuario con esto
		//VaadinSession.getCurrent()
	}

	private boolean Autenticar(LoginEvent e) {
		// en el evento por parametro vienen los datos suficientes para hacer la autenticacion
	    // aca tengo que aprovechar a guardar en sesion el usuario si es que esta ok
		boolean salida = false;
		String usuario = e.getUsername();
		String clave = e.getPassword();
		if(usuario.toLowerCase().equalsIgnoreCase("admin") && clave.toLowerCase().equalsIgnoreCase("admin")) salida = true; // por ahora dejo un salvo conducto
		
		// resta encriptar la clave aca y al momento de grabar o hacer update dentro de OperariosView.
		
		
		List<Operarios> op = ore.findUser(usuario, clave);
		Iterator it = op.iterator();
		if(it.hasNext()) return true;
		
		//  valido contra la base de datos
		
		
		return salida;
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
