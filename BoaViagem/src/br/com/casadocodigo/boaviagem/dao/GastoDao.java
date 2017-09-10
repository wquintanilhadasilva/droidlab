package br.com.casadocodigo.boaviagem.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.casadocodigo.boaviagem.bd.DatabaseHelper;
import br.com.casadocodigo.boaviagem.domain.Gasto;

public class GastoDao {

	private DatabaseHelper helper;
	
	private final static String TABELA = "gasto";
	
	private final static String ID = "_id";
	
	private final static String CATEGORIA = "categoria";
	
	private final static String DATA = "data";
			
	private final static String VALOR = "valor";
	
	private final static String DESCRICAO = "descricao";
	
	private final static String LOCAL = "local";
	
	private final static String VIAGEM_ID = "viagem_id";
	
	private final static String SELECT_LIST = ID + "," + CATEGORIA + "," + 
												DATA + "," + VALOR + "," + 
												DESCRICAO + "," + LOCAL + "," + 
												VIAGEM_ID;
	
	public GastoDao(Context ctx){
		
		this.helper = new DatabaseHelper(ctx);
		
	}
	
	public Gasto inserir(Gasto gasto){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		ContentValues content = new ContentValues();
		
		content.put(CATEGORIA, gasto.getTipo());
		
		content.put(DATA, gasto.getData().getTime());
		
		content.put(DESCRICAO, gasto.getDescricao());
		
		content.put(LOCAL, gasto.getLocal());
		
		content.put(VALOR, gasto.getValor());
		
		content.put(VIAGEM_ID, gasto.getIdViagem());
		
		long id = db.insert(TABELA, null, content);
		
		db.close();
		
		gasto.setId(id);
		
		return gasto;
	}
	
	public void remove(int id){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		String[] where = new String[] { String.valueOf(id) };
		
		db.delete(TABELA, "_id = ?", where);
		
		db.close();
		
	}
	
	public void removeViagem(int idViagem){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		String[] where = new String[] { String.valueOf(idViagem) };
		
		db.delete(TABELA, VIAGEM_ID + " = ?", where);
		
		db.close();
	
	}
	
	public List<Gasto> listarGastos(String idViagem){
		
		List<Gasto> retorno = new ArrayList<>();
		
		SQLiteDatabase db = this.helper.getReadableDatabase();
		
		String[] where = new String[] { idViagem };
		
		Cursor cursor = db.rawQuery("SELECT " + SELECT_LIST + 
				" FROM " + TABELA + " WHERE _id = ? ORDER BY " 
				+ DATA + " DESC", where);
		
		cursor.moveToFirst();
		
		Gasto gasto;
		
		for(int i = 0; i < cursor.getCount(); i++){
			
			gasto = new Gasto();
			
			gasto.setData(new Date(cursor.getLong(2)));
			gasto.setDescricao(cursor.getString(4));
			gasto.setId(cursor.getLong(0));
			gasto.setIdViagem(cursor.getLong(6));
			gasto.setLocal(cursor.getString(5));
			gasto.setTipo(cursor.getString(1));
			gasto.setValor(cursor.getDouble(3));
			
			retorno.add(gasto);
			
			cursor.moveToNext();
			
		}
		
		cursor.close();
		
		return retorno;
		
	}
	
	public void finalize(){
		
		this.helper.close();
		
	}
	
}
