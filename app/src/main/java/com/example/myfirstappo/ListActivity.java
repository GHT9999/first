package com.example.myfirstappo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstappo.adapter.StarAdapter;
import com.example.myfirstappo.beans.Star;
import com.example.myfirstappo.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private StarService service;
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private static final String TAG = "StarAdapter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void init(){
        service.create(new Star("Lionel Messi", "https://le10static.com/img/cache/article/576x324/0000/0020/202909.jpeg", 3.5f));
        service.create(new Star("Cristiano Ronaldo", "https://upload.wikimedia.org/wikipedia/commons/2/2e/Ronaldo_in_2018.jpg", 3));
        service.create(new Star("Neymar ", "https://lh3.googleusercontent.com/G0OmFZ3J0kkNIVdVhYD--Rmi8u3H2JkF4pDAt4aRauZg0zLpCg673kNH9B-W2Lpn7K0pQtsQfWHYEt4Ibk8drrl9", 5));
        service.create(new Star("Kylian Mbappé ", "https://www.ligue1.fr/-/media/Project/LFP/shared/Images/Players/2021-2022/21/56621.jpg", 1));
        service.create(new Star("Mohamed Salah","https://img.a.transfermarkt.technology/portrait/big/148455-1546611604.jpg?lm=1",3));
        service.create(new Star("Paul Pogba","https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Paul_Pogba_2017.jpg/196px-Paul_Pogba_2017.jpg",1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {

                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle("Stars").setText(txt).startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}
