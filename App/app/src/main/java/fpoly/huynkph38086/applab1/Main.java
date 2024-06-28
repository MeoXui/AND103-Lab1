package fpoly.huynkph38086.applab1;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import fpoly.huynkph38086.applab1.adapter.ListCitiesAdapter;
import fpoly.huynkph38086.applab1.model.City;
import fpoly.huynkph38086.applab1.services.FirebaseServices;

public class Main extends AppCompatActivity {
    ListView lv;
    //LottieAnimationView lav;
    List<City> cities;
    ListCitiesAdapter adapter;

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

        FirebaseServices services = new FirebaseServices(this);
        cities = services.getCities();

        adapter = new ListCitiesAdapter(this, cities);
        lv.setAdapter(adapter);

        //lav.setOnClickListener(v -> services.signOut());
        findViewById(R.id.btn_logout).setOnClickListener(v -> services.signOut());
    }
}