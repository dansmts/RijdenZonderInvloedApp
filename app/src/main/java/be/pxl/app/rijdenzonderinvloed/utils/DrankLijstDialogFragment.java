package be.pxl.app.rijdenzonderinvloed.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import be.pxl.app.rijdenzonderinvloed.R;
import be.pxl.app.rijdenzonderinvloed.data.Drank;
import be.pxl.app.rijdenzonderinvloed.data.DrankLijst;

public class DrankLijstDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_DRANKLIJST = "dranklijst";
    private Listener mListener;

    public static DrankLijstDialogFragment newInstance() {
        final DrankLijstDialogFragment fragment = new DrankLijstDialogFragment();

        ArrayList<Drank> drankLijst = new DrankLijst().getDrankLijst();

        final Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_DRANKLIJST, drankLijst);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dranklijst_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DrankAdapter(getArguments().<Drank>getParcelableArrayList(ARG_DRANKLIJST)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onDrankClicked(int position);
    }


// Adapter
    private class DrankAdapter extends RecyclerView.Adapter<DrankAdapter.ViewHolder> {

        private ArrayList<Drank> mDrankLijst;

// ViewHolder
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;
            ViewHolder(LinearLayout ll) {
                super(ll);
                textView = ll.findViewById(R.id.tv_drank_item);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onDrankClicked(getAdapterPosition());
                            dismiss();
                        }
                    }
                });
            }
        }

        DrankAdapter(ArrayList<Drank> drankLijst) {
            mDrankLijst = drankLijst;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder((LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dranklijst_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(mDrankLijst.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mDrankLijst.size();
        }
    }

}
