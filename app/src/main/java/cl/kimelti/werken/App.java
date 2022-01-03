package cl.kimelti.werken;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import cl.kimelti.werken.data.database.DBOpenHelper;
import cl.kimelti.werken.service.EstadoService;

public class App extends Application {
    private static Resources resources;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        resources = getResources();
        context = getApplicationContext();

    }

    public static Resources getAppResources() {
        return resources;
    }

    public static Context getContext(){
        return context;
    }

}
