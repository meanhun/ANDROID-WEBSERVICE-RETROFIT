package com.share4happy.service;

import com.share4happy.model.ModelCommom;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroService {
    @GET("data.json")
    Call<ModelCommom> getAll();
}
