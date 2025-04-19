package com.silisurfers.savnac.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavnacRepository {
    private final SavnacDatabase db;
    private static SavnacRepository instance;
    private final ExecutorService writeExecutor = Executors.newFixedThreadPool(2);

    private SavnacRepository(Context context) {
        db = SavnacDatabase.getInstance(context);
    }

    public static synchronized SavnacRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SavnacRepository(context.getApplicationContext());
        }
        return instance;
    }

    public SavnacUser getCurrentUserSync() {
        return db.savnacUserDao().getFirstUserSync();
    }

    public LiveData<List<SavnacCourse>> getAllCourses() {
        return db.savnacCourseDao().getAllCourses();
    }

    public void insertUser(SavnacUser user) {
        writeExecutor.execute(() -> db.savnacUserDao().insert(user));
    }

    // TODO: add more methods: insertCourse, getAssignmentsForCourse, insertAssignment, etc.
}
