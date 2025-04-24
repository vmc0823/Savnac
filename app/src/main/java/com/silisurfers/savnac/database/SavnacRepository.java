package com.silisurfers.savnac.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.silisurfers.savnac.database.entities.SavnacAssignment;
import com.silisurfers.savnac.database.entities.SavnacAssignmentWithGrade;
import com.silisurfers.savnac.database.entities.SavnacCourse;
import com.silisurfers.savnac.database.entities.SavnacEnrollment;
import com.silisurfers.savnac.database.entities.SavnacGradeEntry;
import com.silisurfers.savnac.database.entities.SavnacUser;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

//@author: vw

//all DB ops go through here
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

    //USERS**********
    //who is currently signed in?
    public LiveData<SavnacUser> getCurrentUser() {
        return db.savnacUserDao().getFirstUserSync();
    }

    //all users (for debugging)
    public LiveData<List<SavnacUser>> getAllUsers() {
        return db.savnacUserDao().getAll();
    }

    //sign up or add a user
    public void insertUser(SavnacUser user) {
        writeExecutor.execute(() -> db.savnacUserDao().insert(user));
    }

    public CompletableFuture<SavnacUser> insertAndReturnUser(SavnacUser user){
        return CompletableFuture.supplyAsync(()-> {
            long id = db.savnacUserDao().insert(user);

            return db.savnacUserDao().getByIdSync((int) id);

        }, writeExecutor);
    }

    public void updateUser(SavnacUser user) {
        writeExecutor.execute(() -> db.savnacUserDao().update(user));
    }

    public LiveData<SavnacUser> getUserByUsername(String username) {
        return db.savnacUserDao().getByUsername(username);
    }

    public void deleteUser(SavnacUser user) {
        writeExecutor.execute(() -> db.savnacUserDao().delete(user));
    }

    //COURSES*******
    //all courses in system
    public LiveData<List<SavnacCourse>> getAllCourses() {
        return db.savnacCourseDao().getAllCourses();
    }

    //courses taught by certain teacher
    public LiveData<List<SavnacCourse>> getCourseByTeacher(int teacherId) {
        return db.savnacCourseDao().getCoursesByTeacher(teacherId);
    }

    public void updateCourse(SavnacCourse course) {
        writeExecutor.execute(() -> db.savnacCourseDao().update(course));
    }

    //create a new course
    public long insertCourse(SavnacCourse course) {
        long[] courseId = new long[1];
        writeExecutor.execute(() -> {
            courseId[0] = db.savnacCourseDao().insert(course);
        });
        return courseId[0];
    }

    public CompletableFuture<SavnacCourse> insertAndReturnCourse(SavnacCourse course){
        return CompletableFuture.supplyAsync(()-> {
            long id = db.savnacCourseDao().insert(course);
            return db.savnacCourseDao().getCourseByIdSync((int) id);
        }, writeExecutor);
    }


    //delete a course
    public void deleteCourse(int courseId) {
        writeExecutor.execute(() -> db.savnacCourseDao().deleteById(courseId));
    }

    //ENROLLMENTS*********

    //raw enrollments for example options
    public LiveData<List<SavnacEnrollment>> getAllEnrollments() {
        return db.savnacEnrollmentDao().getAllEnrollmentOptions();
    }

    //create new enrollment (link student-course)
    public void insertEnrollment(SavnacEnrollment enrollment) {
        writeExecutor.execute(() -> db.savnacEnrollmentDao().insert(enrollment));
    }

    //single enrollment by PK
    public LiveData<SavnacEnrollment> getEnrollmentById(int id) {
        return db.savnacEnrollmentDao().getById(id);
    }

    //courseId column

    //ASSIGNMENTS************
    //all assignments in system
    public LiveData<List<SavnacAssignment>> getAllAssignments() {
        return db.savnacAssignmentDao().getAllAssignments();
    }

    //create new assignments
    public void insertAssignment(SavnacAssignment assignment) {
        writeExecutor.execute(() -> db.savnacAssignmentDao().insert(assignment));
    }

    public CompletableFuture<SavnacAssignment> insertAndReturnAssignment(SavnacAssignment assignment){
        return CompletableFuture.supplyAsync(()-> {
            long id = db.savnacAssignmentDao().insert(assignment);
            return db.savnacAssignmentDao().getAssignmentByIdSync( (int) id);
        }, writeExecutor);
    }

    public LiveData<List<SavnacAssignmentWithGrade>> getAllGradesWithAssignments() {
        return db.savnacAssignmentWithGradeDao().getAllGradesWithAssignments();
    } //CALL THIS IN GRADESACTIVITY

    //GRADES*********
    //all grades entries, most recent 1st
    public LiveData<List<SavnacGradeEntry>> getAllGradeEntries() {
        return db.savnacGradeEntryDao().getAllGradeEntries();
    }

    public LiveData<List<SavnacGradeEntry>> getGradeForStudents(int studentId) {
        return db.savnacGradeEntryDao().getForStudent(studentId);
    }

    public LiveData<List<SavnacGradeEntry>> getGradeForAssignment(int assignmentId) {
        return db.savnacGradeEntryDao().getForAssignment(assignmentId);
    }

    public LiveData<List<SavnacAssignmentWithGrade>> getUserGradesByCourseId(int userId, int courseId) {
        return db.savnacGradeEntryDao().getUserGradesByCourseId(userId, courseId);
    }

    public void insertGradeEntry(SavnacGradeEntry entry) {
        writeExecutor.execute(() -> db.savnacGradeEntryDao().insert(entry));
    }

    public CompletableFuture<SavnacGradeEntry> insertAndReturnGradeEntry(SavnacGradeEntry gradeEntry){
        return CompletableFuture.supplyAsync(()-> {
            long id = db.savnacGradeEntryDao().insert(gradeEntry);
            return db.savnacGradeEntryDao().getGradeEntryByIdSync( (int) id);
        }, writeExecutor);
    }

    public void updateGradeEntry(SavnacGradeEntry entry) {
        writeExecutor.execute(() -> db.savnacGradeEntryDao().delete(entry));
    }
}
