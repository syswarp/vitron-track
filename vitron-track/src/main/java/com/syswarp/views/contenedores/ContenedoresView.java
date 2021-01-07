package com.syswarp.views.contenedores;

import java.util.Optional;

import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.service.ContenedoresService;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "Contenedor", layout = MainView.class)
@PageTitle("Contenedores")
@CssImport("./styles/views/contenedores/contenedores-view.css")
public class ContenedoresView extends Div {

	private Grid<Contenedores> grid = new Grid<>(Contenedores.class, false);

	// private TextField idcontenedor;
	private TextField contenedor;
	private TextField tagid;

	private Button cancel = new Button("Cancelar");
	private Button save = new Button("Guardar");
	private Button filtrar = new Button("Filtrar");
	private Button alta = new Button("Nuevo Contenedor");
	private Button baja = new Button("Eliminar");

	private BeanValidationBinder<Contenedores> binder;

	private Contenedores contenedores;
	private TextField filtro = new TextField("Buscar ocurrencia...");

	public ContenedoresView(@Autowired ContenedoresService contenedoresService) {
		setId("contenedores-view");
		// Create UI
		baja.setEnabled(false);

		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();

		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		// grid.addColumn("idcontenedor").setAutoWidth(true);
		grid.addColumn("id").setHeader("Codigo").setWidth("100px"); // .setAutoWidth(true);

		grid.addColumn("contenedor").setAutoWidth(true);
		grid.addColumn("tagid").setAutoWidth(true);
		grid.setDataProvider(new CrudServiceDataProvider<>(contenedoresService));
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

		grid.setHeightFull();

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				Optional<Contenedores> contenedoresFromBackend = contenedoresService.get(event.getValue().getId());
				// when a row is selected but the data is no longer available, refresh grid
				if (contenedoresFromBackend.isPresent()) {
					baja.setEnabled(true);
					populateForm(contenedoresFromBackend.get());
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
		binder = new BeanValidationBinder<>(Contenedores.class);

		// Bind fields. This where you'd define e.g. validation rules
		// binder.forField(idcontenedor).withConverter(new
		// StringToIntegerConverter("Only numbers are allowed")).bind("idcontenedor");

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				if (this.contenedores == null) {
					this.contenedores = new Contenedores();
				}
				binder.writeBean(this.contenedores);
				if (validarCampos()) {
					contenedoresService.update(this.contenedores);
					clearForm();
					refreshGrid();
					Notification.show("Contenedor actualizado correctamente ");
				}
			} catch (ValidationException validationException) {
				Notification.show("Ocurrio una excepcion mientras se intentaba actualizar Contenedores");
			}
		});

		// listerner para escuchar el boton de altas
		alta.addClickListener(e -> {
			clearForm();
			refreshGrid();
			// Notification.show("Alta de Contenedor");
		});

		// listerner para escuchar el filtro
		filtrar.addClickListener(e -> {
			if (filtro.getValue().equalsIgnoreCase("")) {
				Notification.show("Eliminacion de Filtro");
				grid.setItems(contenedoresService.findAll(filtro.getValue()));
			} else {
				grid.setItems(contenedoresService.findAll(filtro.getValue()));
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

			contenedoresService.delete(this.contenedores.getId());
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
		// idcontenedor = new TextField("Idcontenedor");
		contenedor = new TextField("Contenedor");
		tagid = new TextField("Tagid");
		// Component[] fields = new Component[]{idcontenedor, contenedor, tagid};
		Component[] fields = new Component[] { contenedor, tagid };

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
		// wrapper.add(filtro);
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

	private void populateForm(Contenedores value) {
		this.contenedores = value;
		binder.readBean(this.contenedores);

	}
	
	private boolean validarCampos() {
		boolean salida = true;
		if(contenedor.getValue()==null || contenedor.getValue().trim().equals("")) {
			Notification.show("El campo contenedor no puede quedar vacio");
			salida = false;
		}
		if(tagid.getValue()==null || tagid.getValue().trim().equals("")) {
			Notification.show("El campo tagid no puede quedar vacio");
			salida = false;
		}

		return salida;
	}
}
