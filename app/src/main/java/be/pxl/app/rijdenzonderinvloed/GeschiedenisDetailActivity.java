package be.pxl.app.rijdenzonderinvloed;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class GeschiedenisDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geschiedenis_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(GeschiedenisDetailActivityFragment.ARG_ONDER_INVLOED,
                    getIntent().getParcelableExtra(GeschiedenisDetailActivityFragment.ARG_ONDER_INVLOED));
            GeschiedenisDetailActivityFragment fragment = new GeschiedenisDetailActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nsv_geschiedenis_detail_container, fragment)
                    .commit();
        }
    }

}
