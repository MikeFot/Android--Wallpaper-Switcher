package com.michaelfotiadis.wallpaperswitcher.ui.activity.gallery;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.michaelfotiadis.wallpaperswitcher.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomPhotoGalleryActivity extends AppCompatActivity {

    private ThumbnailRecyclerViewAdapter mAdapter;


    /**
     * Overrides methods
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final FloatingActionButton okButton = (FloatingActionButton) findViewById(R.id.fab_add);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new ThumbnailRecyclerViewAdapter();
        recyclerView.setAdapter(mAdapter);

        loadData();

        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(final View v) {

                deliverSelectedPhotos();
            }
        });
    }

    private void loadData() {
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(final int i, final Bundle bundle) {


                final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
                final String orderBy = MediaStore.Images.Media.DATE_ADDED;
                return new CursorLoader(getApplicationContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            }

            @Override
            public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {

                final List<SelectablePhoto> items = new ArrayList<>();

                final int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    final int imageID = cursor.getInt(columnIndex);
                    final Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageID));

                    items.add(new SelectablePhoto(imageUri));
                }

                cursor.close();

                Collections.reverse(items);

                mAdapter.addAll(items);

            }

            @Override
            public void onLoaderReset(final Loader<Cursor> loader) {

            }
        });
    }

    private void deliverSelectedPhotos() {
        final List<SelectablePhoto> photos = mAdapter.getItems();

        final List<SelectablePhoto> selections = new ArrayList<>();

        for (final SelectablePhoto photo : photos) {
            if (photo.isSelected()) {
                selections.add(photo);
            }
        }

        final Intent intent = new Intent();
        intent.putExtra("data", new ArrayList<>(selections));

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();

    }


}
