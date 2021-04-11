package com.example.librarydatabase.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.librarydatabase.R;

public class Slideradapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public Slideradapter(Context context)
    {
        this.context=context;
    }

    String desc[]={"WELCOME TO LIBRARY DATABASE MANAGEMENT APP","YOU CAN ADD, SEARCH AND DELETE","YOU CAN EDIT, ISSUE AND RETURN BOOKS"};

    @Override
    public int getCount() {
        return desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(View)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,null);
        ImageView img1=view.findViewById(R.id.img1);
        ImageView img2=view.findViewById(R.id.img2);
        ImageView img3=view.findViewById(R.id.img3);
        ImageView img4=view.findViewById(R.id.img4);
        TextView txt=view.findViewById(R.id.txt);

        if(position==0)
        {
            img1.setVisibility(View.VISIBLE);
            img1.setImageResource(R.drawable.books);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
        }
        else if(position==1)
        {
            img1.setVisibility(View.INVISIBLE);
            img2.setImageResource(R.drawable.addbooks);
            img3.setImageResource(R.drawable.searchbooks);
            img4.setImageResource(R.drawable.deletebooks);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
        }
        else if(position==2)
        {
            img3.setImageResource(R.mipmap.returnbook);
            img4.setImageResource(R.drawable.issuebooks);
            img2.setImageResource(R.drawable.edit12);
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
        }

        container.addView(view);
        txt.setText(desc[position]);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
