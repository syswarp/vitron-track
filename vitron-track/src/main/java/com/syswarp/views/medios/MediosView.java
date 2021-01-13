package com.syswarp.views.medios;

import java.util.Objects;
import com.syswarp.Utiles;
import java.util.Optional;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.service.MediosRepository;
import com.syswarp.data.service.MediosService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.ui.themes.ValoTheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import org.vaadin.reports.PrintPreviewReport;

import com.syswarp.views.main.MainView;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "medios", layout = MainView.class)
@PageTitle("Medios")
@CssImport("./styles/views/medios/medios-view.css")
public class MediosView extends Div {

	private Grid<Medios> grid = new Grid<>(Medios.class, false);

	// private TextField idmedio;
	private TextField medio;

	private Button cancel = new Button("Cancelar");
	private Button save = new Button("Guardar");
	private Button filtrar = new Button("Filtrar");
	private Button alta = new Button("Nuevo Medio");
	private Button baja = new Button("Eliminar");

	private BeanValidationBinder<Medios> binder;

	private Medios medios;

	private TextField filtro;

	public MediosView(@Autowired MediosService mediosService) {
		setId("medios-view");
		baja.setEnabled(false);

		
		// Create UI
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();

		createGridLayout(splitLayout);

		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		// grid.addColumn("idmedio").setAutoWidth(true);
		grid.addColumn("id").setHeader("Codigo").setWidth("100px"); // .setAutoWidth(true);
		grid.addColumn("medio").setAutoWidth(true);
	
		/* por si quiero agregar un icono o imagen
		grid.addComponentColumn( item-> { Icon icon = VaadinIcon.CHECK_CIRCLE.create(); 
		  return icon;
		   } );
		*/
		
		grid.setDataProvider(new CrudServiceDataProvider<>(mediosService));
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setHeightFull();

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				Optional<Medios> mediosFromBackend = mediosService.get(event.getValue().getId());
				// when a row is selected but the data is no longer available, refresh grid
				if (mediosFromBackend.isPresent()) {
					baja.setEnabled(true);
					populateForm(mediosFromBackend.get());
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
		binder = new BeanValidationBinder<>(Medios.class);

		// Bind fields. This where you'd define e.g. validation rules

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				java.util.Date hoy = new java.util.Date();
				Utiles u = new Utiles();
				if (this.medios == null) {
					this.medios = new Medios();
					this.medios.setFechaalt(u.convert(hoy)); 
				} else {
					this.medios.setFechaact(u.convert(hoy)); 
				}
				binder.writeBean(this.medios);
				if (validarCampos()) {
					
					
					mediosService.update(this.medios);
					Notification.show("Medios actualizado correctamente.");
					clearForm();
					refreshGrid();
				}
			} catch (ValidationException validationException) {
				Notification.show("Ocurrio un error mientras se intentaba actualizar Medios.");
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
				grid.setItems(mediosService.findAll(filtro.getValue()));
			} else {
				grid.setItems(mediosService.findAll(filtro.getValue()));
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
		    
			mediosService.delete(this.medios.getId());
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

		medio = new TextField("Medio");
		Component[] fields = new Component[] { medio };

		for (Component field : fields) {
			((HasStyle) field).addClassName("full-width");
		}
		formLayout.add(fields);
		editorDiv.add(formLayout);
		createButtonLayout(editorLayoutDiv);

		splitLayout.addToSecondary(editorLayoutDiv);
	}

	private void createButtonLayout(Div buttonLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setId("button-layout");
		buttonLayout.setWidthFull();
		buttonLayout.setSpacing(true);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		baja.addThemeVariants(ButtonVariant.LUMO_ERROR);
		buttonLayout.add(save, cancel, baja);
		buttonLayoutDiv.add(buttonLayout);
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

	private void populateForm(Medios value) {
		this.medios = value;
		binder.readBean(this.medios);

	}
    
	
	private  void reporte(ListDataProvider<Medios> dataProvider, @Autowired MediosService mediosService) {

		PrintPreviewReport<Medios> report = new PrintPreviewReport<>(Medios.class);
		report.setItems(mediosService.findAll());
		//addComponent(report);
		
	}
	
	private boolean validarCampos() {
		boolean salida = true;
		if(medio.getValue()==null || medio.getValue().trim().equals("")) {
			Notification.show("El campo medio no puede quedar vacio");
			salida = false;
		}
		return salida;
	}
	
	
	
	
}