package br.com.fiap.projeto_musica.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_musica.dto.MusicaDTO;
import br.com.fiap.projeto_musica.model.Musica;
import br.com.fiap.projeto_musica.projection.MusicaProjection;
import br.com.fiap.projeto_musica.repository.MusicaRepository;
import br.com.fiap.projeto_musica.service.MusicaCachingService;
import br.com.fiap.projeto_musica.service.MusicaPaginacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/musicas")
public class MusicaController {

	@Autowired
	private MusicaRepository repM;
	
	@Autowired
	private MusicaPaginacaoService paginacaoM;
	
	@Autowired
	private MusicaCachingService cacheM;
	
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<MusicaDTO>> paginar(
		@RequestParam(name = "page", defaultValue = "0")	Integer page,
		@RequestParam(name = "size", defaultValue = "2")	Integer size){	
		PageRequest pr = PageRequest.of(page,size);
		Page<MusicaDTO> musicas_dto_paginadas = paginacaoM.paginar(pr);
		
		musicas_dto_paginadas.forEach(dto -> {dto.add(linkTo(methodOn(MusicaController.class)
				.retornarTodasMusicas())
				.withRel("Gostaria de acessar a listagem de todas as músicas? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicaPorID(dto.getId()))
				.withRel("Gostaria de acessar a busca de músicas por ID? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstringCaching(null))
				.withRel("Gostaria de acessar a busca por substring otimizada por caching? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstring(null))
				.withRel("Gostaria de acessar a busca por substring? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracaoOtimizado(null))
				.withRel("Gostaria de acessar a busca por duração otimizada por caching? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracao(null))
				.withRel("Gostaria de acessar a busca por duração? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorData(null))
				.withRel("Gostaria de acessar a busca por data de lançamento? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDataOtimizado(null))
				.withRel("Gostaria de acessar a busca otimizada por data de lançamento? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).inserirMusica(null))
				.withRel("Gostaria de acessar o endpoint de inserção de novas músicas? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).atualizarMusica(dto.getId(), null))
				.withRel("Gostaria de atualizar a música (título: " + dto.getTitulo() + ")? Clique aqui!"));

		dto.add(linkTo(methodOn(MusicaController.class).removerMusica(dto.getId()))
				.withRel("Gostaria de remover a música (título: " + dto.getTitulo() + " )? Clique aqui!"));});
		
		return ResponseEntity.ok(musicas_dto_paginadas);
	}
	
	@GetMapping(value = "/todas")
	public List<Musica> retornarTodasMusicas() {	
		
		List<Musica> todas = cacheM.findAll();
		
		for(Musica musica : todas) {
			musica.add(linkTo(methodOn(MusicaController.class).paginar(null, null))
					.withRel("Gostaria de acessar a listagem de músicas paginadas? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicaPorID(musica.getId()))
					.withRel("Gostaria de acessar a busca de músicas por ID? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstringCaching(null))
					.withRel("Gostaria de acessar a busca por substring otimizada por caching? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstring(null))
					.withRel("Gostaria de acessar a busca por substring? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracaoOtimizado(null))
					.withRel("Gostaria de acessar a busca por duração otimizada por caching? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracao(null))
					.withRel("Gostaria de acessar a busca por duração? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorData(null))
					.withRel("Gostaria de acessar a busca por data de lançamento? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDataOtimizado(null))
					.withRel("Gostaria de acessar a busca otimizada por data de lançamento? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).inserirMusica(null))
					.withRel("Gostaria de acessar o endpoint de inserção de novas músicas? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).atualizarMusica(musica.getId(), null))
					.withRel("Gostaria de atualizar a música (título: " + musica.getTitulo() + ")? Clique aqui!"));

			musica.add(linkTo(methodOn(MusicaController.class).removerMusica(musica.getId()))
					.withRel("Gostaria de remover a música (título: " + musica.getTitulo() + " )? Clique aqui!"));
					
		}
		
		return todas;
	}

	@GetMapping(value = "/{id}")
	public Musica retornarMusicaPorID(@PathVariable Long id) {

		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			
			Musica musica = op.get();
			
			musica.add(linkTo(methodOn(MusicaController.class).paginar(null, null))
			.withRel("Gostaria de acessar a listagem de músicas paginadas? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarTodasMusicas())
			.withRel("Gostaria de acessar a listagem de todas as músicas? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstringCaching(null))
			.withRel("Gostaria de acessar a busca por substring otimizada por caching? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorSubstring(null))
			.withRel("Gostaria de acessar a busca por substring? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracaoOtimizado(null))
			.withRel("Gostaria de acessar a busca por duração otimizada por caching? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDuracao(null))
			.withRel("Gostaria de acessar a busca por duração? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorData(null))
			.withRel("Gostaria de acessar a busca por data de lançamento? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).retornarMusicasPorDataOtimizado(null))
			.withRel("Gostaria de acessar a busca otimizada por data de lançamento? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).inserirMusica(null))
			.withRel("Gostaria de acessar o endpoint de inserção de novas músicas? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).atualizarMusica(musica.getId(), null))
			.withRel("Gostaria de atualizar a música (título: " + musica.getTitulo() + ")? Clique aqui!"));
			
			musica.add(linkTo(methodOn(MusicaController.class).removerMusica(musica.getId()))
			.withRel("Gostaria de remover a música (título: " + musica.getTitulo() + " )? Clique aqui!"));
			
			return musica;
			
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping(value = "/genero_ordenado")
	public List<MusicaProjection> retornarMusicasOrdenadasPorGenero(String substring){
		return cacheM.retornarMusicasOrdenadasPorGenero(substring);
	}
	
	@GetMapping(value = "/substring_caching")
	public List<MusicaProjection> 
	retornarMusicasPorSubstringCaching(@RequestParam String substring){
		return cacheM.retornarMusicasPorSubstring(substring);
	}
	
	@GetMapping(value = "/substring")
	public List<MusicaProjection> 
	retornarMusicasPorSubstring(@RequestParam String substring){
		return repM.retornarMusicasPorSubstring(substring);
	}
	
	@GetMapping(value = "/duracao_otimizado")
	public List<Musica> retornarMusicasPorDuracaoOtimizado(@RequestParam Double x){
		return cacheM.retornarMusicasPorDuracao(x);
	}

	@GetMapping(value = "/duracao")
	public List<Musica> retornarMusicasPorDuracao(@RequestParam Double x) {

		List<Musica> todas = cacheM.findAll();
		List<Musica> validas = new ArrayList<Musica>();

		for (Musica musica : todas) {
			if (musica.getDuracao() >= x) {
				validas.add(musica);
			}
		}

		return validas;

	}
	
	@GetMapping(value = "/data_lancamento_otimizado")
	public List<MusicaProjection> retornarMusicasPorDataOtimizado(@RequestParam LocalDate x){
		return repM.retornarMusicasPorData(x);
	}

	@GetMapping(value = "/data_lancamento")
	public List<Musica> retornarMusicasPorData(@RequestParam LocalDate x) {

		List<Musica> todas = cacheM.findAll();
		List<Musica> validas = new ArrayList<Musica>();

		for (Musica musica : todas) {
			if (musica.getData_lancamento().isBefore(x) || musica.getData_lancamento().isEqual(x)) {
				validas.add(musica);
			}
		}

		return validas;

	}

	@PostMapping(value = "/inserir")
	public Musica inserirMusica(@RequestBody @Valid Musica musica) {
		repM.save(musica);
		cacheM.removerCache();
		return musica;
	}

	@DeleteMapping(value = "/{id}")
	public Musica removerMusica(@PathVariable Long id) {
		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			repM.delete(op.get());
			cacheM.removerCache();
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping(value = "/{id}")
	public Musica atualizarMusica(@PathVariable Long id, @RequestBody @Valid Musica musica) {
		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			Musica musica_banco = op.get();
			musica_banco.transferirMusica(musica);
			repM.save(musica_banco);
			cacheM.removerCache();
			return musica_banco;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}
