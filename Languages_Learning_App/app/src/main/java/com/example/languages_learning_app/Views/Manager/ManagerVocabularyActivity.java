package com.example.languages_learning_app.Views.Manager;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.languages_learning_app.Adapters.Manager.VocabularyAdapter;
import com.example.languages_learning_app.Common.Common;
import com.example.languages_learning_app.DAO.VocabularyDAO;
import com.example.languages_learning_app.DTO.Vocabulary;
import com.example.languages_learning_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManagerVocabularyActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private ArrayList<Vocabulary> vocabularies;
    private VocabularyAdapter adapter;
    private VocabularyAdapter.RecyclerViewClickListener listener;

    private FloatingActionButton fabAddVocab;
    private EditText edWord, edMeaning;
    private ImageView imgVocab;

    private StorageReference mStorage;
    private DatabaseReference mDataBase;
    private Uri mImageUri;
    private StorageTask mUploadTask;

    private String lessonId, lessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_vocabulary);

        // Get data from intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            lessonId = bundle.getString("lessonId");
            lessonName = bundle.getString("lessonName");
        }

        mStorage = FirebaseStorage.getInstance().getReference("Vocabularies");
        mDataBase = FirebaseDatabase.getInstance().getReference("Vocabularies/" + Common.language.getId());

        setToolbar();
        setOnClickListener();
        setRecyclerView();

        fabAddVocab = findViewById(R.id.fabAdd);
        fabAddVocab.setOnClickListener((View v) -> {
            openDialog();
        });
    }

    // Toolbar on the top of screen
    private void setToolbar() {
        // Finish activity when clicking on back item
        ImageView backIcon = findViewById(R.id.left_icon);
        backIcon.setOnClickListener((View v) -> {
            this.finish();
        });

        // Set name for activity
        TextView txtToolbarName = findViewById(R.id.activity_name);
        txtToolbarName.setText(lessonName);
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_vocabulary);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        edWord = dialog.findViewById(R.id.edWord);
        edMeaning = dialog.findViewById(R.id.edMeaning);

        imgVocab = dialog.findViewById(R.id.imgVocab);
        imgVocab.setOnClickListener((View v) -> {
            openFileChooser();
        });

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnClose);

        btnCancel.setOnClickListener((View v) -> {
            dialog.dismiss();
        });

        btnSave.setOnClickListener((View v) -> {
            saveNewVocab();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imgVocab);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void saveNewVocab() {
        if(mImageUri != null) {
            StorageReference fileReference = mStorage.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String vocabId = mDataBase.push().getKey();
                                    String vocabWord = edWord.getText().toString().trim();
                                    String vocabMeaning = edMeaning.getText().toString().trim();

                                    if(vocabWord.equals("")) {
                                        edWord.setError("Từ vựng không được để trống!");
                                        return;
                                    }

                                    if(vocabMeaning.equals("")) {
                                        edMeaning.setError("Nghĩa không được để trống!");
                                    }

                                    Log.e("Lesson Id", lessonId);
                                    Vocabulary vocab = new Vocabulary.VocabularyBuilder(vocabId, vocabWord, vocabMeaning)
                                            .setLessonId(lessonId)
                                            .setImageUrl(uri.toString())
                                            .build();


                                    mDataBase.child(vocabId).setValue(vocab);

                                    Toast.makeText(ManagerVocabularyActivity.this, "Thêm từ vựng thành công!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ManagerVocabularyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private void setOnClickListener() {
        listener = new VocabularyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                //openDialog(Common.mode.read, position);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, int position) {
                menu.add(position,0,0,"Edit");
                menu.add(position,1,1,"Delete");
            }

            @Override
            public void onTouch(View v, int position) {
                if(position >= 0) {
                    Vocabulary vocabulary = vocabularies.get(position);
                    VocabularyDAO.getInstance().changeStatus(vocabulary);
                }
            }
        };
    }

    private void setRecyclerView(){
        recyclerView = findViewById(R.id.rvListVocabularies);

        vocabularies = new ArrayList<>();

        adapter = new VocabularyAdapter(this, vocabularies, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adapter);

        // Get data from firebase
        mDataBase = FirebaseDatabase.getInstance().getReference("Vocabularies").child(Common.language.getId());
        Query query = mDataBase.orderByChild("lessonId").equalTo(lessonId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vocabularies.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Vocabulary vocabulary = dataSnapshot.getValue(Vocabulary.class);
                    vocabularies.add(vocabulary);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}