package com.example.sayhi.ui.activity;

import static com.example.sayhi.ui.ext.Utils.ShowAlert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sayhi.R;
import com.example.sayhi.databinding.ActivityChatBinding;
import com.example.sayhi.ui.ChatAdapter;
import com.example.sayhi.ui.User;
import com.example.sayhi.ui.model.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    Intent intent;

    DatabaseReference databaseReference,msgdatabaseReference;

    Toolbar toolbar;
    ActivityChatBinding binding;

    String remote_user_id;
    ChatAdapter chatAdapter;

    FirebaseUser firebaseUser;

    List <Chat> chatList;

    Chat chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        toolbar = findViewById(R.id.toolbar);   //toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        chatList=new ArrayList<>();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        intent=getIntent();//user receiver



            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);
            binding.chatRcv.setLayoutManager(linearLayoutManager);

            if(intent.hasExtra("user_id")){

                remote_user_id=intent.getStringExtra("user_id");
                Log.i("TAG",remote_user_id);
            }


        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        msgdatabaseReference= FirebaseDatabase.getInstance().getReference("chat");


       getRemoteUser(remote_user_id);
       readChat();






       binding.sendBtn.setOnClickListener(v->{

           String message=binding.messageEdt.getText().toString().trim();
          String currentUserId=firebaseUser.getUid();
          String messageId=databaseReference.push().getKey();


           chat=new Chat(currentUserId,remote_user_id,message,messageId);


          databaseReference.child(messageId).setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {

              if(task.isSuccessful()){
                  Toast.makeText(ChatActivity.this, "Message sent...!", Toast.LENGTH_SHORT).show();
              binding.messageEdt.setText("");

              }



              }
          });

       });



    }

    private void readChat() {
msgdatabaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        chatList.clear();  //don't reapet msg

        for(DataSnapshot dataSnapshot: snapshot.getChildren()){

             chat=dataSnapshot.getValue(Chat.class);

          if (chat != null){

                if(chat.getSenderId().equals(firebaseUser.getUid()) && chat.getReceiverId().equals(remote_user_id)
                        || chat.getSenderId().equals(remote_user_id) && chat.getReceiverId().equals(firebaseUser.getUid())) {

                    chatList.add(chat);

              }

            }



        }

        chatAdapter=new ChatAdapter(chatList,firebaseUser.getUid());
        binding.chatRcv.setAdapter(chatAdapter);


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }

    private void getRemoteUser(String user_id) {

databaseReference.child(user_id).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {


          User remoteUser=snapshot.getValue(User.class);

        // if(remoteUser.getProfileImage()!=null || !remoteUser.getProfileImage().isEmpty()){

    Glide.with(getApplicationContext()).load(remoteUser.getProfileImage())
            .placeholder(R.drawable.avatar_placeholder).into(binding.userImage);

//}



            binding.userName.setText(remoteUser.getUserName());
            binding.userEmail.setText(remoteUser.getUserEmail());




    }

    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }
}