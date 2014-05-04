package com.aesthetikx.hubski.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface TreeListItem {

    public void setExpanded(boolean visible);
    public void toggleExpanded();
    public boolean isExpanded();

    public void setVisible(boolean visible);
    public boolean isVisible();

    public void parentToggled(boolean parentExpanded, boolean parentVisible);

    public int getDepth();

    public View getExpandedView(LayoutInflater inflater, ViewGroup parent);

    public View getCollapsedView(LayoutInflater inflater, ViewGroup parent);

    public int getChildCount();
}
