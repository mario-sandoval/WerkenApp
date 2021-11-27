package cl.kimelti.werken.data;

import android.os.AsyncTask;

import cl.kimelti.werken.data.model.LoggedInUser;
import cl.kimelti.werken.data.model.MensajeroVo;
import cl.kimelti.werken.service.MensajeroService;
import cl.kimelti.werken.service.PreferenceService;
import cl.kimelti.werken.util.StringUtil;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    LoggedInUser fakeUser;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            LoginTask loginTask = new LoginTask(username,password);
            synchronized(loginTask){
                loginTask.execute((Void) null).get();
            }

            if(fakeUser == null){
                throw new RuntimeException("Error al iniciar sesión");
            }
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error al iniciar sesión", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean>{
        private String username;
        private String password;

        private MensajeroVo mensajero;

        LoginTask(String username, String password){
           this.username = username;
           this.password = password;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mensajero = MensajeroService.getInstance().getMensajero(username,password);

            boolean islogged = mensajero != null;

            if(islogged){
                fakeUser = new LoggedInUser(mensajero.getLogin(), mensajero.getNombre());
            }

            return islogged;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                String authString = StringUtil.concatenate(username, ":",password);
                String authStringEnc = StringUtil.encript(authString);
                PreferenceService.getInstance().putPreferencesAuthString(authStringEnc);

                PreferenceService.getInstance().putPreferencesMessenger(String.valueOf(mensajero.getId()));
            }
        }
    }
}