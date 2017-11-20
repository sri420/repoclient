package repoclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
