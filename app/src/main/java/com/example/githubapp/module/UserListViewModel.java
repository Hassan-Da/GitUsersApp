package com.example.githubapp.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubapp.R;

import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListViewModel extends ArrayAdapter<GitHubUser> {
    private List<GitHubUser> userList;
    private int resource;

    public UserListViewModel(@NonNull Context context, int resource, @NonNull List<GitHubUser> data) {
        super(context, resource, data);
        this.resource = resource;
        this.userList = data;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        CircleImageView imageView = listViewItem.findViewById(R.id.imageViewAvatar);
        TextView editTextLogin = listViewItem.findViewById(R.id.textViewLogin);
        TextView editTextScore = listViewItem.findViewById(R.id.textViewScore);
        editTextLogin.setText(userList.get(position).login);
        editTextScore.setText(String.valueOf(userList.get(position).score));
        try {
            URL url=new URL(userList.get(position).avatarUrl);
            Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listViewItem;
    }

}
