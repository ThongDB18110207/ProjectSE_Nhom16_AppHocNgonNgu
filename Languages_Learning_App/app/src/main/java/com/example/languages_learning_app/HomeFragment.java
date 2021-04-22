package com.example.languages_learning_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView languageTitle;
        String language = "123";

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        languageTitle = (TextView) view.findViewById(R.id.tvLanguageTitle);

        Bundle extras = getActivity().getIntent().getExtras();

        if (extras !=null) {
            language = "Home Frament\n\n" + extras.getString("Language");
        }

        languageTitle.setText(language);

        return view;
    }
}
