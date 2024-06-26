package fpoly.huynkph38086.applab1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInWithPhone extends AppCompatActivity {
    EditText edPhone, edOTP;
    Button btnGet, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in_with_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edPhone = findViewById(R.id.ed_phone);
        edOTP = findViewById(R.id.ed_otp);
        btnGet = findViewById(R.id.btn_get);
        btnLogin = findViewById(R.id.btn_login);

        FirebaseServices services = new FirebaseServices(this);
        services.getCallbacks(edOTP);

        btnGet.setOnClickListener(v -> services.getOTP(Long.valueOf(edPhone.getText().toString()).toString()));

        btnLogin.setOnClickListener(v -> services.signInWhitOTP(edOTP.getText().toString()));
    }
}