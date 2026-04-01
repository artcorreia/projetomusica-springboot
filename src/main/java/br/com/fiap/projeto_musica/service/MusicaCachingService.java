package br.com.fiap.projeto_musica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.projeto_musica.model.Musica;
import br.com.fiap.projeto_musica.projection.MusicaProjection;
import br.com.fiap.projeto_musica.repository.MusicaRepository;

@Service
public class MusicaCachingService {

	@Autowired
	private MusicaRepository repM;

	@Cacheable(value = "todasMusicas")
	public List<Musica> findAll() {
		return repM.findAll();
	}

	@Cacheable(value = "musicasPorID", key = "#id")
	public Optional<Musica> findById(Long id) {
		return repM.findById(id);
	}

	@Cacheable(value = "musicasPorPaginacao", key = "#pr")
	public Page<Musica> findAll(PageRequest pr) {
		return repM.findAll(pr);
	}

	@Cacheable(value = "retornarMusicasPorSubstring", key = "#substring")
	public List<MusicaProjection> retornarMusicasPorSubstring(String substring) {
		return repM.retornarMusicasPorSubstring(substring);
	}

	@Cacheable(value = "retornarMusicasPorDuracao", key = "#duracao")
	public List<Musica> retornarMusicasPorDuracao(Double duracao) {
		return repM.retornarMusicasPorDuracao(duracao);
	}
	
	@Cacheable(value = "retornarMusicasOrdenadasPorGenero", key = "#substring")
	public List<MusicaProjection> retornarMusicasOrdenadasPorGenero(String substring){
		return repM.retornarMusicasOrdenadasPorGenero(substring);
	}

	@CacheEvict(value = { "retornarMusicasPorDuracao", "retornarMusicasPorSubstring",
			"musicasPorPaginacao",
			"musicasPorID", "todasMusicas","retornarMusicasOrdenadasPorGenero"}, allEntries = true)
	public void removerCache() {
		System.out.println("Removendo arquivos de cache");
	}

}
