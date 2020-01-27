package be.pxl.app.rijdenzonderinvloed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

import be.pxl.app.rijdenzonderinvloed.data.OnderInvloed;
import be.pxl.app.rijdenzonderinvloed.dummy.DummyContent;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class GeschiedenisListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geschiedenis_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rvGeschiedenisLijst = findViewById(R.id.rv_geschiedenis_lijst);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGeschiedenisLijst.setLayoutManager(layoutManager);
        setupRecyclerView(rvGeschiedenisLijst);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        DummyContent dc = new DummyContent();
        recyclerView.setAdapter(new SimpleGeschiedenisListRecyclerViewAdapter(this, dc.getOnderInvloedArrayList()));
    }

    public static class SimpleGeschiedenisListRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleGeschiedenisListRecyclerViewAdapter.ViewHolder> {

        private GeschiedenisListActivity mParentActivity;
        private ArrayList<OnderInvloed> mDataset;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnderInvloed onderInvloedExtra = (OnderInvloed) view.getTag();

                Context context = view.getContext();
                Intent intent = new Intent(context, GeschiedenisDetailActivity.class);
                intent.putExtra(GeschiedenisDetailActivityFragment.ARG_ONDER_INVLOED, onderInvloedExtra);

                context.startActivity(intent);

            }
        };

        SimpleGeschiedenisListRecyclerViewAdapter(GeschiedenisListActivity parent, ArrayList<OnderInvloed> dataset) {
            mDataset = dataset;
            mParentActivity = parent;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_geschiedenislijst_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            String icoon = mDataset.get(position).getDrankIcoon();
            try {
                holder.ivDrankIcoon.setImageResource(R.drawable.class.getField(icoon).getInt(null));
                holder.tvDatum.setText(mDataset.get(position).getDatumDrinkAvond().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                holder.tvPromille.setText(mDataset.get(position).maxPromilleToString());
                holder.tvStandaardGlazen.setText(mDataset.get(position).aantalStandaardGlazenToString());

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            holder.itemView.setTag(mDataset.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);

        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView ivDrankIcoon;
            final TextView tvDatum;
            final TextView tvPromille;
            final TextView tvStandaardGlazen;

            ViewHolder(View view) {
                super(view);
                ivDrankIcoon = view.findViewById(R.id.iv_drank_icoon);
                tvDatum = view.findViewById(R.id.tv_datum);
                tvPromille = view.findViewById(R.id.tv_promille);
                tvStandaardGlazen = view.findViewById(R.id.tv_standaard_glazen);
            }
        }
    }
}
