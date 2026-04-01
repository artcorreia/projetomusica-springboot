package br.com.fiap.projeto_musica.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import br.com.fiap.projeto_musica.model.Artista;
import br.com.fiap.projeto_musica.model.Musica;

public class MusicaDTO extends RepresentationModel<MusicaDTO> {

	private Long id;
	private Artista artista;
	private String titulo;
	private LocalDate data_lancamento;
	private Double duracao;
	private String genero;

	public MusicaDTO() {

	}

	public MusicaDTO(Artista artista, String titulo, LocalDate data_lancamento, Double duracao, String genero) {
		super();
		this.artista = artista;
		this.titulo = titulo;
		this.data_lancamento = data_lancamento;
		this.duracao = duracao;
		this.genero = genero;
	}

	public MusicaDTO(Musica musica) {
		this.id = musica.getId();
		this.artista = musica.getArtista();
		this.titulo = musica.getTitulo();
		this.data_lancamento = musica.getData_lancamento();
		this.duracao = musica.getDuracao();
		this.genero = musica.getGenero();
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getData_lancamento() {
		return data_lancamento;
	}

	public void setData_lancamento(LocalDate data_lancamento) {
		this.data_lancamento = data_lancamento;
	}

	public Double getDuracao() {
		return duracao;
	}

	public void setDuracao(Double duracao) {
		this.duracao = duracao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
