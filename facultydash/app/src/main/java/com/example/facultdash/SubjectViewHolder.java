package com.example.facultdash;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class SubjectViewHolder extends RecyclerView.ViewHolder {
    TextView subject;
    public SubjectViewHolder(@NonNull View itemView){
        super(itemView);
        subject= itemView.findViewById(R.id.subject);

    }


}
