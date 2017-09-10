package br.com.casadocodigo.boaviagem.domain;

import java.util.Date;

public class Viagem {
	
	private Long id;
	
	private String destino;
	
	private Date dataSaida;
	
	private Date dataChegada;
	
	private int tipoViagem;
	
	private Double orcamento;
	
	private int qtdePessoas;

	public Long getId() {
		return id;
	}

	public void setId(long id2) {
		this.id = id2;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public int getTipoViagem() {
		return tipoViagem;
	}

	public void setTipoViagem(int tipoViagem) {
		this.tipoViagem = tipoViagem;
	}

	public Double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}

	public int getQtdePessoas() {
		return qtdePessoas;
	}

	public void setQtdePessoas(int qtdePessoas) {
		this.qtdePessoas = qtdePessoas;
	}
	
	

}
