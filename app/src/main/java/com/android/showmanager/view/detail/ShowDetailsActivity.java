package com.android.showmanager.view.detail;

import com.android.showmanager.R;
import com.android.showmanager.model.ShowDetails;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.Utils;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ShowDetailsActivity extends AppCompatActivity
{
    private static final String TAG = ShowDetailsActivity.class.getSimpleName();
    String imdbId;
    TextView director;
    TextView genre;
    TextView actors;
    TextView imdbRating;
    TextView name;
    TextView year;
    ImageView imageView;
    ImageButton button;
    private ProgressBar progressBar;
    private ShowDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_layout);
        initView();
        imdbId = getIntent().getExtras().getString(Constants.IMDB_ID);
        mViewModel = ViewModelProviders.of(this).get(ShowDetailViewModel.class);
        loadShowDetails(imdbId);


    }

    void loadShowDetails(String imdbId)
    {
        if(!Utils.checkInternetConnection(this)){
            showToastMessage("Check your connectivity");
            return;
        }
        showProgress();

        mViewModel.getShowDetails(imdbId).observe(this, new Observer<ShowDetails>()
        {
            @Override
            public void onChanged(ShowDetails showDetails)
            {
                initUI(showDetails);
                hideProgress();
            }
        });
    }

    private void initView()
    {

        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.bookmarkButton);
        button.setVisibility(View.GONE);
        director = findViewById(R.id.director);
        actors = findViewById(R.id.actors);
        imdbRating = findViewById(R.id.imdbRating);
        genre = findViewById(R.id.genre);
        name = findViewById(R.id.showName);
        year = findViewById(R.id.showYear);
        imageView = findViewById(R.id.imageView);

    }


    public void showProgress()
    {
        Log.i(TAG, "Showing progress");
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress()
    {
        Log.i(TAG, "hiding progress");
        progressBar.setVisibility(View.GONE);
    }

    private void initUI(final ShowDetails showDetails)
    {
        name.setText(showDetails.getTitle());
        year.setText(showDetails.getYear());
        //TODO Remove hardcoded Strings
        director.setText("Director: " + showDetails.getDirector());
        actors.setText("Actors: " + showDetails.getActors());
        imdbRating.setText("IMDB Rating: " + showDetails.getImdbRating());
        genre.setText("Genre: " + showDetails.getGenre());

        Picasso.with(this)
            .load(showDetails.getPoster())
            .placeholder(R.drawable.placeholder_background)
            .error(R.drawable.placeholder_background)
            .fit()
            .noFade()
            .into(imageView);
    }


    public void showToastMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}
