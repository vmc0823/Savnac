package com.silisurfers.savnac.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.R;
import com.silisurfers.savnac.database.entities.SavnacCourse;

import java.util.List;

public class CoursesActivityRecyclerAdapter extends RecyclerView.Adapter<CoursesActivityRecyclerAdapter.MyViewHolder> {
    private List<SavnacCourse> itemList;
    private OnCourseClickListener listener; // added by Tom (19 April 2025, 9:30 pm)
    Context context; // added by Tom (21 April 2025, 5:55 am)

    // added by Tom (19 April 2025, 9:30 pm)
    // first constructor
    public CoursesActivityRecyclerAdapter(List<SavnacCourse> itemList, OnCourseClickListener listener){
        this.itemList = itemList;
        this.listener = listener;
    }

//    public CoursesActivityRecyclerAdapter(List<SavnacCourse> itemList) {
//        this.itemList = itemList;
//    }

    // added by Tom (19 April 2025, 9:30 pm)
    public interface OnCourseClickListener{
        void onCourseClick(SavnacCourse course);
    }

    // [Serving as an internal ViewHolder class (instead of creating a separate file)]
    // =============================================================================================================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;

        // added by Tom (21 April 2025, 6:15 am)
        public Button courseButton; // will eventually hold a reference of CourseButton from "item_link.xml"

        public MyViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.course_link_text);

            // added by Tom (21 April 2025, 6:15 am)
            courseButton = itemView.findViewById(R.id.course_link_text); // reference of button from "item_link.xml"
        }
    }
    //===============================================================================================================================================

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SavnacCourse item = itemList.get(position);
        holder.textTitle.setText(item.getCourseName());

        // added by Tom (19 April 2025, 9:30 pm)
        // edit adjustment by Tom on (21 April 2025, 7:27 am)
        //      - changed "holder.itemView.setOnClickListener" to
        //      - "holder.courseButton.setOnClickListener"
        //      - this will allow add or removeItem to work when the button itself
        //        is clicked as opposed to clicking on the entire row itself.
        holder.courseButton.setOnClickListener(v -> {
            if (listener != null){
                listener.onCourseClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(SavnacCourse item) {
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    // added by Tom (19 April 2025, 9:30 pm)
    public void removeItem(SavnacCourse item){
        int index = itemList.indexOf(item);
        if (index >= 0){
            itemList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void updateData(List<SavnacCourse> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }
}