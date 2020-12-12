package com.syswarp.views.operarios;

import java.util.Optional;

import com.syswarp.data.entity.Operarios;
import com.syswarp.data.service.OperariosService;
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

@Route(value = "operarios", layout = MainView.class)
@PageTitle("Operarios")
@CssImport("./styles/views/operarios/operarios-view.css")
public class OperariosView extends Div {

    private Grid<Operarios> grid = new Grid<>(Operarios.class, false);

    private TextField idoperario;
    private TextField operario;
    private TextField usuario;
    private TextField clave;
    private TextField esadmin;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Operarios> binder;

    private Operarios operarios;

    public OperariosView(@Autowired OperariosService operariosService) {
        setId("operarios-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idoperario").setAutoWidth(true);
        grid.addColumn("operario").setAutoWidth(true);
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("clave").setAutoWidth(true);
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
                    populateForm(operariosFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Operarios.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(idoperario).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idoperario");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.operarios == null) {
                    this.operarios = new Operarios();
                }
                binder.writeBean(this.operarios);

                operariosService.update(this.operarios);
                clearForm();
                refreshGrid();
                Notification.show("Operarios details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the operarios details.");
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
        idoperario = new TextField("Idoperario");
        operario = new TextField("Operario");
        usuario = new TextField("Usuario");
        clave = new TextField("Clave");
        esadmin = new TextField("Esadmin");
        Component[] fields = new Component[]{idoperario, operario, usuario, clave, esadmin};

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

    private void populateForm(Operarios value) {
        this.operarios = value;
        binder.readBean(this.operarios);

    }
}
