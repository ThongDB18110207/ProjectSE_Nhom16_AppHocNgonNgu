package com.example.languages_learning_app.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.languages_learning_app.R;

public class MoreFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        CardView cvManageProfile = (CardView) view.findViewById(R.id.cvManageProfile);
        CardView cvChangePassword = (CardView) view.findViewById(R.id.cvChangePassword);

        cvManageProfile.setOnClickListener((View v) -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        cvChangePassword.setOnClickListener((View v) -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        return view;
    }
}
