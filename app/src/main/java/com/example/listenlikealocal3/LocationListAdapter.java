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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.listenlikealocal3.Model.Location;
import com.example.listenlikealocal3.Services.SpotifyClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    public static final String TAG = "LocationListAdapter";
    public static final String COUNTRY_CODE = "country_code";
    private final Context context;
    private final List<Location> locations;
    SpotifyClient spotifyClient;

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

    public void forceUpdate(){
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

            AsyncHttpClient client = new AsyncHttpClient();
            client.get("https://gist.githubusercontent.com/DmytroLisitsyn/1c31186e5b66f1d6c52da6b5c70b12ad/raw/01b1af9b267471818f4f8367852bd4a2814cbae6/country_dial_info.json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    JSONArray jsonArray = json.jsonArray;
                    for (int i = 0;  i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.optString("code").equals(location.getLocation())) {
                                String flagIcon = object.optString("flag");
                                Log.i(TAG, "FLAG: " + flagIcon);
                                flag.setText(flagIcon);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.i(TAG, "onFailure " + throwable);
                }
            });

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlaylistActivity.class);
                intent.putExtra(COUNTRY_CODE, Parcels.wrap(location.getLocation()));
                context.startActivity(intent);
                Log.i(TAG, location.getLocation());
            });

        }

    }

}






