package com.syswarp.views.multiplicaciones;

import java.util.List;
import java.util.Optional;

import com.syswarp.data.entity.Multiplicaciones;
import com.syswarp.data.entity.Operaciones;
import com.syswarp.data.service.MultiplicacionesRepository;
import com.syswarp.data.service.MultiplicacionesService;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "multiplicaciones", layout = MainView.class)
@PageTitle("Multiplicaciones")
@CssImport("./styles/views/multiplicaciones/multiplicaciones-view.css")
public class MultiplicacionesView extends Div {
	@Autowired
	MultiplicacionesRepository mulr;
	
	private Grid<Multiplicaciones> grid = new Grid<>(Multiplicaciones.class, false);

	private ComboBox<Multiplicaciones> idmultiplicacion_padre;

	private TextField descripcion;

	private Button cancel = new Button("Cancelar");
	private Button save = new Button("Guardar");
	private Button filtrar = new Button("Filtrar");
	private Button alta = new Button("Nueva Multiplicacion");
	private Button baja = new Button("Eliminar");

	private BeanValidationBinder<Multiplicaciones> binder;

	private Multiplicaciones multiplicaciones;
	private TextField filtro;

	public MultiplicacionesView(@Autowired MultiplicacionesService multiplicacionesService) {
		setId("multiplicaciones-view");
		// Create UI
		baja.setEnabled(false);
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();

		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		grid.addColumn("id").setHeader("Codigo").setWidth("100px"); // .setAutoWidth(true);

		// grid.addColumn("idmultiplicacion_padre").setAutoWidth(true).setHeader("Cod.
		// Multiplicacion Padre");
		grid.addColumn("descripcion").setAutoWidth(true).setHeader("Descripcion");
		grid.addColumn(this::FKMultiplicacionID).setHeader("ID Padre").setAutoWidth(true);

		grid.addColumn(this::FKMultiplicacion).setHeader("Padre").setAutoWidth(true);

		grid.setDataProvider(new CrudServiceDataProvider<>(multiplicacionesService));
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setHeightFull();

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				Optional<Multiplicaciones> multiplicacionesFromBackend = multiplicacionesService
						.get(event.getValue().getId());
				// when a row is selected but the data is no longer available, refresh grid
				if (multiplicacionesFromBackend.isPresent()) {
					baja.setEnabled(true);
					populateForm(multiplicacionesFromBackend.get());
				} else {
					refreshGrid();
					baja.setEnabled(false);
				}
			} else {
				clearForm();
				baja.setEnabled(false);
			}
		});

		// Configure Form
		binder = new BeanValidationBinder<>(Multiplicaciones.class);

		// Bind fields. This where you'd define e.g. validation rules
		// binder.forField(idmultiplicacion_padre).withConverter(new
		// StringToIntegerConverter("Solo se aceptan valores numero"))
		// .bind("idmultiplicacion_padre");

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				if (this.multiplicaciones == null) {
					this.multiplicaciones = new Multiplicaciones();
				}
				binder.writeBean(this.multiplicaciones);
				if (validarCampos()) {
					multiplicacionesService.update(this.multiplicaciones);
					clearForm();
					refreshGrid();
					Notification.show("Multiplicaciones actualizado correctamente.");
				}
			} catch (ValidationException validationException) {
				Notification.show("Ocurrio una excepcion mientras se intentaba actualizar Multiplicaciones.");
			}
		});

		// listerner para escuchar el boton de altas
		alta.addClickListener(e -> {
			clearForm();
			refreshGrid();
			// Notification.show("Alta de Medio");
		});

		// listerner para escuchar el filtro
		filtrar.addClickListener(e -> {
			if (filtro.getValue().equalsIgnoreCase("")) {
				Notification.show("Eliminacion de Filtro");
				grid.setItems(multiplicacionesService.findAll(filtro.getValue()));
			} else {
				grid.setItems(multiplicacionesService.findAll(filtro.getValue()));
				refreshGrid();
				Notification.show("Filtro aplicado");
			}
		});

		// listener para escuchar el boton de bajas
		baja.addClickListener(e -> {

			ConfirmDialog dialog = new ConfirmDialog("Confirma eliminacion de registro",
					"Esta seguro que quiere eliminar el item seleccionado?", "Cancelar", this::onCancelar, "Borrar",
					null);
			dialog.setConfirmButtonTheme("error primary");
			dialog.open();

			multiplicacionesService.delete(this.multiplicaciones.getId());
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
		// idmultiplicacion = new TextField("Idmultiplicacion");
		// idmultiplicacion_padre = new TextField("Cod. Mult. (Padre)");
		descripcion = new TextField("Descripcion");
		idmultiplicacion_padre = new ComboBox("Id Padre");
		// Component[] fields = new Component[]{idmultiplicacion_padre, descripcion};
		Component[] fields = new Component[] { descripcion, idmultiplicacion_padre };
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

	private void populateForm(Multiplicaciones value) {
		this.multiplicaciones = value;
		// Multiplicaciones
		List<Multiplicaciones> multiplicacionesList = mulr.findAll();
		idmultiplicacion_padre.setItems(multiplicacionesList);
		idmultiplicacion_padre.setItemLabelGenerator(Multiplicaciones::getDescripcion);

		binder.readBean(this.multiplicaciones);
	}

	private String FKMultiplicacion(Multiplicaciones mu) {
		if (mu.getPadre() == null)
			return "Original";
		else
			return mu.getPadre().getDescripcion();
	}

	private String FKMultiplicacionID(Multiplicaciones mu) {
		if (mu.getPadre() == null)
			return "#";
		else
			return mu.getPadre().getId().toString();
	}

	private boolean validarCampos() {
		boolean salida = true;
		if(descripcion.getValue()==null || descripcion.getValue().trim().equals("")) {
			Notification.show("El campo descripcion no puede quedar vacio");
			salida = false;
		}
		return salida;
	}

	
}
