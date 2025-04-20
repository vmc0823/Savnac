package com.silisurfers.savnac.viewHolder;

//@author: vw

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//list of admitted student names
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private final List<String> students;
    public StudentListAdapter(List<String> students) {
        this.students = students;
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView studentName;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(android.R.id.text1);
        }
    }

    @NonNull @Override
    public StudentViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            @NonNull StudentViewHolder holder, int position) {
        holder.studentName.setText(students.get(position));
        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getAdapterPosition();
            students.remove(pos);
            notifyItemRemoved(pos);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    //this is a helper to add one name and refresh
    public void addStudent(String name) {
        students.add(name);
        notifyItemInserted(students.size() - 1);
    }

}
