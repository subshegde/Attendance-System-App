package com.example.facultdash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class SubjectAdapter extends RecyclerView.Adapter<SubjectViewHolder> {
    ImageView faculty;
    Context context;
    List<SubjectListItem> items;

    public SubjectAdapter(Context context,List<SubjectListItem> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_subject,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.subject.setText(items.get(position).getSubject());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


