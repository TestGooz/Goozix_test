package iconmobiletechnology.goozix_test.RecyclerView_Adapters;

public class item_repo_list {

    private String mName;
    private String mFullName;
    private String mDescription;
    private String mHtmlUrl;

    public item_repo_list(String _mName, String _mFullName, String _mDescription, String _mHtmlUrl)
    {
        mName = _mName;
        mFullName = _mFullName;
        mDescription = _mDescription;
        mHtmlUrl = _mHtmlUrl;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }
}
