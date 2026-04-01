package br.com.fiap.projeto_musica.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "musica")
public class Musica extends RepresentationModel<Musica> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fk_artista")
	private Artista artista;
	@NotEmpty(message = "O título é um campo obrigatório")
	@Size(min = 1, max = 80, message = "O título deve ter ao menos 1 " + "caracter e, no máximo, 80 caracteres")
	private String titulo;
	@PastOrPresent(message = "A data de lançamento está inválida")
	private LocalDate data_lancamento;
	@DecimalMax("60.0")
	@DecimalMin("1.0")
	private Double duracao;
	@NotEmpty(message = "O gênero da música é um campo obrigatório")
	@Size(min = 3, max = 30, message = "O gênero da música deve possuir ao menos 1 "
			+ "caracter, no máximo, 30 caracteres")
	private String genero;

	public Musica() {

	}

	public Musica(Long id, Artista artista,
			@NotEmpty(message = "O título é um campo obrigatório") @Size(min = 1, max = 80, message = "O título deve ter ao menos 1 caracter e, no máximo, 80 caracteres") String titulo,
			@PastOrPresent(message = "A data de lançamento está inválida") LocalDate data_lancamento,
			@DecimalMax("60.0") @DecimalMin("1.0") Double duracao,
			@NotEmpty(message = "O gênero da música é um campo obrigatório") @Size(min = 3, max = 30, message = "O gênero da música deve possuir ao menos 1 caracter, no máximo, 30 caracteres") String genero) {
		super();
		this.id = id;
		this.artista = artista;
		this.titulo = titulo;
		this.data_lancamento = data_lancamento;
		this.duracao = duracao;
		this.genero = genero;
	}

	public void transferirMusica(Musica musica) {
		this.artista = musica.getArtista();
		this.titulo = musica.getTitulo();
		this.data_lancamento = musica.getData_lancamento();
		this.duracao = musica.getDuracao();
		this.genero = musica.getGenero();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
