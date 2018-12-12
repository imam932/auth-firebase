package id.nawawi.fierbaseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.provider.MediaStore;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bumptech.glide.Glide;
import static android.provider.CalendarContract.CalendarCache.URI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Toast";
    private File mFileURI;
    FrameLayout fl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FloatingActionButton fab = ((FloatingActionButton) findViewById(R.id.floatingActionButton));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");
        //ButterKnife.bind(this);
        //memberi isi value pada database dengan nama field message
        myRef.setValue("Welcome");
        RelativeLayout rl = ((RelativeLayout) findViewById(R.id.rlayout));
        fl = ((FrameLayout) findViewById(R.id.flayout));

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Toast.makeText(MainActivity.this, "Welcome ",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] dialogitem = {
                        "Take Background Photo",
                        "Remove Background"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                captureImage();
                                break;
                            case 1:

                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),"Camera di device anda tidak tersedia", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mFileURI = getMediaFileName();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URI.fromFile(mFileURI));
            startActivityForResult(takePictureIntent, 100);
        }
    }

    /** activity result akan dipanggi setelah camera ditutup      **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Glide.with(getApplicationContext()).load(new File(mFileURI.getPath())).asBitmap().into(new SimpleTarget<Bitmap>(fl.getWidth(),fl.getHeight()) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getApplicationContext().getResources());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        fl.setBackground(drawable);
                    }
                }
            });
        }
    }

    private static File getMediaFileName() {
        // Lokasi External sdcard
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");
        // Buat directori tidak direktori tidak eksis
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraDemo", "Gagal membuat directory" + "CameraDemo");
                return null;
            }
        }
        // Membuat nama file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",  Locale.getDefault()).format(new Date());
        File mediaFile = null;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    /** mengecek pada perangkat mobile memiliki kamera atau tidak      **/
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
