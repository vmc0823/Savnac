package com.silisurfers.savnac;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silisurfers.savnac.database.SavnacRepository;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.ArrayList;
import java.util.List;

//@author: vw

public class CreateCourseActivity extends AppCompatActivity {

    private SavnacRepository repo;
    private SavnacUser currentUser;

    private RecyclerView recyclerViewStudents;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private final List<String> admitted = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing repo and curreent user
        repo = SavnacRepository.getInstance(getApplication());
        currentUser = repo.getCurrentUserSync().getValue();

        if(currentUser == null || !"teacher".equals(currentUser.getRole())) { //block non-teachers
            finish();
            return;
        }

        //inflate layout
        setContentView(R.layout.activity_create_course);

        //bind views
        EditText editTextCourseName = findViewById(R.id.editTextCourseName);
        EditText editTextStudentName = findViewById(R.id.editTextStudentName);
        Button buttonAddStudent = findViewById(R.id.buttonAddStudent);
        Button buttonConfirmCourse = findViewById(R.id.buttonConfirmCourse);
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);

        //layout manager
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));

        //recyclerView
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public int getItemCount() {
                return admitted.size();
            }

            @NonNull @Override
            public RecyclerView.ViewHolder onCreateViewHolder(
                    @NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1,
                                parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(
                    @NonNull RecyclerView.ViewHolder holder,
                    int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(admitted.get(position));
                holder.itemView.setOnLongClickListener(view -> {
                    admitted.remove(holder.getAdapterPosition());
                    this.notifyItemRemoved(holder.getAdapterPosition());
                    return true;
                });
            }
        };
        recyclerViewStudents.setAdapter(adapter);

       //add student button
        buttonAddStudent.setOnClickListener(view -> {
            String name = editTextStudentName.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                admitted.add(name);
                adapter.notifyItemInserted(admitted.size() - 1);
                editTextStudentName.getText().clear();
            }
        });

        //press enter to confirm course button
        buttonConfirmCourse.setOnClickListener(view -> {
            String course = editTextCourseName.getText().toString().trim();
            if (TextUtils.isEmpty(course) || admitted.isEmpty()) {
            //TODO: show error to user
            return;
        }
        for (String studentName : admitted) {
            SavnacUser user = new SavnacUser(
                    studentName,
                    "",
                    "student"
            );
            repo.insertUser(user);
        }
        finish();
        });
    }
}
