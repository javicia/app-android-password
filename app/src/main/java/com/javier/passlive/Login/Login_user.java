package com.javier.passlive.Login;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.javier.passlive.R;

public class Login_user extends AppCompatActivity {

    private static final String SHARED_PREF = "mi_pref";
    private static final String KEY_PASSWORD = "password";
    private BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login_user);

    }
}