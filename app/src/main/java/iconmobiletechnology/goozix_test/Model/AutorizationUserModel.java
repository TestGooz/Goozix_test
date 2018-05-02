package iconmobiletechnology.goozix_test.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AutorizationUserModel implements Serializable {

    @SerializedName("login") @Expose private String login;
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("avatar_url") @Expose private String avatarUrl;
    @SerializedName("gravatar_id") @Expose private String gravatarId;
    @SerializedName("html_url") @Expose private String htmlUrl;
    @SerializedName("gists_url") @Expose private String gistsUrl;
    @SerializedName("repos_url") @Expose private String reposUrl;
    @SerializedName("type") @Expose private String type;
    @SerializedName("site_admin") @Expose private Boolean siteAdmin;
    @SerializedName("name") @Expose private String name;
    @SerializedName("company") @Expose private String company;
    @SerializedName("blog") @Expose private String blog;
    @SerializedName("location") @Expose private String location;
    @SerializedName("email") @Expose private String email;
    @SerializedName("hireable") @Expose private Boolean hireable;
    @SerializedName("bio") @Expose private String bio;
    @SerializedName("public_repos") @Expose private Integer publicRepos;
    @SerializedName("public_gists") @Expose private Integer publicGists;
    @SerializedName("followers") @Expose private Integer followers;
    @SerializedName("following") @Expose private Integer following;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("updated_at") @Expose private String updatedAt;
    @SerializedName("total_private_repos") @Expose private Integer totalPrivateRepos;
    @SerializedName("owned_private_repos") @Expose private Integer ownedPrivateRepos;
    @SerializedName("private_gists") @Expose private Integer privateGists;
    @SerializedName("disk_usage") @Expose private Integer diskUsage;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }

    public void setGistsUrl(String gistsUrl) {
        this.gistsUrl = gistsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(Boolean siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHireable() {
        return hireable;
    }

    public void setHireable(Boolean hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(Integer publicRepos) {
        this.publicRepos = publicRepos;
    }

    public Integer getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(Integer publicGists) {
        this.publicGists = publicGists;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTotalPrivateRepos() {
        return totalPrivateRepos;
    }

    public void setTotalPrivateRepos(Integer totalPrivateRepos) {
        this.totalPrivateRepos = totalPrivateRepos;
    }

    public Integer getOwnedPrivateRepos() {
        return ownedPrivateRepos;
    }

    public void setOwnedPrivateRepos(Integer ownedPrivateRepos) {
        this.ownedPrivateRepos = ownedPrivateRepos;
    }

    public Integer getPrivateGists() {
        return privateGists;
    }

    public void setPrivateGists(Integer privateGists) {
        this.privateGists = privateGists;
    }

    public Integer getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Integer diskUsage) {
        this.diskUsage = diskUsage;
    }

}