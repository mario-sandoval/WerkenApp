package cl.kimelti.werken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import cl.kimelti.werken.databinding.ActivityStartupBinding;
import cl.kimelti.werken.service.EstadoService;
import cl.kimelti.werken.ui.login.LoginActivity;

public class StartupActivity extends AppCompatActivity {

    private ActivityStartupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataLoaderTask loaderTask = new DataLoaderTask();
        loaderTask.execute();
        //Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
        //startActivity(intent);
    }

    private class DataLoaderTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            EstadoService.getInstance().loadData();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                finish();
                Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}