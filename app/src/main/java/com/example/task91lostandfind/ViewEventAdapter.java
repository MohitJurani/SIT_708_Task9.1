package com.example.task91lostandfind;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ViewEventAdapter extends RecyclerView.Adapter<ViewEventAdapter.MyViewHolder> {
    private List<Item> list;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.label);
        }
    }


    public ViewEventAdapter(List<Item> list, Context ctx) {
        this.list = list;
        context = ctx;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final Item itm = list.get(position);
        viewHolder.title.setText(itm.getId() + ": " + itm.getTitle() + ": " + itm.getDescription());
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewEventDetailsActivity.class);
                intent.putExtra("title", itm.getTitle());
                intent.putExtra("description", itm.getDescription());
                intent.putExtra("phone", itm.getContact());
                intent.putExtra("location", itm.getLocation());
                intent.putExtra("date", itm.getDate());
                intent.putExtra("id", itm.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
