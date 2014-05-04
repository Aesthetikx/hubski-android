package com.aesthetikx.hubski.adapter;

import java.util.List;

public abstract class BaseTreeListItem implements TreeListItem {

    private boolean visible;
    private boolean expanded;
    private List<? extends BaseTreeListItem> children;
    private int depth;

    public BaseTreeListItem(List<? extends BaseTreeListItem> children, boolean visible, boolean expanded, int depth) {
        this.children = children;
        this.visible = visible;
        this.expanded = expanded;
        this.depth = depth;
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        for (BaseTreeListItem child: children) {
            child.parentToggled(expanded, isVisible());
        }
    }

    @Override
    public void parentToggled(boolean parentExpanded, boolean parentVisible) {
        setVisible( parentVisible && parentExpanded );
        for (BaseTreeListItem child: children) child.parentToggled(isExpanded(), isVisible());
    }

    @Override
    public void toggleExpanded() {
        setExpanded(!isExpanded());
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public int getChildCount() {
        int count = 0;
        for (TreeListItem child : children) {
            count += (1 + child.getChildCount());
        }
        return count;
    }

}
