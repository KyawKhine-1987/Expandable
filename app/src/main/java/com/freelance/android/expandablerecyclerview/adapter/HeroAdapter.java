package com.freelance.android.expandablerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.freelance.android.expandablerecyclerview.R;
import com.freelance.android.expandablerecyclerview.model.Hero;

import java.util.List;


public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.MyViewHolder> {

    private static final String LOG_TAG = HeroAdapter.class.getName();

    private List<Hero> heroList;
    private Context mContext;

    private static int currentPosition = 0;

    public HeroAdapter(Context context, List<Hero> heroList) {
        Log.i(LOG_TAG, "TEST: HeroAdapter() is called...");

        this.mContext = context;
        this.heroList = heroList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() is called...");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_heroes, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() is called...");

        Hero hero = heroList.get(position);

        holder.textViewName.setText(hero.getName());

        holder.textViewRealName.setText(hero.getRealName());

        holder.textViewTeam.setText(hero.getTeam());

        holder.textViewFirstAppearance.setText(" "+ hero.getFirstAppearance());

        holder.textViewCreatedBy.setText(hero.getCreatedBy());

        holder.textViewPublisher.setText(hero.getPublisher());

        holder.textViewBio.setText("                    "+ hero.getBio().trim());

        Glide.with(mContext).load(hero.getImageUrl()).into(holder.imageView);

        holder.linearLayout.setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an slide_down
            Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);

            //toggling visibility
            holder.linearLayout.setVisibility(View.VISIBLE);

            //adding sliding effect
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloading the list
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i(LOG_TAG, "Test : getItemCount() called...");

        return heroList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewRealName, textViewTeam, textViewFirstAppearance,
                textViewCreatedBy, textViewPublisher, textViewBio;
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewName = this.itemView.findViewById(R.id.textViewName);
            textViewRealName = this.itemView.findViewById(R.id.textViewRealName);
            textViewTeam = this.itemView.findViewById(R.id.textViewTeam);
            textViewFirstAppearance = this.itemView.findViewById(R.id.textViewFirstAppearance);
            textViewCreatedBy = this.itemView.findViewById(R.id.textViewCreatedBy);
            textViewPublisher = this.itemView.findViewById(R.id.textViewPublisher);
            textViewBio = this.itemView.findViewById(R.id.textViewBio);
            imageView = this.itemView.findViewById(R.id.imageView);

            linearLayout = this.itemView.findViewById(R.id.linearLayout);
        }
    }

}
