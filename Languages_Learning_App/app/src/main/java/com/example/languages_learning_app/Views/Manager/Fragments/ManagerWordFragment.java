package com.example.languages_learning_app.Views.Manager.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.Adapters.LessonAdapter;
import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DTO.Lesson;
import com.example.languages_learning_app.R;
import com.example.languages_learning_app.Views.Manager.ManagerVocabularyActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerWordFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Lesson> listLesson;
    LessonAdapter lessonAdapter;
    EditText edtName, edtDescription;
    Button btSetLesson, btClose;
    AlertDialog alertDialog;
    DatabaseReference mDatabase;
    FloatingActionButton btOpenDialog;

    private LessonAdapter.RecyclerViewClickListener listener;


    public ManagerWordFragment() {
        // Required empty public constructor
    }

    public static ManagerWordFragment newInstance() {
        ManagerWordFragment fragment = new ManagerWordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_manager_word, container, false);

        setOnClickListener();
        setRecyclerView(root);

        return root;
    }

    private void setOnClickListener() {
        listener = new LessonAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Lesson lesson = listLesson.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("lesson", lesson);

                Intent intent = new Intent(getActivity(), ManagerVocabularyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, int position) {

            }

            @Override
            public void onTouch(View v, int position) {

            }
        };
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
        mDatabase.child(Common.language.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLesson.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Lesson lesson = dataSnapshot.getValue(Lesson.class);
                    listLesson.add(lesson);
                }
                lessonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}