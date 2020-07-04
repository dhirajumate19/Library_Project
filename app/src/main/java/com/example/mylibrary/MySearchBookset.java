package com.example.mylibrary;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MySearchBookset extends FirestoreRecyclerAdapter<Book,MySearchBookset.Book> {
    private String key;





    public MySearchBookset(FirestoreRecyclerOptions<com.example.mylibrary.Book> options) {
        super(options);
    }


    @NonNull
    @Override
    public Book onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_view,parent,false);

        return new Book(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull Book holder, int i, @NonNull com.example.mylibrary.Book book2) {
        holder.bookId.setText("ID : "+String.valueOf(book2.getId()));
        Log.i("task","set ids"+holder.bookId);

        holder.bookType.setText("Category : "+book2.getType());

        holder.bookAvailable.setText("Available : "+String.valueOf(book2.getAvailable()));

        holder.bookName.setText("Title : "+book2.getTitle());

        holder.bookTotal.setText("Total : "+String.valueOf(book2.getTotal()));
    }

    public class Book extends  RecyclerView.ViewHolder {
        TextView bookName,bookId,bookType,bookAvailable,bookTotal;
        public Book(View itemView) {
            super(itemView);
            bookId= itemView.findViewById(R.id.bookId);
            bookAvailable=itemView.findViewById(R.id.bookAvailable);
            bookName=itemView.findViewById(R.id.bookName);
            bookType=itemView.findViewById(R.id.bookType);
            bookTotal=itemView.findViewById(R.id.bookTotal);
        }
    }
}
