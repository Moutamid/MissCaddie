package com.moutamid.misscaddie.Notifications;

public class Data {
    private String user;
    private int icon;
    private String body;
    private String title;
    private String sent;
    private String groupId;
    private String type;
    private boolean isGroup = false;



    public Data(String type,String user, int icon, String body, String title, String sent){
        this.type = type;
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sent = sent;

    }

    public Data(String user, int icon, String body, String title, String sent, String groupId, boolean isGroup) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sent = sent;
        this.groupId = groupId;
        this.isGroup = isGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data(){

    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public int getIcon() { return icon; }

    public void setIcon(int icon) { this.icon = icon; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSent() { return sent; }

    public void setSent(String sent) { this.sent = sent; }
}
