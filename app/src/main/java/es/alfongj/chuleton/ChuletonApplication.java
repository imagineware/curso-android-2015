package es.alfongj.chuleton;

import android.app.Application;

import com.parse.Parse;

public class ChuletonApplication extends Application {

    // TODO: Rellenar con los valores de vuestra app registrada en Parse
    private final static String APP_ID = "RELLENAME";
    private final static String CLIENT_KEY = "RELLENAME";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APP_ID, CLIENT_KEY);
    }
}
