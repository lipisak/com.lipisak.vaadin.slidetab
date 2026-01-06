package com.lipisak.vaadin.slidetab;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * @author erik@vaadin.com
 * @since 10.5.2021
 */
@Push
@StyleSheet(Lumo.STYLESHEET) // Use Aura.STYLESHEET to use Aura instead
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("demo-styles.css") // Your custom styles

public class DemoApplication implements AppShellConfigurator {
}
