package com.weilai.service.utils.internet.impl;


import com.weilai.service.utils.internet.InternetPlugin;
import java.io.*;
import java.net.Socket;

public class SocketClientPlugin implements InternetPlugin {
	
	private final String host;
	private final Integer port;
	private final String charsetName;
	private Socket socket;
	
	public SocketClientPlugin(String host, Integer port, String charsetName) {
		this.host = host;
		this.port = port;
		this.charsetName = charsetName;
	}
	
	private Socket getSocket() throws Exception{
		if(socket == null || !socket.isClosed()){
			socket = new Socket(host, port);
		}
		return socket;
	}

	@Override
	public String getInternetLinkWay() {
		return "Socket";
	}

	@Override
	public String sendMessagePackets(String requestMethod, Object messagePackets) {
		Socket socket = null;
		try {
			socket = getSocket();
			OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream(), charsetName);
			PrintWriter writer = new PrintWriter(os, true);
			writer.print(messagePackets);
			os.flush();   //发送
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charsetName));
			String temp = in.readLine();
			while (temp != null) {
				stringBuilder.append(temp);
				temp = in.readLine();
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			throw new RuntimeException("请查看嵌套异常详细信息.",e);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void close() {
		if (socket == null || !socket.isClosed()) {
			try {
				socket.close();
			} catch (Exception e) {
				logger.error("关闭socket错误",e);
			}
		}
	}
}
