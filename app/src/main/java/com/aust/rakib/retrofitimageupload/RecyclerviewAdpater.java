package com.aust.rakib.retrofitimageupload;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Personal on 7/27/2017.
 */

public class RecyclerviewAdpater extends RecyclerView.Adapter<RecyclerviewAdpater.Views>  {


    ArrayList<ImageGetModelClass> imageResponses;
    Context context;
    public RecyclerviewAdpater(ArrayList<ImageGetModelClass> imageResponses, Context context) {
        this.imageResponses = imageResponses;
        this.context = context;
    }

    public class Views extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public Views(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image);
            textView= (TextView) itemView.findViewById(R.id.text1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    View view=inflater.inflate(R.layout.customalertdialog,null);
                    AlertDialog.Builder builder =new AlertDialog.Builder(context);
                    builder.setView(view);
                    ImageView imageView= (ImageView) view.findViewById(R.id.imageview);
                    Picasso.with(context).load(String.format("https://rakibjoarder.000webhostapp.com/%s",imageResponses.get(position).getPath())).into(imageView);
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                }
            });

        }
    }
    @Override
    public Views onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_recycler_row,null);

        return new Views(view);
    }

    @Override
    public void onBindViewHolder(Views holder, int position) {


        Picasso.with(context).load(String.format("https://rakibjoarder.000webhostapp.com/%s",imageResponses.get(position).getPath())).resize(600,600)
                .centerCrop().into( holder.imageView);
        holder.textView.setText(imageResponses.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return imageResponses.size();
    }


}
