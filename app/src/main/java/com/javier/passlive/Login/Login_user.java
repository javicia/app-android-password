package com.javier.passlive.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Login_user extends AppCompatActivity {

    private static final String SHARED_PREF = "my_pref";
    private static final String KEY_PASSWORD = "password";
    //Declaramos aviso biométrico
    private BiometricPrompt biometricPrompt;
    //Como debe comportarse el sistema biométrico
    private BiometricPrompt.PromptInfo prompInfo;


    EditText Et_Email, Et_UserPassword;
    Button Btn_Access, Btn_Biometric_Login;
    SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_login_user);
        Inicialize_Variables();

        Btn_Access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = Et_UserPassword.getText().toString().trim();
                //Obtenemos contraseña almacenada en SharedPreferences
                String Share_password = sharedPreferences.getString(KEY_PASSWORD, null);
                //Si el campo está vacío
                if (Share_password.equals("")) {
                    Toast.makeText(Login_user.this, "Campo obligatorio", Toast.LENGTH_SHORT).show();
                    //Si la contraseña no es igual a la contraseña almacenada
                } else if (!password.equals(Share_password)) {
                    Toast.makeText(Login_user.this, "El email o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                    //Si la contraseña es correcta
                } else {
                    Intent intent = new Intent(Login_user.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        biometricPrompt = new BiometricPrompt(Login_user.this, new BiometricPrompt.AuthenticationCallback(){
        @Override
        public void onAuthenticationError ( int errorCode, @NonNull CharSequence errString){
            super.onAuthenticationError(errorCode, errString);
            Toast.makeText(Login_user.this, "No existen huellas dactilares registradas", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded (@NonNull BiometricPrompt.AuthenticationResult result)
        {
            super.onAuthenticationSucceeded(result);
            Toast.makeText(Login_user.this, "se ha realizado la autenticación con éxito, Bienvedid@!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_user.this, MainActivity.class));
            finish();
        }

        @Override
        public void onAuthenticationFailed () {
            super.onAuthenticationFailed();
            Toast.makeText(Login_user.this, "Falló la autenticación", Toast.LENGTH_SHORT).show();
        }
    });

    //Configuramos comportamiento de aviso biométrico
    prompInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Autenticación biométrica")
            .setNegativeButtonText("Cancelar").build();


    Btn_Biometric_Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        biometricPrompt.authenticate(prompInfo);
        }
    });

    }

    private void Inicialize_Variables(){
        Et_UserPassword = findViewById(R.id.Et_UserPassword);
        Btn_Access = findViewById(R.id.Btn_Access);
        Btn_Biometric_Login = findViewById(R.id.Btn_Biometric_Login);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

    }
}