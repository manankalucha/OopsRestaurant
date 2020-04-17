package in.dete.oops;

import android.content.Context;
import android.content.Intent;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    private ArrayList<Menu> menu;
    private int c = 0;

//    public interface OnItemClickListener{
//        void onItemClick(int position);
//    }

    public MenuAdapter(Context context, ArrayList<Menu> menu, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.menu = menu;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_row_layout, parent, false);
        return new MenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dishquantity.setText("0");
        holder.dishName.setText(menu.get(position).getN());

        holder.price.setText(menu.get(position).getP());

        if (menu.get(position).getUrl()!=null && !menu.get(position).getUrl().isEmpty())
            GlideApp.with(context)
                    .load(menu.get(position).getUrl())
                    .centerInside()
                    .into(holder.image);
        else holder.image.setImageURI(null);

        if(menu.get(position).getV().equalsIgnoreCase("veg")){
            holder.vnv.setImageResource(R.drawable.veg);
        }
        else {
            holder.vnv.setImageResource(R.drawable.non);
        }
//        holder.dishadd.setOnClickListener(v -> {
//            ++c;
//            StringBuffer sr = new StringBuffer(c);
//            holder.dishquantity.setText(sr);
//        });
//
//        holder.dishminus.setOnClickListener(v -> {
//            if(c==0){
//                Toast.makeText(context, "Please add some dish first", Toast.LENGTH_SHORT).show();
//            }else{
//                --c;
//                StringBuffer sr = new StringBuffer(c);
//                holder.dishquantity.setText(sr);
//            }
//        });

        holder.dishadd.setOnClickListener(v -> {

        });


    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image, vnv, dishadd, dishminus;
        private TextView dishName,price,veg, dishquantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vnv = itemView.findViewById(R.id.vnv);
            dishName = itemView.findViewById(R.id.dishName);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
            veg = itemView.findViewById(R.id.veg);
            dishadd = itemView.findViewById(R.id.dishadd);
            dishminus = itemView.findViewById(R.id.dishminus);
            dishquantity = itemView.findViewById(R.id.dish);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
