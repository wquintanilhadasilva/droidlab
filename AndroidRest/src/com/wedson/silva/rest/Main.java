package com.wedson.silva.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.github.kevinsawicki.http.HttpRequest;

public class Main extends Activity{
	
	private ListView lista;
	
	private EditText texto;

	public String accessToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		this.lista = (ListView) findViewById(R.id.lista);
		
		this.texto = (EditText) findViewById(R.id.texto);
		
	}
	
	public void abrir(View view){
		
		startActivity(new Intent(this, TwitterActivity.class));
		
	}
	
	public void buscar(View view){
		
		String filtro = this.texto.getText().toString();
		
		new TwitterTask().execute(filtro);
			
	}
	
	class TwitterTask extends AsyncTask<String, Void, String[]>{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			
			dialog= new ProgressDialog(Main.this);
			
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
				
				String urlTwitter = "https://www.googleapis.com/books/v1/volumes?q=";
				
				String url = Uri.parse(urlTwitter + filtro).toString();
				
				String conteudo = HttpRequest.get(url).body();
				
				JSONObject jsonObject = new JSONObject(conteudo);
				
				JSONArray resultados = jsonObject.getJSONArray("items");
				
				
				if(resultados != null && resultados.length() > 0){
					
					//Processa os resultados num array de string para montar o adapter da listview
					String[] tweets = new String[resultados.length()];
					
					for(int i = 0; i < resultados.length(); i++){
						
						JSONObject tweet = resultados.getJSONObject(i);
						
						String usuario = tweet.getJSONObject("volumeInfo").getString("title");
						
						String texto = tweet.getJSONObject("volumeInfo").getString("publishedDate");
						
						tweets[i]= usuario + " - Data de Publicação: " + texto;
						
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
