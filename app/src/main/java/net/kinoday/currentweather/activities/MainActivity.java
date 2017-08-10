package net.kinoday.currentweather.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.kinoday.currentweather.R;

public class MainActivity extends AppCompatActivity {

    private Button btnCurrentWeather, btnSearchWeather;
    private ActionBar mActionBar;
    private final int REQUEST_PERMISSION_LOCATION = 1; // id для permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        init();
    }

    private void init() {
        btnCurrentWeather = (Button) findViewById(R.id.button_current_weather);
        btnSearchWeather = (Button) findViewById(R.id.button_search_weather);

        btnCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            currentWeather();
            }
        });

        btnSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchWeatherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void currentWeather() {
        PackageManager pm = this.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
            String provider = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (!provider.equals("")) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    ifTruePermission();
                } else {
                    showLocationPermission();
                }
            } else {
                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);
            }
        }
    }

    // Method for verifying permissions
    private void showLocationPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation(getResources().getString(R.string.string_perm_location1),
                        getResources().getString(R.string.string_perm_location2), Manifest.permission.ACCESS_FINE_LOCATION,
                        REQUEST_PERMISSION_LOCATION);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_LOCATION);
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.string_perm_location3), Toast.LENGTH_SHORT).show();
            ifTruePermission();
        }
    }

    // Obtaining permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getResources().getString(R.string.string_perm_grant), Toast.LENGTH_SHORT).show();
                    ifTruePermission();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.string_perm_notgrant), Toast.LENGTH_SHORT).show();
                    ifFalsePermission();
                }
                break;
        }
    }

    // Messages about permission or rejection of permission
    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    // Refusal of permission
    private void requestPermission(String permissionName, int permissionRequestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{permissionName}, permissionRequestCode);
        }
    }

    public void ifTruePermission() {
        Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
        startActivity(intent);
    }

    public void ifFalsePermission() {
        Toast.makeText(this, getResources().getString(R.string.string_perm_grant), Toast.LENGTH_SHORT).show();
    }
}
