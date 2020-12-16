package com.syswarp.views.operaciones;

import java.util.List;
import java.util.Optional;

import com.syswarp.data.entity.Contenedores;
import com.syswarp.data.entity.Medios;
import com.syswarp.data.entity.Multiplicaciones;
import com.syswarp.data.entity.Operaciones;
import com.syswarp.data.entity.Operarios;
import com.syswarp.data.entity.Variedades;
import com.syswarp.data.service.OperacionesService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "operaciones", layout = MainView.class)
@PageTitle("Operaciones")
@CssImport("./styles/views/operaciones/operaciones-view.css")
public class OperacionesView extends Div {

    private Grid<Operaciones> grid = new Grid<>(Operaciones.class, false);

  //  private TextField idoperacion;
    private DatePicker fecha;
   // private TextField idcontenedor;
    
    private ComboBox<Contenedores> contenedores;
    private ComboBox<Variedades>  idvariedad;
    private ComboBox<Operarios> idoperario;
    private ComboBox<Multiplicaciones> idmultiplicacion;
    private ComboBox<Medios> idmedio;
    private TextField observaciones;

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");

    private BeanValidationBinder<Operaciones> binder;

    private Operaciones operaciones;

    public OperacionesView(@Autowired OperacionesService operacionesService) {
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
        
        
        //grid.addColumn(this::FKContenedor).setAutoWidth(true);
        
        //grid.addColumn("idcontenedor").setAutoWidth(true);
        grid.addColumn("idvariedad").setAutoWidth(true);
        grid.addColumn("idoperario").setAutoWidth(true);
        grid.addColumn("idmultiplicacion").setAutoWidth(true);
        grid.addColumn("idmedio").setAutoWidth(true);
        grid.addColumn("observaciones").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(operacionesService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Operaciones> operacionesFromBackend = operacionesService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (operacionesFromBackend.isPresent()) {
                    populateForm(operacionesFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Operaciones.class);

        // Bind fields. This where you'd define e.g. validation rules
        //binder.forField(idcontenedor).bind("idcontenedor");
        binder.forField(idvariedad).bind("idvariedad");
        binder.forField(idoperario).bind("idoperario");
        binder.forField(idmultiplicacion).bind("idmultiplicacion");
        binder.forField(idmedio).bind("idmedio");
        
        

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

                operacionesService.update(this.operaciones);
                clearForm();
                refreshGrid();
                Notification.show("Operaciones details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the operaciones details.");
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
//        idoperacion = new TextField("Idoperacion");
        fecha = new DatePicker("Fecha");
        contenedores = new ComboBox("Contenedor");
        idvariedad = new ComboBox("Variedad");
        idoperario = new ComboBox("Operario");
        idmultiplicacion = new ComboBox("Multiplicacion");
        idmedio = new ComboBox("Medio");
        observaciones = new TextField("Observaciones");
        Component[] fields = new Component[]{ fecha, contenedores, idvariedad, idoperario, idmultiplicacion,
                idmedio, observaciones};

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

    private void populateForm(Operaciones value) {
        this.operaciones = value;
        
        binder.readBean(this.operaciones);

    }
    
    private String FKContenedor(Operaciones op) {
        return op.getContenedores().getContenedor();
     }
 
}
