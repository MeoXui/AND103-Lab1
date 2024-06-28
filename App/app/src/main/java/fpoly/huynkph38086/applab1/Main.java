package fpoly.huynkph38086.applab1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import fpoly.huynkph38086.applab1.adapter.ListCitiesAdapter;
import fpoly.huynkph38086.applab1.adapter.UaD;
import fpoly.huynkph38086.applab1.model.City;
import fpoly.huynkph38086.applab1.services.FirebaseServices;

public class Main extends AppCompatActivity {
    ListView lv;
    //LottieAnimationView lav;
    List<City> cities;
    ListCitiesAdapter adapter;
    FirebaseServices services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv = findViewById(R.id.lv);
        //lav = findViewById(R.id.lav);

        services = new FirebaseServices(this);

        refresh();

        //lav.setOnClickListener(v -> services.signOut());
        findViewById(R.id.ib_logout).setOnClickListener(v -> services.signOut());

        findViewById(R.id.ib_add).setOnClickListener(v -> openDialog(null));
    }

    void refresh() {
        cities = services.readCitiesData();
        adapter = new ListCitiesAdapter(this, cities, new UaD() {
            @Override
            public void delete(String id) {
                services.deleteCity(id);
                refresh();
            }

            @Override
            public void update(City old) {
                openDialog(old);
            }
        });
        lv.setAdapter(adapter);
        new Handler().postDelayed(() -> adapter.notifyDataSetChanged(), 1000);
    }

    @SuppressLint("SetTextI18n")
    void openDialog(@Nullable City old) {
        String id = "CT" + cities.size();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_update_city, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        EditText edName = view.findViewById(R.id.ed_name),
                edState = view.findViewById(R.id.ed_state),
                edCountry = view.findViewById(R.id.ed_country),
                edPopulation = view.findViewById(R.id.ed_population);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch swtCapital = view.findViewById(R.id.swt_capital);
        Button btnCancel = view.findViewById(R.id.btn_cancel),
                btnSave = view.findViewById(R.id.btn_save);

        if (old != null) {
            id = old._id;
            edName.setText(old.name);
            edState.setText(old.state);
            edCountry.setText(old.country);
            swtCapital.setChecked(old.capital);
            edPopulation.setText(String.valueOf(old.population));
        }

        String finalId = id;
        btnSave.setOnClickListener(v -> {
            City aNew = new City(
                    finalId,
                    edName.getText().toString(),
                    edState.getText().toString(),
                    edCountry.getText().toString(),
                    swtCapital.isChecked(),
                    Integer.parseInt(edPopulation.getText().toString())
            );
            services.writeCitiesData(aNew);
            refresh();
            alertDialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }
}