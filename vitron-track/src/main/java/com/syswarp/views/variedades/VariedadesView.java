package com.syswarp.views.variedades;


import java.util.Objects;
import java.util.Optional;

import com.syswarp.data.entity.Variedades;
import com.syswarp.data.service.VariedadesRepository;
import com.syswarp.data.service.VariedadesService;
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

@Route(value = "variedades", layout = MainView.class)
@PageTitle("Variedades")
@CssImport("./styles/views/variedades/variedades-view.css")
public class VariedadesView extends Div {

	private Grid<Variedades> grid = new Grid<>(Variedades.class, false);

	private TextField variedad;

	private Button cancel = new Button("Cancelar");
	private Button save = new Button("Guardar");
	private Button filtrar = new Button("Filtrar");
	private Button alta = new Button("Nueva Variedad");
	private Button baja = new Button("Eliminar");

	private BeanValidationBinder<Variedades> binder;

	private Variedades variedades;

	private TextField filtro;

	public VariedadesView(@Autowired VariedadesService variedadesService) {
		setId("variedades-view");
		baja.setEnabled(false);

		
		// Create UI
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();

		createGridLayout(splitLayout);

		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		grid.addColumn("id").setHeader("Codigo").setWidth("100px"); // .setAutoWidth(true);
		grid.addColumn("variedad").setAutoWidth(true);
		grid.setDataProvider(new CrudServiceDataProvider<>(variedadesService));
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setHeightFull();

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				Optional<Variedades> variedadesFromBackend = variedadesService.get(event.getValue().getId());
				// when a row is selected but the data is no longer available, refresh grid
				if (variedadesFromBackend.isPresent()) {
					baja.setEnabled(true);
					populateForm(variedadesFromBackend.get());
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
		binder = new BeanValidationBinder<>(Variedades.class);

		// Bind fields. This where you'd define e.g. validation rules

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				if (this.variedades == null) {
					this.variedades = new Variedades();
				}
				binder.writeBean(this.variedades);
				if (validarCampos()) {
					variedadesService.update(this.variedades);
					Notification.show("Variedades actualizado correctamente.");
					clearForm();
					refreshGrid();
				}
			} catch (ValidationException validationException) {
				Notification.show("Ocurrio un error mientras se intentaba actualizar Variedades.");
			}
		});

		// listerner para escuchar el boton de altas
		alta.addClickListener(e -> {
			clearForm();
			refreshGrid();
			//Notification.show("Alta de Variedades");
		});

		// listerner para escuchar el filtro
		filtrar.addClickListener(e -> {
			if (filtro.getValue().equalsIgnoreCase("")) {
				Notification.show("Eliminacion de Filtro");
				grid.setItems(variedadesService.findAll(filtro.getValue()));
			} else {
				grid.setItems(variedadesService.findAll(filtro.getValue()));
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
		    
			variedadesService.delete(this.variedades.getId());
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

		variedad = new TextField("Variedad");
		Component[] fields = new Component[] { variedad };

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

	private void populateForm(Variedades value) {
		this.variedades = value;
		binder.readBean(this.variedades);

	}
    
	
	private  void reporte(ListDataProvider<Variedades> dataProvider, @Autowired VariedadesService variedadesService) {

		PrintPreviewReport<Variedades> report = new PrintPreviewReport<>(Variedades.class);
		report.setItems(variedadesService.findAll());
		//addComponent(report);
		
	}
	
	private boolean validarCampos() {
		boolean salida = true;
		if(variedad.getValue()==null || variedad.getValue().trim().equals("")) {
			Notification.show("El campo variedad no puede quedar vacio");
			salida = false;
		}
		return salida;
	}
	
}