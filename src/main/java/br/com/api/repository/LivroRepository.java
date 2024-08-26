package br.com.api.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.api.config.Datasource;
import br.com.api.domain.Livro;
import br.com.api.domain.DTO.CapaDTO;
import br.com.api.domain.DTO.LivroDTO;
import br.com.api.exceptions.GeralException;
import br.com.api.relatorio.convertJasperToPdf;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class LivroRepository {

	// SALVA OS LIVROS NO BANCO
	public Livro postLivroRepository(Livro livro) throws GeralException {
		try {
			Connection con = Datasource.getConnection();
			String sql = "INSERT INTO livro (id, nome, data_lancamento, autor, editora, numero_paginas) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			java.sql.Date sqldate = null;
			Integer numpags = 0;
			if (livro.getDatalancamento() != null) {
				sqldate = new java.sql.Date(livro.getDatalancamento().getTime() + 86400000);
			}
			if (livro.getNumpags() != null) {
				numpags = livro.getNumpags();
			}
			statement.setLong(1, livro.getId());
			statement.setString(2, livro.getNome());
			statement.setDate(3, sqldate);
			statement.setString(4, livro.getAutor());
			statement.setString(5, livro.getEditora());
			statement.setInt(6, numpags);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Novo livro inserido com sucesso");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return livro;
	}

	// BUSCA OS LIVROS E CAPA NO BANCO
	public List<LivroDTO> getLivroRepository() {
		try {
			List<LivroDTO> list = new ArrayList<>();
			Connection con = Datasource.getConnection();
			String sql = "SELECT * FROM livro l LEFT JOIN capa c ON l.id = c.id";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				LivroDTO livrodto = new LivroDTO();
				CapaDTO capadto = new CapaDTO();
				livrodto.setId(result.getLong("id"));
				livrodto.setNome(result.getString("nome"));
				livrodto.setDatalancamento(result.getDate("data_lancamento"));
				livrodto.setAutor(result.getString("autor"));
				livrodto.setEditora(result.getString("editora"));
				livrodto.setNumpags(result.getInt("numero_paginas"));
				capadto.setId(result.getLong("id"));
				capadto.setNomeArquivo(result.getString("nome_arquivo"));
				capadto.setExtensao(result.getString("extensao"));
				livrodto.setCapa(capadto);

				list.add(livrodto);
			}
			return list;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// BUSCA LIVRO NO BANCO POR ID
	public Livro getIdLivroRepository(Long id) {
		try {
			Connection con = Datasource.getConnection();
			String sql = "SELECT * FROM livro WHERE id = " + id;
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			Livro livro = new Livro();
			if (result.next()) {
				livro.setId(id);
				livro.setNome(result.getString("nome"));
				livro.setDatalancamento(result.getDate("data_lancamento"));
				livro.setAutor(result.getString("autor"));
				livro.setEditora(result.getString("editora"));
				livro.setNumpags(result.getInt("numero_paginas"));
			}
			return livro;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// ATUALIZA LIVRO NO BANCO
	public Livro updateLivroRepository(Long id, Livro livro) throws GeralException {
		try {
			Connection con = Datasource.getConnection();
			String sql = "UPDATE livro SET nome = ?, data_lancamento = ?, autor = ?, editora = ?, numero_paginas = ? WHERE id = "
					+ id;
			PreparedStatement statement = con.prepareStatement(sql);
			java.sql.Date sqldate = null;
			if (livro.getDatalancamento() != null) {
				sqldate = new java.sql.Date(livro.getDatalancamento().getTime() + 86400000);
			}
			statement.setString(1, livro.getNome());
			statement.setDate(2, sqldate);
			statement.setString(3, livro.getAutor());
			statement.setString(4, livro.getEditora());
			statement.setInt(5, livro.getNumpags());
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Livro atualizado com sucesso");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// DELETA LIVRO E CAPA NO BANCO
	public String deleteLivroRepository(Long id) throws GeralException {
		try {
			Connection con = Datasource.getConnection();
			String sql = "DELETE FROM livro WHERE id = " + id;
			PreparedStatement statement = con.prepareStatement(sql);
			int rowsDeleted = statement.executeUpdate();
			String sqlcapa = "DELETE FROM capa WHERE id = " + id;
			PreparedStatement statementcapa = con.prepareStatement(sqlcapa);
			int rowsDeletedCapa = statementcapa.executeUpdate();
			if (rowsDeleted > 0 || rowsDeletedCapa > 0) {
				return "Livro excluído com sucesso";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GeralException(ex.getMessage());
		}
		return "Não foi possível deletar o livro";
	}

	// SALVA CAPA NO BANCO
	public byte[] postCapaRepository(byte[] capa, String extensao, String nomeArquivo, Long id) {
		try {
			Connection con = Datasource.getConnection();
			String sql = "INSERT INTO capa (id, capa, extensao, nome_arquivo) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setLong(1, id);
			statement.setBytes(2, capa);
			statement.setString(3, extensao);
			statement.setString(4, nomeArquivo);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Nova capa inserida com sucesso");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return capa;
	}

	// BUSCA CAPA NO BANCO POR ID
	public CapaDTO getIdCapaRepository(Long id) {
		try {
			Connection con = Datasource.getConnection();
			String sql = "SELECT * FROM capa WHERE id =" + id;
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			CapaDTO capadto = new CapaDTO();
			if (result.next()) {
				capadto.setNomeArquivo(result.getString("nome_arquivo"));
				capadto.setExtensao(result.getString("extensao"));
			}
			return capadto;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// BUSCA CAPA NO BANCO POR ID (BYTE)
	public byte[] getIdCapaRepositoryByte(Long id) {
		try {
			Connection con = Datasource.getConnection();
			String sql = "SELECT * FROM capa WHERE id =" + id;
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) {
				return result.getBytes("capa");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// BUSCA RELATÓRIO DE LIVROS
	public byte[] getRelatorioRepository() throws SQLException, JRException, IOException {
		List<Livro> list = new ArrayList<Livro>();
		Connection con = Datasource.getConnection();
		String sql = "SELECT * FROM livro";
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			Livro livro = new Livro();
			livro.setId(result.getLong("id"));
			livro.setNome(result.getString("nome"));
			livro.setDatalancamento(result.getDate("data_lancamento"));
			livro.setAutor(result.getString("autor"));
			livro.setEditora(result.getString("editora"));
			livro.setNumpags(result.getInt("numero_paginas"));
			list.add(livro);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("CollectionBeanParam", new JRBeanCollectionDataSource(list));
		byte[] arquivo = convertJasperToPdf.convertToPdf(map, "relatorioLivro.jrxml");
		return arquivo;
	}
}