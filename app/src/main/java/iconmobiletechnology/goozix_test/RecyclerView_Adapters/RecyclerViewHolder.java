package iconmobiletechnology.goozix_test.RecyclerView_Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import iconmobiletechnology.goozix_test.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    public TextView name;
    public TextView full_name;
    public TextView descr;
    public CardView cardBody;

    public RecyclerViewHolder(View view) {
        super(view);

        this.name = (TextView) view
                .findViewById(R.id.text_name);

        this.full_name = (TextView) view
                .findViewById(R.id.text_full_name);

        this.descr = (TextView) view
                .findViewById(R.id.text_desc);

        this.cardBody = (CardView) view
                .findViewById(R.id.card_body);
    }
}