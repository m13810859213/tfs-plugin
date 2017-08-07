package hudson.plugins.tfs;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.tfs.util.TeamRestClient;
import hudson.tasks.*;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 */
@SuppressWarnings("unused" /* Jenkins extension */)
public class SendCompletedEventPostBuildAction extends Notifier implements SimpleBuildStep {

    @DataBoundConstructor
    public SendCompletedEventPostBuildAction() {
    }

    @Override
    public void perform(
            @Nonnull final Run<?, ?> run,
            @Nonnull final FilePath workspace,
            @Nonnull final Launcher launcher,
            @Nonnull final TaskListener listener
    ) throws InterruptedException, IOException {

        // Check to see if there are any collections "connected" to this Jenkins server
        List<TeamCollectionConfiguration> connectedCollections = TeamCollectionConfiguration.getConnectedCollections();
        if (connectedCollections.size() > 0) {
            for(TeamCollectionConfiguration c: connectedCollections) {
                sendCompletedEvent(run, listener, c);
            }
        }
    }

    private void sendCompletedEvent(final @Nonnull Run<?, ?> run, final @Nonnull TaskListener listener, final @Nonnull TeamCollectionConfiguration collection) {
        try {
            final TeamRestClient client = new TeamRestClient(URI.create(collection.getCollectionUrl()));
            client.sendJobCompletionEvent(null, null);
        }
        catch (final IllegalArgumentException e) {
            listener.error(e.getMessage());
        }
        catch (final Exception e) {
            e.printStackTrace(listener.error("Error while trying to send completed event to " + collection.getCollectionUrl()));
        }
    }

    String getDisplayName() {
        final Descriptor<Builder> descriptor = getDescriptor();
        return descriptor.getDisplayName();
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        // we don't need the outcome of any previous builds for this step
        return BuildStepMonitor.NONE;
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return null;
            //return "Send build completed event to all connected TFS/Team Services servers.";
        }
    }
}
