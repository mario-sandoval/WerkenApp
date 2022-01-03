package cl.kimelti.werken;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import cl.kimelti.werken.data.EntregaDetailFragment;
import cl.kimelti.werken.data.EntregaDialogFragment;
import cl.kimelti.werken.data.RetiroDetailHostActivity;
import cl.kimelti.werken.data.database.DBOpenHelper;
import cl.kimelti.werken.data.model.EnvioVo;
import cl.kimelti.werken.databinding.ActivityMainBinding;
import cl.kimelti.werken.service.EnvioService;
import cl.kimelti.werken.service.PreferenceService;
import cl.kimelti.werken.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements EntregaDialogFragment.EntregaDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_retiro, R.id.nav_entrega)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //DBOpenHelper.getInstance().getReadableDatabase().close();
        //DBOpenHelper.getInstance().close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EntregaDialogFragment entregaDialogFragment = (cl.kimelti.werken.data.EntregaDialogFragment) dialog;
        EnvioVo envio = entregaDialogFragment.getmItem();

        MensajeroTask mensajeroTask = new MensajeroTask(envio);

        try {
            //synchronized(mensajeroTask) {
                mensajeroTask.execute((Void) null).get();
            //}
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Envío Actualizado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    private void logout(){
        PreferenceService.getInstance().removeLoginPreferences();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private class MensajeroTask extends AsyncTask<Void,Void, Boolean> {

        private EnvioVo mItem;

        public MensajeroTask(EnvioVo mItem){
            this.mItem = mItem;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                EnvioService.getInstance().updateEnvio(mItem);
            } catch (IOException e) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }
}