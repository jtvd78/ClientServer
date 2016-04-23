package server.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.imageio.stream.FileImageOutputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import client.Server;
import client.User;
import server.ui.Console;

public  class ConnectionController{
	boolean running = true;
	
	int connectionCounter = 0;
	ArrayList<ServerConnection> connectionList = new ArrayList<ServerConnection>();

	
	public void init() {
		new Thread(new ConnectionListener()).start();
	}
	
	public class ConnectionListener implements Runnable{	
		public void run() {
			
			//SSLServerSocket ss = null;
			ServerSocket ss = null;
			
			try {
				
				/*
				char[] passphrase = "ThisIsThePassword".toCharArray();
				
				
				KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			    keyStore.load(this.getClass().getResourceAsStream("/keystore.jks"), passphrase);
				
			    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); //THIS
				
				
			    SSLServerSocketFactory sslFactory = null;
				
				
				
				
			    SSLContext ctx = SSLContext.getInstance("SSL");
			    ctx.init(null, tmf.getTrustManagers(), null);
			    sslFactory = ctx.getServerSocketFactory();
			    ss	= (SSLServerSocket) sslFactory.createServerSocket(res.Settings.port);
			    */
				ss	= new ServerSocket(client.Settings.port);
				
				
				
				
				
				
				
			//	String[] suite =  {"TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA"};
			//	ss.setEnabledCipherSuites(suite);
			//	String[] aaa = ss.getEnabledCipherSuites();
			//	
			//	for(int c = 0; c < aaa.length; c++){
			//		System.out.println(aaa[c]);
			//	}				
				
				while(running){
					Socket sock = ss.accept();
					
					connectionCounter++;
					
					ServerConnection c = new ServerConnection(sock,connectionCounter);
					c.start();
					connectionList.add(c);
				}
				
				ss.close();
				
			}catch (BindException e) {
				Console.out.println("There is already a program using this port (" + client.Settings.port + "). The server could not be started");
				e.printStackTrace(Console.out.getPrintStream());
			} catch (IOException e) {
				Console.out.println("There was an error when initiating the server.");
				e.printStackTrace(Console.out.getPrintStream());
			}
		}
		
		public void stop(){
			running = false;
		}
	}
}