package me.imstudio.core.ui.pager;

public abstract class XSelectable {

    private transient boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}