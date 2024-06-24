package com.example.githubapp.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubUsersResponce {
    @SerializedName("total_count")
    public int totalCount;
    @SerializedName("items")
    public List<GitHubUser> users;
}
