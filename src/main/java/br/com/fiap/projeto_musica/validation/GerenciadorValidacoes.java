package br.com.fiap.projeto_musica.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorValidacoes {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> 
	gerirValidacoes(MethodArgumentNotValidException ex) {

		Map<String, String> erros = new HashMap<String, String>();
		
		try {
			//Tipo 1
			ex.getBindingResult().getFieldErrors().forEach(erro -> {
				erros.put(erro.getField(), erro.getDefaultMessage());
			});
			
			//Tipo 2
			/*for(FieldError fe : ex.getBindingResult().getFieldErrors()) {
				erros.put(fe.getField(), fe.getDefaultMessage());
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.badRequest().body(erros);
	}

}
