package be.pxl.app.rijdenzonderinvloed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import be.pxl.app.rijdenzonderinvloed.data.Consumptie;
import be.pxl.app.rijdenzonderinvloed.data.OnderInvloed;

public class GeschiedenisDetailActivityFragment extends Fragment {

    public static final String ARG_ONDER_INVLOED = "arg_onder_invloed";
    private OnderInvloed mOnderInvloed;

    public GeschiedenisDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ONDER_INVLOED)) {
            mOnderInvloed = (OnderInvloed) getArguments().getParcelable(ARG_ONDER_INVLOED);
        }
    }

    public String getDrunkFaseIcoon() {
        double maxPromille = mOnderInvloed.getMaxPromille();
        if (maxPromille < 0.5) { return getString(R.string.ic_drunk_fase_1); }
        else if (maxPromille < 1.5) { return getString(R.string.ic_drunk_fase_2); }
        else if (maxPromille < 3) { return getString(R.string.ic_drunk_fase_3); }
        else if (maxPromille < 4) { return getString(R.string.ic_drunk_fase_4); }
        else if (maxPromille < 5) { return getString(R.string.ic_drunk_fase_5); }
        else { return getString(R.string.ic_drunk_fase_6); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_geschiedenis_detail, container, false);

        if (mOnderInvloed != null) {

            try {
                ((TextView) rootView.findViewById(R.id.tv_promille_value)).setText(mOnderInvloed.maxPromilleToString());
                ((TextView) rootView.findViewById(R.id.tv_standaardglazen_value)).setText(Double.toString(mOnderInvloed.getAantalStandaardGlazen()));
                ((TextView) rootView.findViewById(R.id.tv_datum_value)).setText(mOnderInvloed.getDatumDrinkAvond().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                ((TextView) rootView.findViewById(R.id.tv_eerste_consumptie_value)).setText(mOnderInvloed.getTijdEersteConsumptie().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                ((ImageView) rootView.findViewById(R.id.iv_drunk_fase)).setImageResource(R.drawable.class.getField(getDrunkFaseIcoon()).getInt(null));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }

            RecyclerView rvConsumptieLijst = rootView.findViewById(R.id.rv_geschiedenis_consumptie_lijst);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rvConsumptieLijst.setLayoutManager(layoutManager);
            setupRecyclerView(rvConsumptieLijst);
        }
        return rootView;

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleGeschiedenisDetailRecyclerViewAdapter(mOnderInvloed.getLijstConsumpties()));
    }

    public static class SimpleGeschiedenisDetailRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleGeschiedenisDetailRecyclerViewAdapter.ViewHolder> {

        private GeschiedenisListActivity mParentActivity;
        private ArrayList<Consumptie> mDataset;

        SimpleGeschiedenisDetailRecyclerViewAdapter(ArrayList<Consumptie> dataset) {
            mDataset = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_consumptielijst_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            String icoon = mDataset.get(position).getDrank().getIcoonNaam();
            try {
                holder.ivDrankIcoon.setImageResource(R.drawable.class.getField(icoon).getInt(null));
                holder.tvDrankSoort.setText(mDataset.get(position).getDrank().toString());
                holder.tvUurConsumptie.setText(mDataset.get(position).getTijdConsumptie().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                holder.tvStandaardGlazen.setText(mDataset.get(position).getDrank().standaardGlazenToString());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvDrankSoort;
            public TextView tvUurConsumptie;
            public TextView tvStandaardGlazen;
            public ImageView ivDrankIcoon;

            public ViewHolder(View view) {
                super(view);
                ivDrankIcoon = view.findViewById(R.id.iv_drank_icoon);
                tvDrankSoort = view.findViewById(R.id.tv_drank_soort);
                tvUurConsumptie = view.findViewById(R.id.tv_uur_consumptie);
                tvStandaardGlazen = view.findViewById(R.id.tv_standaard_glazen);
            }
        }
    }
}
