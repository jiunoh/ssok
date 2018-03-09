package com.example.jiun.sookpam.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchableService {
    @GET("{query}")
    Call<List<RecordResponse>> getItems(@Path("query") String query);
}
