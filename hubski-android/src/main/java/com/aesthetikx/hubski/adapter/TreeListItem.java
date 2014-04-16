package com.aesthetikx.hubski.adapter;

public interface TreeListItem {

    public void setExpanded(boolean visible);
    public void toggleExpanded();
    public boolean isExpanded();

    public void setVisible(boolean visible);
    public boolean isVisible();

    public void parentToggled(boolean parentExpanded, boolean parentVisible);

    public int getDepth();

}
