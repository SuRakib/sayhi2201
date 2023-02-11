package com.example.sayhi.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sayhi.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Context context;

    private List <User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

   holder.userName.setText(userList.get(position).getUserName());
        holder.userMail.setText(userList.get(position).getUserName());


//        String nameStr=userList.get(position).getName();
//
//        String phnNmbr=userList.get(position).getMobile();
//
//        String age=userList.get(position).getAge();
//
//        Integer id=userList.get(position).getId();   //
//
//
//
//        holder.userName.setText(nameStr);
//
//        holder.userPhone.setText(phnNmbr);
//
//        holder.userAge.setText(age);
//
//        holder.userID.setText(id.toString());




    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}