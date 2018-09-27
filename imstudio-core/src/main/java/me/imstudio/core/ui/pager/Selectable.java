package me.imstudio.core.ui.pager;

public abstract class Selectable {

    private transient boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}