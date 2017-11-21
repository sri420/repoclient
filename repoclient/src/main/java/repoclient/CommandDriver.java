package repoclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class CommandDriver {

	public static void main(String[] args) throws Exception {

		String s = null;
		String groupId = "com.mkyong.web";
		String artifactId = "10thhproject";
		String gitAdd="git add ."; 
		String message="Initial";
		String user="bsridhar";
		
		
	/*	String remoteAdd="git remote add origin https://github.com/sri420/okok.git";
	 * http://bsridhar@localhost:7990/scm/home/9thproject.git
		String remotePush="git push -u origin master";*/
		
		/*
		 * Inputs required for the Program
		 * ===============================
		 * For Maven Project Creation from archetype
		 * -----------------------------------------
		 * groupId
		 * artifactId
		 * package
		 * version
		 * description
		 * 
		 * For Remote Bitbucket Reository Creation
		 * ---------------------------------------
		 * repository name ( 
		 * 	Ideally we can resuse artifactId  without prompting the user ,
		 *  Alternatively,  we can default to artifactId and have user override it if required )
		 * 
		 * 
		 * For Bitbucket authentication
		 * ----------------------------
		 * username
		 * password
		 * 
		 * This is only required for first time. After successful authentication,
		 * the username and base64 encoded password would be stored in the user's home directory.
		 * On subsequent runs, the values would be fetched from there and authenticated with bitbucket accordingly.
		 * 
		 * If authentication fails on subsequent runs, due to one or more reasons as below
		 * 	--Credentials have expired/unknown credentials
		 *  --Config file stored in user's home directory is not accessible / missing or
		 *  --Config File exists but username and/or password vales are either null or empty
		 *  --Some other unknown reason
		 *  
		 *  In those scenarios, the solution is  Renew the config credentials by,
		 *  -- Prompt the user for username and password and 
		 *  -- Authenticate with  bitbucket and 
		 *  -- On successful authentication write it to the config file accordingly.
		 *  
		 *  
		 * 
		 */
		
		
		if(ConfigFileManager.shouldPromptUserForCredentials()){
			System.out.println("Prompt User for Credentials");
			//Base64 encode and store them in user's home directory
			Properties prop=new Properties();
			prop.setProperty("username", "kiss");
			prop.put("password", "MjYxNTA2MjQzOTI3OhWGI9RPvwGhvtoQQwGZDVujVemE");
			prop.put("api_endpoint", "/rest/api/1.0/projects/HOME/repos");
			prop.put("host_port", "http://localhost:7990");
			ConfigFileManager.addPropsToConfigFile(prop);
		}else{
			System.out.println("Credentials are not required already setup");
			//Fetch the values from user's home directory
			System.out.println("username:" + ConfigFileManager.getValueForKey("username"));
		}
		
		
		System.exit(0);
		MyMain.createRepository(new RepoCreationRequest(artifactId, "git", true), MyMain.HOST_PORT + MyMain.API_ENDPOINT);

		String mvnGenerateStr = " mvn archetype:generate -DgroupId=" + groupId + " -DartifactId=" + artifactId
				+ " -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false";

		Process p1 = Runtime.getRuntime().exec(String.format("cmd.exe /c  %s & cd %s & git init & %s  & git commit -m %s & git remote add origin http://%s@localhost:7990/scm/home/%s.git & git checkout -B integration & git push -u origin integration", mvnGenerateStr,artifactId,gitAdd,message,user,artifactId));
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p1.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p1.getErrorStream()));

		// read the output from the command
		/*System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
		}*/

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}

	}
}
