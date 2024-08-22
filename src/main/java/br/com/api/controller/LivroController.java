package br.com.api.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.api.domain.Livro;
import br.com.api.domain.DTO.LivroDTO;
import br.com.api.exceptions.GeralException;
import br.com.api.service.LivroService;
import net.sf.jasperreports.engine.JRException;

@RestController
public class LivroController {

	private LivroService livroService = new LivroService();

	// POSTA UM LIVRO
	@PostMapping(value = "/livros")
	public ResponseEntity<?> postLivroController(@RequestBody Livro livro) {
		try {
			return ResponseEntity.ok(livroService.postLivroService(livro));
		} catch (GeralException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	// CONSULTA TODOS OS LIVROS
	@GetMapping(value = "/livros")
	public List<LivroDTO> getLivroController() {
		return this.livroService.getLivroService();
	}

	// CONSULTA LIVRO POR ID
	@GetMapping(value = "/livros/{id}")
	public ResponseEntity<?> getIdLivroController(@PathVariable Long id) throws GeralException {
		try {
			return ResponseEntity.ok(livroService.getIdLivroService(id));
		} catch (GeralException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	// ATUALIZA OS DADOS DOS LIVROS
	@PutMapping(value = "/livros/{id}")
	public ResponseEntity<?> updateLivroController(@PathVariable Long id, @RequestBody Livro livro) {
		try {
			return ResponseEntity.ok(livroService.updateLivroService(id, livro));
		} catch (GeralException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}

	// DELETA UM LIVRO E CAPA
	@DeleteMapping(value = "/livros/{id}")
	public String deleteLivroController(@PathVariable Long id) {
		try {
			return livroService.deleteLivroService(id);
		} catch (GeralException e) {
			return e.getMessage();
		}
	}

	// POSTA UMA CAPA DE LIVRO
	@PostMapping(value = "/livros/capa/{id}")
	public ResponseEntity<?> postCapaController(@RequestPart("capa") MultipartFile capa, @PathVariable Long id)
			throws IOException, GeralException {
		try {
			return ResponseEntity.ok(livroService.postCapaService(capa, id));
		} catch (GeralException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}

	// BUSCA UMA CAPA DE LIVRO POR ID
	@GetMapping(value = "/livros/capa/{id}")
	public ResponseEntity<?> getIdCapaController(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG)
					.body(livroService.getIdCapaService(id));
		} catch (GeralException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}

	// BUSCA RELATÓRIO DE LIVROS
	@GetMapping(value = "/relatorio")
	public ResponseEntity<?> getRelatorioController() throws GeralException, SQLException, JRException, IOException {
		byte[] retorno = livroService.getRelatorioService();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(retorno);
	}
}
