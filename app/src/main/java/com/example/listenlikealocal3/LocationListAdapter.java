package com.example.listenlikealocal3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenlikealocal3.Model.Location;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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

    public void deleteItem(int position) {
        locations.remove(position);
        notifyDataSetChanged();
        deleteQuery(locations.get(position));
    }

    private void deleteQuery(Location locationName) {
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with finding location", e);
                    return;
                }
                locationName.deleteInBackground();
                Log.i(TAG, "location deleted");
            }
        });
    }

    public Context getContext() {
        return context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView locationName;
        TextView flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.locationName);
            flag = itemView.findViewById(R.id.flag);

        }
        public void bind(Location location) {
            locationName.setText(location.getLocation());
//            flag.setText(location.getFlag(location.getLocation()));
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






