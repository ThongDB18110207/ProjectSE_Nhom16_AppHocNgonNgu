package com.example.languages_learning_app.Controllers;

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
import androidx.fragment.app.Fragment;

import com.example.languages_learning_app.R;

public class HomeFragment extends Fragment {
    ImageView contry_flag;
    TextView languageTitle;
    Button btAudio, btTest, btPratice;
    String languageN, languageDN, languageIM;

    Fragment selectedFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setData(view);

        contry_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChooseLanguageActivity.class));
            }
        });

        btAudio = (Button) view.findViewById(R.id.btAudio);
        btAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_manager);
            }
        });

        btTest = (Button) view.findViewById(R.id.btTest);
        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_trainee);
            }
        });

        btPratice = (Button) view.findViewById(R.id.btPractice);
        btPratice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNav.setSelectedItemId(R.id.nav_practice);
            }
        });

        return view;
    }

    private void setData(View view) {
        languageTitle = (TextView) view.findViewById(R.id.tvLanguageTitle);
        contry_flag = (ImageView) view.findViewById(R.id.ivChangeLanguage);

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras !=null) {
            languageN = extras.getString("LanguageN");
            languageDN = extras.getString("LanguageDN");
            languageIM = extras.getString("LanguageIM");
        }

        languageTitle.setText(languageDN);
        int id = Integer.parseInt(languageIM);
        contry_flag.setImageResource(id);
    }
}
