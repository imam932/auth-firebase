package id.nawawi.fierbaseapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.io.IOException;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Toast";
    private static final int REQUEST_STORAGE = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "id.nawawi.fileprovider";
    private String tempPath;
    private Bitmap resultBmp;
    private static final int REQUEST_CAMERA = 1;
    private File mFileURI;

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
        FrameLayout fl = ((FrameLayout) findViewById(R.id.flayout));

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

    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mFileURI = getMediaFileName();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, URI.fromFile(mFileURI));
            startActivityForResult(takePictureIntent, 100);
        }
    }

}
