package br.com.casadocodigo.boaviagem;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContatosActivity extends Activity{
	
	private ListView layout;
	
	private EditText txtFiltro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.contatos);
		
		this.txtFiltro = (EditText) findViewById(R.id.txtFiltro);
		
		this.layout = (ListView) findViewById(R.id.contatos);
		
		this.layout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				TextView t = (TextView) view;
				
				Toast.makeText(ContatosActivity.this, t.getText(), Toast.LENGTH_SHORT).show();
				
			}
			
		});
		
		this.processaContatos();
		
	}
	
	public void processaContatosOnClick(View view){
		
		this.processaContatos();
		
	}
	
	private void processaContatos(){
		
		String filtro = this.txtFiltro.getText().toString();
		
		CarregarContatosTask task = new CarregarContatosTask();
		
		task.execute(filtro);
		
	}
	
	class CarregarContatosTask extends AsyncTask<String, Void, List<String>>{
		
		ProgressDialog dialog;
		
		private ContentResolver contentResolver;
		
		private Uri uri;
		
		private ArrayList<String> dados;
		
		private Cursor obtemContatos(String[] selectionArgs){
			
			String projection[] = new String[]{ ContactsContract.Contacts.DISPLAY_NAME, 
						ContactsContract.Contacts.CONTACT_STATUS };

			String selection = null;
			
			String sortOrder = Contacts.DISPLAY_NAME + " ASC";
			
			Cursor cursor = this.contentResolver.query(this.uri, projection,
							selection, selectionArgs, sortOrder);
			
			return cursor;
			
		}
		
		@Override
		protected void onPreExecute() {
			
			this.contentResolver = getContentResolver();
			
			this.dialog = new ProgressDialog(ContatosActivity.this);
			
			this.dialog.setMessage(getString(R.string.Aguarde));
			
			this.dialog.show();
			
		}

		@Override
		protected List<String> doInBackground(String... params) {
			
			String filtro[] = null;
			
			if(params != null && !params[0].isEmpty()){
				
				this.uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, params[0]);
				
			}else{
				
				//busca sem filtro
				this.uri = Contacts.CONTENT_URI;
				
			}
			
			Cursor cursor = this.obtemContatos(filtro);
			
			this.dados = new ArrayList<>();
			
			if(cursor.getCount() > 0){
				
				cursor.moveToFirst();
				
				for(int count = 0; count < cursor.getCount(); count++){
					
					this.dados.add(cursor.getString(0));
					
					cursor.moveToNext();
					
				}
				
			}
			
			cursor.close();
			
			return this.dados;
		}
		
		@Override
		protected void onPostExecute(List<String> result) {
			
			ArrayAdapter<String> d = new ArrayAdapter<>(getBaseContext(), 
							android.R.layout.simple_list_item_1, result);
			
			layout.setAdapter(d);
			
			dialog.dismiss();
		}
		
	}
}

