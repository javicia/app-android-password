package com.javier.passlive.UserRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.Login.Login_user;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Registration extends AppCompatActivity {

    EditText Et_UserPassword, Et_ConfirmPassword;
    Button Btn_Registration;

    //Guardar preferencias de usuario en un archivo con una clave y valor
    SharedPreferences sharedPreferences;

    private static final String SHARE_PREF = "my_pref";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_C_PASSWORD = "c_password";
    private static final String KEY_PRIVACY_POLICY_ACCEPTED = "política de privacidad aceptada";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_registration);
        Inicialize_Variables();
        VerificationPassword();

        Btn_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos el texto obtenido en el campo contraseña
                String password = Et_UserPassword.getText().toString().trim();
                String confirm_Password = Et_ConfirmPassword.getText().toString().trim();

                //Validamos los campos
                //Si el campo contraseña está vacío, se mostrará el Toast
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Registration.this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                    //Si contiene menos de 8 dígitos la contraseña
                } else if (password.length()<8) {
                    Toast.makeText(Registration.this, "Debe de tener al menos 8 dígitos", Toast.LENGTH_SHORT).show();
                    //Si el campo contraseña está vacío, se mostrará el Toast
                } else if (TextUtils.isEmpty(confirm_Password)){
                    Toast.makeText(Registration.this, "Confirme contraseña", Toast.LENGTH_SHORT).show();
                    //Si las contraseñas no coinciden
                } else if (!password.equals(confirm_Password)) {
                    Toast.makeText(Registration.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else {
                    //Si ninguna condición se cumple. Pasamos todos los datos introducidos al SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_PASSWORD, password);
                    editor.putString(KEY_C_PASSWORD, confirm_Password);
                    editor.apply();

                    //Para comprobar que se están alamacenando las contraseña dentro de las Keys, realizamos los Toast

                    Toast.makeText(Registration.this, "KEY PASSWORD" + password, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Registration.this, "KEY_C_PASSWORD" + confirm_Password, Toast.LENGTH_SHORT).show();

                    //Cuando el usuario ingrese por primera vez y se registre, lo va a redirigir a la app
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();//Cuando el usuario presione la tecla retroceso, salga de la app
                }
            }
        });
    }
    //Inicializamos variables
    private void Inicialize_Variables(){
        Et_UserPassword = findViewById(R.id.Et_UserPassword);
        Et_ConfirmPassword = findViewById(R.id.Et_ConfirmPassword);
        Btn_Registration = findViewById(R.id.Btn_Registration);
        sharedPreferences = getSharedPreferences(SHARE_PREF,MODE_PRIVATE);
    }

    //Comprobar si la contraseña ya ha sido registrada
    private void VerificationPassword(){
        String m_Password = sharedPreferences.getString(KEY_PASSWORD, null);
        //Si el usuario ya tiene la contraseña registrada, lo mandamos a login
        if(m_Password != null){
            Intent intent = new Intent(Registration.this, Login_user.class);
            startActivity(intent);
            finish();
        }
    }
    public void onPrivacyPolicyAccepted() {
        // Update the shared preferences to indicate that the privacy policy has been accepted
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_PRIVACY_POLICY_ACCEPTED, true);
        editor.apply();

        // Show a toast message to indicate that the privacy policy has been accepted
        Toast.makeText(this, "Política de privacidad aceptada", Toast.LENGTH_SHORT).show();
    }
}