package com.lipisak.vaadin.slidetab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author eriklumme
 * @since 12/10/2018
 */
public class SlideTabBuilder {

    protected Component content;

    protected SlideMode mode = SlideMode.TOP;

    protected boolean expanded = false;

    protected boolean flowInContent = false;

    protected boolean tabVisible = true;

    protected int tabSize = 40;

    protected List<ComponentEventListener<SlideToggleEvent>> listeners = null;

    protected String caption = null;

    protected SlideTabPosition tabPosition = SlideTabPosition.BEGINNING;

    protected int animationDuration = 500;

    protected List<String> styles = null;

    protected int pixel = -1;

    protected boolean autoCollapseSlider = false;

    protected int zIndex = 1;

    protected ScheduleStrategy scheduleStrategy;

    /**
     * creates an builder instance that can be configured fluently
     *
     * @param content that is wrapped by the SliderPanel<br>
     *                typically it's a Vertical or HorizontalLayout
     */
    public SlideTabBuilder(final Component content) {
        this.content = content;
    }

    /**
     * creates an builder instance that can be configured fluently
     *
     * @param content that is wrapped by the SliderPanel<br>
     *                typically it's a Vertical or HorizontalLayout
     * @param caption of the slider navigation element
     */
    public SlideTabBuilder(final Component content, final String caption) {
        this.content = content;
        this.caption = caption;
    }

    /**
     * Which type of display you want to have
     *
     * @param mode default <b>TOP</b>
     * @return builder
     */
    public SlideTabBuilder mode(final SlideMode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Should the slider be expanded on intial paint<br>
     *
     * @param expanded default <b>false</b>
     * @return builder
     */
    public SlideTabBuilder expanded(final boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    /**
     * Should the navigator flow within the content of the other layouts below
     *
     * @param flowInContent default <b>false</b>
     * @return builder
     */
    public SlideTabBuilder flowInContent(final boolean flowInContent) {
        this.flowInContent = flowInContent;
        return this;
    }

    /**
     * allows to change the short width/height of the tab-caption<br>
     * you need to change also your css when you change from default value<br>
     * important <b>need to get setted before first attach!</b>
     *
     * @param tabSize default <b>40</b>
     * @return builder
     */
    public SlideTabBuilder tabSize(final int tabSize) {
        this.tabSize = tabSize;
        return this;
    }

    /**
     * add the listener to the list of Listeners
     *
     * @param listener instance of {@link ComponentEventListener<SlideToggleEvent>}
     */
    public SlideTabBuilder listener(ComponentEventListener<SlideToggleEvent> listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList<>();
        }
        this.listeners.add(listener);
        return this;
    }

    /**
     * set caption
     *
     * @param caption of the slider navigation element
     * @return builder
     */
    public SlideTabBuilder caption(final String caption) {
        this.caption = caption;
        return this;
    }

    /**
     * Which type of display you want to have<br>
     * This controls the position of the navigation element within the {@link SlideTab} total area
     *
     * @param tabPosition default <b>BEGINNING</b>
     * @return builder
     */
    public SlideTabBuilder tabPosition(final SlideTabPosition tabPosition) {
        this.tabPosition = tabPosition;
        return this;
    }

    /**
     * Sets the visibility of the tab used for opening or closing the panel.
     *
     * @param tabVisible    True (default) if the tab should be visible
     * @return              This builder
     */
    public SlideTabBuilder tabVisible(boolean tabVisible) {
        this.tabVisible = tabVisible;
        return this;
    }

    /**
     * How long a collapse/expand should take in milliseconds
     *
     * @param animationDuration defaul <b>500</b>
     * @return builder
     */
    public SlideTabBuilder animationDuration(final int animationDuration) {
        this.animationDuration = animationDuration;
        return this;
    }

    /**
     * add a style to the sliderPanel
     *
     * @param style style that should get added
     * @return builder
     */
    public SlideTabBuilder style(final String... style) {
        if (this.styles == null) {
            this.styles = new ArrayList<>();
        }
        if (style != null) {
            styles.addAll(Arrays.asList(style));
        }
        return this;
    }

    /**
     * by default the {@link SlideTab} calculates it's content width/height (depending on it's mode)<br>
     * in some cases it's useful to programmatically set this value
     *
     * @param pixel width/height (depending on it's mode)
     * @return builder
     */
    public SlideTabBuilder fixedContentSize(final int pixel) {
        this.pixel = pixel;
        return this;
    }

    /**
     * by default the {@link SlideTab} stays open when use clicks outside<br>
     * when you enable autoCollapse the slider closes in mode of expand when user clicks somewhere else
     *
     * @param autoCollapseSlider enable auto collapse in expand state
     * @return builder
     */
    public SlideTabBuilder autoCollapseSlider(final boolean autoCollapseSlider) {
        this.autoCollapseSlider = autoCollapseSlider;
        return this;
    }

    /**
     * z-Index of navigator, content and wrapper<br>
     * you can specify for multiple sliders which lays above another
     *
     * @param zIndex default <b>9990</b>
     * @return
     */
    public SlideTabBuilder zIndex(int zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    /**
     * Can be overridden to gain more control over how collapse/expand commands are scheduled,
     * for example what threads are used.
     */
    public SlideTabBuilder scheduleStrategy(ScheduleStrategy scheduleStrategy) {
        this.scheduleStrategy = scheduleStrategy;
        return this;
    }

    /**
     * generates the SliderPanel
     *
     * @return instance of configured {@link SlideTab}
     */
    public SlideTab build() {
        return new SlideTab(this);
    }
}