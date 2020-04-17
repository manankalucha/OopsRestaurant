package in.dete.oops;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Restaurant> restaurants;


    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.restaurantName.setText(restaurants.get(position).getN());
        if(restaurants.get(position).getVn().equals("")){
            holder.nonVeg.setVisibility(View.INVISIBLE);
        }

        if(restaurants.get(position).getV().equals("")){
            holder.veg.setVisibility(View.INVISIBLE);
        }
        holder.rating.setText(restaurants.get(position).getR());

        holder.bg.setOnClickListener(v->{
            Intent intent = new Intent(context, MenuActivity.class);
            intent.putExtra("restaurants", restaurants.get(position).getN());
            context.startActivity(intent);
        });

        if (restaurants.get(position).getImg() != null && !restaurants.get(position).getImg().isEmpty())
            GlideApp.with(context)
                    .load(restaurants.get(position).getImg().get(0))
                    .centerInside()
                    .into(holder.image);
        else holder.image.setImageURI(null);

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private CardView bg;
        private TextView restaurantName, rating,veg, nonVeg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.bg);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            image = itemView.findViewById(R.id.image);
            rating = itemView.findViewById(R.id.rating);
            veg = itemView.findViewById(R.id.veg);
            nonVeg = itemView.findViewById(R.id.nonVeg);
        }
    }
}
