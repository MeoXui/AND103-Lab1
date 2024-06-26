package fpoly.huynkph38086.applab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    EditText edMail, edPass;
    Button btnLogin;
    TextView tvForget, tvSignUp;
    ImageButton ibPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edMail = findViewById(R.id.ed_mail);
        edPass = findViewById(R.id.ed_pass);
        btnLogin = findViewById(R.id.btn_login);
        tvForget = findViewById(R.id.tv_forget);
        tvSignUp = findViewById(R.id.tv_signup);
        ibPhone = findViewById(R.id.ib_phone);

        FirebaseServices services = new FirebaseServices(this);

        btnLogin.setOnClickListener(v -> {
            String email = edMail.getText().toString(),
                    pass = edPass.getText().toString();
            if (email.isEmpty() || pass.isEmpty())
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            else services.signIn(email, pass);
        });

        tvForget.setOnClickListener(v -> {
            String email = edMail.getText().toString();
            if (email.isEmpty())
                Toast.makeText(this, "Vui lòng nhập ddiaj chir email", Toast.LENGTH_SHORT).show();
            else services.forget(email);
        });

        tvSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUp.class)));

        ibPhone.setOnClickListener(v -> startActivity(new Intent(this, SignInWithPhone.class)));
    }
}