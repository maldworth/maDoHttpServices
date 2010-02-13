package com.maldworth.httpservices;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import com.maldworth.httpservices.exceptions.HttpServicesException;

public abstract class DefaultRequest
{	
	private List<? extends NameValuePair> _queryParams;
	private String _scheme;
	private String _host;
	private int _port;
	private String _path;
	private HttpMethod _httpMethod = HttpMethod.GET;
	
	private String _replace;

	/**
	 * @param Sets the HTTP Method that is used in the Communication. Default is HTTP_GET
	 */
	public final void setHttpMethod(HttpMethod methodUsed) {
		_httpMethod = methodUsed;
	}

	/**
	 * @return Returns the HTTP Method type that is going to be used in communication.
	 */
	public final HttpMethod getHttpMethod() {
		return _httpMethod;
	}

	public DefaultRequest(String scheme, String host, String path)
	{
		initialize(scheme, host, -1, path, null, null);
	}

	public DefaultRequest(String scheme, String host, int port, String path)
	{
		initialize(scheme, host, port, path, null, null);
	}

	private void initialize(String scheme, String host, int port, String path, List<? extends NameValuePair> queryParams, String fragment)
	{
		//TODO parse the host value and the path value, there should never be any = or & in them
		_scheme = scheme;
		_host = host;
		_port = port;
		_path = path;
		_queryParams = queryParams;
	}
	
	public void replaceQueryDelimiter(String replace)
	{
		_replace = replace;
	}
	
	/**
	 * ALERT: Only use this method if you are sure that you want to override the default scheme, host, port and path that you assigned in the constructor.
	 * @param <T>
	 * @param <V>
	 * @param response
	 * @param uri This Overwrites the scheme, host, port and path that was assigned in the constructor.
	 * @return
	 */
	public final <T extends DefaultResponseModelBase<V>,V> T execute(T response, URI uri)//TODO instead of throwing this error, I should return the T Response and set the error flag to what occurred
	{
		T resp = response;
		
		try{

			HttpClient httpClient = new DefaultHttpClient();

			HttpGet httpGet = new HttpGet(uri);
			HttpPost httpPost = new HttpPost(uri);		

			V responseEntity = null;

			if(_httpMethod == HttpMethod.GET)
				responseEntity = httpClient.execute(httpGet, resp.getRespHandler());
			else
				responseEntity = httpClient.execute(httpPost, resp.getRespHandler());

			if(responseEntity == null)
				System.out.println("in Execute(), the response type is null");

			resp.setUnParsedEntity(responseEntity);
			resp.parseEntityToObject(responseEntity);

			httpClient.getConnectionManager().shutdown(); //Deallocates all unneeded resource
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(HttpServicesException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} 
		return resp;
	}

	public final <T extends DefaultResponseModelBase<V>,V> T execute(T response, List<NameValuePair> qParams)//TODO instead of throwing this error, I should return the T Response and set the error flag to what occurred
	{
		_queryParams = qParams;

		T resp = response;

		try {
			HttpClient httpClient = new DefaultHttpClient();

			String tmpQueryParams = URLEncodedUtils.format(_queryParams, "UTF-8");

			if(_replace != null)
			{
				tmpQueryParams = tmpQueryParams.replace("&", _replace);
			}

			URI uri = URIUtils.createURI(_scheme, _host, _port, _path, 
					tmpQueryParams, null);//TODO Add some sort of switch, to choose the delimiter between params
			HttpGet httpGet = new HttpGet(uri);
			HttpPost httpPost = new HttpPost(uri);		

			V responseEntity = null;

			if(_httpMethod == HttpMethod.GET)
				responseEntity = httpClient.execute(httpGet, resp.getRespHandler());
			else
				responseEntity = httpClient.execute(httpPost, resp.getRespHandler());

			if(responseEntity == null)
				System.out.println("in Execute(), the response type is null");

			resp.setUnParsedEntity(responseEntity);
			resp.parseEntityToObject(responseEntity);

			httpClient.getConnectionManager().shutdown(); //Deallocates all unneeded resource
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(HttpServicesException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public final <T extends DefaultResponseModelBase<V>,V> T execute(T response, String query) throws ClientProtocolException, IOException//TODO instead of throwing this error, I should return the T Response and set the error flag to what occurred
	{
		//TODO make sure query isn't null or empty. Perhaps have a helper method to make sure it has http:// and all that in it.
		
		T resp = response;
		try {
			HttpClient httpClient = new DefaultHttpClient();

			HttpGet httpGet = new HttpGet(query);
			HttpPost httpPost = new HttpPost(query);	

			V responseEntity = null;

			if(_httpMethod == HttpMethod.GET)
				responseEntity = httpClient.execute(httpGet, resp.getRespHandler());
			else
				responseEntity = httpClient.execute(httpPost, resp.getRespHandler());

			if(responseEntity == null)
				System.out.println("in Execute(), the response type is null");

			resp.setUnParsedEntity(responseEntity);
			resp.parseEntityToObject(responseEntity);

			httpClient.getConnectionManager().shutdown(); //Deallocates all unneeded resource
		}  catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(HttpServicesException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
}
