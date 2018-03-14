package com.example.jiun.sookpam.server;

import android.util.Log;
import com.example.jiun.sookpam.model.DualModel;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebFilter {
    public static ArrayList<DualModel> webFilter(String charText) {
        SearchableService service = ApiUtils.Companion.getSearchableService();
        final ArrayList<DualModel> itemList = new ArrayList<DualModel>();
        charText.replace(" ", "-");

        service.getItems(charText).enqueue(new Callback<List<RecordResponse>>() {
            @Override
            public void onResponse(Call<List<RecordResponse>> call, Response<List<RecordResponse>> response) {
                if (!response.isSuccessful())
                    return;
                final List<RecordResponse> records = response.body();
                itemList.addAll(records);
            }

            @Override
            public void onFailure(Call<List<RecordResponse>> call, Throwable t) {
                Log.v("onFailure:", "onFailure");
            }
        });

        return itemList;
    }
}
