package br.com.casadocodigo.boaviagem.domain;

import java.util.Date;

public class Gasto {
	
	private long id;
	
	private String tipo;
	
	private Date data;
	
	private Double valor;
	
	private String local;
	
	private String descricao;
	
	private long idViagem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getIdViagem() {
		return idViagem;
	}

	public void setIdViagem(long idViagem) {
		this.idViagem = idViagem;
	}
	
	

}
