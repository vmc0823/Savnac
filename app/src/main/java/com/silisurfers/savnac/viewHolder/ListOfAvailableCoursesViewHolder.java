package com.silisurfers.savnac.viewHolder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import com.silisurfers.savnac.R;

/**
 * Author: Wootark (Tom) Kim
 * Date: 21 April 2025
 * Resource used: https://www.youtube.com/watch?v=TAEbP_ccjsk&t=3s
 *
 * Note: This class file no longer necessary. ViewHolder already internally added within the "CoursesActivityRecyclerAdapter."
 *       Not deleting this class for archive/history purpose.
 */
public class ListOfAvailableCoursesViewHolder  extends RecyclerView.ViewHolder {

    // reference(s) are in regards to the "list_of_course_recycler_view_layout.xml" found within the layout folder
    Button courseButton; // will eventually hold a reference of CourseButton from "list_of_course_recycler_view_layout.xml"

    public ListOfAvailableCoursesViewHolder(@NonNull View itemView) {
        super(itemView);
        courseButton = itemView.findViewById(R.id.CourseButton); // reference of CourseButton from "list_of_course_recycler_view_layout.xml"
    }
}
