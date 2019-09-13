package com.inc.niccher.task2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Video_Car extends AppCompatActivity {

    Button sel,sendvid;
    VideoView mplay;

    Intent ids;

    private String viewId ;

    int reqcod=4,vidcoun=0;

    Uri uri_vid;

    private ProgressDialog pds2;
    ProgressBar buffa;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref,drefup;
    FirebaseDatabase fdbas;

    private String videlement[];
    List<String> urlelement = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_car);

        sel=findViewById(R.id.buttonSEL);
        mplay=findViewById(R.id.viuvid);
        buffa=findViewById(R.id.Buffasee);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        ids=getIntent();

        Setta();

        VidInit();

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NextVid();
            }
        });
    }

    private void Setta(){
        viewId=ids.getStringExtra("ChildNode");
        try {
            if (viewId.length() < 1){
                finish();
                startActivity(new Intent(Video_Car.this, PostCarD.class));
            }else {
                //Toast.makeText(this, "Video Cars IDs -> "+viewId, Toast.LENGTH_LONG).show();
                //Log.e("Child Nodes", viewId );
            }
        }catch (Exception ex){
            Toast.makeText(this, "Parsing Error occurred\n"+ex, Toast.LENGTH_LONG).show();
        }
    }

    private void VidInit(){
        try {
            dref= FirebaseDatabase.getInstance().getReference("Posteds/Vehicles/"+viewId);
            dref.keepSynced(true);

            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nod="cVid",fina = null;

                    for (int i=0;i<10;i++){
                        fina=nod+i;
                        if (dataSnapshot.child(fina).exists()){
                            urlelement.add((String) dataSnapshot.child(fina).getValue());
                            PlayNow((String) dataSnapshot.child(fina).getValue());
                        }
                    }

                    videlement=Arrays.copyOf(urlelement.toArray(), urlelement.size(),String[].class);

                    Log.e("Urls Array", " Videos : "+ Arrays.toString(videlement) );

                    vidcoun=videlement.length;

                    Toast.makeText(Video_Car.this, "Videos Present -> ("+vidcoun+").", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception ex){
            Toast.makeText(Video_Car.this, "Initialisation error ->"+ex, Toast.LENGTH_SHORT).show();
        }

        //PlayNow();
    }


    private void PlayNow(String Vals) {

        try {
            uri_vid= Uri.parse(String.valueOf(videlement[0]));
            mplay.setVideoURI(uri_vid);
            mplay.requestFocus();
            mplay.start();

            MediaController controller = new MediaController(this);
            controller.setMediaPlayer(mplay);
            controller.setAnchorView(mplay);
            mplay.setMediaController(controller);

            mplay.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {

                    if (i==mediaPlayer.MEDIA_INFO_BUFFERING_START){
                        buffa.setVisibility(View.VISIBLE);

                    }else if (i==mediaPlayer.MEDIA_INFO_BUFFERING_END){
                        buffa.setVisibility(View.INVISIBLE);
                    }

                    return false;
                }
            });
        }catch (Exception es){
            Toast.makeText(this, "Error\n"+es.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
