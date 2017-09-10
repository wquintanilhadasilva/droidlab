package com.wedson.silva.rest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

public class TwitterActivity extends Activity{
private ListView lista;
	
	private EditText texto;

	public String accessToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		this.lista = (ListView) findViewById(R.id.lista);
		
		this.texto = (EditText) findViewById(R.id.texto);
		
		new AutenticacaoTask().execute();
		
	}
	
	public void abrir(View view){
		
		startActivity(new Intent(this, Main.class));
		
	}
	
	public void buscar(View view){
		
		String filtro = this.texto.getText().toString();
		
		if(this.accessToken == null){
			
			Toast.makeText(this, "Token não disponível!", Toast.LENGTH_SHORT).show();
			
		}else{
			
			new TwitterTask().execute(filtro);
			
		}
		
	}
	
	
	/**
	 * Classe para obter o token de acesso ao Twitter...faz chamada remota ao twitter para isso
	 * @author Wedson
	 *
	 */
	class AutenticacaoTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			Map<String, String> data = new HashMap<>();
			
			data.put("grant_type", "client_credentials");
			
			try{
				
				String json = HttpRequest.post("https://api.twitter.com/oauth2/token")
						.authorization("Basic " + gerarChave())
						.form(data)
						.body();
				
				JSONObject token = new JSONObject(json);
				
				accessToken = token.getString("access_token");
			
			}catch(Exception e){
				
				return null;
				
			}
			
			return null;
		}

		private String gerarChave() throws UnsupportedEncodingException{
			
			String key = "wquintanilhadasilva@gmail.com";
			
			String secret = "nandinha";
			
			String  token = key + ":" + secret;
			
			String base64 = Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
			
			return base64;
			
		}
		
	}

	
	class TwitterTask extends AsyncTask<String, Void, String[]>{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			
			dialog= new ProgressDialog(TwitterActivity.this);
			
			dialog.setTitle(getString(R.string.aguarde));
			
			dialog.show();
			 
		}
		

		@Override
		protected String[] doInBackground(String... params) {
			
			try{
				
				String filtro = params[0];
				
				if(TextUtils.isEmpty(filtro)){
					
					return null;
					
				}
				
				String urlTwitter = "https://api.twitter.com./1.1/search/tweets.json?q=";
				
				String url = Uri.parse(urlTwitter + filtro).toString();
				
				String conteudo = HttpRequest.get(url)
										.authorization("Bearer " + accessToken)
										.body();
				
				JSONObject jsonObject = new JSONObject(conteudo);
				
				JSONArray resultados = jsonObject.getJSONArray("items");
				
				if(resultados != null && resultados.length() > 0){
					
					//Processa os resultados num array de string para montar o adapter da listview
					String[] tweets = new String[resultados.length()];
					
					for(int i = 0; i < resultados.length(); i++){
						
						JSONObject tweet = resultados.getJSONObject(i);
						
						String usuario = tweet.getJSONObject("user").getString("screen_name");
						
						String texto = tweet.getString("text");
						
						tweets[i]= usuario + " - " + texto;
						
					}
					
					return tweets;
				
				}
				
				return null;
				
			}catch(Exception e){
				
				Log.e(getPackageName(), e.getMessage(), e);
				
				throw new RuntimeException(e);
				
			}
			
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			
			if(result == null){
				
				result =  new String[]{"Nenhum resultado a exibir"};
				
			}
				
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
					android.R.layout.simple_list_item_1, result);
			
			lista.setAdapter(adapter);
				
			dialog.dismiss();
			
		}
		
	}

}
