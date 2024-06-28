package fpoly.huynkph38086.applab1.services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fpoly.huynkph38086.applab1.Main;
import fpoly.huynkph38086.applab1.model.City;

public class FirebaseServices {
    Activity mActivity;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    FirebaseFirestore db;
    List<City> cities;

    public FirebaseServices(Activity activity) {
        mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void getCurrentUser() {
        mUser = mAuth.getCurrentUser();
    }

    public void getCallbacks(EditText edOTP) {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                edOTP.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = s;
            }
        };
    }

    public List<City> getCities() {
        cities = new ArrayList<>();
        readCitiesData();
        if (cities.isEmpty()) {
            writeSampleCitiesData();
            readCitiesData();
        }
        Toast.makeText(mActivity, "data count: " + cities.size(), Toast.LENGTH_SHORT).show();
        return cities;
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mActivity, task -> {
            if (task.isSuccessful()) {
                getCurrentUser();
                Toast.makeText(mActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mActivity, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mActivity, task -> {
            if (task.isSuccessful()) {
                getCurrentUser();
                mActivity.startActivity(new Intent(mActivity, Main.class));
                Toast.makeText(mActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mActivity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    public void signOut() {
        mAuth.signOut();
        mActivity.finish();
    }

    public void forget(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(mActivity, "Hãy kiểm tra hộp thư của bạn", Toast.LENGTH_SHORT).show();
            else Toast.makeText(mActivity, "Gửi thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    public void getOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+84" + phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(mActivity)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void signInWhitOTP(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        mAuth.signInWithCredential(credential).addOnCompleteListener(mActivity, task -> {
            if (task.isSuccessful()) {
                getCurrentUser();
                mActivity.startActivity(new Intent(mActivity, Main.class));
                Toast.makeText(mActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mActivity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    public void writeSampleCitiesData() {
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("state", "CA");
        data1.put("country", "USA");
        data1.put("capital", false);
        data1.put("population", 860000);
        data1.put("regions", Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("state", "CA");
        data2.put("country", "USA");
        data2.put("capital", false);
        data2.put("population", 3900000);
        data2.put("regions", Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("state", null);
        data3.put("country", "USA");
        data3.put("capital", true);
        data3.put("population", 680000);
        data3.put("regions", Arrays.asList("east_coast"));
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("state", null);
        data4.put("country", "Japan");
        data4.put("capital", true);
        data4.put("population", 9000000);
        data4.put("regions", Arrays.asList("kanto", "honshu"));
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("state", null);
        data5.put("country", "China");
        data5.put("capital", true);
        data5.put("population", 21500000);
        data5.put("regions", Arrays.asList("jingjinji", "hebei"));
        cities.document("BJ").set(data5);
    }

    public void writeCitiesData(City city){
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data = new HashMap<>();
        data.put("name", city._name);
        data.put("state", city.state);
        data.put("country", city.country);
        data.put("capital", city.capital);
        data.put("population", city.population);
        data.put("regions", city.regions);

        cities.document(city._id).set(data);
    }

    public void readCitiesData() {
        db.collection("cities").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    Map<String, Object> data = document.getData();
                    City item = new City(
                            id,
                            data.get("name").toString(),
                            "null",
                            data.get("country").toString(),
                            Boolean.parseBoolean(data.get("capital").toString()),
                            Long.parseLong(data.get("population").toString())//,
                            //(Arrays) data.get("regions")
                    );
                    cities.add(item);
                    Log.d("List", id + " => " + data);
                }
            } else {
                Log.d("List", "Error getting documents: ", task.getException());
            }
        });
    }
}
