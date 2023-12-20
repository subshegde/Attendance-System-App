package com.example.facultdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.Name.setText(model.getName());
        holder.UniqueID.setText(model.getUniqueID());
//        holder.Email.setText(model.getEmail());
//        holder.Phone.setText(model.getPhone());


        //for image

//        Glide.with(holder.img.getContext())
//                .load(model.getImage())
//                .placeholder(R.drawable.baseline_student) // get image from drawble if getImage not getting img
//                .circleCrop()
//                .error(R.drawable.baseline_logout)
//                .into(holder.img);
//
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    //    create myViewHolder class
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView Name,UniqueID,Email,Phone;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);
//        register all declared variable(name,UniqueID,Email,Phone)
        img = (CircleImageView) itemView.findViewById(R.id.img1);
        Name = (TextView) itemView.findViewById(R.id.nametxt);
        UniqueID = (TextView) itemView.findViewById(R.id.uniqueidtxt);
//        Email = (TextView) itemView.findViewById(R.id.emailtxt);
//        Phone = (TextView) itemView.findViewById(R.id.phonenotxt);

    }
}
}

