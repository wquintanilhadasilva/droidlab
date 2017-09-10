package br.com.casadocodigo.boaviagem.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String BANCO_DADOS = "BoaViagem";
	
	private static final int VERSAO = 1;
	
	public DatabaseHelper(Context context){
		
		super(context,BANCO_DADOS, null, VERSAO);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("CREATE TABLE viagem ( ");
		sb.append("_id INTEGER PRIMARY KEY, ");
		sb.append("destino TEXT, tipo_viagem INTEGER, data_chegada DATE, ");
		sb.append("data_saida DATE, orcamento DOUBLE, quantidade_pessoas INTEGER);");
		
		db.execSQL(sb.toString());
		
		sb.delete(0, sb.toString().length());
		
		sb.append("CREATE TABLE gasto (");
		sb.append("_id INTEGER PRIMARY KEY, ");
		sb.append("categoria TEXT, data DATE, valor DOUBLE, ");
		sb.append("descricao TEXT, local TEXT, viagem_id INTEGER, ");
		sb.append("FOREIGN KEY (viagem_id) REFERENCES viagem(_id));");
		
		db.execSQL(sb.toString());
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
