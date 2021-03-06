package com.esprit.socialapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.esprit.socialapp.database.MyDataBase;

import com.esprit.socialapp.entities.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView user_name;
    TextView post_description;
    TextView likes;
    FloatingActionButton add_post;
    ImageButton imgbtn;
    RecyclerView listPosts;
    List<Post> dataList = new ArrayList<>();
    MyDataBase userDatabase;
    LinearLayoutManager linearLayoutManager;
    MainAdapter mainAdapter;
    Button comment_button;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home,container,true);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        /*post_user_name = rootView.findViewById(R.id.post_user_name);
        add_post = rootView.findViewById(R.id.add_post);
        imgbtn = rootView.findViewById(R.id.like_button);
        String login = sharedPreferences.getString(KEY_NAME,null);
        post_user_name.setText(login);*/


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post_description = view.findViewById(R.id.post_description);
        likes = view.findViewById(R.id.likes);
        user_name = view.findViewById(R.id.user_name);
        comment_button = view.findViewById(R.id.comment_button);



        listPosts = view.findViewById(R.id.listPosts);
        // recyclerView = findViewById(R.id.recycler_view);
        userDatabase = userDatabase.getDataBase(getActivity().getApplicationContext());//getUserDatabase(getApplicationContext());
        dataList = userDatabase.postDao().getAllPosts();
       // linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listPosts.setLayoutManager(layoutManager);
        mainAdapter = new MainAdapter(getActivity(),dataList);
        listPosts.setAdapter(mainAdapter);

    }

}