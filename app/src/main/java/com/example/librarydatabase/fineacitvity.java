package com.example.librarydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class fineacitvity extends AppCompatActivity {

    private TextView booksfind_name;
    private TextView finestudents_name;
    private TextView finestudents_id;
    private TextView fine_dateissue;
    private TextView finedate_return;
    private TextView fine_money;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fineacitvity);


          booksfind_name=findViewById(R.id.books_findname);
          finestudents_name=findViewById(R.id.finestudents_name);
          finestudents_id=findViewById(R.id.finestudents_id);
          fine_dateissue=findViewById(R.id.fine_dateissue);
          finedate_return=findViewById(R.id.finedate_return);
          fine_money=findViewById(R.id.fine_money);

          Bundle bundle=getIntent().getExtras();
          String name="";
          String students_name = null;
          int id = 0;
          String dateissue = null;
          if(bundle!=null)
          {
              name=bundle.getString("booksname","");
              students_name=bundle.getString("studentsname","");
              id=bundle.getInt("studentid",0);
              dateissue=bundle.getString("dateissued","");
          }

          booksfind_name.setText(name);
          finestudents_name.setText(students_name);
            finestudents_id.setText(String.valueOf(id));
            fine_dateissue.setText(dateissue);
            String datereturned=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            finedate_return.setText(datereturned);
        SimpleDateFormat dates;
        dates = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null,date2 = null;
        try {
            date1 = dates.parse(dateissue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = dates.parse(datereturned);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        long fine=0;
        if(differenceDates>=2)
        {
            differenceDates=differenceDates-2;
            fine=differenceDates*5;
        }
        String dayDifference = Long.toString(fine);
        fine_money.setText(dayDifference);

    }
}