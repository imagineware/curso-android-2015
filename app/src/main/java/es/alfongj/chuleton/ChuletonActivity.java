package es.alfongj.chuleton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class ChuletonActivity extends ActionBarActivity {

    private static final String SP_CHULETA_KEY = "SP_CHULETA_KEY";

    // Shared Preferences
    SharedPreferences mSharedPreferences;

    // Views
    EditText mChuletonEditText;
    ImageButton mSaveBtn;
    ImageButton mShareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuleton);

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        initViews();
    }

    private void initViews() {
        // Obtenemos referencias a todas las vistas que vayamos a usar
        mChuletonEditText = (EditText) findViewById(R.id.et_chuleta);
        mSaveBtn = (ImageButton) findViewById(R.id.bt_save);
        mShareBtn = (ImageButton) findViewById(R.id.bt_share);

        // Obtenemos el último valor guardado de la chuleta...
        String currentChuleta = mSharedPreferences.getString(SP_CHULETA_KEY, "");
        // ... y actualizamos el contenido del EditText con ello
        mChuletonEditText.setText(currentChuleta);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto actual de la chuleta
                String newChuleta = mChuletonEditText.getText().toString();

                // Guardamos en SharedPreferences
                mSharedPreferences.edit()
                        .putString(SP_CHULETA_KEY, newChuleta)
                        .apply();

            }
        });

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto actual de la chuleta
                String newChuleta = mChuletonEditText.getText().toString();

                // Creamos un nuevo intent con la intención de mandar (ACTION_SEND)...
                Intent intent = new Intent(Intent.ACTION_SEND);
                // ... algo de tipo texto (text/plain)...
                intent.setType("text/plain");
                // .. que será el contenido de la chuleta
                intent.putExtra(
                        android.content.Intent.EXTRA_TEXT,
                        newChuleta);

                startActivity(
                        Intent.createChooser(intent,"Dónde quiere guardar su chuleta?"));
            }
        });
    }

}
