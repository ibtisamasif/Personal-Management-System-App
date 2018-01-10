package com.example.hp.pms_project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by HP on 12/26/2017.
 */

public class SessionManager {
    public static final String TAG = "SessionManager";
    private static final String PREF_NAME = "PropertyManagementSystem";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_LOGIN_TOKEN = "user_login_token";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_LOGIN_TIMESTAMP = "user_login_timestamp";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public Boolean isSiteSignedIn() {

        if (getLoginToken().equals("")) {
            return false;
        }

        if (getLoginTimestamp() == 00L) {
            return false;
        }
        Long oldTimestamp = getLoginTimestamp();
        Long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        Long oldAnd24Hours = oldTimestamp + 15552000000L; //Six months expiry
        if (currentTimestamp > oldAnd24Hours) {
            return false;
        }
        return true;
    }

    public void loginSite(String email, String password, Long timeStamp) {
        Log.d(TAG, "loginSite: ");
//        deleteDataIfDifferentUser();
        setKeyEmail(email);
        setKeyPassword(password);
        setLoginTimestamp(timeStamp);
        editor.commit();
    }

    private void deleteDataIfDifferentUser(String number) {
        //TODO implement
    }

    public void logoutSite() {
        deleteAllUserData();
        setKeyEmail("");
        setKeyPassword("");
        setLoginTimestamp(00L);
        editor.commit();
    }

    private void deleteAllUserData() {
        //TODO implement
    }

    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setKeyEmail(String path) {
        editor.putString(KEY_EMAIL, path);
        editor.commit();
    }

    public String getKeyPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    public void setKeyPassword(String name) {
        editor.putString(KEY_PASSWORD, name);
        editor.commit();
    }


    public Long getLoginTimestamp() {
        return pref.getLong(KEY_LOGIN_TIMESTAMP, 00l);
    }

    public void setLoginTimestamp(Long timestamp) {
        editor.putLong(KEY_LOGIN_TIMESTAMP, timestamp);
        editor.commit();
    }

    public String getLoginToken() {
        return pref.getString(KEY_LOGIN_TOKEN, "");
    }

    public void setLoginToken(String loginToken) {
        editor.putString(KEY_LOGIN_TOKEN, loginToken);
        editor.commit();
    }

}
