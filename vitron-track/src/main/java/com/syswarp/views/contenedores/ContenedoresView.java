package com.syswarp.views.contenedores;

import java.util.Optional;

import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.service.ContenedoresService;
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

@Route(value = "Contenedor", layout = MainView.class)
@PageTitle("Contenedores")
@CssImport("./styles/views/contenedores/contenedores-view.css")
public class ContenedoresView extends Div {

    private Grid<Contenedores> grid = new Grid<>(Contenedores.class, false);

    //private TextField idcontenedor;
    private TextField contenedor;
    private TextField tagid;
    
    private TextField filtro = new  TextField("Buscar ocurrencia...");

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private BeanValidationBinder<Contenedores> binder;

    private Contenedores contenedores;

    public ContenedoresView(@Autowired ContenedoresService contenedoresService) {
        setId("contenedores-view");
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
       // grid.addColumn("idcontenedor").setAutoWidth(true);
        grid.addColumn("id").setHeader("Codigo").setWidth("100px") ;  //.setAutoWidth(true);
        
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
                    populateForm(contenedoresFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Contenedores.class);

        // Bind fields. This where you'd define e.g. validation rules
      //  binder.forField(idcontenedor).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("idcontenedor");

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

                contenedoresService.update(this.contenedores);
                clearForm();
                refreshGrid();
                Notification.show("Contenedor actualizado correctamente ");
            } catch (ValidationException validationException) {
                Notification.show("Ocurrio una excepcion mientras se intentaba actualizar Contenedores");
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
       // idcontenedor = new TextField("Idcontenedor");
        contenedor = new TextField("Contenedor");
        tagid = new TextField("Tagid");
       // Component[] fields = new Component[]{idcontenedor, contenedor, tagid};
        Component[] fields = new Component[]{ contenedor, tagid};

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
      //  wrapper.add(filtro);
        wrapper.add(grid);
      
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
}
