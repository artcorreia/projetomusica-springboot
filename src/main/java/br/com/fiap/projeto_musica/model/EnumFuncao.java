package br.com.fiap.projeto_musica.model;

public enum EnumFuncao {
	
	VOCALISTA("Vocalista"), BATERISTA("Baterista"), GUITARRISTA("Guitarrista"), BAIXISTA("Baixista"), FLAUTISTA("Flautista");
	
	private String descricao;
	
	private EnumFuncao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
