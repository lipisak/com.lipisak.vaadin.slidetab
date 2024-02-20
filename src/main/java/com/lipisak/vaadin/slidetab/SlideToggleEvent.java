package com.lipisak.vaadin.slidetab;

import com.vaadin.flow.component.ComponentEvent;

/**
 * @author eriklumme
 * @since 12/10/2018
 */
public class SlideToggleEvent extends ComponentEvent<SlideTab> {

    private boolean expand;

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     */
    public SlideToggleEvent(SlideTab source, boolean fromClient, boolean expand) {
        super(source, fromClient);
        this.expand = expand;
    }

    @Override
    public SlideTab getSource() {
        return super.getSource();
    }

    public boolean isExpand() {
        return expand;
    }
}
