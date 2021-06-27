package com.lmo.fadergsn2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

public class AdapterSubTask extends BaseAdapter {
    private List<SubTask> taskList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterSubTask(Context context, List<SubTask> tasksList){
        this.taskList = tasksList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(this.taskList.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterSubTask.ItemHelper item;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.task_layout,null);
            item = new AdapterSubTask.ItemHelper();
            item.tlTitle = convertView.findViewById(R.id.fhtvTaskTitle);
            item.tlDesc = convertView.findViewById(R.id.fhtvTaskDesc);
            item.layout = convertView.findViewById(R.id.layout);
            item.mainTask = convertView.findViewById(R.id.mainTask);
            convertView.setTag(item);
        }else{
            item = (AdapterSubTask.ItemHelper) convertView.getTag();
        }
        SubTask task = taskList.get(position);
        item.tlTitle.setText(task.getTitle());
        item.tlDesc.setText(task.getDesc());
        if(position % 2 ==0){
            item.layout.setBackgroundColor(Color.rgb(230,230,230));
        }else{
            item.layout.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    private class ItemHelper{
        TextView tlTitle,tlDesc;
        LinearLayout layout,mainTask;
    }
}
