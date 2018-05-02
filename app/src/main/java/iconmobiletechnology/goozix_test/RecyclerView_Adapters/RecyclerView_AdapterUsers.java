package iconmobiletechnology.goozix_test.RecyclerView_Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import iconmobiletechnology.goozix_test.Model.UsersSearchModel;
import iconmobiletechnology.goozix_test.R;

public class RecyclerView_AdapterUsers extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<UsersSearchModel> objects;
    private Context context;

    public RecyclerView_AdapterUsers(Context context, ArrayList<UsersSearchModel> _objects) {
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

    public UsersSearchModel getBox(int position) {
        return ((UsersSearchModel) getItem(position));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerViewHolderUsers) {
            UsersSearchModel model = objects.get(position);
            RecyclerViewHolderUsers mainHolder = (RecyclerViewHolderUsers) holder;

            mainHolder.name.setText(model.getLogin());
            Uri uri = Uri.parse(model.getAvatarUrl());
            Glide.with(context).load(uri).into(mainHolder.avatar);

            mainHolder.cardBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBody(holder.getAdapterPosition(), objects);
                }
            });
        }
    }

    protected void onClickBody(int Position, ArrayList<UsersSearchModel> objects) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_users, viewGroup, false);
        return new RecyclerViewHolderUsers(mainGroup);
    }
}