package fpoly.huynkph38086.applab1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.huynkph38086.applab1.services.FirebaseServices;

public class SignUp extends AppCompatActivity {
    EditText edMail, edPass;
    Button btnSignUp, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edMail = findViewById(R.id.ed_mail);
        edPass = findViewById(R.id.ed_pass);
        btnSignUp = findViewById(R.id.btn_sign);
        btnBack = findViewById(R.id.btn_back);

        FirebaseServices services = new FirebaseServices(this);

        btnSignUp.setOnClickListener(v -> {
            String email = edMail.getText().toString(),
                    pass = edPass.getText().toString();
            if (email.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            else services.signUp(email, pass);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}