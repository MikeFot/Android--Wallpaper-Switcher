package com.michaelfotiadis.wallpaperswitcher.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.michaelfotiadis.wallpaperswitcher.R;
import com.michaelfotiadis.wallpaperswitcher.ui.activity.gallery.CustomPhotoGalleryActivity;
import com.michaelfotiadis.wallpaperswitcher.ui.activity.gallery.SelectablePhoto;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUESTED_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int REQUEST_STORAGE = 501;
    private static final int EXTRA_PICK_IMAGE_MULTIPLE = 1;
    private RecyclerView mRecyclerView;
    private Button mButtonAddPhotos;
    private Button mButtonSave;
    private List<String> mImagePaths;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mButtonAddPhotos = (Button) findViewById(R.id.btnAddPhots);
        mButtonSave = (Button) findViewById(R.id.btnSaveImages);
        mButtonAddPhotos.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btnAddPhots:
                openImageChooser();
                break;
            case R.id.btnSaveImages:
                if (mImagePaths != null) {
                    if (mImagePaths.size() > 1) {
                        Toast.makeText(MainActivity.this, mImagePaths.size() + " no of images are selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, mImagePaths.size() + " no of image are selected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, " no images are selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openImageChooser() {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {

            final Intent intent = new Intent(MainActivity.this, CustomPhotoGalleryActivity.class);
            startActivityForResult(intent, EXTRA_PICK_IMAGE_MULTIPLE);

        } else {
            Ask.on(this)
                    .id(REQUEST_STORAGE)
                    .forPermissions(REQUESTED_PERMISSION)
                    .withRationales(getString(R.string.rationale_storage))
                    .go();
        }
    }

    //optional
    @AskGranted(REQUESTED_PERMISSION)
    public void fileAccessGranted(final int id) {
        openImageChooser();
    }

    @AskDenied(REQUESTED_PERMISSION)
    public void fileAccessDenied(final int id) {
        Toast.makeText(this, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == EXTRA_PICK_IMAGE_MULTIPLE) {

                final ArrayList<SelectablePhoto> photos = data.getParcelableArrayListExtra("data");

                Toast.makeText(this, "Received " + photos.size() + " photos", Toast.LENGTH_SHORT).show();


            }
        }

    }

}
