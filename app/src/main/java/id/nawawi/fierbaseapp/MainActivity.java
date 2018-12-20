package id.nawawi.fierbaseapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private static final int REQUEST_STORAGE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final String FILE_PROVIDER_AUTHORITY = "id.nawawi.fileprovider";
    private File mFileURI;
    FrameLayout fl;
    GridLayout gl;
    RelativeLayout rl;
    LinearLayout ll;
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FloatingActionButton fab = ((FloatingActionButton) findViewById(R.id.floatingActionButton));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        //memberi isi value pada database dengan nama field message
        myRef.setValue("Welcome");
        rl = (RelativeLayout) findViewById(R.id.rlayout);
        ll = (LinearLayout) findViewById(R.id.llayout);
        fl = (FrameLayout) findViewById(R.id.flayout);
        gl = (GridLayout) findViewById(R.id.glayout);
        iv = (ImageView)findViewById(R.id.imageView2);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
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
                                askPermission(Manifest.permission.CAMERA,REQUEST_CAMERA);

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

    public void askPermission (String Permisson, Integer reqCode) {
        // Check for the external storage permission

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Permisson)
                != PackageManager.PERMISSION_GRANTED) {
            // If you do not have permission, request it
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Permisson},
                    reqCode);
        }
        else if (reqCode == REQUEST_CAMERA) {
            askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,REQUEST_STORAGE);
        }
        else {
            captureImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                    Toast.makeText(this, "Sudah ada permission ke write external storage", Toast.LENGTH_SHORT).show();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,REQUEST_STORAGE);
                    Toast.makeText(this, "Sudah ada permission ke kamera", Toast.LENGTH_SHORT).show();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mFileURI = getMediaFileName();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(),FILE_PROVIDER_AUTHORITY,mFileURI));
            startActivityForResult(takePictureIntent, 100);
        }
    }

    /** activity result akan dipanggi setelah camera ditutup      **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Glide.with(MainActivity.this).
                    load(new File(mFileURI.getPath())).asBitmap().
                    override(800,800).centerCrop().
                    into(new SimpleTarget<Bitmap>(iv.getWidth(),iv.getWidth()
                    ) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    //Drawable drawable = new BitmapDrawable(resource);
                    iv.setImageBitmap(resource);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            ll.setOrientation(LinearLayout.HORIZONTAL);
            //gl.getLayoutParams().width = (rl.getWidth()-fl.getWidth());
            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        }else{
            ll.setOrientation(LinearLayout.VERTICAL);
            //gl.getLayoutParams().height = (rl.getHeight()-fl.getHeight());
            Toast.makeText(this, "POTRAIT", Toast.LENGTH_SHORT).show();
        }
    }
}
