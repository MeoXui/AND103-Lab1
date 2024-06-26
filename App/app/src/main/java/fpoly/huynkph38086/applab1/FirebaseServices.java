package fpoly.huynkph38086.applab1;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FirebaseServices {
    Activity mActivity;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    public FirebaseServices(Activity activity) {
        mActivity = activity;
        mAuth = FirebaseAuth.getInstance();
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
            } else Log.w(TAG, "Đăng nhập thất bại", task.getException());
        });
    }
}
