package com.example.librarydatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.librarydatabase.ui.Slideradapter;

public class intro extends AppCompatActivity {

    private ViewPager viewPager;
    private Slideradapter slideradapter;
    private Button next_button;
    private Button prev_button;
    private int curr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager=(ViewPager)findViewById(R.id.slidepager);
        slideradapter=new Slideradapter(intro.this);
        next_button=findViewById(R.id.next_btn);
        prev_button=findViewById(R.id.prev_btn);
        viewPager.setAdapter(slideradapter);
        viewPager.addOnPageChangeListener(listener);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curr<=1)
                {
                    viewPager.setCurrentItem(curr+1);
                }
                else if(curr==2)
                {
                    Intent intent=new Intent(intro.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curr>0)
                {
                    viewPager.setCurrentItem(curr-1);
                }
            }
        });
    }

    ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        curr=position;
        if(position==0)
        {
        next_button.setEnabled(true);
        next_button.setVisibility(View.VISIBLE);
        next_button.setText("NEXT");
            prev_button.setEnabled(false);
            prev_button.setVisibility(View.INVISIBLE);
            prev_button.setText("PREVIOUS");

        }
        else if(position==1)
        {
            next_button.setEnabled(true);
            next_button.setVisibility(View.VISIBLE);
            next_button.setText("NEXT");
            prev_button.setEnabled(true);
            prev_button.setVisibility(View.VISIBLE);
            prev_button.setText("PREVIOUS");

        }
        else
        {
            next_button.setEnabled(true);
            next_button.setVisibility(View.VISIBLE);
            next_button.setText("LET'S GET STARTED");
            prev_button.setEnabled(true);
            prev_button.setVisibility(View.VISIBLE);
            prev_button.setText("PREVIOUS");

        }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}