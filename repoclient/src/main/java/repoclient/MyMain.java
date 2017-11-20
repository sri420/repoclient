package repoclient;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyMain {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final String API_ENDPOINT = "/rest/api/1.0/projects/HOME/repos";
	public static final String HOST_PORT = "http://localhost:7990";
	public static final String BEARER_TOKEN = "Bearer MjYxNTA2MjQzOTI3OhWGI9RPvwGhvtoQQwGZDVujVemE";
	public static final String AUTH_HEADER = "Authorization";
	public static final String SCMID = "git";

	public static void main(String[] args) throws IOException {

		if (args.length > 1) {
			System.out.println("Please Pass Only one Parameter Repo Name to Be Created");
			System.exit(0);
		}else if (args.length==0) {
			System.out.println("No Parameter Passed. Please Pass Only one Parameter Repo Name to Be Created");
			System.exit(0);
		}
		try {
			readConfigurations();
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			
			
			System.out.println(
					MyMain.createRepository(new RepoCreationRequest(args[0], SCMID, true), HOST_PORT + API_ENDPOINT));
			System.out.println(MyMain.executeRepoFetch(HOST_PORT + API_ENDPOINT));
		} catch (ConnectException ce) {
			System.out.println("Exception connecting to HOST:PORT:" + HOST_PORT);
		} catch (IOException ioe) {
			System.out.println("IO Exception connecting to :" + HOST_PORT);
		} catch (Exception e) {
			System.out.println("Exception connecting to :" + HOST_PORT);
		}*/
	}

	private static void readConfigurations() throws org.apache.commons.configuration2.ex.ConfigurationException {
		Configurations configs = new Configurations();
		try
		{
		    Configuration config = configs.properties(new File("repo.properties"));
		    System.out.println(config.getString("api_endpoint"));
		}
		catch (ConfigurationException cex)
		{
			System.out.println("Exception connecting to :" + cex);
		}
	}

	static String executeRepoFetch(String url) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().header(AUTH_HEADER, BEARER_TOKEN).url(url).build();

		Response response = client.newCall(request).execute();

		return toPrettyPrintString(response.body().string());
	}

	public static String createRepository(RepoCreationRequest repoCreateRequest, String url) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Gson gson = new Gson();
		String jsonInString = gson.toJson(repoCreateRequest);

		RequestBody body = RequestBody.create(JSON, jsonInString);

		Request request = new Request.Builder().header(AUTH_HEADER, BEARER_TOKEN).url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return toPrettyPrintString(response.body().string());
	}

	private static String toPrettyPrintString(String string) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(string);
		return gson.toJson(je);
	}
}
