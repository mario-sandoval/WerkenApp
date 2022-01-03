package cl.kimelti.werken.data.task;

import android.os.AsyncTask;

import cl.kimelti.werken.service.EstadoService;

public class DbAsyncTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {
        EstadoService.getInstance().loadData();
        return true;
    }
}
