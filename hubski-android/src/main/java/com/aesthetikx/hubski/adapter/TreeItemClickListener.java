package com.aesthetikx.hubski.adapter;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;

public class TreeItemClickListener implements AdapterView.OnItemClickListener {

    private List<? extends BaseTreeListItem> items;
    private CommentListAdapter adapter;

    public TreeItemClickListener(List<? extends BaseTreeListItem> items, CommentListAdapter adapter) {
        this.items = items;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((TreeListItem)adapter.getItem(position)).toggleExpanded();
        adapter.notifyDataSetChanged();
    }

}
