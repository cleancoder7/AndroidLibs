package me.imstudio.core.ui.pager;

public abstract class Selectable {
    private transient boolean isSelected;

    public Selectable() {
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }
}