package br.com.fiap.projeto_musica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.projeto_musica.dto.MusicaDTO;
import br.com.fiap.projeto_musica.model.Musica;
import br.com.fiap.projeto_musica.repository.MusicaRepository;

@Service
public class MusicaPaginacaoService {

	@Autowired
	private MusicaCachingService cacheM;

	@Transactional(readOnly = true)
	public Page<MusicaDTO> paginar(PageRequest req) {

		Page<Musica> paginadas_musicas = cacheM.findAll(req);

		Page<MusicaDTO> paginadas_musicas_dto 
		= paginadas_musicas.map(musica -> new MusicaDTO(musica));

		return paginadas_musicas_dto;

	}

}
