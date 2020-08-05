package com.softwareladers.gitRepo.ui.search;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareladers.gitRepo.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    ConstraintLayout searchLayout;
    SearchViewModel searchViewModel;
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;
    TextView btnSearch;
    EditText searchKeyword;
    String keyword;
    ArrayList searchArray = new ArrayList();
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        recyclerView = view.findViewById(R.id.rv_search_content);
        searchAdapter = new SearchAdapter(getActivity(),searchArray);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchAdapter);
        searchKeyword = view.findViewById(R.id.search_keyword);
        searchLayout= (ConstraintLayout) view.findViewById(R.id.main);

        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchLayout.getWindowToken(), 0);
                keyword = searchKeyword.getText().toString();
                searchViewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
                searchViewModel.getSearchMutableLiveData(keyword).observe(getActivity(), searchListUpdateObserver);
            }
        });
        return view;
    }
    Observer<ArrayList<SearchModel>> searchListUpdateObserver = new Observer<ArrayList<SearchModel>>() {
        @Override
        public void onChanged(ArrayList<SearchModel> userArrayList) {
            searchAdapter = new SearchAdapter(getActivity(),userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(searchAdapter);
        }
    };
}