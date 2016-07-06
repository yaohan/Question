package com.ssdut411.app.questionanswer.activity.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ssdut411.app.questionanswer.R;
import com.ssdut411.app.questionanswer.utils.L;
import com.ssdut411.app.questionanswer.widget.EmptyLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by yao_han on 2016/4/8.
 */
public abstract class BaseExpandListActivity extends BaseActivity {
    protected ExpandableListView lvList;
    protected EmptyLayout emptyLayout;
    @Override
    protected abstract int initMenu();

    @Override
    protected int initContentView() {
        return R.layout.activity_error_question;
    }

    @Override
    protected void initViews() {
        lvList = getExpandableListView(R.id.lv_list);
        lvList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                initChildClick(parent, v, groupPosition, childPosition, id);
                return false;
            }
        });
        emptyLayout = new EmptyLayout(context,lvList);
        emptyLayout.showLoading();
    }

    protected abstract void initChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id);

    @Override
    protected abstract void loadData();

    @Override
    protected void showView() {
        setTitle(initTitle());
        setCanBack();
    }

    protected abstract String initTitle();

    public abstract class MyAdapter<T> extends BaseExpandableListAdapter {
        private List<String> groupList;
        private Map<String,List<T>> childMap;

        private LayoutInflater layoutInflater;
        private int groupLayoutId;
        private int childLayoutId;

        public MyAdapter(Context context, List<String> groupList, int groupLayoutId, Map<String, List<T>> childMap, int childLayoutId) {
            layoutInflater = LayoutInflater.from(context);
            this.groupList = groupList;
            this.groupLayoutId = groupLayoutId;
            this.childMap = childMap;
            this.childLayoutId = childLayoutId;
        }

        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childMap.get(groupList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childMap.get(groupList.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.getViewHolder(parent, convertView, layoutInflater, groupLayoutId);
            convertGroup(viewHolder, groupList.get(groupPosition));
            return viewHolder.getConvertView();
        }
        public abstract void convertGroup(ViewHolder viewHolder,String s);
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.getViewHolder(parent,convertView,layoutInflater,childLayoutId);
            convertChild(viewHolder, childMap.get(groupList.get(groupPosition)).get(childPosition));
            return viewHolder.getConvertView();
        }

        protected abstract void convertChild(ViewHolder viewHolder, T t);

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
