package es.alfongj.chuleton;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class LandingActivity extends ActionBarActivity {

    static final String PASSWORD = "en mi plato";

    // Views
    EditText mPasswdEditText;
    Button mUnlockBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Especificamos el XML que queremos cargar
        setContentView(R.layout.activity_landing);

        // Inicializamos comportamiento de las vistas
        initViews();
    }

    private void initViews() {
        // Obtenemos referencias a todas las vistas que vayamos a usar
        mPasswdEditText = (EditText) findViewById(R.id.et_passwd);
        mUnlockBtn = (Button) findViewById(R.id.bt_unlock);

        // Cuando hacemos click en el botón de desbloqueo...
        mUnlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ... leemos el contenido del campo de texto...
                String password = mPasswdEditText.getText().toString();

                // ... y lo imprimimos por log y por pantalla
                Log.d("LandingActivity", "Intento de desbloqueo con password: " + password);

                if (PASSWORD.equals(password)) {
                    navigateToChuletonActivity();
                } else {
                    Crouton.cancelAllCroutons();
                    Crouton.makeText(LandingActivity.this,
                            "Contraseña errónea",
                            Style.ALERT).show();
                }
            }
        });
    }

    private void navigateToChuletonActivity() {
        Intent i = new Intent(this, ChuletonActivity.class);
        startActivity(i);
    }
}
