package com.syswarp.views.acercade;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.syswarp.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "about", layout = MainView.class)
@PageTitle("Acerca de:")
@RouteAlias(value = "", layout = MainView.class)
public class AcercadeView extends Div {

    public AcercadeView() {
        setId("acercade-view");
        add(new Text("Vaadin Track Version 1.0"));
    }

}
