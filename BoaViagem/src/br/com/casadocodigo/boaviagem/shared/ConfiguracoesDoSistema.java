package br.com.casadocodigo.boaviagem.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ConfiguracoesDoSistema {
	
	public static SharedPreferences getPreferences(Context ctx){
		
		return PreferenceManager.getDefaultSharedPreferences(ctx);
		
	}

}
