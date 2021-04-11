package com.example.librarydatabase.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarydatabase.R;
import com.example.librarydatabase.model.Books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class recycleviewadapter extends RecyclerView.Adapter<recycleviewadapter.Viewholder>  implements Filterable {

    private final List<Books> list;
    private List<Books>list_all;
    Itemclicked context;



    public interface Itemclicked
    {
        void ondelete(int id,int adapterpostion);
        void edititem(Books books,int adapterposition);
        void issue_btn(Books books,int adapterposition);
        void return_btn(Books books,int adapterposition);
    }

    public recycleviewadapter(Context context, List<Books>list)
   {
       this.context=(Itemclicked) context;
       this.list=list;
       this.list_all=new ArrayList<>(list);
   }

    @NonNull
    @Override
    public recycleviewadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleviewadapter.Viewholder holder, int position) {
    Books books=list.get(position);
    holder.itemView.setTag(list.get(position));
    holder.list_book.setText(books.getBook_name());
    holder.list_author.setText(books.getAuthor());
    holder.list_stock.setText(String.valueOf(books.getStock()));
    holder.list_date.setText(books.getDateitemadded());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView list_book;
        public TextView list_author;
        public TextView list_stock;
        public TextView list_date;
        public Button delete;
        public Button edit;
        public Button issue;
        public Button list_return;



        public Viewholder(@NonNull View itemView) {
            super(itemView);
            list_book=itemView.findViewById(R.id.list_books_name);
            list_author=itemView.findViewById(R.id.list_author_name);
            list_stock=itemView.findViewById(R.id.list_stock_name);
            list_return=itemView.findViewById(R.id.list_return);
            list_date=itemView.findViewById(R.id.list_date_name);
            edit=itemView.findViewById(R.id.edit2);
            delete=itemView.findViewById(R.id.delete);
            issue=itemView.findViewById(R.id.issue);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int position=getAdapterPosition();
                Books books=list.get(position);
                context.edititem(books,position);

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    int id=list.get(position).getId();
                    context.ondelete(id,position);
                }
            });

            list_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Books books=list.get(position);
                    context.return_btn(books,position);
                }
            });

            issue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Books books=list.get(position);

                    context.issue_btn(books,position);

                }
            });


        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Books>filteredlist=new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                filteredlist.addAll(list_all);
            }
            else {
                for (Books book3 : list_all) {
                    if (book3.getBook_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredlist.add(book3);
                    }
                }


            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        list.clear();
        list.addAll((Collection<? extends Books>) results.values);
        notifyDataSetChanged();
        }
    };
}
