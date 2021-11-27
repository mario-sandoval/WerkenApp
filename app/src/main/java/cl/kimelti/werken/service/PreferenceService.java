package cl.kimelti.werken.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cl.kimelti.werken.App;
import cl.kimelti.werken.R;

public class PreferenceService {

    private SharedPreferences preferences;
    private static PreferenceService instance = null;

    private PreferenceService() {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

    public static synchronized PreferenceService getInstance(){
        if (instance == null) {
            instance = new PreferenceService();
        }
        return instance;
    }

    public void putPreferencesAuthString(String authString){
        if(this.preferences != null) {
            SharedPreferences.Editor editor = this.preferences.edit();
            editor.putString(App.getAppResources().getString(R.string.preference_auth), authString);
            editor.apply();
        }
    }

    public String getAuthString(){
        if(this.preferences != null) {
            return preferences.getString(App.getAppResources().getString(R.string.preference_auth), "");
        }
        return null;
    }

    public void putPreferencesMessenger(String messengerId){
        if(this.preferences != null) {
            SharedPreferences.Editor editor = this.preferences.edit();
            editor.putString(App.getAppResources().getString(R.string.preference_messenger), messengerId);
            editor.apply();
        }
    }

    public String getMessenger(){
        if(this.preferences != null) {
            return preferences.getString(App.getAppResources().getString(R.string.preference_messenger), "");
        }
        return null;
    }

    public void removeLoginPreferences(){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(App.getAppResources().getString(R.string.preference_auth));
        editor.remove(App.getAppResources().getString(R.string.preference_messenger));
        editor.apply();
    }
}
