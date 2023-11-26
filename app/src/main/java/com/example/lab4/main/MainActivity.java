package com.example.lab4.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lab4.App;
import com.example.lab4.R;
import com.example.lab4.details.DetailsActivity;

public class MainActivity extends AppCompatActivity {
private Button searchButton;
private RecyclerView booksList;
private EditText nameEditText;
private ProgressBar progress;
private TextView emptyList;
private TextView errorTextView;
private MainViewModel viewModel;
private BookAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.findBookButton);
        booksList = findViewById(R.id.booksRecyclerView);
        nameEditText = findViewById(R.id.bookTitleEditText);
        progress = findViewById(R.id.progressBarDetails);
        emptyList = findViewById(R.id.emptyList);
        //!!!------------------------------------
        errorTextView = findViewById(R.id.errorTextView);

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this,app.getViewModelFactory());
        viewModel =viewModelProvider.get(MainViewModel.class);

        viewModel.getViewState().observe(this,state -> {
            searchButton.setEnabled(state.isEnableSearchButton());
            booksList.setVisibility(toVisibility(state.isShowList()));
            progress.setVisibility(toVisibility(state.isShowProgress()));
            emptyList.setVisibility(toVisibility(state.isShowEmptyHint()));
            errorTextView.setVisibility(toVisibility(state.isShowError()));
            adapter.setBooks(state.getBooks());
        });

        searchButton.setOnClickListener(v ->{
            String title = nameEditText.getText().toString();
            title = title.replace(" ", "+");
            viewModel.getBook(title);
        });
        initBooksList();
    }

    private void initBooksList(){
        LayoutManager layoutManager = new LinearLayoutManager(this);
        booksList.setLayoutManager(layoutManager);
        adapter = new BookAdapter(book -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("title",book.getTitle());
            intent.putExtra("authors",book.getAuthor());
            intent.putExtra(DetailsActivity.EXTRA_BOOK_ID, book.getKey());
            startActivity(intent);
        });
        booksList.setAdapter(adapter);
    }
    static int toVisibility(boolean show){
        return show ? View.VISIBLE : View.GONE;
    }

}