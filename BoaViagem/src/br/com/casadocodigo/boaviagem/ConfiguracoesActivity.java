package br.com.casadocodigo.boaviagem;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfiguracoesActivity extends PreferenceActivity{
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferencias);
		
	}

}
