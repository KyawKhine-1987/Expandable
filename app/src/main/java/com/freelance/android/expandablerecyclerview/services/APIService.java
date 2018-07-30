package com.freelance.android.expandablerecyclerview.services;

import com.freelance.android.expandablerecyclerview.model.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("demos/marvel/")
    Call<List<Hero>> getMarvelHero();
}
