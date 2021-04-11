package com.example.librarydatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarydatabase.data.booksdatabase;
import com.example.librarydatabase.data.studentsdatabase;
import com.example.librarydatabase.model.Books;
import com.example.librarydatabase.model.Students;
import com.example.librarydatabase.ui.recycleviewadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements recycleviewadapter.Itemclicked{

    private RecyclerView recyclerView;
    private recycleviewadapter adapter;
    private booksdatabase database;
    private List<Books> list;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button savebtn;
    private EditText pop_book;
    private EditText pop_author;
    private EditText pop_stock;
    private FloatingActionButton fab;
    private studentsdatabase studentsdatabase;
    private List<Students>studentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=new booksdatabase(this);
        studentsdatabase=new studentsdatabase(MainActivity.this);
        studentsList=new ArrayList<>();
        studentsList=studentsdatabase.getallstudents();
        for (Students students2: studentsList)
        {
            Log.d("GODHELP", "onCreate: "+students2.getStudent_id());
            Log.d("HELP", "onCreate: "+students2.getDebt());
            Log.d("check", "onCreate: "+students2.getBook_name());
        }



        recyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        list=database.getallbooks();
        adapter=new recycleviewadapter(this,list);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
                createpop();
            }
        });
    }

    private void createpop() {
        builder=new AlertDialog.Builder(this);
         View view=getLayoutInflater().inflate(R.layout.popup,null);
        savebtn=view.findViewById(R.id.savebutton);
        pop_book=view.findViewById(R.id.pop_book);
        pop_author=view.findViewById(R.id.pop_author);
        pop_stock=view.findViewById(R.id.pop_stock);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pop_book.getText().toString().trim().isEmpty() && !pop_author.getText().toString().trim().isEmpty() &&
                        !pop_stock.getText().toString().trim().isEmpty())
                {
                    savestate(v);
                }
                else
                {
                    Snackbar.make(v,"PLEASE ENTER ALL THE FIELDS!!!!",Snackbar.LENGTH_SHORT).show();
                }
            }

        });
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();

    }

    private void savestate(View v) {
    Books books=new Books();
    String bookname=pop_book.getText().toString().trim();
    String authorname=pop_author.getText().toString().trim();
    int stock= Integer.parseInt(pop_stock.getText().toString().trim());
    books.setBook_name(bookname);
    books.setAuthor(authorname);
    books.setStock(stock);
    database.onbooksadded(books);
    Snackbar.make(v,"ITEM ADDED SUCCESSFULLY",Snackbar.LENGTH_SHORT).show();
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            alertDialog.dismiss();
            startActivity(new Intent(MainActivity.this,MainActivity.class));
            finish();
        }
    },1200);
    }


    @Override
    public void ondelete(final int id, final int adapterpostion) {
    builder=new AlertDialog.Builder(this);
    View view=getLayoutInflater().inflate(R.layout.confirmation,null);
    Button yes_button=view.findViewById(R.id.conf_yes);
    Button no_button=view.findViewById(R.id.conf_no);
    builder.setView(view);
    alertDialog=builder.create();
    alertDialog.show();
    yes_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        database.deletebook(id);
        list.remove(adapterpostion);
        adapter.notifyItemRemoved(adapterpostion);
            Snackbar.make(v,"ITEM SUCCESSFULLY DELETED",Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            },1200);

        }
    });

    no_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
        }
    });
    }

    @Override
    public void edititem(final Books books, final int adapterposition) {
    builder=new AlertDialog.Builder(this);
    View view=getLayoutInflater().inflate(R.layout.popup,null);
//    Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();

        TextView title=view.findViewById(R.id.title);
        savebtn=view.findViewById(R.id.savebutton);
        pop_book=view.findViewById(R.id.pop_book);
        pop_author=view.findViewById(R.id.pop_author);
        pop_stock=view.findViewById(R.id.pop_stock);
        savebtn.setText("UPDATE");
        title.setText("EDIT BOOKS DETAILS");
        pop_book.setText(books.getBook_name());
        pop_author.setText(books.getAuthor());
        pop_stock.setText(String.valueOf(books.getStock()));
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pop_book.getText().toString().trim().isEmpty() && !pop_author.getText().toString().trim().isEmpty() &&
                        !pop_stock.getText().toString().trim().isEmpty())
                {
                    books.setBook_name(pop_book.getText().toString().trim());
                    books.setAuthor(pop_author.getText().toString().trim());
                    books.setStock(Integer.parseInt(pop_stock.getText().toString().trim()));
                    database.update(books);
                    adapter.notifyItemChanged(adapterposition,books);
                    Snackbar.make(v,"ITEM SUCCESSFULLY UPDATED",Snackbar.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
//                            Intent intent=new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    },1200);

                }
                else
                {
                    alertDialog.dismiss();
                    Snackbar.make(v,"PLEASE ENTER ALL THE FIELDS!!!!",Snackbar.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public void issue_btn(final Books books, final int adapterposition) {
        builder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.issue_pop,null);
         Button issue_btn;
         final EditText issue_id;
         final EditText issue_name;

         issue_btn=view.findViewById(R.id.issue_btn);
         issue_id=view.findViewById(R.id.issue_id);
         issue_name=view.findViewById(R.id.issue_name);

        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();

        issue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"wohhh",Toast.LENGTH_SHORT).show();
                if(!issue_name.getText().toString().trim().isEmpty() && !issue_id.getText().toString().trim().isEmpty())
                {
                    int checkid=Integer.parseInt(issue_id.getText().toString().trim());
                    for(Students students1:studentsList) {
                        if (students1.getStudent_id() == checkid) {

                            if (students1.getDebt() == 1) {
                                Snackbar.make(view, "ERROR!!!!", Snackbar.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    }

                        Students students3=new Students();
                        students3.setStudent_name(issue_name.getText().toString().trim());
                        students3.setDebt(1);
                        students3.setStudent_id(Integer.parseInt(issue_id.getText().toString().trim()));
                        students3.setBook_name(books.getBook_name());
                        studentsdatabase.onstudentsadded(students3);
                        checkid=books.getStock();
                        checkid--;
                        if(checkid<0)
                        {
                            checkid=0;
                        }
                        books.setStock(checkid);
                        database.update(books);
                        adapter.notifyItemChanged(adapterposition,books);
                        Snackbar.make(view,"TRANSACTION SUCCESSFUL",Snackbar.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },1200);

                    }


                else
                {
//                    Toast.makeText(MainActivity.this,"brothers",Toast.LENGTH_SHORT).show();
                    Snackbar.make(view,"PLEASE ENTER ALL THE FIELDS!!!!",Snackbar.LENGTH_SHORT).show();




                }

            }
        });


    }

    @Override
    public void return_btn(final Books books, final int adapterposition) {
        builder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.return_pop,null);
        Button return_btn1;
        final EditText return_id;


        return_btn1=view.findViewById(R.id.return_btn1);
        return_id=view.findViewById(R.id.return_id);


        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();
        return_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!return_id.getText().toString().isEmpty())
                {
                    int checkid=Integer.parseInt(return_id.getText().toString().trim());
                    int found=0;
                    for(final Students students1:studentsList) {
                        if (students1.getStudent_id() == checkid) {

                            if (students1.getBook_name().equals(books.getBook_name())) {
                                found=1;
                                checkid=books.getStock();
                                checkid++;
                                books.setStock(checkid);

                                Snackbar.make(view,"TRANSACTION SUCCESSFUL",Snackbar.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.dismiss();
                                        Intent intent=new Intent(MainActivity.this,fineacitvity.class);
                                        intent.putExtra("booksname",books.getBook_name());
                                        intent.putExtra("studentsname",students1.getStudent_name());
                                        intent.putExtra("studentid",students1.getStudent_id());
                                        intent.putExtra("dateissued",students1.getDate_issued());
                                        database.update(books);
                                        adapter.notifyItemChanged(adapterposition,books);
                                        studentsdatabase.deletestudent(students1.getId());
                                        startActivity(intent);

                                    }
                                },1200);




                            }
                            else
                            {
                                Log.d("JUST CHECKING", "onClick: "+books.getBook_name());
                                Snackbar.make(view, "ERROR!!!!", Snackbar.LENGTH_SHORT).show();
                                return;
                            }

                        }

                    }

                    if(found==0)
                    {
                        Log.d("BRO", "onClick: ");
                        Snackbar.make(view, "ERROR!!!!", Snackbar.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Snackbar.make(view,"PLEASE ENTER ALL THE FIELDS!!!!",Snackbar.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}