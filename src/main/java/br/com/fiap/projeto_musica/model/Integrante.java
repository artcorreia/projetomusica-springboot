package br.com.fiap.projeto_musica.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "integrante")
public class Integrante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fk_artista")
	private Artista artista;
	@NotEmpty(message = "O nome é um campo obrigatório")
	@Size(min = 2, max = 70, message = "O nome deve ter ao menos 2 caracteres "
			+ "e, no máximo, 70")
	private String nome;
	@PastOrPresent(message = "A data de nascimento deve ser a atual ou uma data passada")
	private LocalDate data_nascimento;
	@Enumerated(EnumType.STRING)
	private EnumFuncao funcao;

	public Integrante() {

	}

	public Integrante(Long id, Artista artista, String nome, LocalDate data_nascimento, EnumFuncao funcao) {
		super();
		this.id = id;
		this.artista = artista;
		this.nome = nome;
		this.data_nascimento = data_nascimento;
		this.funcao = funcao;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public EnumFuncao getFuncao() {
		return funcao;
	}

	public void setFuncao(EnumFuncao funcao) {
		this.funcao = funcao;
	}

}
