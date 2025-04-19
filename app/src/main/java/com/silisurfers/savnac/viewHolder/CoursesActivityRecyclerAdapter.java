package com.silisurfers.savnac.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.R;
import com.silisurfers.savnac.database.entities.SavnacCourse;

import java.util.List;

public class CoursesActivityRecyclerAdapter extends RecyclerView.Adapter<CoursesActivityRecyclerAdapter.MyViewHolder> {
    private List<SavnacCourse> itemList;

    public CoursesActivityRecyclerAdapter(List<SavnacCourse> itemList) {
        this.itemList = itemList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.course_link_text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_link, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SavnacCourse item = itemList.get(position);
        holder.textTitle.setText(item.getCourseName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(SavnacCourse item) {
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }
}