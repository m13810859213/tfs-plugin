package hudson.plugins.tfs.model;

public class ConnectionParameters {
    private String teamCollectionUrl;
    private String connectionKey;
    private String connectionSignature;
    private boolean sendJobCompletionEvents;

    public ConnectionParameters() {
    }

    public String getConnectionKey() {
        return connectionKey;
    }

    public void setConnectionKey(String connectionKey) {
        this.connectionKey = connectionKey;
    }

    public String getConnectionSignature() {
        return connectionSignature;
    }

    public void setConnectionSignature(String connectionSignature) {
        this.connectionSignature = connectionSignature;
    }

    public String getTeamCollectionUrl() {
        return teamCollectionUrl;
    }

    public void setTeamCollectionUrl(String teamCollectionUrl) {
        this.teamCollectionUrl = teamCollectionUrl;
    }

    public boolean isSendJobCompletionEvents() {
        return sendJobCompletionEvents;
    }

    public void setSendJobCompletionEvents(boolean sendJobCompletionEvents) {
        this.sendJobCompletionEvents = sendJobCompletionEvents;
    }
}
