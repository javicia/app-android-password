package com.javier.passlive.Menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.javier.passlive.R;


public class Social_Networks extends Fragment {

    ImageView Iv_facebook, Iv_instagram, Iv_twitter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        Iv_facebook = view.findViewById(R.id.Iv_facebook);
        Iv_instagram = view.findViewById(R.id.Iv_instagram);
        Iv_twitter = view.findViewById(R.id.Iv_twitter);


        Iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(intent_Facebook);
            }
        });

        Iv_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Instagram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
                startActivity(intent_Instagram);
            }
        });
        Iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Twitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
                startActivity(intent_Twitter);
            }
        });


        return view;
    }
}