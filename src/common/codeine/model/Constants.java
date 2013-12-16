package codeine.model;

import java.io.File;

import codeine.utils.network.InetUtils;

public class Constants
{

	public static final boolean SECURITY_ENABLED = true;

	public static final String HTTP_ROOT_CONTEXT = "/http-root";
	
	public static final String PEER_LOG = "codeine_peer.log";
	public static final String DIRECTORY_LOG = "codeine_directory.log";
	public static final String SERVER_LOG = "codeine_server.log";
	
	public static final String INSTALLATION = ".";
	public static final String MONITORS_DIR = "/monitors";
	public static final String CONF_DIR = "/conf";
	public static final String PLUGINS_DIR = "/plugins";
	public static final String PLUGINS_OUTPUT_DIR = "/plugins_output";
	public static final String MONITOR_OUTPUT_CONTEXT = "/monitor-output";
	public static final String RAW_OUTPUT_CONTEXT = "/raw-output";
	public static final String PROGRESSIVE_RAW_OUTPUT_CONTEXT = "/progressive-raw-output";
	public static final String FILE_GETTER_CONTEXT = "/file-getter";
	public static final String PROJECT_FILES_CONTEXT = "/projects-files";
	
	public static final String NEW_PROJECT_CONTEXT = "/new-project";
	public static final String CONFIGURE_PROJECT_CONTEXT = "/configure-project";
	public static final String DELETE_PROJECT_CONTEXT = "/delete-project";
	public static final String PROJECT_NODES_CONTEXT = "/project-nodes";
	public static final String PROJECT_STATUS_CONTEXT = "/project-status";
	public static final String COMMANDS_LOG_CONTEXT = "/commands-log";
	public static final String COMMANDS_EXECUTER_STATUS = "/commands-status";
	public static final String RESTART_ALL_PEERS_CONTEXT = "/restart-all";
	public static final String PROJECTS_LIST_CONTEXT = "/projects";
	public static final String MANAGEMENT_CONTEXT = "/manage";
	public static final String PROJECT_PATH = "/project";
	public static final String NODE_PATH = "/node";
	public static final String COMMAND_NODE_CONTEXT = "/command-node";
	public static final String RELOAD_CONFIGURATION_CONTEXT = "/reload-configuration";
	public static final String REGISTER_PEER_IN_DIRECTORY_CONTEXT = "/register-in-directory";
	public static final String COMMAND_NODE_ALL_CONTEXT = "/command-node-all";
	public static final String COMMAND_NODES_CONTEXT = "/command-nodes";
	public static final String SCHEDULE_COMMAND_CONTEXT = "/schedule-command";
	public static final String LABELS_CONTEXT = "/labels";
	public static final String LABEL_CONTEXT = "/label-version";
	public static final String RESOURCESS_CONTEXT = "/resources";
	public static final String CONFIGURE_CONTEXT = "/configure";
	public static final String CONFIG_SUBMIT_CONTEXT = "/config-submit";
	public static final String PEER_STATUS_CONTEXT = "/peer/status";
	public static final String PEER_PROJECT_STATUS_CONTEXT = "/project/status";
	public static final String PROJECT_CONF_FILE = "project.conf.json";
	public static final String NODE_INFO_CONTEXT = "/node-info";
	
	public static final String INFO_CONTEXT = "/info";
	public static final String VERSION_INFO_CONTEXT = INFO_CONTEXT + "/version";
	public static final String VERSIONS_MAPPING_CONTEXT = "/versions-mapping";
	public static final String PEER_REPORT_CONTEXT = "/peer-report";
	public static final String RSYNC_SOURCE = "/version/rsync";
	public static final String COMMAND_NODE_IN_SERVER_CONTEXT = "/command-to-node";
	public static final String PEER_PORT_CONTEXT = "/peer/port";
	public static final String REGISTER_CONTEXT = "/register";

	public static final String REPLACE_NODE_NAME = "$node_name";	
	public static final String DB_NAME = "codeineDB";
	public static final int DB_PORT = 27017;
	
	public static final boolean IS_MAIL_STARTEGY_MONGO = true;
	
	public static final String NO_VERSION = "No version";
	public static final String ALL_VERSION = "All versions";
	public static final String VERSION = "version";

	public static final int ERROR_MONITOR = -1;

	public static final String COMMAND_RESULT = "codeine-command-result=";

	public static final String JSON_COMMAND_FILE_NAME = "/command.json";
	
	public static final String EXECUTION_ENV_PROJECT_NAME = "CODEINE_PROJECT_NAME";
	public static final String EXECUTION_ENV_OUTPUT_FILE = "CODEINE_OUTPUT_FILE";
	public static final String EXECUTION_ENV_NODE_NAME = "CODEINE_NODE_NAME";
	public static final String ENV_CODEINE_WORKAREA = "CODEINE_WORKAREA";
	public static final String ENV_CODEINE_HOST_WORKAREA = "CODEINE_HOST_WORKAREA";
	
	public static String installDir = null;
	
	public static String getConfDir() {
		return getWorkarea() + "/conf";
	}
	public static String getConfPath() {
		return getConfDir() + "/codeine.conf.json";
	}
	public static String getWebConfPath() {
		return getConfDir() + "/codeine.web.conf.json";
	}
	public static String getIdentityConfPath() {
		return getConfDir() + "/identity.json";
	}
	public static String getPermissionsConfPath() {
		return getConfDir() + "/permissions.json";
	}
	public static String getSpnegoPropertiesPath() {
		return getConfDir() + "/spnego.properties";
	}
	
	public static String getLogDir(){
		return System.getProperty("log.dir", getResourcesDir());
	}
	public static String getResourcesDir() {
		return Constants.getInstallDir() + HTTP_ROOT_CONTEXT;
	}
	
	public static String getInstallDir(){
		if (installDir != null)
		{
			return installDir;
		}
		installDir = System.getProperty("installDir");
		if (installDir == null)
		{
			// if install dir not set, get relative to cwd:
			String sJarPath = Constants.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			File jarFile = new File(sJarPath);
			installDir = jarFile.getParentFile().getParent();
		}
		System.out.println("Setting installDir to '" + installDir + "'");
		return installDir;
	}

	public static final String COMMAND_FINISH_FILE = "/finished";

	public static final String COMMAND_LOG_FILE = "/log";

	public static final int DEFAULT_WEB_SERVER_PORT = 12347;

	public static final String DISCOVERY_PLUGIN = "discovery";

	
	
	public static class UrlParameters {
		public static final String PROJECT_NAME = "project";
		public static final String VERSION_NAME = "version";
		public static final String NODE_NAME = "node-name";
		public static final String COMMAND_NAME = "command";
		public static final String PATH_NAME = "path";
		public static final String RESOURCE_NAME = "resource";
		public static final String LINK_NAME = "link";
		public static final String DATA_NAME = "data";
		public static final String REDIRECT = "redirect";
		public static final String COMMAND_ID = "command-id";
		public static final String DATA_ADDITIONAL_COMMAND_INFO_NAME = "more-command-info";
		
		
	}

	public static String getPersistentDir() {
		return getWorkarea() + "/persistent." + InetUtils.getLocalHost().getHostName();
	}
	public static String getWorkarea(){
		String workarea = System.getenv(ENV_CODEINE_WORKAREA);
		if (null == workarea){
			return getInstallDir();
		}
		return workarea;
	}
	public static String getHostWorkareaDir() {
//		return Constants.getInstallDir() + "/../..";
		String workarea = System.getenv(ENV_CODEINE_HOST_WORKAREA);
		if (null == workarea){
			return getPersistentDir();//assuming workarea is common somehow, so pid+port files will be separate for each host
		}
		return workarea;
	}

	public static String apiContext(String projectNodesContext) {
		return projectNodesContext + "_json";
	}
}