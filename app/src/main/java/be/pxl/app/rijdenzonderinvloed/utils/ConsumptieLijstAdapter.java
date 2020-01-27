package be.pxl.app.rijdenzonderinvloed.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import be.pxl.app.rijdenzonderinvloed.R;
import be.pxl.app.rijdenzonderinvloed.data.Consumptie;

public class ConsumptieLijstAdapter extends RecyclerView.Adapter<ConsumptieLijstAdapter.MyViewHolder> {

    private ArrayList<Consumptie> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDrankSoort;
        public TextView tvUurConsumptie;
        public TextView tvStandaardGlazen;
        public ImageView ivDrankIcoon;

        public MyViewHolder(ConstraintLayout cl) {
            super(cl);
            ivDrankIcoon = cl.findViewById(R.id.iv_drank_icoon);
            tvDrankSoort = cl.findViewById(R.id.tv_drank_soort);
            tvUurConsumptie = cl.findViewById(R.id.tv_uur_consumptie);
            tvStandaardGlazen = cl.findViewById(R.id.tv_standaard_glazen);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConsumptieLijstAdapter(ArrayList<Consumptie> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder((ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_consumptielijst_item, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String icoon = mDataset.get(position).getDrank().getIcoonNaam();
        try {
            holder.ivDrankIcoon.setImageResource(R.drawable.class.getField(icoon).getInt(null));
            holder.tvDrankSoort.setText(mDataset.get(position).getDrank().toString());
            holder.tvUurConsumptie.setText(mDataset.get(position).getTijdConsumptie().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            holder.tvStandaardGlazen.setText(mDataset.get(position).getDrank().standaardGlazenToString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //
    public void deleteItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
}