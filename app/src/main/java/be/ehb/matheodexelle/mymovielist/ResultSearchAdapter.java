package be.ehb.matheodexelle.mymovielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ResultSearchHolder> {

    private List<MovieEntity> movies = new ArrayList<>();

    @NonNull
    @Override
    public ResultSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ResultSearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultSearchHolder holder, int position) {
        MovieEntity currentMov = movies.get(position);
        holder.textViewTitle.setText(currentMov.getTitle());
        holder.textViewDescription.setText(currentMov.getDescription());    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieEntity> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }
    class ResultSearchHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;

        public ResultSearchHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }
    }

}

