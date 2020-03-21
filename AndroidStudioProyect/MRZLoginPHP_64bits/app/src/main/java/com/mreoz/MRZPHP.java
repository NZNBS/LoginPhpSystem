package com.mreoz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MRZPHP extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SetSplashScreen();
    }

    private void SetSplashScreen() {
        setContentView(getResID("mrz_login","layout"));
        TryLoginPHP();
    }
    private final String USER = "USER";
    private final String PASS = "PASS";
    private Prefs prefs;
    private void TryLoginPHP() {
        prefs = Prefs.with(this);
        final EditText mail = findViewById(getID("mrz_name"));
        final EditText pass = findViewById(getID("mrz_psw"));
        mail.setText(prefs.read(USER, ""));
        pass.setText(prefs.read(PASS, ""));
        Button init = findViewById(getID("sign_btn"));
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mail.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
                    prefs.write(USER, mail.getText().toString());
                    prefs.write(PASS, pass.getText().toString());
                    new Auth(MRZPHP.this).execute(mail.getText().toString(), pass.getText().toString());
                }
                if(mail.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
                    mail.setError("Please enter username");
                    pass.setError("Please enter password");
                }
                if(mail.getText().toString().isEmpty()){
                    mail.setError("Please enter username");
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Please enter password");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Please allow this permission, so " + "MRZPHPSource" + " could be drawn.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public int getResID(String name, String type){
        return getResources().getIdentifier(name, type, getPackageName());
    }
    public int getID(String name){
        return getResID(name, "id");
    }
}
