package com.example.githubapp.service;

import com.example.githubapp.module.GitHubUsersResponce;
import com.example.githubapp.module.GitRepo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubUsersService {
    @GET("search/users")
    Call<GitHubUsersResponce> listGithubUsers(@Query("q") String query);

    @GET("users/{user}/repos")
    Call<List<GitRepo>> UserRepos(@Path("user") String user);
}
