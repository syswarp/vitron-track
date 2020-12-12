package com.syswarp.views.variedades;

import java.util.Optional;

import com.syswarp.data.entity.Variedades;
import com.syswarp.data.service.VariedadesService;
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

@Route(value = "variedades", layout = MainView.class)
@PageTitle("Variedades")
@CssImport("./styles/views/variedades/variedades-view.css")
public class VariedadesView extends Div {

    private Grid<Variedades> grid = new Grid<>(Variedades.class, false);

    private TextField idvariedad;
    private TextField variedad;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Variedades> binder;

    private Variedades variedades;

    public VariedadesView(@Autowired VariedadesService variedadesService) {
        setId("variedades-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idvariedad").setAutoWidth(true);
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
                    populateForm(variedadesFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Variedades.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idvariedad).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idvariedad");

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

                variedadesService.update(this.variedades);
                clearForm();
                refreshGrid();
                Notification.show("Variedades details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the variedades details.");
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
        idvariedad = new TextField("Idvariedad");
        variedad = new TextField("Variedad");
        Component[] fields = new Component[]{idvariedad, variedad};

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

    private void populateForm(Variedades value) {
        this.variedades = value;
        binder.readBean(this.variedades);

    }
}
