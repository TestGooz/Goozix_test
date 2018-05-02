package iconmobiletechnology.goozix_test.RecyclerView_Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iconmobiletechnology.goozix_test.R;

public class RecyclerViewHolderUsers extends RecyclerView.ViewHolder  {
    public TextView name;
    public ImageView avatar;
    public CardView cardBody;

    public RecyclerViewHolderUsers(View view) {
        super(view);

        this.name = (TextView) view
                .findViewById(R.id.text_name);

        this.avatar = (ImageView) view
                .findViewById(R.id.image_avatar);

        this.cardBody = (CardView) view
                .findViewById(R.id.card_body);
    }
}