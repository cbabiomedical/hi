package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private EditText edit;
    private Button add;
    private ListView listview;
    private Uri imageuri;

    private static final int IMAGE_REQUEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout=findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
//        listview = findViewById((R.id.listview));

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StartActivity.class));
            }
        });

       add.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
                openImage();


//               Log.d("ERROR MY","IN THE FUNCTION");
//               Log.d("ERROR ","-----------------d----------------------");
//               Log.d("ERROR MY","-----------------d--------------a--------");
//               Log.d("ERROR","-----------------d---------d-------------");
//               Log.d("ERROR","----------h-------d----------------------");
//               Log.d("ERROR","-----------------d------------------g----");
//               String txt_name = edit.getText().toString();
//               if (txt_name.isEmpty()){
//                   Toast.makeText(MainActivity.this,"No Name Entered",Toast.LENGTH_SHORT).show();
//                   Log.d("ERROR MY","eMPTY");
//                   Log.d("ERROR","-----------------d----------------------");
//                   Log.d("ERROR","-----------------d--------------a--------");
//                   Log.d("ERROR","-----------------d---------d-------------");
//                   Log.d("ERROR","----------h-------d----------------------");
//                   Log.d("ERROR","-----------------d------------------g----");
//               }else{
//                  FirebaseDatabase.getInstance().getReference().child("ProgramingKnowledge").push().child("Name").setValue(txt_name);
//
//
//
//                   Log.d("ERROR MY",txt_name);
//                   Log.d("ERROR","-----------------d----------------------");
//                   Log.d("ERROR","-----------------d--------------a--------");
//                   Log.d("ERROR","-----------------d---------d-------------");
//                   Log.d("ERROR","----------h-------d----------------------");
//                   Log.d("ERROR","-----------------d------------------g----");
//

//                  FirebaseDatabase.getInstance().getReference().child("ProgramingKnowledge").child("Android").setValue("dddddd");


               }

                              }

       );

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Map<String , Object> data = new HashMap<>();
        data.put("capital",false);

        db.collection("cities").document("JSR").set(data , SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Merge Successfull",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        DocumentReference ref = FirebaseFirestore.getInstance().collection("cities").document("JSR");
//        ref.update("capital",true);
//
//        DocumentReference docRef = FirebaseFirestore.getInstance().collection("cities").document("DC");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    if(doc.exists()){
//                        Log.d("Document",doc.getData().toString());
//                    }else{
//                        Log.d("Document" , "No data");
//                    }
//                }
//            }
//        });
        FirebaseFirestore.getInstance().collection("cities").whereEqualTo("country","china").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                for(QueryDocumentSnapshot doc : task.getResult()) {
                    Log.d("Document", doc.getId() + "=>" + doc.getData().toString());
                }
                }
            }
        });


//       ArrayList<String> list = new ArrayList<>();
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
//        listview.setAdapter(adapter);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Languages");
//        reference.addValueEventListener(new ValueEventListener() {
//
//
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1 :snapshot.getChildren()){
//                    list.add(snapshot1.getValue().toString());
//
//
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//       HashMap<String , Object> map = new HashMap<>();
//       map.put("Name" , "DE");
//       map.put("Email" , "de@gmail.com");
//
//       FirebaseDatabase.getInstance().getReference().child("ProgrammingKnowledge").child("MultipleValues").updateChildren(map);


    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK);
        imageuri = data.getData();
        uploadImage();
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentresolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentresolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageuri != null){
            StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis() + "."+getFileExtension(imageuri));

            fileRef.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url =uri.toString();

                            Log.d("DownloadUrl",url);
                            pd.dismiss();
                            Toast.makeText(MainActivity.this,"Image upload successful",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        }
    }
}