package com.weilai.service.utils.internet.impl;

import com.weilai.service.utils.internet.InternetPlugin;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HttpClientPlugin implements InternetPlugin {
	
	private final String host;
	private final Integer port;  // 默认80端口
	private final String charsetName;
	private CloseableHttpClient httpClient;
	private String cookie = "";
	
	public HttpClientPlugin() {
		this.host = null;
		this.charsetName = "UTF-8";
		this.port = 80;
	}

	public HttpClientPlugin(String host, Integer port, String charsetName) {
		this.host = host;
		if (port == null || port < 80)
			port = 80;
		this.port = port;
		this.charsetName = charsetName;
	}
	
	private CloseableHttpClient getHttpClient(){
		if(httpClient == null){
			RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		}
		return httpClient;
	}

	@Override
	public String getInternetLinkWay() {
		return "HttpClient";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String sendMessagePackets(String requestMethod, Object messagePackets) {
		Map<String, Object> message = (Map<String, Object>) messagePackets;
		String linkURL = (String) message.get("url");
		Map<String, String> hread = (Map<String, String>) message.get("hread");
		Object body =  message.get("body");
		Integer entityType = message.get("entityType") == null ? 0 : Integer.class.cast(message.get("entityType")); // 0 StringEntity(默认) 1 ByteArrayEntity
		if(linkURL == null){
			throw new RuntimeException("请求链接不能为空.");
		}
		Integer port = this.port;
		if (port == null || port <= 0) {
			port = 80;
		}
		StringBuilder httpURL = new StringBuilder();
		if (this.host != null) {
			if (host.toLowerCase().indexOf("http://") >= 0){
				httpURL.append(host);
				if (!"/".equals(httpURL.charAt(httpURL.length() - 1))) {
					httpURL.append("/");
				}
			} else {
				httpURL.append("http://");
				httpURL.append(host).append(":").append(port);
				httpURL.append("/");
			}
		}
		httpURL.append(linkURL);
		

		HttpRequestBase httpRequest = getHttpRequest(requestMethod);
		httpRequest.setURI(URI.create(httpURL.toString()));
		if(hread != null && !hread.isEmpty()){
			for (Entry<String, String> entry : hread.entrySet()) {
				httpRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(cookie != null){
			httpRequest.addHeader("Set-cookie", cookie);
		}
		if (body != null) {
			if(HttpEntityEnclosingRequestBase.class.isAssignableFrom(httpRequest.getClass())){
				HttpEntityEnclosingRequestBase requestBase = HttpEntityEnclosingRequestBase.class.cast(httpRequest);
				AbstractHttpEntity reqEntity = null;
				if (body instanceof String) {
					String param = (String) body;
					if (1 == entityType) {
						try {
							reqEntity = new ByteArrayEntity(param.getBytes("UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						reqEntity = new StringEntity(param, charsetName);
					}
					Header contentHeader = httpRequest.getFirstHeader("ContentType");
					if (contentHeader != null)
						reqEntity.setContentType(contentHeader.getValue());
					
				} else if (body instanceof Map) {
					Map<String, String> params = (Map<String, String>) body;
					List<NameValuePair> pairs = null;
					if (params != null && !params.isEmpty()) {
						pairs = new ArrayList<NameValuePair>(params.size());
						for (Entry<String, String> entry : params.entrySet()) {
							String value = entry.getValue();
							if (value != null) {
								pairs.add(new BasicNameValuePair(entry.getKey(), value));
							}
						}
					}
					try {
						reqEntity = new UrlEncodedFormEntity(pairs, charsetName);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				requestBase.setEntity(reqEntity);
				
			} else {
				throw new RuntimeException(String.format("HttpClient \"%s\"请求方法不支持Body报文方式，请将参数拼接到URL中.", httpRequest.getMethod()));
			}
		}
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient().execute(httpRequest);
			Header header = response.getFirstHeader("Set-cookie");
			if (header != null)
				cookie = header.getValue();
			if (response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				String result = null;
				if (entity != null) {
					result = EntityUtils.toString(entity, charsetName);
				}
				EntityUtils.consume(entity);
				return result;
			} else {				
				throw new RuntimeException(String.format("HttpClient, error status code : %s", response.getStatusLine().getStatusCode()));
			}
		} catch (Exception e) {
			throw new RuntimeException("请查看嵌套异常信息." + e.getMessage(),e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("关闭http请求出错",e);
				}
			}
			
			this.close();
		}
	}
	
	private HttpRequestBase getHttpRequest(String requestMethod){
		HttpRequestBase httpRequest = null;
		if ("POST".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpPost();
		} else if ("HEAD".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpHead();
		} else if ("PUT".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpPut();
		} else if ("TRACE".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpTrace();
		} else if ("DELETE".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpDelete();
		} else if ("PATCH".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpPatch();
		} else if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
			httpRequest = new HttpOptions();
		} else{
			httpRequest = new HttpGet();
		}
		return httpRequest;
	}

	@Override
	public void close() {
		try {
			httpClient.close();
		} catch (Exception e) {
			logger.error("关闭http客户端出错",e);
		}
	}
}
