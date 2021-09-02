package com.appforysy.activity.activity_perfect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.appforysy.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AdapterMainBanner extends RecyclerView.Adapter<AdapterMainBanner.HolderView> {
    private List<ItemPagerContent> listData;
    public AdapterMainBanner(List<ItemPagerContent> listData) {
        this.listData = listData;
    }
    @NonNull
    @NotNull
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_main_banner, parent, false);
        return new HolderView(view);
    }
    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        ItemPagerContent item=listData.get(position);
        holder.iconImage.setImageResource(item.icon);
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class HolderView extends RecyclerView.ViewHolder {
        private ImageView iconImage;
        public HolderView(@NonNull @NotNull View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.iconImage);
        }
    }

}
