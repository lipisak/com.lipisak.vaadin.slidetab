package com.lipisak.vaadin.slidetab;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;
import java.util.TimerTask;

/**
 * A component for showing a tab that when clicked expands a panel
 */
@Tag("slide-tab")
@JsModule("./src/slide-tab.js")
public class SlideTab extends LitTemplate implements HasComponents, HasSize, HasStyle {

    @Id("tab")
    private Div tabComponent;
    @Id("content")
    private Div contentComponent;

    private Component expandComponent;
    private Component collapseComponent;

    private final SlideMode slideMode;
    private final ScheduleStrategy scheduleStrategy;

    private boolean expanded;
    private boolean autoCollapsing;
    private boolean toggleEnabled;

    private int pixelSize;
    private int animationDuration;
    private int zIndex;

    public SlideTab(SlideTabBuilder builder) {
        add(builder.content);

        tabComponent.setHeight(builder.tabSize + "px");
        slideMode = builder.mode;
        addClassName(builder.mode.toString().toLowerCase());

        setAnimationDuration(builder.animationDuration);
        setFixedContentSize(builder.pixel);
        setZIndex(builder.zIndex);
        setCaption(builder.caption);
        setTabPosition(builder.tabPosition);
        setClosingOnOutsideClick(builder.autoCollapseSlider);
        setTabVisible(builder.tabVisible);
        setToggleEnabled(true);

        if (builder.scheduleStrategy != null) {
            scheduleStrategy = builder.scheduleStrategy;
        } else {
            scheduleStrategy = new DefaultScheduleStrategy();
        }

        if (builder.listeners != null) {
            builder.listeners.forEach(this::addToggleListener);
        }

        if (builder.styles != null) {
            for (String style : builder.styles) {
                addClassName(style);
            }
        }
    }

    /**
     * Expands the SlideTab panel
     */
    public void expand() {
        expand(false);
    }

    /**
     * Expands the SlideTab panel
     *
     * @param fromClient    For the toggle event, true if expansion was triggered by the client
     */
    private void expand(boolean fromClient) {
        if (!expanded && toggleEnabled) {
            expanded = true;
            doExpand();
            fireEvent(new SlideToggleEvent(this, fromClient, true));
        }
    }

    private void doExpand() {
        getElement().callJsFunction("expand", pixelSize, slideMode.isVertical());
    }

    /**
     * Collapses the SlideTab panel
     */
    public void collapse() {
        collapse(false);
    }

    /**
     * Collapses the SlideTab panel
     *
     * @param fromClient    For the toggle event, true if collapse was triggered by the client
     */
    private void collapse(boolean fromClient) {
        if(expanded && toggleEnabled) {
            expanded = false;
            getElement().callJsFunction("collapse", slideMode.isVertical());
            fireEvent(new SlideToggleEvent(this, fromClient, false));
        }
    }

    /**
     * Called by the client when clicking on the tab
     */
    @ClientCallable
    public void toggle() {
        if (isExpanded()) {
            collapse(true);
        } else {
            expand(true);
        }
    }

    /**
     * Called by the client when clicking outside the panel
     */
    @ClientCallable
    public void onOutsideClicked() {
        if (autoCollapsing && expanded) {
            collapse(true);
        }
    }

    /**
     * Sets the caption of the tab
     */
    public void setCaption(final String caption) {
        getElement().setProperty("caption", caption);
    }

    /**
     * Returns the caption of the tab
     */
    public String getCaption() {
        return getElement().getProperty("caption", null);
    }

    /**
     * Sets the component that should be shown next to the caption in the tab when the component is collapsed.
     */
    public void setExpandComponent(Component component) {
        if (expandComponent != null) {
            remove(expandComponent);
        }
        expandComponent = component;
        expandComponent.getElement().setAttribute("slot", "expand");
        add(expandComponent);
    }

    /**
     * Returns the component that is shown next to the caption in the tab when the component is collapsed,
     * or null if one has not been explicitly set.
     */
    public Component getExpandComponent() {
        return expandComponent;
    }

    /**
     * Sets the component that should be shown next to the caption in the tab when the component is expanded.
     */
    public void setCollapseComponent(Component component) {
        if (collapseComponent != null) {
            remove(collapseComponent);
        }
        collapseComponent = component;
        collapseComponent.getElement().setAttribute("slot", "collapse");
        add(collapseComponent);
    }

    /**
     * Returns the component that is shown next to the caption in the tab when the component is expanded,
     * or null if one has not been explicitly set.
     */
    public Component getCollapseComponent() {
        return collapseComponent;
    }

    /**
     * Controls the position of the tab-panel
     *
     * @param tabPosition by default MIDDLE
     */
    public void setTabPosition(final SlideTabPosition tabPosition) {
        Arrays.stream(SlideTabPosition.values())
                .forEach(value -> setClassName(value.name().toLowerCase(), value == tabPosition));
    }

    /**
     * Sets if the panel should close when clicking outside it
     */
    public void setClosingOnOutsideClick(boolean autoCollapsing) {
        this.autoCollapsing = autoCollapsing;
        setElementClosingOnOutsideClick();
    }

    private void setElementClosingOnOutsideClick() {
        getElement().callJsFunction("setClosingOnOutsideClick", autoCollapsing);
    }

    /**
     * Returns if the panel should close when clicking outside it
     */
    public boolean isClosingOnOutsideClick() {
        return autoCollapsing;
    }

    /**
     * Returns the animation duration when expanding or collapsing the panel
     *
     * @return duration in milliseconds
     */
    public int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * set the animation duration
     * by default 500ms
     *
     * @param animationDuration in milliseconds
     */
    public void setAnimationDuration(final int animationDuration) {
        if (this.animationDuration != animationDuration) {
            this.animationDuration = animationDuration;
            contentComponent.getStyle().set("transition", String.format("height %dms, width %dms", animationDuration, animationDuration));
        }
    }

    /**
     * Sets a fixed size for the content in pixels
     */
    public void setFixedContentSize(final int pixelHeight) {
        this.pixelSize = pixelHeight;
    }

    /**
     * Returns the fixed size of the content in pixels, may be 0
     */
    public int getFixedContentSize() {
        return pixelSize;
    }

    /**
     * Defines the z-index of the panel, default 1
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        getStyle().set("z-index", String.valueOf(zIndex));
    }

    /**
     * Returns the z-index of the panel
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * Sets the visibility of the tab.
     *
     * If the tab is not visible, it can not be used to open or close the panel.
     */
    public void setTabVisible(boolean visible) {
        // Setting setVisible only toggles the 'hidden' attribute, which for a normal div does nothing
        tabComponent.getStyle().set("display", visible ? "flex" : "none");
    }

    /**
     * Returns true if the tab is visible
     */
    public boolean isTabVisible() {
        return tabComponent.isVisible();
    }

    /**
     * Expands or collapses the panel
     *
     * @param expanded      true to expand
     * @param animated      should be animated or not
     */
    public void setExpanded(final boolean expanded, final boolean animated) {
        if (this.expanded != expanded) {
            setAnimationDuration(animated ? animationDuration : 0);

            if (expanded) {
                expand();
            } else {
                collapse();
            }
        }
    }

    /**
     * Returns true if expanded or is expanding
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * schedule a state change of the slider
     * a recall within the schedule will cancel the previous one
     *
     * @param value       true means expand
     * @param animated    should be animated or not
     * @param delayMillis millis in future the task will happen
     */
    public void scheduleExpand(final boolean value, final boolean animated, final int delayMillis) {
        scheduleStrategy.schedule(new TabTask(() -> setExpanded(value, animated)), delayMillis);
    }

    /**
     * schedule a change from expand to collapse vice versa in future.
     * a recall within the schedule will cancel the previous one
     *
     * @param delayMillis millis in future the task will happen
     */
    public void scheduleToggle(final int delayMillis) {
        scheduleStrategy.schedule(new TabTask(this::toggle), delayMillis);
    }

    /**
     * schedule a collapse in future. will trigger a timer that will collapse the slider
     * a recall within the schedule will cancel the previous one
     *
     * @param delayMillis millis in future the task will happen
     */
    public void scheduleCollapse(final int delayMillis) {
        scheduleStrategy.schedule(new TabTask(this::collapse), delayMillis);
    }

    /**
     * schedule an expand in future. will trigger a timer that will expand the slider
     * a recall within the schedule will cancel the previous one
     *
     * @param delayMillis millis in future the task will happen
     */
    public void scheduleExpand(final int delayMillis) {
        scheduleStrategy.schedule(new TabTask(this::expand), delayMillis);
    }

    /**
     * Adds a listener that will be notified when the panel expands or collapses
     */
    public Registration addToggleListener(ComponentEventListener<SlideToggleEvent> listener) {
        return this.addListener(SlideToggleEvent.class, listener);
    }

    /**
     *  If set, the panel can not be expanded or collapsed
     */
    public void setToggleEnabled(boolean enabled) {
        this.toggleEnabled = enabled;
    }

    /**
     * Returns whether or not the panel can be expanded/collapsed
     */
    public boolean isToggleEnabled() {
        return toggleEnabled;
    }

    /**
     * A utility class for wrapping a command in a TimerTask and running it in the UI
     */
    public class TabTask extends TimerTask {

        private final Command command;

        private TabTask(Command command) {
            this.command = command;
        }

        @Override
        public void run() {
            getUI().ifPresent(ui -> ui.access(command));
        }
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        // Ensures the component in the browser is in sync
        if (expanded) {
            doExpand();
        }
        setElementClosingOnOutsideClick();
    }

}