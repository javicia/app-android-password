package com.javier.passlive.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.javier.passlive.R;


public class F_About extends Fragment {

    ImageView Iv_facebook, Iv_instagram, Iv_twitter;
    Button Txt_political_terms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        Iv_facebook = view.findViewById(R.id.Iv_facebook);
        Iv_instagram = view.findViewById(R.id.Iv_instagram);
        Iv_twitter = view.findViewById(R.id.Iv_twitter);
        Txt_political_terms = view.findViewById(R.id.Txt_political_terms);

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

        Txt_political_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Political = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/terminosypoliticasdeprivacidad/inicio"));
                startActivity(intent_Political);
            }
        });
        return view;
    }
}