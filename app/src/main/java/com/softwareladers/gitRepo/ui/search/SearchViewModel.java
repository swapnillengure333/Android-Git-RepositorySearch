package com.softwareladers.gitRepo.ui.search;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.softwareladers.gitRepo.ui.Config;

import java.util.ArrayList;

public class SearchViewModel extends  AndroidViewModel {
    MutableLiveData<ArrayList<SearchModel>> searchData;
    ArrayList<SearchModel> searchArrayList;
    RequestQueue rqContent = Volley.newRequestQueue(getApplication().getApplicationContext());

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchData = new MutableLiveData<>();
         }
    public MutableLiveData<ArrayList<SearchModel>> getSearchMutableLiveData(String keyword) {
        searchArrayList = new ArrayList<>();
        getData(keyword);
        return searchData;
    }
    public void getData(String keyword){
        String search_url = Config.git_search_repo_url + keyword;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, search_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SearchModel searchModel = new SearchModel();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        searchModel.setRepo_name(jsonObject.getString("name"));
                        searchModel.setRepo_full_name(jsonObject.getString("full_name"));
                        searchModel.setRepo_image(jsonObject.getJSONObject("owner").getString("avatar_url"));
                        searchModel.setRepo_watch(jsonObject.getString("watchers"));

                         searchArrayList.add(searchModel);
                    }
                    searchData.setValue(searchArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("BlueDev Volley Error: ", error+"");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(25000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rqContent.add(jsonArrayRequest);
    }
}