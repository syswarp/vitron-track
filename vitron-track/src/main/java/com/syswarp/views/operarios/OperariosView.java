package com.syswarp.views.operarios;

import java.util.Optional;

import com.syswarp.data.entity.Operarios;
import com.syswarp.data.service.OperariosService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "operarios", layout = MainView.class)
@PageTitle("Operarios")
@CssImport("./styles/views/operarios/operarios-view.css")
public class OperariosView extends Div {

    private Grid<Operarios> grid = new Grid<>(Operarios.class, false);

    private TextField operario;
    private TextField usuario;
    private PasswordField   clave;
    private TextField esadmin;

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");
	private Button filtrar = new Button("Filtrar");
	private Button alta = new Button("Nuevo Operario");
	private Button baja = new Button("Eliminar");
	
	private TextField filtro;

    private BeanValidationBinder<Operarios> binder;

    private Operarios operarios;

    public OperariosView(@Autowired OperariosService operariosService) {
        setId("operarios-view");
        // Create UI
        baja.setEnabled(false);
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id").setHeader("Codigo").setWidth("100px") ;  //.setAutoWidth(true);
        grid.addColumn("operario").setAutoWidth(true);
        grid.addColumn("usuario").setAutoWidth(true);
      //  grid.addColumn("clave").setAutoWidth(true);
        grid.addColumn("esadmin").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(operariosService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Operarios> operariosFromBackend = operariosService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (operariosFromBackend.isPresent()) {
                	baja.setEnabled(true);
                    populateForm(operariosFromBackend.get());
                } else {
                	baja.setEnabled(false);
                    refreshGrid();
                }
            } else {
            	baja.setEnabled(false);
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Operarios.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.operarios == null) {
                    this.operarios = new Operarios();
                	//String md5Hex = DigestUtils.sha256Hex(clave.getValue());
                	//clave.setValue(md5Hex);
                	// todo, tengo que ver como se cambia la clave en realidad y hacer un desarrollo para eso.
                }
                binder.writeBean(this.operarios);
                if (validarCampos()) {
                    operariosService.update(this.operarios);
                     clearForm();
                     refreshGrid();
                     Notification.show("Operarios actualizado correctamente.");
                }    
            } catch (ValidationException validationException) {
                Notification.show("Ocurrio una excepcion mientras se intentaba actualizar Operarios.");
            }
        });

		// listerner para escuchar el boton de altas
		alta.addClickListener(e -> {
			clearForm();
			refreshGrid();
			//Notification.show("Alta de Medio");
		});

		// listerner para escuchar el filtro
		filtrar.addClickListener(e -> {
			if (filtro.getValue().equalsIgnoreCase("")) {
				Notification.show("Eliminacion de Filtro");
				grid.setItems(operariosService.findAll(filtro.getValue()));
			} else {
				grid.setItems(operariosService.findAll(filtro.getValue()));
				refreshGrid();
				Notification.show("Filtro aplicado");
			}
		});

		// listener para escuchar el boton de bajas
		baja.addClickListener(e -> {

			ConfirmDialog dialog = new ConfirmDialog("Confirma eliminacion de registro",
					"Esta seguro que quiere eliminar el item seleccionado?", "Cancelar",this::onCancelar, "Borrar", null);
			dialog.setConfirmButtonTheme("error primary");
			dialog.open();
		    
			operariosService.delete(this.operarios.getId());
			Notification.show("Se elimino el Item");
	     	refreshGrid();
				
		});

    }
	public void onCancelar(ConfirmEvent l) {
		   Notification.show("Cancelado"); 
			//

		}

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        operario = new TextField("Operario");
        usuario = new TextField("Usuario");
        clave = new PasswordField("Clave");
        esadmin = new TextField("Esadmin");
        Component[] fields = new Component[]{ operario, usuario, clave, esadmin};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		baja.addThemeVariants(ButtonVariant.LUMO_ERROR);
		buttonLayout.add(save, cancel, baja);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        
        createFiltroLayout(wrapper);
        
        wrapper.add(grid);
    }
    
	private void createFiltroLayout(Div editorLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		filtro = new TextField();
		filtro.setPlaceholder("Indique ocurrencia");

		Icon icon = new Icon(VaadinIcon.EDIT);
		alta.getElement().appendChild(icon.getElement());

		alta.getElement().getStyle().set("margin-left", "auto");
		alta.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		buttonLayout.setId("button-layout");
		buttonLayout.setWidthFull();
		buttonLayout.setSpacing(true);

		filtrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Icon iconFilter = new Icon(VaadinIcon.FILTER);
		filtrar.getElement().appendChild(iconFilter.getElement());

		
		buttonLayout.add(filtro, filtrar, alta);
		editorLayoutDiv.add(buttonLayout);
	}

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Operarios value) {
        this.operarios = value;
        binder.readBean(this.operarios);

    }
    private boolean validarCampos() {
		boolean salida = true;
		if(operario.getValue()==null || operario.getValue().trim().equals("")) {
			Notification.show("El campo operario no puede quedar vacio");
			salida = false;
		}
		if(usuario.getValue()==null || usuario.getValue().trim().equals("")) {
			Notification.show("El campo usuario no puede quedar vacio");
			salida = false;
		}

		if(clave.getValue()==null || clave.getValue().trim().equals("")) {
			Notification.show("El campo clave no puede quedar vacio");
			salida = false;
		}
		
		if(esadmin.getValue()==null || esadmin.getValue().trim().equals("")) {
			Notification.show("El campo (Es Administrador(S/N)) no puede quedar vacio");
			salida = false;
		}
		
		
		return salida;
	}
}
