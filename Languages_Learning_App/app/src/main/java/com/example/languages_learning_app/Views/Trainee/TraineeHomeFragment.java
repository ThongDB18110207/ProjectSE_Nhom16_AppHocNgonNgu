package com.example.languages_learning_app.Views.Trainee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.R;
import com.example.languages_learning_app.Views.ChooseLanguageActivity;

public class TraineeHomeFragment extends Fragment {
    ImageView contry_flag;
    TextView languageTitle;

    Button btAudio, btTest, btPractice;
    CardView cvFlashcard, cvSongs;
    String languageN, languageDN, languageIM;

    Fragment selectedFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainee_home, container, false);

        setData(view);

        contry_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChooseLanguageActivity.class));
            }
        });

        cvFlashcard = (CardView) view.findViewById(R.id.cvFlashcard);
        cvFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TraineeMainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_flashcard);
            }
        });

        cvSongs = (CardView) view.findViewById(R.id.cvSongs);
        cvSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TraineeSongActivity.class));
            }
        });

        btTest = (Button) view.findViewById(R.id.btTest);
        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TraineeMainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_test);
            }
        });

        btPractice = (Button) view.findViewById(R.id.btPractice);
        btPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TraineeMainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_practice);
            }
        });

        return view;
    }

    private void setData(View view) {
        languageTitle = (TextView) view.findViewById(R.id.tvLanguageTitle);
        contry_flag = (ImageView) view.findViewById(R.id.ivChangeLanguage);

        languageTitle.setText(Common.language.getDisplayName());
        contry_flag.setImageResource(Common.language.getImage());
    }
}