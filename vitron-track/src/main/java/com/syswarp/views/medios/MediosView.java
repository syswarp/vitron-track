package com.syswarp.views.medios;

import java.util.Optional;

import com.syswarp.data.entity.Medios;
import com.syswarp.data.service.MediosService;
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

@Route(value = "medios", layout = MainView.class)
@PageTitle("Medios")
@CssImport("./styles/views/medios/medios-view.css")
public class MediosView extends Div {

    private Grid<Medios> grid = new Grid<>(Medios.class, false);

   // private TextField idmedio;
    private TextField medio;

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private BeanValidationBinder<Medios> binder;

    private Medios medios;

    public MediosView(@Autowired MediosService mediosService) {
        setId("medios-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
       // grid.addColumn("idmedio").setAutoWidth(true);
        grid.addColumn("id").setHeader("Codigo").setWidth("100px") ;  //.setAutoWidth(true);
        grid.addColumn("medio").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(mediosService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Medios> mediosFromBackend = mediosService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (mediosFromBackend.isPresent()) {
                    populateForm(mediosFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
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
                if (this.medios == null) {
                    this.medios = new Medios();
                }
                binder.writeBean(this.medios);

                mediosService.update(this.medios);
                clearForm();
                refreshGrid();
                Notification.show("Medios actualizado correctamente.");
            } catch (ValidationException validationException) {
                Notification.show("Ocurrio un error mientras se intentaba actualizar Medios.");
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
        medio = new TextField("Medio");
        Component[] fields = new Component[]{ medio};

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

    private void populateForm(Medios value) {
        this.medios = value;
        binder.readBean(this.medios);

    }
}
