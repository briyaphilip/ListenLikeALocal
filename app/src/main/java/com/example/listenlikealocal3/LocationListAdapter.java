package com.example.listenlikealocal3;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenlikealocal3.Model.Location;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    public static final String TAG = "LocationListAdapter";
    private final Context context;
    private final List<Location> locations;

    public LocationListAdapter(Context context, List<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void clear() {
        locations.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Location> list) {
        locations.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView locationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.locationName);

        }
        public void bind(Location location) {
            locationName.setText(location.getLocation());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaylistActivity.class);
                    intent.putExtra("country_code", Parcels.wrap(location.getLocation()));
                    context.startActivity(intent);
                    Log.i(TAG, location.getLocation());
                }
            });

        }

    }

}






