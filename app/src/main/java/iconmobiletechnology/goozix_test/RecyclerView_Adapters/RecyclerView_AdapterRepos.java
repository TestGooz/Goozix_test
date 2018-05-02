package iconmobiletechnology.goozix_test.RecyclerView_Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import iconmobiletechnology.goozix_test.R;

public class RecyclerView_AdapterRepos extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<item_repo_list> objects;
    private Context context;

    public RecyclerView_AdapterRepos(Context context, ArrayList<item_repo_list> _objects) {
        this.context = context;
        this.objects = _objects;
    }

    @Override
    public int getItemCount() {
        return (null != objects ? objects.size() : 0);
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    public item_repo_list getBox(int position) {
        return ((item_repo_list) getItem(position));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerViewHolder) {
            item_repo_list model = objects.get(position);
            RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;

            mainHolder.name.setText(model.getName());
            mainHolder.full_name.setText(model.getFullName());
            if (model.getDescription() == null) mainHolder.descr.setVisibility(View.GONE);
            else mainHolder.descr.setText(model.getDescription());

            mainHolder.cardBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mUrl = getBox(holder.getAdapterPosition()).getHtmlUrl();
                    if(mUrl != null) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_repos, viewGroup, false);
        return new RecyclerViewHolder(mainGroup);
    }
}