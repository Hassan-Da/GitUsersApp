package com.example.githubapp;

import static com.example.githubapp.MainActivity.USER_LOGIN_PARAM;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapp.module.GitHubUser;
import com.example.githubapp.module.GitHubUsersResponce;
import com.example.githubapp.module.GitRepo;
import com.example.githubapp.service.GitHubUsersService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_layout);
        Intent intent = getIntent();
        String login = intent.getStringExtra(USER_LOGIN_PARAM);
        final List<String> data = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                data
        );
        TextView textViewUserLogin = findViewById(R.id.textViewUserLogin);
        textViewUserLogin.setText(login);
        ListView listViewRepos = findViewById(R.id.listRepos);
        listViewRepos.setAdapter(arrayAdapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubUsersService service = retrofit.create(GitHubUsersService.class);
        Call<List<GitRepo>> reposCall = service.UserRepos(login);
        reposCall.enqueue(new Callback<List<GitRepo>>() {
            @Override
            public void onResponse(Call<List<GitRepo>> call, Response<List<GitRepo>> response) {
                if (!response.isSuccessful()) {
                    Log.i("indo", String.valueOf(response.code()));
                    return;
                }
                List<GitRepo> gitHubUserRepos = response.body();
                for (GitRepo repo : gitHubUserRepos) {
                    String content = "";
                    content += repo.id + "\n";
                    content += repo.name + "\n";
                    content += repo.language + "\n";
                    content += repo.size + "\n";
                    data.add(content);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GitRepo>> call, Throwable t) {
                Log.e("error", "error");
            }
        });

        setTitle("Repositories");
    }
}
