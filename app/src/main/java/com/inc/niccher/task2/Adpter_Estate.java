package com.inc.niccher.task2;

/**
 * Created by niccher on 29/05/19.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.util.List;


public class Adpter_Estate extends RecyclerView.Adapter<Adpter_Estate.Shika>{

    Context cnt;
    List<Mod_E> poslist;
    Dialog conf;
    String Postid=null;

    FirebaseUser fusa;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref1;

    public Adpter_Estate(Context cnt, List<Mod_E> poslist) {
        this.cnt = cnt;
        this.poslist = poslist;
    }

    @Override
    public Shika onCreateViewHolder(ViewGroup parent, int viewType) {
        View infla= LayoutInflater.from(cnt).inflate(R.layout.part_poste,parent,false);

        fusa= FirebaseAuth.getInstance().getCurrentUser();

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        return new Shika(infla);
    }

    @Override
    public void onBindViewHolder(Shika holder, final int position) {

        final Mod_E kar=poslist.get(position);

        try {
            holder.viu_Loc.setText(kar.geteCounty()+" at "+kar.eCountySub);
            holder.viu_Area.setText(kar.geteArea());
            holder.viu_Pric.setText(kar.getePrice());
            holder.viu_typ.setText(kar.geteType());

            Picasso.get().load(kar.geteImg0()).into(holder.viu_estimg);

            holder.viu_PosDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent posd=new Intent(cnt, PostEstateD.class);
                    posd.putExtra("PostUUIDCode",(String) kar.geteKey());
                    cnt.startActivity(new Intent(posd));
                }
            });
        }catch (Exception ex){
            Toast.makeText(cnt, "No Posted Ads currently\n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return poslist.size();
    }

    class Shika extends RecyclerView.ViewHolder {

        public TextView viu_Loc,viu_Area,viu_Pric,viu_typ;
        public ImageView viu_estimg;
        RelativeLayout viu_PosDetail;

        public Shika(View itemView) {
            super(itemView);

            viu_Loc = itemView.findViewById(R.id.est_loc);
            viu_Area = itemView.findViewById(R.id.est_area);
            viu_Pric = itemView.findViewById(R.id.est_pric);
            viu_typ = itemView.findViewById(R.id.est_typ);

            viu_estimg= itemView.findViewById(R.id.est_imgs);
            viu_PosDetail = itemView.findViewById(R.id.disp_post);
        }
    }
}