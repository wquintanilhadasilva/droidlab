package br.com.casadocodigo.boaviagem.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.casadocodigo.boaviagem.domain.Viagem;

public class ViagemAdapter extends BaseAdapter{
	
	private List<Viagem> viagens;
	
	private Context ctx;
	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public ViagemAdapter(Context context, List<Viagem> viagens){
		
		this.ctx = context;
		
		this.viagens = viagens;
		
	}

	@Override
	public int getCount() {
		return this.viagens.size();
	}

	@Override
	public Object getItem(int pos) {
		return this.viagens.get(pos);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		TextView tv = new TextView(this.ctx);
		
		Viagem v = this.viagens.get(position);
		
		String texto = v.getDestino() + " [" + sdf.format(v.getDataSaida()) + " a " + sdf.format(v.getDataChegada()) + "]";
		
		tv.setText(texto);
		
		tv.setTextColor(Color.BLACK);
		
		return tv;
		
	}

}
