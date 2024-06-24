package com.example.githubapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapp.module.GitHubUser;
import com.example.githubapp.module.GitHubUsersResponce;
import com.example.githubapp.module.UserListViewModel;
import com.example.githubapp.service.GitHubUsersService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String USER_LOGIN_PARAM = "user.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        Button btnSearch = findViewById(R.id.btnSearch);
        ListView listViewResults = findViewById(R.id.list);
        final List<GitHubUser> data = new ArrayList<>();

        UserListViewModel userListViewModel = new UserListViewModel(this, R.layout.user_list_view_layout, data);
        listViewResults.setAdapter(userListViewModel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubUsersService service = retrofit.create(GitHubUsersService.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                String query = editTextSearch.getText().toString();
                Call<GitHubUsersResponce> search = service.listGithubUsers(query);
                search.enqueue(new Callback<GitHubUsersResponce>() {
                    @Override
                    public void onResponse(Call<GitHubUsersResponce> call, Response<GitHubUsersResponce> response) {
                        Log.i("indo", String.valueOf(call.request().url()));
                        if (!response.isSuccessful()) {
                            Log.i("indo", String.valueOf(response.code()));
                            return;
                        }
                        GitHubUsersResponce gitHubUsersResponce = response.body();
                        for (GitHubUser user : gitHubUsersResponce.users) {
                            data.add(user);
                        }
                        userListViewModel.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GitHubUsersResponce> call, Throwable t) {
                        Log.e("error", "error");
                    }
                });

            }
        });
        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String login = data.get(position).login;
                Intent intent = new Intent(getApplicationContext(), RepositoryActivity.class);
                intent.putExtra(USER_LOGIN_PARAM, login);
                startActivity(intent);
            }
        });
    }

}