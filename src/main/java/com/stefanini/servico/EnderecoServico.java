package com.stefanini.servico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.Valid;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.model.Endereco;

/**
 * 
 * Classe de servico, as regras de negocio devem estar nessa classe
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EnderecoServico implements Serializable {
	
	@Inject
	private EnderecoDao dao;


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

	public Endereco salvar(@Valid Endereco entity) {
		return dao.salvar(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

	public Endereco atualizar(@Valid Endereco entity) {
		return dao.atualizar(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

	public void remover(Long id) {
	dao.remover(id);
	}


	public Optional<List<Endereco>> getList() {
		return dao.getList();
	}

	public Optional<Endereco> encontrar(Long id) {
		return dao.encontrar(id);
	}

	public Optional<String> buscarCep(String cep) {
		try {
  	  URL urlReq = new URL("https://viacep.com.br/ws/" + cep + "/json/");
          HttpURLConnection con = (HttpURLConnection) urlReq.openConnection();
          con.setRequestMethod("GET");
          BufferedReader in = new BufferedReader(
                  new InputStreamReader(con.getInputStream(), "UTF-8"));
          String inputLine;
          StringBuilder response = new StringBuilder();
          while ((inputLine = in.readLine()) != null) {
              response.append(inputLine);
          }
          in.close();
          con.disconnect();
          return Optional.ofNullable(response.toString());
		} catch (IOException e) {
		  return Optional.empty();
		}
	}
}
