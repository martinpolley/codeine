package codeine;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import codeine.configuration.Links;
import codeine.configuration.PathHelper;
import codeine.db.mysql.MysqlHostSelector;
import codeine.executer.PeriodicExecuter;
import codeine.jsons.info.CodeineRuntimeInfo;
import codeine.model.Constants;
import codeine.nodes.NodesRunner;
import codeine.servlets.CodeinePeerServletModule;
import codeine.utils.FilesUtils;
import codeine.utils.TextFileUtils;
import codeine.utils.ThreadUtils;
import codeine.utils.network.InetUtils;

import com.google.common.collect.Lists;
import com.google.inject.Module;

public class CodeinePeerBootstrap extends AbstractCodeineBootstrap
{
	
	private final Logger log = Logger.getLogger(CodeinePeerBootstrap.class);
	private String hostname = InetUtils.getLocalHost().getHostName();	
	
	public static void main(String[] args)
	{
		boot(Component.PEER, CodeinePeerBootstrap.class);
	}

	@Override
	protected void execute() throws Exception
	{
		CodeineRuntimeInfo peerRuntimeInfo = injector().getInstance(CodeineRuntimeInfo.class);
		PathHelper pathHelper = injector().getInstance(PathHelper.class);
		startMysqlSelectorThread();
		log.info("creating local workarea: " + Constants.getHostWorkareaDir());
		FilesUtils.mkdirs(Constants.getHostWorkareaDir());
		log.info("writing pid " + peerRuntimeInfo.pid() + " to file " + pathHelper.getPidFile());
		TextFileUtils.setContents(pathHelper.getPidFile(), String.valueOf(peerRuntimeInfo.pid()));
		int port = peerRuntimeInfo.port();
		log.info("writing port " + port + " to file " + pathHelper.getPortFile());
		TextFileUtils.setContents(pathHelper.getPortFile(), String.valueOf(port));
		log.info("Hostname " + hostname);
		injector().getInstance(SnoozeKeeper.class).snoozeAll();
		startNodeMonitoringThreads();
		new PeriodicExecuter(ConfigurationGetter.INTERVAL, injector().getInstance(NodesRunner.class)).runInThread();
		log.info("starting PeerStatusChangedUpdater");
		ThreadUtils.createThread(injector().getInstance(PeerStatusChangedUpdater.class)).start();
	}

	private void startNodeMonitoringThreads() {
		new PeriodicExecuter(NodesRunner.NODE_RUNNER_INTERVAL, injector().getInstance(NodesRunner.class)).runInThread();
	}
	
	private void startMysqlSelectorThread() {
		new PeriodicExecuter(MysqlHostSelector.INTERVAL, injector().getInstance(MysqlHostSelector.class)).runInThread();
	}
	
	@Override
	protected void specificCreateFileServer(ContextHandlerCollection contexts)
	{
		PathHelper pathHelper = injector().getInstance(PathHelper.class);
		Links links = injector().getInstance(Links.class);
		addHandler(links.getNodeMonitorOutputContextPathAllProjects(), pathHelper.getMonitorOutputDirAllProjects(), contexts);
	}

	@Override
	protected List<Module> getGuiceModules() {
		return Lists.<Module>newArrayList(new CodeinePeerModule(), new CodeinePeerServletModule());
	}

	@Override
	public int getHttpPort() {
		return 0;
	}
	
}
