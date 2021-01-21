package com.syswarp.views.operaciones;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Multiplicaciones;
import com.syswarp.data.entity.Operaciones;
import com.syswarp.data.entity.Operarios;
import com.syswarp.data.entity.Variedades;
import com.syswarp.data.service.ContenedoresRepository;
import com.syswarp.data.service.ContenedoresService;
import com.syswarp.data.service.MediosRepository;
import com.syswarp.data.service.MultiplicacionesRepository;
import com.syswarp.data.service.OperacionesRepository;
import com.syswarp.data.service.OperacionesService;
import com.syswarp.data.service.OperariosRepository;
import com.syswarp.data.service.VariedadesRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
/*
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.themes.ValoTheme;
*/
//import ar.com.syswarp.MesaCoordinacion.samples.ResetButtonForTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "operaciones", layout = MainView.class)
@PageTitle("Operaciones")
@CssImport("./styles/views/operaciones/operaciones-view.css")
public class OperacionesView extends Div {
	

	 @Autowired
	 ContenedoresRepository cr;

	 @Autowired
	 MediosRepository mr;

	 @Autowired
	 OperariosRepository or;

	 @Autowired
	 OperacionesRepository ore;
	 
	 
	 @Autowired
	 VariedadesRepository vr;

	 @Autowired
	 MultiplicacionesRepository mulr;

	 
	 private TextField filtro;
		private Button filtrar = new Button("Filtrar");
		private Button alta = new Button("Nueva Operacion");
		private Button baja = new Button("Eliminar");

	
    private Grid<Operaciones> grid = new Grid<>(Operaciones.class, false);

  //  private TextField idoperacion;
    private DatePicker fecha;
   // private TextField idcontenedor;
    
    private ComboBox<Contenedores> contenedores;
    private ComboBox<Variedades>  variedades;
    private ComboBox<Operarios> operarios;
    private ComboBox<Multiplicaciones> multiplicaciones;
    private ComboBox<Medios> medios;
    private TextArea observaciones;
    private NumberField cantidad = new NumberField();

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private BeanValidationBinder<Operaciones> binder;

    private Operaciones operaciones;

    public OperacionesView(@Autowired OperacionesService operacionesService) {
    	baja.setEnabled(false);
    	setId("operaciones-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
      //  grid.addColumn("idoperacion").setAutoWidth(true);
        grid.addColumn("fecha").setAutoWidth(true);
        
        
        grid.addColumn(this::FKContenedor).setHeader("Contenedor").setAutoWidth(true);
        grid.addColumn(this::FKMedio).setHeader("Medio").setAutoWidth(true);
        grid.addColumn(this::FKOperario).setHeader("Operario").setAutoWidth(true);
        grid.addColumn(this::FKVariedad).setHeader("Variedad").setAutoWidth(true);
        grid.addColumn(this::FKMultiplicacion).setHeader("Multiplicacion").setAutoWidth(true);
        
        //grid.addColumn("idcontenedor").setAutoWidth(true);
        //grid.addColumn("idvariedad").setAutoWidth(true);
        //grid.addColumn("idoperario").setAutoWidth(true);
        
        
        //grid.addColumn("idmultiplicacion").setAutoWidth(true);
      //  grid.addColumn("idmedio").setAutoWidth(true);
        grid.addColumn("observaciones").setAutoWidth(true);
        grid.addColumn("cantidad").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(operacionesService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Operaciones> operacionesFromBackend = operacionesService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (operacionesFromBackend.isPresent()) {
                	baja.setEnabled(true);
                    populateForm(operacionesFromBackend.get());
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
        binder = new BeanValidationBinder<>(Operaciones.class);

        // Bind fields. This where you'd define e.g. validation rules
        //binder.forField(idcontenedor).bind("idcontenedor");
        //binder.forField(idvariedad).bind("idvariedad");
        //binder.forField(idoperario).bind("idoperario");
        //binder.forField(idmultiplicacion).bind("idmultiplicacion");
        //binder.forField(idmedio).bind("idmedio");
        
        

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.operaciones == null) {
                    this.operaciones = new Operaciones();
                }
                
                binder.writeBean(this.operaciones);
            	if (validarCampos()) {
                    operacionesService.update(this.operaciones);
                    clearForm();
                    refreshGrid();
                    Notification.show("Operacion actualizada correctamente");
            	} 
            } catch (ValidationException validationException) {
                Notification.show("Ocurrio un problema mientras se intentaba actualizar la operacion");
            }
        });

		// listerner para escuchar el boton de altas
		alta.addClickListener(e -> {
			clearForm();
			refreshGrid();
			//Notification.show("Alta de Operacion");
		});

		// listerner para escuchar el filtro
		filtrar.addClickListener(e -> {
			if (filtro.getValue().equalsIgnoreCase("")) {
				Notification.show("Eliminacion de Filtro");
				grid.setItems(operacionesService.findAll(filtro.getValue()));
			} else {
				grid.setItems(operacionesService.findAll(filtro.getValue()));
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
		    
			operacionesService.delete(this.operaciones.getId());
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
//        idoperacion = new TextField("Idoperacion");
        fecha = new DatePicker("Fecha");
        contenedores = new ComboBox("Contenedor");
        medios = new ComboBox("Medio");
        variedades = new ComboBox("Variedad");
        operarios = new ComboBox("Operario");
        multiplicaciones = new ComboBox("Multiplicacion");
        observaciones = new TextArea("Observaciones");
        cantidad = new NumberField("Cantidad");
        cantidad.setValue(1d);
        cantidad.setHasControls(true);
        cantidad.setMin(1);
        cantidad.setMax(5000);
        
        observaciones.getStyle().set("maxHeight", "150px");
        //observaciones.setHeightFull();
       
        Component[] fields = new Component[]{ fecha, contenedores, medios, variedades, operarios, multiplicaciones,
                 cantidad,observaciones};

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

    private void populateForm(Operaciones value) {
        this.operaciones = value;
        // poblar combos
        
        //contenedores
        List<Contenedores> contenedoresList = cr.findAll();
        contenedores.setItems(contenedoresList);
        contenedores.setItemLabelGenerator(Contenedores::getContenedor);
        //contenedores.setClearButtonVisible(true);
        //contenedores.setReadOnly(true);

        //medios
        List<Medios> mediosList = mr.findAll();
        medios.setItems(mediosList);
        medios.setItemLabelGenerator(Medios::getMedio);
        
        //operarios
        List<Operarios> operariosList = or.findAll();
        operarios.setItems(operariosList);
        operarios.setItemLabelGenerator(Operarios::getOperario);
        
        //variedades
        List<Variedades> variedadesList = vr.findAll();
        variedades.setItems(variedadesList);
        variedades.setItemLabelGenerator(Variedades::getVariedad);
        
        //Multiplicaciones
        List<Multiplicaciones> multiplicacionesList = mulr.findAll();
        multiplicaciones.setItems(multiplicacionesList);
        multiplicaciones.setItemLabelGenerator(Multiplicaciones::getDescripcion);
        
        
       // Contenedores
        // esto devuelve unicamente el que esta guardado.
        /*
        Contenedores c = new Contenedores();
        Set<Contenedores> s = c.getContenedores();
        List<Contenedores> cList = s.stream().collect(Collectors.toList()); 
        //contenedores.setItems(cList);
        //contenedores.setItemLabelGenerator(Contenedores::getContenedor);
        */
        //Contenedores c= value.getContenedores();
        
       
        
        
        
        
       // List<Contenedores> contenedoresList = contenedores.  ;
       // contenedores.setItemLabelGenerator(Contenedores::getContenedor);
       // contenedores.setItems(contenedoresList);
       // comboBox.setItems(departmentList); 
        binder.readBean(this.operaciones);

    }
    
    private String FKContenedor(Operaciones op) {
        return op.getContenedores().getContenedor();
     }

    private String FKMedio(Operaciones op) {
        return op.getMedios().getMedio();
     }

    private String FKOperario(Operaciones op) {
        return op.getOperarios().getOperario();
     }

    private String FKVariedad(Operaciones op) {
        return op.getVariedades().getVariedad();
     }

    private String FKMultiplicacion(Operaciones op) {
        return op.getMultiplicaciones().getDescripcion();
     }
    
    
    public HorizontalLayout createTopBar() {
        filtro = new TextField();
        //filtro.setStyleName("filter-textfield");
        filtro.setPlaceholder("Ocurrencia..");
        
        //ResetButtonForTextField.extend(filtro);
        
        
        // Apply the filter to grid's data provider. TextField value is never null
       // filtro.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        //nuevo.addStyleName(ValoTheme.BUTTON_PRIMARY);
        //nuevo.setIcon(VaadinIcons.PLUS_CIRCLE);
        //nuevo.addClickListener(click -> viewLogic.nuevo());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
      //  topLayout.addComponent(filtro);
      //  topLayout.addComponent(nuevo);
       // topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
      //  topLayout.setExpandRatio(filter, 1);
       // topLayout.setStyleName("top-bar");
        return topLayout;
    }

	private boolean validarCampos() {
		boolean salida = true;
		if(observaciones.getValue()==null || observaciones.getValue().trim().equals("")) {
			Notification.show("El campo observaciones no puede quedar vacio");
			salida = false;
		}
		return salida;
	}
	 
	
}
