package com.example.languages_learning_app.Views.Trainee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.languages_learning_app.Adapters.LessonAdapter;
import com.example.languages_learning_app.DTO.Lesson;
import com.example.languages_learning_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TraineeAudioFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Lesson> listLesson;
    LessonAdapter lessonAdapter;
    DatabaseReference mDatabase;

    private LessonAdapter.RecyclerViewClickListener listener;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_trainee_audio, container, false);

        setRecyclerView(root);

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvLesson);

        listLesson = new ArrayList<>();

        lessonAdapter = new LessonAdapter(getContext(), listLesson, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(lessonAdapter);

        // Get data from firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("Lessons");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLesson.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Lesson lesson = dataSnapshot.getValue(Lesson.class);

                    if(lesson.isStatus()){
                        listLesson.add(lesson);
                    }
                }
                lessonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}