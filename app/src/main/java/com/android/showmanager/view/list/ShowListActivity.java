package com.android.showmanager.view.list;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.showmanager.R;
import com.android.showmanager.adapter.BookMarkAdapter;
import com.android.showmanager.adapter.IShowClickListner;
import com.android.showmanager.adapter.ShowListAdapter;
import com.android.showmanager.model.ShowSearchDetails;
import com.android.showmanager.utils.Constants;
import com.android.showmanager.utils.MySharedPrefs;
import com.android.showmanager.utils.ThreadExecutor;
import com.android.showmanager.view.detail.ShowDetailsActivity;
import com.android.showmanager.view.login.LoginActivity;

import java.util.List;
import java.util.concurrent.Executor;
//Created by jay on 6-1-2021
public class ShowListActivity extends AppCompatActivity {

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView mBookmarkView;
    private BookMarkAdapter mBookMarkAdapter;
    private ShowListAdapter mAdapter;
    private LinearLayout mBookMarkLinearLayout;

    String mSearchKey = Constants.DEFAULT_SEARCH;
    private ShowViewModel mShowViewModel;
    private Executor mExecutor;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        SharedPreferences sharedPreferences = getSharedPreferences(MySharedPrefs.sharedPref, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        edit.apply();
        mExecutor = new ThreadExecutor();
        mShowViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        registerBookMarkObserver();
        observe();
        loadDefaultSearch();
        initBookMarkView();
        initResultRecylerView();

    }

    private void observe() {
        mShowViewModel.getmShowSearchLiveData().observe(this, new Observer<PagedList<ShowSearchDetails>>() {
            @Override
            public void onChanged(PagedList<ShowSearchDetails> showSearchDetails) {
                Log.i(TAG, "On Changed  list size is " + (showSearchDetails != null ? showSearchDetails.size() : 0));
                mAdapter.submitList(showSearchDetails);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void registerBookMarkObserver() {
        mShowViewModel.getBookmarkList().observe(this, new Observer<List<ShowSearchDetails>>() {
            @Override
            public void onChanged(List<ShowSearchDetails> showSearchDetailsList) {
                if (showSearchDetailsList == null || showSearchDetailsList.isEmpty()) {
                    mBookMarkLinearLayout.setVisibility(View.GONE);
                    return;
                }
                mBookMarkLinearLayout.setVisibility(View.VISIBLE);
                mBookMarkAdapter.setShowList(showSearchDetailsList);
                mBookMarkAdapter.notifyDataSetChanged();
            }
        });
    }


    private void loadDefaultSearch() {
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setMaxWidth(Integer.MAX_VALUE);
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                refreshData();
                observe();
            }
        }, 1000);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "Text Submitted " + query);
                mSearchKey = query;
                refreshData();
                observe();
                hideKeyboard(ShowListActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.e(TAG, "Text Changed " + query);
                // DO Nothing
                return true;

            }

        });

        return true;

    }

    public  void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        edit.remove("is_logged_out");
        edit.putBoolean("is_logged_out", true).apply();
        edit.commit();
        for (int i = 0; i < BookMarkAdapter.showList.size(); i++) {
            mShowViewModel.delete(BookMarkAdapter.showList.remove(i));
        }
        Toast.makeText(ShowListActivity.this, "You are successfully logout", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ShowListActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(i);
    }

    private void initBookMarkView() {
        mBookmarkView = findViewById(R.id.bookmarkRecylerView);
        mBookmarkView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mBookmarkView.setLayoutManager(layoutManager);
        mBookMarkAdapter = new BookMarkAdapter(this, showClickListener);
        mBookmarkView.setAdapter(mBookMarkAdapter);
        mBookmarkView.setItemAnimator(new DefaultItemAnimator());
        mBookMarkLinearLayout = findViewById(R.id.bookmarkLayout);
    }

    private void initResultRecylerView() {
        mRecyclerView = findViewById(R.id.showListRecylerView);
        observe();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShowListAdapter(this, showClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * RecyclerItem click event listener
     */
    private IShowClickListner showClickListener = new IShowClickListner() {
        @Override
        public void onShowClick(ShowSearchDetails showSearchDetails) {

            Log.i(TAG, "Show clicked " + showSearchDetails.getTitle());
            startDetailActivity(showSearchDetails.getImdbID());

        }

        @Override
        public void onSaveBookMark(ShowSearchDetails showDetails) {
            Log.i(TAG, "Save book Mark for " + showDetails.getTitle());
            mShowViewModel.insert(showDetails);
        }

        public void onDeleteBookMark(ShowSearchDetails showDetails) {
            mShowViewModel.delete(showDetails);
        }
    };

    /**
     * Start Detail Activity
     *
     * @param imdbID
     */
    private void startDetailActivity(String imdbID) {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        intent.putExtra(Constants.IMDB_ID, imdbID);
        startActivity(intent);
    }

    public void refreshData() {
        Log.i(TAG, "Search key is " + mSearchKey);
        observe();
        mShowViewModel.searchShow(mSearchKey, mExecutor);

    }


}
