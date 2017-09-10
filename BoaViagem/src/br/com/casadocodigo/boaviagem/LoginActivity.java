package br.com.casadocodigo.boaviagem; 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import br.com.casadocodigo.boaviagem.shared.ConfiguracoesDoSistema;


public class LoginActivity extends Activity{
	
	private final String MANTER_LOGADO = "manter_conectado";
	
	private EditText login;
	
	private EditText senha;
	
	private CheckBox manterLogado;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		
		setContentView(R.layout.login);
		
		this.login = (EditText) findViewById(R.id.login_txtUsuario);
		
		this.senha = (EditText) findViewById(R.id.login_txtSenha);
		
		this.manterLogado = (CheckBox) findViewById(R.id.login_chkManterConectado);
		
		SharedPreferences preferencias = this.getPreferences();
		
		boolean manterConectado = preferencias.getBoolean(MANTER_LOGADO, false);
		
		if(manterConectado){
			
			startActivity(new Intent(this, DashboardActivity.class));
			
		}
	}
	
	@SuppressLint("DefaultLocale")
	public void entrarOnClick(View v){
		
		String usuario = "" + this.login.getText().toString();
		
		String senha = "" + this.senha.getText().toString();
		
		if("leitor".equals(usuario.toLowerCase()) && "123".equals(senha.toLowerCase())){
			
			Intent dashboard = new Intent(this, DashboardActivity.class);
			
			SharedPreferences preferences = this.getPreferences();
			
			Editor editor = preferences.edit();
			
			editor.putBoolean(MANTER_LOGADO, manterLogado.isChecked());
			
			editor.commit();
			
			startActivity(dashboard);
			
		}else{
			
			String msgErro = getString(R.string.login_msg_erro_autenticacao);
			
			Toast toast = Toast.makeText(this, msgErro, Toast.LENGTH_LONG);
			
			toast.show();
		}
		
	}
	
	private SharedPreferences getPreferences(){
		
		return ConfiguracoesDoSistema.getPreferences(this); // PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
	}

}
