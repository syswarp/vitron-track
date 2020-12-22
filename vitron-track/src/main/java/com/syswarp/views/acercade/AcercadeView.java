package com.syswarp.views.acercade;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.Version;

@Route(value = "about", layout = MainView.class)
@PageTitle("Acerca de:")
@RouteAlias(value = "", layout = MainView.class)


public class AcercadeView extends Div {

    public AcercadeView() {
        setId("acercade-view");
        //add(new Text("Vaadin Track Version 1.0"));
        
        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(" Vitron Tracking Version 1.0 - Esta aplicacion esta desarrollada con Vaadin version "
                + Version.getFullVersion() + "."));
        setSizeFull();
      //  setJustifyContentMode(JustifyContentMode.CENTER);
      //  setAlignItems(Alignment.CENTER);
        
    }

}
