package iconmobiletechnology.goozix_test.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserSearchItems {
    @SerializedName("items") @Expose private List<UsersSearchModel> items;

    public List<UsersSearchModel> getItems() {
        return items;
    }

    public void setItems(List<UsersSearchModel> items) {
        this.items = items;
    }
}
