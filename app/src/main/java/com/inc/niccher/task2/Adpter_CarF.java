package com.inc.niccher.task2;

/**
 * Created by niccher on 29/05/19.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Adpter_CarF extends RecyclerView.Adapter<Adpter_CarF.Shika> implements Filterable{

    Context cnt;
    List<Mod_V> poslist;
    List<Mod_V> poslistx;

    String Postid=null;

    FirebaseUser fusa;

    public Adpter_CarF(Context cnt, List<Mod_V> poslist) {
        this.cnt = cnt;
        this.poslist = poslist;
    }

    @Override
    public Shika onCreateViewHolder(ViewGroup parent, int viewType) {
        View infla= LayoutInflater.from(cnt).inflate(R.layout.part_postv,parent,false);

        fusa= FirebaseAuth.getInstance().getCurrentUser();

        return new Shika(infla);
    }

    @Override
    public void onBindViewHolder(Shika holder, final int position) {

        final Mod_V kar=poslist.get(position);

        holder.viu_Bod.setText(kar.getcBody());
        holder.viu_Mak.setText(kar.getcMaker());
        holder.viu_Conditio.setText(kar.getcCondition());
        holder.viu_Pric.setText(kar.getcPrice());
        holder.viu_region.setText(kar.getcRegion());

        Postid=kar.getcKey();

        Picasso.get().load(kar.cImg0).into(holder.viu_carimg);

        holder.viu_PosDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent posd=new Intent(cnt, PostCarD.class);
                posd.putExtra("PostUUIDCode",kar.getcKey());
                cnt.startActivity(new Intent(posd));
            }
        });
    }

    @Override
    public int getItemCount() {
        return poslist.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Mod_V> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(poslist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Mod_V maker : poslist) {
                    if (maker.getcMaker().toLowerCase().contains(filterPattern)) {
                        filteredList.add(maker);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            try {
                poslistx.clear();
                poslistx.addAll((List) results.values);
                notifyDataSetChanged();
            }catch (Exception e){
                Toast.makeText(cnt, "NullPointerException\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    class Shika extends RecyclerView.ViewHolder {

        public TextView viu_Mak,viu_Bod,viu_Conditio,viu_Pric,viu_region;
        public ImageView viu_carimg;
        RelativeLayout viu_PosDetail;

        public Shika(View itemView) {
            super(itemView);

            viu_Mak = itemView.findViewById(R.id.disp_maker);
            viu_Bod = itemView.findViewById(R.id.disp_body);
            viu_Conditio = itemView.findViewById(R.id.disp_condition);
            viu_Pric = itemView.findViewById(R.id.disp_price);
            viu_region = itemView.findViewById(R.id.disp_region);

            viu_carimg = itemView.findViewById(R.id.disp_imgs);
            viu_PosDetail = itemView.findViewById(R.id.disp_post);
        }
    }
}