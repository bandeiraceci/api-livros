package br.com.api.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.api.domain.Capa;
import br.com.api.domain.Livro;
import br.com.api.domain.DTO.LivroDTO;
import br.com.api.exceptions.GeralException;
import br.com.api.repository.LivroRepository;
import net.sf.jasperreports.engine.JRException;

public class LivroService {

	private LivroRepository livrorepository = new LivroRepository();

	// CRIA LIVRO NO BANCO DE DADOS
	public Livro postLivroService(Livro livro) throws GeralException {
		Livro buscado = livrorepository.getIdLivroRepository(livro.getId());
		if (buscado.getId() != null) {
			throw new GeralException("Livro já cadastrado");
		}
		return livrorepository.postLivroRepository(livro);
	}

	// RETORNA A LISTA DE LIVROS
	public List<LivroDTO> getLivroService() {
		return livrorepository.getLivroRepository();
	}

	// RETORNA LISTA POR ID
	public Livro getIdLivroService(Long id) throws GeralException {
		Livro buscado = livrorepository.getIdLivroRepository(id);
		if (buscado.getId() == null) {
			throw new GeralException("Livro não encontrado");
		}
		return livrorepository.getIdLivroRepository(id);
	}

	// ATUALIZA LIVRO NO BANCO DE DADOS
	public Livro updateLivroService(Long id, Livro livro) throws GeralException {
		Livro buscado = livrorepository.getIdLivroRepository(id);
		if (buscado.getId() == null) {
			throw new GeralException("Livro não encontrado para atualização");
		}
		return livrorepository.updateLivroRepository(id, livro);
	}

	// DELETA LIVRO E CAPA NO BANCO DE DADOS
	public String deleteLivroService(Long id) throws GeralException {
		Livro buscado = livrorepository.getIdLivroRepository(id);
		if (buscado.getId() == null) {
			throw new GeralException("Livro não encontrado para deleção");
		}
		return livrorepository.deleteLivroRepository(id);
	}

	// CRIA CAPA NO BANCO DE DADOS
	public byte[] postCapaService(MultipartFile capa, Long id) throws GeralException, IOException {

		byte[] conteudo = capa.getBytes();
		String extensao = capa.getContentType().substring(6);
		String nomeArquivo = capa.getOriginalFilename();

		Livro buscado = livrorepository.getIdLivroRepository(id);
		Capa buscada = livrorepository.getIdCapaRepository(id);
		if (buscado.getId() == null) {
			throw new GeralException("Livro não encontrado para inserção de capa");
		} else if (buscada.getCapa() != null) {
			throw new GeralException("Capa já cadastrada");
		}
		return livrorepository.postCapaRepository(conteudo, extensao, nomeArquivo, id);
	}

	// BUSCA RELATÓRIO DE LIVROS
	public byte[] getRelatorioService() throws GeralException, SQLException, JRException, IOException {
		return livrorepository.getRelatorioRepository();
	}
}