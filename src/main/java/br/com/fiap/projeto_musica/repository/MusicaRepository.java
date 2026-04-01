package br.com.fiap.projeto_musica.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.projeto_musica.model.Musica;
import br.com.fiap.projeto_musica.projection.MusicaProjection;

public interface MusicaRepository extends JpaRepository<Musica,Long>{

	@Query("from Musica mus where mus.duracao >= :duracao_param")
	public List<Musica> retornarMusicasPorDuracao(Double duracao_param);
	
	@Query(nativeQuery = true, 
	value = "select distinct mus.titulo musica_titulo, art.nome artista_nome,"
			+ " mus.duracao musica_duracao, mus.genero musica_genero"
			+ " from musica mus "
			+ "inner join artista art on (mus.fk_artista = art.id) "
		    + "inner join integrante inte on (inte.fk_artista = art.id) "
		    + "where (mus.titulo ilike concat('%',:substring,'%')) "
		    + "or (upper(art.nome) like upper(concat('%',:substring,'%')))"
		    + " or (inte.nome ilike concat('%',:substring,'%')) "
		    + "order by art.nome asc")
	public List<MusicaProjection> retornarMusicasPorSubstring(String substring);
	
	@Query(nativeQuery = true,
		   value = "select distinct mus.titulo musica_titulo, art.nome artista_nome, "
		   		 + "mus.duracao musica_duracao, mus.genero musica_genero "
		   		 + "from musica mus inner join artista art on (mus.fk_artista = art.id) "
		   		 + "where mus.data_lancamento <= :data_param "
		   		 + " order by mus.titulo asc")
	public List<MusicaProjection> retornarMusicasPorData(LocalDate data_param);
	
	
	@Query(nativeQuery = true, 
			value = "select distinct mus.titulo musica_titulo, art.nome artista_nome, "
				  + "mus.duracao musica_duracao, mus.genero musica_genero "
				  + "from musica mus inner join artista art on (mus.fk_artista = art.id) "
				  + "where mus.genero ilike concat('%',:substring,'%') "
				  + "order by mus.duracao desc")
	public List<MusicaProjection> retornarMusicasOrdenadasPorGenero(String substring);
	
	
}
