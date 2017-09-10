package br.com.casadocodigo.boaviagem.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.casadocodigo.boaviagem.bd.DatabaseHelper;
import br.com.casadocodigo.boaviagem.domain.Viagem;

public class ViagemDao {
	
	private final static String TABELA = "viagem";
	
	private final static String ID = "_id";
	
	private final static String TIPO_VIAGEM = "tipo_viagem";
			
	private final static String DESTINO = "destino";
	
	private final static String DATA_CHEGADA = "data_chegada";
	
	private final static String DATA_SAIDA = "data_saida";
	
	private final static String ORCAMENTO = "orcamento";
	
	private final static String QUANTIDADE_PESSOAS = "quantidade_pessoas";
	
	private final static String SELECT_LIST = ID + "," + TIPO_VIAGEM + "," + 
						DESTINO + "," + DATA_CHEGADA + "," + 
						DATA_SAIDA + "," + ORCAMENTO + "," + 
						QUANTIDADE_PESSOAS;

	private DatabaseHelper helper;

	public ViagemDao(Context ctx){
		
		this.helper = new DatabaseHelper(ctx);
		
	}
	
	public Viagem inserir(Viagem viagem){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		ContentValues values = this.convertViagemToContentValue(viagem);
		
		long id = db.insert(TABELA, null, values);
		
		viagem.setId(id);
		
		return viagem;
		
	}
	
	public void atualizar(Viagem viagem){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		ContentValues values = this.convertViagemToContentValue(viagem);
		
		db.update("viagem", values, "_id = ?", new String[]{ viagem.getId().toString() });
		
	}
	
	public void remove(Long id){
		
		String[] where = new String[] { id.toString() };
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		db.delete(TABELA, "_id = ?", where);
		
	}
	
	public Viagem consultar(Long id){
		
		SQLiteDatabase db = this.helper.getReadableDatabase();
		
		String groupBy = null;
		
		String having = null;
		
		String orderBy = null;
		
		Viagem retorno = null;
		
		Cursor cursor = db.query(TABELA, SELECT_LIST.split(","), "_id = ?", new String[] { id.toString() }, groupBy, having, orderBy);
		
		if(cursor.getCount() > 0){
			
			cursor.moveToFirst();
			
			retorno = convertCursorToViagem(cursor);
			
		}
		
		db.close();
		
		return retorno;
		
	}
	
	public Viagem obterViagemAtiva(){
		
		Date dataAtual = new Date();
		
		SQLiteDatabase db = this.helper.getReadableDatabase();
		
		String d = String.valueOf(dataAtual.getTime());
		
		String[] selectionArgs = new String[]{ d, d};
		
		Viagem retorno = null;
		
		Cursor cursor = db.rawQuery("SELECT " + SELECT_LIST + " FROM " + TABELA + " WHERE " + DATA_SAIDA + " <= ? " +
					" AND " + DATA_CHEGADA + " >= ?", selectionArgs);
		
		if(cursor.getCount() > 0){
			
			cursor.moveToFirst();
			
			retorno = convertCursorToViagem(cursor);
			
		}
		
		db.close();
		
		return retorno;
		
		
	}
	
	public List<Viagem> listarViagens(){
		
		String[] selectList = SELECT_LIST.split(",");
		
		String groupBy = null;
		
		String having = null;
		
		String orderBy = DATA_SAIDA + " DESC";
		
		String selection = null;
		
		String[] selectionArgs = null;
		
		List<Viagem> retorno = new ArrayList<>();
		
		SQLiteDatabase db = this.helper.getReadableDatabase();
		
		Cursor cursor = db.query(TABELA, selectList, selection, selectionArgs, groupBy, having, orderBy);
		
		cursor.moveToFirst();
		
		Viagem v;
		
		for(int i = 0; i < cursor.getCount(); i++){
			
			v = convertCursorToViagem(cursor);
			
			retorno.add(v);
			
			cursor.moveToNext();
			
		}
		
		cursor.close();
		
		return retorno;
		
	}
	
	private Viagem convertCursorToViagem(Cursor cursor){
		
		Viagem v = new Viagem();
		
		v.setDataChegada(new Date(cursor.getLong(3)));
		
		v.setDataSaida(new Date(cursor.getLong(4)));
		
		v.setDestino(cursor.getString(2));
		
		v.setId(cursor.getLong(0));
		
		v.setOrcamento(cursor.getDouble(5));
		
		v.setQtdePessoas(cursor.getInt(6));
		
		v.setTipoViagem(cursor.getInt(1));
		
		return v;
	}
	
	private ContentValues convertViagemToContentValue(Viagem viagem){
		
		ContentValues values = new ContentValues();
		
		values.put(TIPO_VIAGEM, viagem.getTipoViagem());
		
		values.put(DESTINO, viagem.getDestino());
		
		values.put(DATA_CHEGADA, viagem.getDataChegada().getTime());
		
		values.put(DATA_SAIDA, viagem.getDataSaida().getTime());
		
		values.put(ORCAMENTO, viagem.getOrcamento());
		
		values.put(QUANTIDADE_PESSOAS, viagem.getQtdePessoas());
		
		return values;
		
	}
	
	public void finalize(){
		
		this.helper.close();
		
	}
	
}
