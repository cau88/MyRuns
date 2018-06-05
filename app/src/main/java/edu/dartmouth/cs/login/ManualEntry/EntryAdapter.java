package edu.dartmouth.cs.login.ManualEntry;
import edu.dartmouth.cs.login.*;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {

    private List<Entry> entriesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, value;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            value = (TextView) view.findViewById(R.id.value);
        }
    }


    public EntryAdapter(List<Entry> entriesList) {
        this.entriesList = entriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Entry entry = entriesList.get(position);
        holder.title.setText(entry.getTitle());
        holder.value.setText(entry.getValue());
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }
}
