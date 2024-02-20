package com.lipisak.vaadin.slidetab;

/**
 * @author eriklumme
 * @since 12/10/2018
 */
public enum SlideMode {
    /**
     * slides from top to bottom
     */
    TOP(true),
    /**
     * slides from right to left
     */
    RIGHT(false),
    /**
     * slides from bottom to top
     */
    BOTTOM(true),
    /**
     * slides from left to right
     */
    LEFT(false);

    private boolean vertical;

    SlideMode(final boolean vertical) {
        this.vertical = vertical;
    }

    /**
     * layout is vertical
     *
     * @return is vertical
     */
    public boolean isVertical() {
        return this.vertical;
    }
}
