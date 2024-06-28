package fpoly.huynkph38086.applab1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fpoly.huynkph38086.applab1.R;
import fpoly.huynkph38086.applab1.model.City;

public class ListCitiesAdapter extends ArrayAdapter<City> {
    Context mContext;
    int itemLayout;

    public ListCitiesAdapter(@NonNull Context context, @NonNull List<City> list) {
        super(context, R.layout.item_city, list);
        mContext = context;
        itemLayout = R.layout.item_city;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(itemLayout, null);

        TextView tvID = view.findViewById(R.id.tv_id),
                tvName = view.findViewById(R.id.tv_name),
                tvState = view.findViewById(R.id.tv_state),
                tvCountry = view.findViewById(R.id.tv_country),
                tvCapital = view.findViewById(R.id.tv_capital),
                tvPopulation = view.findViewById(R.id.tv_population),
                tvRegions = view.findViewById(R.id.tv_regions);

        City item = getItem(position);

        if (item != null) {
            tvID.setText(item._id);
            tvName.setText("Name: " + item._name);
            tvState.setText("State: " + item.state);
            tvCountry.setText("Country: " + item.country);
            tvCapital.setText("Capital: " + (item.capital ? "yes" : "no"));
            tvPopulation.setText("Population: " + item.population);
            //tvRegions.setText("Regions: " + item.regions.toString());
        } else Toast.makeText(mContext, "null data", Toast.LENGTH_SHORT).show();

        return view;
    }
}