package be.pxl.app.rijdenzonderinvloed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import be.pxl.app.rijdenzonderinvloed.data.Consumptie;
import be.pxl.app.rijdenzonderinvloed.data.DrankLijst;
import be.pxl.app.rijdenzonderinvloed.data.OnderInvloed;
import be.pxl.app.rijdenzonderinvloed.utils.ConsumptieLijstAdapter;
import be.pxl.app.rijdenzonderinvloed.utils.DrankLijstDialogFragment;

public class MainActivity extends AppCompatActivity implements DrankLijstDialogFragment.Listener,
        SharedPreferences.OnSharedPreferenceChangeListener{

    // Layout
    private RecyclerView rvConsumptieLijst;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvDatum;
    private TextView tvEersteConsumptie;
    private TextView tvPromille;
    private TextView tvAantalGlazen;
    private TextView tvNuchterValue;
    private TextView tvNuchterLabel;

    // Adapters
    private ConsumptieLijstAdapter mConsumptieAdapter;
    private DrankLijstDialogFragment drankLijstDialogFragment;

    // Andere
    private OnderInvloed mOnderInvloed;
    private static final String KEY_ONDER_INVLOED = "key_onder_invloed";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load nieuweOnderInvloed
        mOnderInvloed = new OnderInvloed(this);

        // Load SharedPreferences
        setupSharedPreferences();

        // Load TextViews
        tvDatum = findViewById(R.id.tv_datum_value);
        tvEersteConsumptie = findViewById(R.id.tv_eerste_consumptie_value);
        tvPromille = findViewById(R.id.tv_promille_value);
        tvAantalGlazen = findViewById(R.id.tv_standaardglazen_value);

        tvNuchterValue = findViewById(R.id.tv_nuchter_value);
        tvNuchterValue.setText("Nuchter");
        tvNuchterLabel = findViewById(R.id.tv_nuchter_label);
        tvNuchterLabel.setText("Voeg consumptie toe om te beginnen");

        // Load RecyclerView
        rvConsumptieLijst = findViewById(R.id.rv_consumptie_lijst);
        rvConsumptieLijst.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvConsumptieLijst.setLayoutManager(layoutManager);
        mConsumptieAdapter = new ConsumptieLijstAdapter(mOnderInvloed.getLijstConsumpties());
        rvConsumptieLijst.setAdapter(mConsumptieAdapter);

        // Load Floating Action Button
        drankLijstDialogFragment = DrankLijstDialogFragment.newInstance();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drankLijstDialogFragment.show(getSupportFragmentManager(), drankLijstDialogFragment.getTag());
            }
        });

        // Load ItemTouchHelper for SwipeToDelete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int verwijderdeConsumptie = (int) viewHolder.getAdapterPosition();
                mConsumptieAdapter.deleteItem(verwijderdeConsumptie);
                updateTextViews();
                mConsumptieAdapter.notifyItemRemoved(verwijderdeConsumptie);
            }
        }).attachToRecyclerView(rvConsumptieLijst);
    }

    public void updateTextViews() {
        if (mOnderInvloed.getAantalConsumpties() == 0) {
            // Reset mOnderInvloed
            mOnderInvloed = new OnderInvloed(this);
            mConsumptieAdapter = new ConsumptieLijstAdapter(mOnderInvloed.getLijstConsumpties());
            rvConsumptieLijst.setAdapter(mConsumptieAdapter);
            // Reset de velden
            tvNuchterLabel.setText("Voeg consumptie toe om te beginnen");
            tvNuchterValue.setText("Nuchter");
            tvDatum.setText(mOnderInvloed.getDatumDrinkAvond().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            tvEersteConsumptie.setText("");
            tvPromille.setText("");
            tvAantalGlazen.setText("");
        } else {
            tvNuchterLabel.setText("Terug nuchter om");
            tvNuchterValue.setText(mOnderInvloed.berekenTijdTerugNuchter().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            tvDatum.setText(mOnderInvloed.getDatumDrinkAvond().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            tvEersteConsumptie.setText(mOnderInvloed.getTijdEersteConsumptie().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            tvPromille.setText(mOnderInvloed.bloedAlcoholGehalteToString());
            tvAantalGlazen.setText(Double.toString(mOnderInvloed.getAantalStandaardGlazen()));
        }
    }

// SaveInstanceState
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    //    outState.putParcelable(KEY_ONDER_INVLOED, mOnderInvloed);
        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Toast.makeText(this, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    //    mOnderInvloed = savedInstanceState.getParcelable(KEY_ONDER_INVLOED);
        super.onRestoreInstanceState(savedInstanceState);
    }

// Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_voorkeuren) {
            Intent startVoorkeurenActivity = new Intent(this, VoorkeurenActivity.class);
            startActivity(startVoorkeurenActivity);
            return true;
        } else if (id == R.id.action_geschiedenis) {
            Intent startGeschiedenisActivity = new Intent(this, GeschiedenisListActivity.class);
            startActivity(startGeschiedenisActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

// SharedPreferences
    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_gewicht_key))) {
            mOnderInvloed.setGewicht(Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_gewicht_key),
                    getString(R.string.pref_gewicht_default))));
        } else if (key.equals(getString(R.string.pref_geslacht_key))) {
            mOnderInvloed.setGeslacht(sharedPreferences.getString(getString(R.string.pref_geslacht_key),
                    getString(R.string.pref_geslacht_default_value)));
        }
        updateTextViews();
    }

// Dranklijst fragment
    @Override
    public void onDrankClicked(int position) {
        mOnderInvloed.voegConsumptieToe(new Consumptie(position, LocalDate.now(), LocalTime.now()));
        mConsumptieAdapter.notifyDataSetChanged();
        updateTextViews();
        Toast toast = Toast.makeText(this, new DrankLijst().getDrankLijst().get(position).toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

// Nieuwe OnderInvloed -> opslaan?
    public void onRefreshClicked(View v) {
        String toastOutput;
        if (mOnderInvloed.getAantalConsumpties() == 0) {
            toastOutput = "Geen gegevens om te refreshen" ;
        } else {
            updateTextViews();
            toastOutput = "Gegevens geüpdatet";
        }
        Toast.makeText(this, toastOutput, Toast.LENGTH_SHORT).show();
    }

// TimePicker fragment
//    public void showTimePickerDialog(View v) {
//        DialogFragment timePickerDialogFragment= new TimePickerDialogFragment();
//        timePickerDialogFragment.show(getSupportFragmentManager(), "timePicker");
//    }


}
