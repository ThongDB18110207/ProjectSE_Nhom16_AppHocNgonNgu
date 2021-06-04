package com.example.languages_learning_app.Controllers;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.Adapters.UserAdapter;
import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.UserDAO;
import com.example.languages_learning_app.DTO.User;
import com.example.languages_learning_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminManagerFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    ArrayList<User> listUser;
    UserAdapter userAdapter;
    EditText edtFullName, edtEmail, edtPhone;
    Button btSetUser, btClose;
    AlertDialog alertDialog;
    DatabaseReference mDatabase;
    FloatingActionButton btOpenDialog;

    private UserAdapter.RecyclerViewClickListener listener;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_admin_manager, container, false);

        setOnClickListener();
        setRecyclerView(root);

        btOpenDialog = (FloatingActionButton) root.findViewById(R.id.btOpenUserDialog);
        btOpenDialog.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btOpenUserDialog:
                openDialog(Common.mode.create, -1);
                break;
            case R.id.btClose:
                alertDialog.cancel();
                break;
        }
    }

    private void openDialog(Common.mode mode, int position) {
        // Show alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_account, null);
        alertDialog = builder.create();
        alertDialog.setView(view);
        alertDialog.show();

        btClose = view.findViewById(R.id.btClose);
        btClose.setOnClickListener(this);

        btSetUser = view.findViewById(R.id.btSetUser);

        edtFullName = view.findViewById(R.id.edtFullName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);

        if (mode == Common.mode.update || mode == mode.read){
            User user  = listUser.get(position);
            edtFullName.setText(user.getFullName());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());

            if(mode == Common.mode.update){
                btSetUser.setText("Update");
                edtEmail.setEnabled(false);
            }
            if (mode == Common.mode.read){
                btSetUser.setVisibility(View.GONE);
            }
        }

        btSetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();

                if (fullName.isEmpty()){
                    edtFullName.setError("Enter Full Name");
                    return;
                }
                if (email.isEmpty()){
                    edtEmail.setError("Enter Email");
                    return;
                }
                if (phone.isEmpty()){
                    edtPhone.setError("Enter Phone Number");
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Please provide valid email!");
                    return;
                }

                if(mode == Common.mode.create){
                    for(int i=0;i<listUser.size();i++){
                        if(email.equals(listUser.get(i).getEmail())){
                            edtEmail.setError("Email is duplicated");
                            return;
                        }
                    }

                    User user = new User.UserBuilder(Common.RoleManager, false)
                            .setFullName(fullName)
                            .setPhone(phone)
                            .setEmail(email)
                            .build();

                    createUser(user);
                }
                if (mode == Common.mode.update){
                    User user = listUser.get(position);

                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setPhone(phone);

                    UserDAO.getInstance().setUserValue(user);
                }
                alertDialog.cancel();
            }
        });
    }

    private void createUser(User user) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(user.getEmail(), Common.defaltPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            user.setUserId(userId);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Add Manager successfully!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getContext(), "Failed to register! Try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        switch (item.getItemId()){
            case 0:
                openDialog(Common.mode.update, position);
                return true;
            case 1:
                User user = listUser.get(position);
                if(UserDAO.getInstance().deleteUser(user.getUserId())){
                    Toast.makeText(this.getContext(), "Delete Manager successfully !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getContext(), "Failed to delete Manager!", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void setOnClickListener() {
        listener = new UserAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                openDialog(Common.mode.read, position);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, int position) {
                menu.add(position,0,0,"Edit");
                menu.add(position,1,1,"Delete");
            }

            @Override
            public void onTouch(View v, int position) {
                if(position >= 0) {
                    User user = listUser.get(position);
                    UserDAO.getInstance().changeStatusUser(user.getUserId(), user.isActive());
                }
            }
        };
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvManager);

        listUser = new ArrayList<>();

        userAdapter = new UserAdapter(getContext(), listUser, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(userAdapter);

        // Get data from firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Query myTopPostsQuery = mDatabase.orderByChild("createDate");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listUser.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user.getRole().equals(Common.RoleManager)){
                        listUser.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}