package com.example.lab4.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.R;
import com.example.lab4.model.Book;

import java.util.Collections;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements View.OnClickListener{

    private List<Book> books = Collections.emptyList();
    private BookListener bookListener;

    public BookAdapter(BookListener listener){
        this.bookListener = listener;
    }

    public void setBooks(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View root = layoutInflater.inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(root,this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.itemView.setTag(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onClick(View v) {
        Book book = (Book) v.getTag();
        bookListener.onBookChosen(book);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView authorTextView;

        public BookViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorTextView = itemView.findViewById(R.id.book_author);
            itemView.setOnClickListener(listener);
        }
    }
    public interface BookListener{
        void onBookChosen(Book book);
    }
}
