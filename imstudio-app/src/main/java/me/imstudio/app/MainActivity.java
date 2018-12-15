package me.imstudio.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;

import me.imstudio.core.IMSSystemBar;
import me.imstudio.core.ui.widget.spinner.Spinner;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMSSystemBar.INSTANCE.enableTransparentStatusBar(this);
        setContentView(R.layout.activity_main);
        spinnerExample();
    }

    private void spinnerExample() {
        spinner = findViewById(R.id.layout);
        spinner.setItems(Arrays.asList("1", "2", "3"));
    }
}
