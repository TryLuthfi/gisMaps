package heuroix.myapps.com.heuroix.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

import heuroix.myapps.com.heuroix.json.Content;
import heuroix.myapps.com.heuroix.activity.ContentClicked;
import heuroix.myapps.com.heuroix.R;


public class StaggeredRecyclerViewAdapterdua extends RecyclerView.Adapter<StaggeredRecyclerViewAdapterdua.ProductViewHolder> {


    private Context mCtx;
    private List<Content> productList;

    public StaggeredRecyclerViewAdapterdua(Context mCtx, List<Content> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item2, parent, false);


        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {


        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        final Content product = productList.get(position);
        Glide.with(Objects.requireNonNull(mCtx)).load("https://heuroix.000webhostapp.com/image/" + product.getGambar()).apply(requestOptions).into(holder.imageview_widget);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ContentClicked.class);
                intent.putExtra("id_content", product.getId_content());
                intent.putExtra("judul", product.getJudul());
                intent.putExtra("gambar", product.getGambar());
                intent.putExtra("deskripsi", product.getDeskripsi());
                intent.putExtra("nama", product.getNama());
                intent.putExtra("email", product.getEmail());
                intent.putExtra("userimage", product.getUserimage());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview_widget;
        CardView da;
        View view;


        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            da = itemView.findViewById(R.id.da);
            imageview_widget = itemView.findViewById(R.id.imageview_widget);

        }
    }
}