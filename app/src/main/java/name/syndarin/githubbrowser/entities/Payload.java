
package name.syndarin.githubbrowser.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {

    @SerializedName("push_id")
    @Expose
    private int pushId;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("distinct_size")
    @Expose
    private int distinctSize;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("commits")
    @Expose
    private List<Commit> commits = null;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("ref_type")
    @Expose
    private String refType;
    @SerializedName("master_branch")
    @Expose
    private String masterBranch;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("pusher_type")
    @Expose
    private String pusherType;

    public int getPushId() {
        return pushId;
    }

    public void setPushId(int pushId) {
        this.pushId = pushId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(int distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }

}
