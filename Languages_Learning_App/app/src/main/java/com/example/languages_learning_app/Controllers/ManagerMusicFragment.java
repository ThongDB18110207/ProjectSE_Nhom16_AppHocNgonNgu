package com.example.languages_learning_app.Controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.Adapters.ItemTouchHelperListener;
import com.example.languages_learning_app.Adapters.RecyclerViewItemTouchHelper;
import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.MusicDAO;
import com.example.languages_learning_app.DTO.Music;
import com.example.languages_learning_app.Adapters.MusicAdapter;
import com.example.languages_learning_app.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManagerMusicFragment extends Fragment implements ItemTouchHelperListener {
    Button btnSong, btnCover, btnAddURL, btnAdd;
    AlertDialog alertDialog;
    TextView videoName, videoURL;
    RadioButton radioButtonMusic, radioButtonCover;
    DatabaseReference databaseCover, databaseSong;
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    ArrayList<Music> arrayList = new ArrayList<>();
    RelativeLayout musicRootView;

    int idVideo = 0;


    public ManagerMusicFragment() {
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manager_music, container, false);

        btnSong = rootView.findViewById(R.id.buttonSong);
        btnCover = rootView.findViewById(R.id.buttonVideo);
        btnAddURL = rootView.findViewById(R.id.btnAddURL);
        recyclerView = rootView.findViewById(R.id.listVideo);

        musicRootView = rootView.findViewById(R.id.music_rootView);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerViewItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        btnSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();

                String path = "Language_Lesson/" + Common.language.getId() +"/music/song";
                databaseSong = FirebaseDatabase.getInstance().getReference(path);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                arrayList = new ArrayList<>();
                musicAdapter = new MusicAdapter(getActivity(),arrayList);
                recyclerView.setAdapter(musicAdapter);

                databaseSong.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            Music music = dataSnapshot.getValue(Music.class);
                            arrayList.add(music);


                        }
                        musicAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList.clear();

                String path = "Language_Lesson/" + Common.language.getId() +"/music/cover";
                databaseCover = FirebaseDatabase.getInstance().getReference(path);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                arrayList = new ArrayList<>();
                musicAdapter = new MusicAdapter(getActivity(),arrayList);
                recyclerView.setAdapter(musicAdapter);

                databaseCover.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            Music music = dataSnapshot.getValue(Music.class);
                            arrayList.add(music);


                        }
                        musicAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnAddURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnAddURL:
                        openDialog(Common.mode.create, -1);
                        break;
                }

            }
            private void openDialog(Common.mode mode, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.layout_dialog_add, null);
                alertDialog = builder.create();
                alertDialog.setView(view);
                alertDialog.show();

                btnAdd = view.findViewById(R.id.btnAdd);
                radioButtonCover = view.findViewById(R.id.radiobtnCover);
                radioButtonMusic = view.findViewById(R.id.radiobtnMusic);
                videoName = view.findViewById(R.id.editTextVideoName);
                videoURL = view.findViewById(R.id.editTextVideoUrl);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if (URLUtil.isValidUrl(videoURL.toString())) {
                                Toast.makeText(getActivity(), "Invalid URL", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (videoName.equals("")) {
                                Toast.makeText(getActivity(), "Please fill in al the field!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (radioButtonMusic.isChecked()) {
                                int idLanguage = Common.language.getId();

                                Music musicAdd = new Music(idLanguage, idVideo, videoName.getText().toString(), videoURL.getText().toString(), "song");

                                MusicDAO.getInstance().setMusicValueSong(musicAdd);

                                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();

                            }

                            if (radioButtonCover.isChecked()) {
                                int idLanguage = Common.language.getId();

                                Music musicAdd = new Music(idLanguage, idVideo, videoName.getText().toString(), videoURL.getText().toString(), "cover");

                                MusicDAO.getInstance().setMusicValue(musicAdd);

                                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();

                            }
                    }
                });
            }
        });
        return rootView;
    }

    @Override
    public void onSwipeListener(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof MusicAdapter.MyViewHolder){
            String nameVideoDelete = arrayList.get(viewHolder.getAdapterPosition()).getVideoname();

            Music musicDelete = arrayList.get(viewHolder.getAdapterPosition());
            int indexDelete = viewHolder.getAdapterPosition();

            musicAdapter.removeItem(indexDelete);

            Snackbar snackbar = Snackbar.make(musicRootView, nameVideoDelete + " removed!", Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    musicAdapter.undoItem(musicDelete, indexDelete);
                }
            });
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
    }
}