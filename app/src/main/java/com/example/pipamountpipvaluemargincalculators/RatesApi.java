package com.example.pipamountpipvaluemargincalculators;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RatesApi {
    @GET("/latest")
    Call<Rates> getRates(@Query("base") String base);
}
