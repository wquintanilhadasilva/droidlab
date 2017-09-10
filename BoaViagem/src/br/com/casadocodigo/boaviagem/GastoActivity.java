package br.com.casadocodigo.boaviagem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.casadocodigo.boaviagem.adapters.ViagemAdapter;
import br.com.casadocodigo.boaviagem.bd.Constantes;
import br.com.casadocodigo.boaviagem.dao.GastoDao;
import br.com.casadocodigo.boaviagem.dao.ViagemDao;
import br.com.casadocodigo.boaviagem.domain.Gasto;
import br.com.casadocodigo.boaviagem.domain.Viagem;
import br.com.casadocodigo.boaviagem.shared.ConfiguracoesDoSistema;

public class GastoActivity extends Activity{
	
	private final String MODO_VIAGEM = "modo_viagem";
	
	private int ano, mes, dia;
	
	private Spinner spinnerCategoria;
	
	private Spinner spinnerViagem;
	
	private TextView txtViagem;

	private Button btnDataGasto;
	
	private EditText txtValor;
	
	private EditText txtDescricao;
	
	private EditText txtLocal;
	
	private Gasto gasto;
	
	private GastoDao dao;
	
	private ViagemDao daoViagem;
	
	private CharSequence[] lista_categoria;
	
	SimpleDateFormat sdf;
	
	private List<Viagem> viagens;
	
	private OnDateSetListener dataGastoSelecionada = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int ano, int mes, int dia) {
			
			ajustarData(ano, mes, dia);
			
		}
		
	};
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gasto);
		
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		this.dao = new GastoDao(this);
		
		this.gasto = new Gasto();
		
		this.daoViagem = new ViagemDao(this);
		
		this.txtViagem = (TextView) findViewById(R.id.gasto_txt_viagem);
		
		this.btnDataGasto = (Button) findViewById(R.id.gasto_btnData);
		
		this.txtDescricao = (EditText) findViewById(R.id.gasto_txtDescricao);
		
		this.txtLocal = (EditText) findViewById(R.id.gasto_txtLocal);
		
		this.txtValor = (EditText) findViewById(R.id.gasto_txtValor);
		
		Calendar c = Calendar.getInstance();
		
		this.ajustarData(c.get(Calendar.YEAR), 
						c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));
		
		ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(this,
				R.array.categoria_gasto, android.R.layout.simple_spinner_item);
		
		this.spinnerCategoria = (Spinner) findViewById(R.id.gasto_spn_categoria);
		
		this.spinnerCategoria.setAdapter(adapterCategoria);
		
		this.lista_categoria = getResources().getTextArray(R.array.categoria_gasto);
		spinnerCategoria.setSelection(0);
		
		this.spinnerViagem = (Spinner) findViewById(R.id.gasto_spn_viagem);
		
		String idViagem = getIntent().getStringExtra(Constantes.VIAGEM_ID);
		
		//Se não veio da listagem, então verifica se está no modo viagem
		SharedPreferences pref = ConfiguracoesDoSistema.getPreferences(this);
		
		boolean modoViagem = pref.getBoolean(MODO_VIAGEM, false);
		
		Viagem v = null;
		
		if(idViagem != null && !idViagem.isEmpty()){
			
			this.gasto.setIdViagem(Long.valueOf(idViagem));

		}else{
			
			if(modoViagem){
				
				v = this.daoViagem.obterViagemAtiva();
				
				if(v != null){
					
					this.gasto.setIdViagem(v.getId());
					
				}
				
			}
			
		}
		
		if(this.gasto.getIdViagem() != 0){
			
			if(!modoViagem || v == null){
				
				v = this.daoViagem.consultar(gasto.getIdViagem());
				
			}
			
			this.txtViagem.setText(v.getDestino());
			
			this.txtViagem.setVisibility(View.VISIBLE);
			
			this.spinnerViagem.setVisibility(View.GONE);
			
		}else{
			
			this.spinnerViagem.setAdapter(new ViagemAdapter(this, this.listarViagens()));
			
			this.spinnerViagem.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

					Viagem v = (Viagem) parent.getItemAtPosition(pos);
					
					gasto.setIdViagem(v.getId());
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {}
				
			});
			
			this.spinnerCategoria.setVisibility(View.VISIBLE);
			
			this.txtViagem.setVisibility(View.GONE);
			
		}
		
	}
	
	private List<Viagem> listarViagens() {
		
		this.viagens = this.daoViagem.listarViagens();
		
		return this.viagens;
			
	}

	@SuppressWarnings("deprecation")
	public void selecionarData(View view){
		
		//Abre o diálogo de data
		showDialog(view.getId());
		
	}
	
	public void gravarNovoGasto(View view){
		
		this.gasto.setDescricao(this.txtDescricao.getText().toString());
		
		this.gasto.setLocal(this.txtLocal.getText().toString());
		
		this.gasto.setValor(Double.valueOf(this.txtValor.getText().toString()));
		
		this.gasto.setTipo(this.spinnerCategoria.getSelectedItem().toString());
		
		this.dao.inserir(this.gasto);
		
		finish();
	
	}
	
	@Override
	public Dialog onCreateDialog(int id){
		
		if(id == R.id.gasto_btnData){
			
			return new DatePickerDialog(this, dataGastoSelecionada, ano, mes, dia);
			
		}else{
			
			return null;
			
		}
	}
	
	private void ajustarData(int ano, int mes, int dia){
		
		this.ano = ano;
		
		this.mes = mes;
		
		this.dia = dia;
		
		Calendar data = Calendar.getInstance();
		
		data.set(Calendar.YEAR, ano);
		
		data.set(Calendar.MONTH, mes);
		
		data.set(Calendar.DAY_OF_MONTH, dia);
		
		btnDataGasto.setText(this.sdf.format(data.getTime()));
		
		this.gasto.setData(data.getTime());
		
	}
	
	@Override
	protected void onDestroy() {
		
		this.dao.finalize();
		
		this.daoViagem.finalize();
		
		super.onDestroy();
		
	}
	
}
