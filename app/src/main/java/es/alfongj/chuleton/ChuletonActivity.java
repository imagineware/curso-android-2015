package es.alfongj.chuleton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class ChuletonActivity extends ActionBarActivity {

    private static final String SP_CHULETA_KEY = "SP_CHULETA_KEY";

    // Shared Preferences
    SharedPreferences mSharedPreferences;

    // Views
    EditText mChuletonEditText;
    ImageButton mSaveBtn;
    ImageButton mShareBtn;

    // DB
    ParseObject mParseChuleta = new ParseObject("Chuleta");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuleton);

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        // Obtenemos el último valor guardado de la chuleta
        loadFromParse();

        initViews();
    }

    private void initViews() {
        // Obtenemos referencias a todas las vistas que vayamos a usar
        mChuletonEditText = (EditText) findViewById(R.id.et_chuleta);
        mSaveBtn = (ImageButton) findViewById(R.id.bt_save);
        mShareBtn = (ImageButton) findViewById(R.id.bt_share);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto actual de la chuleta
                String newChuleta = mChuletonEditText.getText().toString();

                // Guardamos en SharedPreferences
                mSharedPreferences.edit()
                        .putString(SP_CHULETA_KEY, newChuleta)
                        .apply();

                // Guardamos en la nube
                saveInParse(newChuleta);
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

    private void saveInParse(String chuleta) {
        // Le creamos un atributo "content" y lo rellenamos con el contenido de la chuleta
        mParseChuleta.put("content", chuleta);
        // Dejamos encargado que se guarde en cuanto se pueda.
        mParseChuleta.saveEventually();
    }

    private void loadFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chuleta");
        // Hace una consulta en la nube de Parse buscando la chuleta. Cuando la encuentre y
        // descargue, se ejecutará el método done().
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseChuleta, ParseException e) {
                // Si hubiera un fallo con internet nos llegará parseChuleta como null. Tenemos que
                // comprobar que no se dé el caso, y después extraer de ella el contenido.
                if (parseChuleta != null) {
                    mParseChuleta = parseChuleta;
                    mChuletonEditText.setText(parseChuleta.getString("content"));
                }
            }
        });
    }
}
