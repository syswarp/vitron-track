package com.syswarp.views.multiplicaciones;

import java.util.Optional;

import com.syswarp.data.entity.Multiplicaciones;
import com.syswarp.data.service.MultiplicacionesService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
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

    private Grid<Multiplicaciones> grid = new Grid<>(Multiplicaciones.class, false);

    private TextField idmultiplicacion_padre;
    private TextField descripcion;

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private BeanValidationBinder<Multiplicaciones> binder;

    private Multiplicaciones multiplicaciones;

    public MultiplicacionesView(@Autowired MultiplicacionesService multiplicacionesService) {
        setId("multiplicaciones-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("id").setHeader("Codigo").setWidth("100px") ;  //.setAutoWidth(true);

        grid.addColumn("idmultiplicacion_padre").setAutoWidth(true).setHeader("Cod. Multiplicacion Padre");
        grid.addColumn("descripcion").setAutoWidth(true).setHeader("Descripcion");

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
                    populateForm(multiplicacionesFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Multiplicaciones.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idmultiplicacion_padre).withConverter(new StringToIntegerConverter("Solo se aceptan valores numero"))
                .bind("idmultiplicacion_padre");

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

                multiplicacionesService.update(this.multiplicaciones);
                clearForm();
                refreshGrid();
                Notification.show("Multiplicaciones actualizado correctamente.");
            } catch (ValidationException validationException) {
                Notification.show("Ocurrio una excepcion mientras se intentaba actualizar Multiplicaciones.");
            }
        });

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
 //       idmultiplicacion = new TextField("Idmultiplicacion");
        idmultiplicacion_padre = new TextField("Cod. Mult. (Padre)");
        descripcion=new TextField("Descripcion");
        Component[] fields = new Component[]{idmultiplicacion_padre, descripcion};

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
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
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
        binder.readBean(this.multiplicaciones);

    }
}
