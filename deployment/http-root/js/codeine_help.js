$(document).ready(function() {
  registerHelpElements($(document));
});

function registerHelpElements(elm) {
  elm.find('.codeine_help').each(function() {
    $(this).popover({
      content : getHelpHtml(this),
      html : true,
      placement : "bottom",
      trigger : "hover",
      delay : {
        hide : 500
      }
    });
  });
}

function getHelpHtml(elm) {
  var text = helpStrings[$(elm).data("help-message")];
  if (text === undefined) {
    console.warn("No help text for element " + elm.id);
    return "No help yet for id '" + $(elm).data("help-message") + 
    "' <a href='mailto:ohad.shai@intel.com?Subject=Please%20provide%20help%20for%20" + $(elm).data("help-message") 
    + "' target='_top'>Suggest a message</a>";
  }
  return text;
}

var helpStrings = {
  "howToRunHelp" : "Select a command execution strategy.<br/><br/><strong>'Immediately'</strong> - run command on nodes without delay.<br/><br/>"
      + "<strong>'Progressive'</strong> - run command on nodes gradually at configured rate.<br/><br/>",
  "monitorHelp" : "Select which nodes to show.<br/><br/><strong>'Any Alert'</strong> - show nodes with alerts (no matter which monitor).<br/><br/>"
      + "<strong>'All Nodes'</strong> - also show nodes without alerts.<br/><br/>"
      + "<strong>Specific Monitor</strong> - show only nodes with alerts from that monitor.",
  "commandNodesFilterHelp" : "Select the nodes on which the command will be executed on nodes of checked versiosns. <br/><br/><strong>'All Selected'</strong> - All the nodes in the selected versions.<br/><br/>"
      + "<strong>'Only Failing'</strong> - Only on nodes that have failing monitors.<br/><br/>"
      + "<strong>'Limited Number'</strong> - the command will be executed on this number of nodes from all selected versions (total).<br/><br/>",
      "ratioHelp" : "The rate for command execution.<br/><br/><strong>'Linear'</strong> - Spread nodes evenly in the specified time period.<br/><br/>"
        + "<strong>'Exponential'</strong> - execute on one node first, then increase number of nodes exponentially (1, 2, 4, ...).<br/><br/>",
  "command_patameter_description_Help" : "A description that will be displayed as a tooltip to the user executing the command.",
  "command_patameter_values_Help" : "Comma-delimited list of parameters for user selection. For example: <strong>1,2,3</strong>.",
  "command_patameter_name_Help" : "The name of the parameter, will also be used as environment variable in the executing command.",
  "command_patameter_default_val_Help" : "The default value that will be suggested to the user when executing the command.",
  "command_patameter_validation_expression_Help" : "A regular expression that will validate the parameter in the UI.<br/><br/>" + 
              "For example: <strong>\\S*</strong> - a word, <strong>\\d</strong> - one digit number, <strong>.*</strong> - not empty (like required).",
  "concurrencyHelp" : "On how many nodes to execute the command in parallel.",
  "stopOnErrorHelp" : "Check for errors during command execution, and stop commanding more nodes if the percent of nodes with failing monitors is greater than the configured value.",
  "durationHelp" : "The time period for the command execution. Codeine will spread execution on nodes across that time based on the ratio.",
  "preventOverrideHelp" : "If checked, when executing the command, the user will not be able to change the configuration below.",
  "commandHistoryHelp" : "All the currently running and finished commands of the selected project.",
  "commandExecutorHelp" : "All the currently running commands on the Codeine server from all projects.",
  "nodeTagsHelp" : "Tags on nodes. Press a tag to filter only nodes with that tag.",
  "emailHelp" : "Codeine will notify on failing monitors to configured users. "
      + "<br/><br/><strong>'Immediately'</strong> - send mail immediately after a monitor has failed."
      + "<br/><br/><strong>'Hourly'/'Daily'</strong> - send mail after a monitor has failed, Codeine will aggregate the notifications to send them at the specified frequency.",
  "nodesHelp" : "Configure how Codeine detects nodes for the project."
      + "<br/><br/><strong>'Configuration'</strong> - nodes are statically configured."
      + "<br/><br/><strong>'Reporter'</strong> - nodes are dynamically created when they report to Codeine via the Codeine API."
      + "<br/><br/><strong>'Script'</strong> - nodes are dynamically discovered by a script.",
  "discoveryScriptHelp" : "Type a shell script to discover nodes for the project. The script runs on each individual Codeine peer.<br/><br/>"
      + "The script output should be written to a file in a path specified in the environment variable <strong>CODEINE_OUTPUT_FILE</strong> in the following json format: <pre>[{name:\"&lt;name&gt;\", alias:\"&lt;alias&gt;\"}, ...]</pre> <br/>"
      + "The script can use the following environment variables in execution: <strong>CODEINE_PROJECT_NAME</strong>",
  "tagsdiscoveryScriptHelp" : "Type a shell script to discover tags for the project's nodes. The script runs on each individual Codeine peer.<br/><br/>"
    	  + "The script output should be written to a file in a path specified in the environment variable <strong>CODEINE_OUTPUT_FILE</strong> in the following json format: <pre>[\"&lt;tag-string&gt;\", ...]</pre> <br/>"
    	  + "The script can use the following environment variables in execution: <strong>CODEINE_PROJECT_NAME</strong>, <strong>CODEINE_NODE_NAME</strong>",
  "commandScriptHelp" : "Type a shell script to execute on nodes.<br/><br/>"
	  + "Parameters will be provided to the script as <strong>Environment Variables</strong>.<br/>"
      + "In addition, the script can use the following environment variables in execution: <strong>CODEINE_PROJECT_NAME</strong>, <strong>CODEINE_NODE_NAME</strong>",
  "monitorsConfigureHelp" : "Monitors used to detect malfunctions and errors on the running nodes.<br/><br/>"
      + "Codeine runs the monitors periodically on all nodes, and notifies when a node fails.",
  "versionHelp" : "A version will be reported by each node.",
  "minIntervalHelp" : "The minimal interval that Codeine will run the monitor.<br/><br/>"
      + "This option is good when the monitor execution itself is time consuming or may cause load on the node itself and hence should not executed at high frequency.<br/><br/>"
      + "Specify an <strong>integer in minutes</strong>. Default is less than a minute.",
  "credentialsHelp" : "The user that will be used to execute the configured script.<br/><br/> This feature does not work for most cases right now.",
  "notificationsEnabledHelp" : "If not checked, emails will not be sent to the configured users, even if this monitor fails.",
  "monitorScriptHelp" : "Type a shell script to monitor the desired functionality of the node.<br/><br/>By convention, monitors are marked as failed if their <strong>exit status is non-zero</strong>.<br/><br/>"
      + "The script can use the following environment variables in execution: <strong>CODEINE_PROJECT_NAME</strong>, <strong>CODEINE_NODE_NAME</strong>.",
  "versionScriptHelp" : "Type a shell script to specify the node's version.<br/><br/>"
      + "The script output should be written to a file in a path specified in the environment variable <strong>CODEINE_OUTPUT_FILE</strong> and should contain the version number (e.g.: 1.0.3).<br/><br/>"
      + "The script can use the following environment variables in execution: <strong>CODEINE_PROJECT_NAME</strong>, <strong>CODEINE_NODE_NAME</strong>.",
  "tabsConfigureHelp" : "Enables a separate view for a group of projects in the main page of Codeine.",
  "serverNameHelp" : "A name to be displayed when accessing UI or in mail messages",
  "webServerHostHelp" : "Full host name of the web server",
  "webServerPortHelp" : "Port of the web server (requires restart)",
  "directory_hostHelp" : "Full host name of the directory server",
  "directory_portHelp" : "Port of the directory server",
  "mysql_hostHelp" : "Full host name of the MySQL server",
  "MysqlPortConfigureHelp" : "Port of the MySQL server",
  "MysqlDirConfigureHelp" : "A directory for MySQL data",
  "MysqlBinDirConfigureHelp" : "The MySQL binaries directory (mysqld, ...)",
  "AdminMailConfigureHelp" : "The Codeine admin's email",
  "AuthenticationMethodConfigureHelp" : "The way Codeine will authenticate users.<br/><strong>Disabled</strong> - No authentication<br/><strong>Builtin</strong> - Codeine internal database will store users<br/><strong>WindowsCredentials</strong> - Using spnego to authenticate users<br/>",
  "rolesConfigureHelp" : "Internal domains for Windows credentials",
  "permissionsConfigureHelp" : "Configure access privileges for the users of Codeine.",
  "tabExpressionHelp" : "Comma-separated list of project names or regular expressions",
  "adminPermissionsConfigureHelp" : "Is administrator of Codeine",
  "viewProjectPermissionsConfigureHelp" : "List of projects (or regular expression) that the user can view only",
  "commandProjectPermissionsConfigureHelp" : "List of projects (or regular expression) for which the user can view and execute commands",
  "configProjectPermissionsConfigureHelp" : "List of projects (or regular expression) for which the user has full privileges",
  "MysqlUserConfigureHelp" : "Username for MySQL connections",
  "MysqlPasswordConfigureHelp" : "Password for MySQL connections",
  "MysqlManagedByCodeineConfigureHelp" : "Codeine is responsible to start/stop MySQL server instance.",
  "codeineConfigureHelp" : "Here the admin can configure all the Codeine global settings.<br/>Some changes might require a restart."
};
