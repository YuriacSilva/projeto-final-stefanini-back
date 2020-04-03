package com.stefanini.resource;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.stefanini.dto.ErroDto;
import com.stefanini.dto.PessoaDto;
import com.stefanini.exception.NegocioException;
import com.stefanini.servico.PessoaServico;

@Path("pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

  private static Logger log = Logger.getLogger(PessoaResource.class.getName());

  /**
   * Classe de servico da Pessoa
   */
  @Inject
  private PessoaServico pessoaServico;
  /**
   *
   */
  @Context
  private UriInfo uriInfo;

  /**
   *
   * @return
   */
  @GET
  public Response obterPessoas() {
//		log.info("Obtendo lista de pessoas");
//		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//		Optional<List<Pessoa>> listPessoa = pessoaServico.getList();
//		return listPessoa.map(pessoas -> Response.ok(pessoas).build()).orElseGet(() -> Response.status(Status.NOT_FOUND).build());
    return pessoaServico.obterCompletas().map(pessoas -> Response.ok(pessoas).build())
        .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
  }

  /**
   *
   * @param pessoa
   * @return
   */
  @POST
  public Response adicionarPessoa(@Valid PessoaDto pessoaDto) {
    if (pessoaServico.validarPessoa(pessoaDto)) {
      return Response.ok(pessoaServico.salvar(pessoaDto)).build();
    }
    return Response.status(Status.METHOD_NOT_ALLOWED).
        entity(new ErroDto("email", "email já existe", pessoaDto.getEmail())).build();
  }

  /**
   *
   * @param pessoaDto
   * @return
   */
  @PUT
  public Response atualizarPessoa(@Valid PessoaDto pessoaDto) {
    if (pessoaServico.validarPessoa(pessoaDto)) {
      return Response.ok(pessoaServico.atualizar(pessoaDto)).build();
    }
    return Response.status(Status.METHOD_NOT_ALLOWED).
        entity(new ErroDto("email", "email já existe", pessoaDto.getEmail())).build();
  }

  /**
   *
   * @param id
   * @return
   */
  @DELETE
  @Path("{id}")
  public Response deletarPessoa(@PathParam("id") Long id) {
    try {
      if (pessoaServico.encontrar(id).isPresent()) {
        pessoaServico.remover(id);
        return Response.status(Response.Status.OK).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (NegocioException e) {
      return Response.status(Response.Status.METHOD_NOT_ALLOWED)
          .entity(new ErroDto(null, e.getMensagem(), id)).build();
    }
  }

  /**
   *
   * @param id
   * @return
   */
  @GET
  @Path("{id}")
  public Response obterPessoa(@PathParam("id") Long id) {
    return pessoaServico.encontrar(id).map(pessoas -> Response.ok(pessoas).build())
        .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
  }

  @GET
  @Path("/completas")
  public Response obterPessoa() {
    return pessoaServico.obterCompletas().map(pessoas -> Response.ok(pessoas).build())
        .orElseGet(() -> Response.status(Status.NOT_FOUND).build());
  }

  @GET
  @Path("/paginadas")
  public Response obterPessoa(
      @QueryParam("indexAtual") Integer indexAtual,
      @QueryParam("qtdPagina") Integer qtdPagina) {
    return Response.ok(pessoaServico.paginarPessoa(indexAtual, qtdPagina)).build();
  }

}
