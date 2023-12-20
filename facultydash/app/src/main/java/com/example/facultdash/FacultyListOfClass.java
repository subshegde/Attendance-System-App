package com.example.facultdash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FacultyListOfClass extends RecyclerView.Adapter<FacultyListOfClass.MyViewHolder> {

    private Context context;
    private ArrayList<Faculty> faculties;

    public FacultyListOfClass(Context context, ArrayList<Faculty> list) {
        this.context = context;
        this.faculties = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.facultylayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Faculty flist = faculties.get(position);

        // Check if the student is not null
        if (flist != null) {
            holder.studentName.setText(flist.getName());
        } else {
            holder.studentName.setText("No one is Absent Today");


            //Handle the case where student is null, e.g., set a default value or log an error.
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, flist.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StudentFacultyView.class);
                intent.putExtra("FACULTY_ID",flist.getUniqueID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
        }
    }
}
