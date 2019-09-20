package com.inc.niccher.task2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PostEstateD extends AppCompatActivity {

    ImageView vImg0;
    ViewPager viewP;

    TextView _Type,_County,_CountySub,_Area,_Price,_Desc,_Time;
    String Owna;

    Button cont_call,cont_sms,cont_eml;

    ProgressDialog pds2;

    private String pat,imageUrls[],imagelement[];
    List<String> urllist = new ArrayList<String>();
    List<String> urlelement = new ArrayList<String>();
    int imcount;

    private FloatingActionButton fab;

    Intent getta;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref;
    FirebaseDatabase fdbas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postestated);

        getSupportActionBar().setTitle("Estate Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pds2=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        getta=getIntent();

        if (getta.getStringExtra("PostUUIDCode")==null){
            //Toast.makeText(this, "No Cross Data", Toast.LENGTH_SHORT).show();
            finish();

            Intent hom=new Intent(PostEstateD.this,Casa.class);
            hom.putExtra("PostUUIDCode","Posts");
            startActivity(hom);
        }else {
            pat=getta.getStringExtra("PostUUIDCode");
        }

        _Type =findViewById(R.id.est_type);
        _County=findViewById(R.id.est_county);
        _CountySub=findViewById(R.id.est_subcounty);
        _Area=findViewById(R.id.est_area);
        _Price=findViewById(R.id.est_price);
        _Desc=findViewById(R.id.est_desc);
        _Time=findViewById(R.id.est_time);

        cont_call=findViewById(R.id.btn_cal);
        cont_sms=findViewById(R.id.btn_sms);
        //cont_eml=findViewById(R.id.btn_eml);

        cont_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call();
            }
        });

        cont_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sms();
            }
        });

        viewP= (ViewPager) findViewById(R.id.est_img_pager);

        LoadPost();
    }

    private void LoadPost(){
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/Estates/"+pat);
        dref3.keepSynced(true);
        try {
            dref3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    try {
                        _Type.setText((String) dataSnapshot.child("eType").getValue());
                        _County.setText((String) dataSnapshot.child("eCounty").getValue());
                        _CountySub.setText((String) dataSnapshot.child("eCountySub").getValue());
                        _Area.setText((String) dataSnapshot.child("eArea").getValue());
                        _Price.setText((String) dataSnapshot.child("ePrice").getValue());
                        _Desc.setText((String) dataSnapshot.child("eDesc").getValue());
                        //eImg0.setText((String) dataSnapshot.child("cBody").getValue());
                        _Time.setText((String) dataSnapshot.child("eTime").getValue());

                        Owna=(String) dataSnapshot.child("eOwner").getValue();

                        try {
                            //Picasso.get().load((String) dataSnapshot.child("cImg0").getValue()).into(vImg0);
                            Counta();

                        }catch (Exception ex){
                            Picasso.get().load(R.drawable.ic_defuser).into(vImg0);
                            Toast.makeText(PostEstateD.this, "Picasso.get() Error"+ex, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception esx){
                        Toast.makeText(PostEstateD.this, "Error parsing the selected post\n"+esx.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception ex){
            Log.e("Casa ", "LoadUsa: \n" +ex.getMessage());
        }
    }

    private void Counta(){
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/Estates/"+pat);
        dref3.keepSynced(true);

        dref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imcount= (int) dataSnapshot.getChildrenCount();

                String nod="eImg",fina = null;

                for (int i=0;i<10;i++){
                    fina=nod+i;
                    //Log.e("Array Maker", "onDataChange: "+fina );
                    if (dataSnapshot.child(fina).exists()){
                        urllist.add(fina);
                        urlelement.add((String) dataSnapshot.child(fina).getValue());
                    }
                }

                //Log.e("Url List", " Imgs : "+urllist );
                //Log.e("Url List Elements ", " Imgs : "+urlelement );
                imageUrls=Arrays.copyOf(urllist.toArray(), urllist.size(),String[].class);
                imagelement=Arrays.copyOf(urlelement.toArray(), urlelement.size(),String[].class);
                //Log.e("Urls Array", " Imgs : "+ Arrays.toString(imageUrls) );
                //Log.e("Urls Array", " Imgs : "+ Arrays.toString(imagelement) );

                CarSlidAdpta adapter = new CarSlidAdpta(PostEstateD.this, imagelement);
                viewP.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Call(){
        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("Task1Admin/"+Owna);
        dref4.keepSynced(true);

        dref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Phone=(String) dataSnapshot.child("aPhone").getValue();

                Intent col=new Intent(Intent.ACTION_CALL);
                if (Phone.trim().isEmpty()){
                    //col.setData(Uri.parse("",""));
                    Toast.makeText(PostEstateD.this, "No Phone number Provided by the Admin", Toast.LENGTH_SHORT).show();
                }else {
                    col.setData(Uri.parse("tel:"+Phone));

                    if (ActivityCompat.checkSelfPermission(PostEstateD.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(PostEstateD.this, "Please Grant permision to Call", Toast.LENGTH_SHORT).show();
                        ReqPerm();
                    }else {
                        startActivity(col);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Sms(){
        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("Task1Admin/"+Owna);
        dref4.keepSynced(true);

        dref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Phone=(String) dataSnapshot.child("aPhone").getValue();

                Intent col=new Intent(Intent.ACTION_CALL);
                if (Phone.trim().isEmpty()){
                    Toast.makeText(PostEstateD.this, "No Phone number Provided by the Admin", Toast.LENGTH_SHORT).show();
                }else {
                    col.setData(Uri.parse("tel:"+Phone));

                    if (ActivityCompat.checkSelfPermission(PostEstateD.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(PostEstateD.this, "Please Grant permision to Send SMS", Toast.LENGTH_SHORT).show();
                        ReqPermSms();
                    }else {
                        startActivity(col);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ReqPerm(){
        ActivityCompat.requestPermissions(PostEstateD.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

    private void ReqPermSms(){
        ActivityCompat.requestPermissions(PostEstateD.this,new String[]{Manifest.permission.SEND_SMS},1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();

            Intent hom=new Intent(PostEstateD.this,Casa.class);
            hom.putExtra("PostUUIDCode","PostsE");
            startActivity(hom);
        }
        return super.onOptionsItemSelected(item);
    }
}
