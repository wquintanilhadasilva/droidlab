package br.com.chart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.TextView;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class WebServerActivity extends Activity {
	private static final int PORT = 8765;
	private TextView hello;
	private MyHTTPD server;
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.webserver);

		hello = (TextView) findViewById(R.id.hello);

	}

	@Override
	protected void onResume() {

		super.onResume();

		TextView textIpaddr = (TextView) findViewById(R.id.ipaddr);

		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

		int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

		final String formatedIpAddress = String.format("%d.%d.%d.%d",
				(ipAddress & 0xff), (ipAddress >> 8 & 0xff),
				(ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

		textIpaddr.setText("Please access! http://" + formatedIpAddress + ":"
				+ PORT);

		try {

			server = new MyHTTPD();

			server.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {

		super.onPause();

		if (server != null)
			server.stop();
	}
	
	private void sendMessage(String numero, String msg){
		
		if(numero == null || numero.equals("") || msg == null || msg.equals("")) return;
		
		SmsManager smsManager = SmsManager.getDefault();
		
		smsManager.sendMultipartTextMessage(numero, null, smsManager.divideMessage(msg), null, null);
		
	}

	private class MyHTTPD extends NanoHTTPD {

		public MyHTTPD() throws IOException {
			super(PORT);
		}

		@Override
		public Response serve(IHTTPSession arg0) {

			Map<String, String> header = arg0.getHeaders();

			final StringBuilder buf = new StringBuilder();

			for (Entry<String, String> kv : header.entrySet())
				buf.append(kv.getKey() + " : " + kv.getValue() + "\n");

			buf.append("Parâmetros:");

			for (Entry<String, String> kv : arg0.getParms().entrySet())
				buf.append(kv.getKey() + " : " + kv.getValue() + "\n");

			handler.post(new Runnable() {
				@Override
				public void run() {
					hello.setText(buf);
				}
			});
			
			if(arg0.getParms() != null && arg0.getParms().size() > 0){
				String numero = arg0.getParms().get("textinput");
				
				String msg = arg0.getParms().get("textarea");
				
				sendMessage(numero, msg);
			}

			final String html = this.obtemConteudoHTML();

			return new NanoHTTPD.Response(Status.OK, MIME_HTML, html);

		}

		private String obtemConteudoHTML(){
			
			AssetManager assetManager = getResources().getAssets();
			
			InputStream inputStream;
			
			StringBuilder linhas_do_arquivo = new StringBuilder();
			
			try {
				
				inputStream = assetManager.open("formulario.html");
				        
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
				String recebe_string;
				
				while ( (recebe_string = bufferedReader.readLine()) != null ) {
					
				linhas_do_arquivo.append(recebe_string);
				    
				}
			
			inputStream.close();
			
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			return linhas_do_arquivo.toString();
			
		}
		
	}
	
}