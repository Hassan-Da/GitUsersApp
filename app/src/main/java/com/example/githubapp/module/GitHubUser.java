package com.example.githubapp.module;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    public String id;
    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;
    public int score;

}
