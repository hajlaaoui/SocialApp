package com.esprit.socialapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esprit.socialapp.dao.PostDao;
import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.Post;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText post_description;
    Button addP;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";


    public FavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        addP = view.findViewById(R.id.addP);
        post_description = view.findViewById(R.id.post_description);

        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase myDataBase = MyDataBase.getDataBase(getActivity().getApplicationContext());
                Post post = new Post();
                post.setContent(post_description.getText().toString());
                post.setLike(0);
                post.setUsername(sharedPreferences.getString(KEY_NAME, null));
                post.setDate("AAA");
                if (validateInput(post)){
                    PostDao postDao = myDataBase.postDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            postDao.addPost(post);
                            //runOnUiThread(new Runnable())
                        }
                    }).start();
                }
            }
        });
    }

    private Boolean validateInput(Post post){
        if(post.getContent().isEmpty()){
            return false;
        }
        return true;
    }
}