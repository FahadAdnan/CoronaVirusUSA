package com.example.coronavirususa;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";
    public ArrayList<StateObj> stateList;
    public ArrayList<StateObj> stateListAll;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerAdapter(ArrayList<StateObj> stateList, RecyclerViewClickInterface recyclerViewClickInterface) { //default constructor is empty
        this.stateList = stateList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        stateListAll = new ArrayList<StateObj>();
        stateListAll.addAll(stateList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.new_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //puts data for each specific position.

       holder.stateText.setText(stateList.get(position).getState());
       holder.positiveText.setText(stateList.get(position).getPositive() != 0 ? Integer.toString(stateList.get(position).getPositive()): "N/A");
       holder.negativeText.setText(stateList.get(position).getNegative() != 0 ? Integer.toString(stateList.get(position).getNegative()): "N/A");
       holder.recoveredText.setText(stateList.get(position).getRecovered() != 0 ? Integer.toString(stateList.get(position).getRecovered()): "N/A");
       holder.deathText.setText(stateList.get(position).getDeath() != 0 ? Integer.toString(stateList.get(position).getDeath()): "N/A");
       holder.updateDateText.setText(stateList.get(position).getLastUpdateEt());
       String totalTests = stateList.get(position).getTotalTestResults() != 0 ? Integer.toString(stateList.get(position).getTotalTestResults()): "N/A";
       holder.totalTestText.setText(totalTests);


       boolean isExpanded = stateList.get(position).getisExpanded();
       holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE); //View.INVISIBlE will keep the space there (no good)


    }

    @Override
    public int getItemCount() { // number of rows in recycler view
        return stateList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        // run on a background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
             ArrayList<StateObj> filteredList = new ArrayList<StateObj>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(stateListAll);
            }else{
                for(StateObj  state : stateListAll){
                    if(state.getState().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(state);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        // run on a UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stateList.clear();
            stateList.addAll((Collection<? extends StateObj>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView stateText,positiveText,negativeText, recoveredText,deathText,updateDateText, totalTestText;
        LinearLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stateText = itemView.findViewById(R.id.stateTextView);
            positiveText = itemView.findViewById(R.id.PositiveTestCasesTxt);
            negativeText = itemView.findViewById(R.id.NegativeTestCasesTxt);
            recoveredText = itemView.findViewById(R.id.RecoveredTxt);
            deathText = itemView.findViewById(R.id.deathsTextView);
            updateDateText = itemView.findViewById(R.id.UpdatedDate);
            totalTestText = itemView.findViewById(R.id.totalTests);
            expandableLayout = itemView.findViewById(R.id.expandableLayout2);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    recyclerViewClickInterface.onItemclick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }


}
