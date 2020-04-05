package com.stefanini.resource;

import java.util.List;
import java.util.Optional;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.stefanini.dto.ErroDto;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;
import com.stefanini.servico.PerfilServico;

@Path("perfils")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {

    private static Logger log = Logger.getLogger(PerfilResource.class.getName());


    /**
     * Classe de servico da Pessoa
     */
    @Inject
    private PerfilServico perfilServico;
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
    public Response obterPerfils() {
        log.info("Obtendo lista de perfils");
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Optional<List<Perfil>> listPessoa = perfilServico.getList();
        return listPessoa.map(perfils -> Response.ok(perfils).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     *
     * @param perfil
     * @return
     */
    @POST
    public Response adicionarPerfil(@Valid Perfil perfil) {
        log.info("Adicionando perfils");
        if(perfilServico.validarPerfil(perfil)){
            return Response.ok(perfilServico.salvar(perfil)).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(new ErroDto("nome","nome do perfil já existe", perfil.getNome())).build();
    }


    /**
     *
     * @param perfil
     * @return
     */
    @PUT
    public Response atualizarPerfil(@Valid Perfil perfil) {
        log.info("Atualizando perfil");
        return Response.ok(perfilServico.atualizar(perfil)).build();
    }


    /**
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("{id}")
    public Response deletarPerfil(@PathParam("id") Long id) {
        log.info("Deletando perfil");
        try{
            if(perfilServico.encontrar(id).isPresent()){
                perfilServico.remover(id);
                return Response.status(Response.Status.OK).build();
            }else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NegocioException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(new ErroDto(null,e.getMensagem(),id)).build();
        }

    }


    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    public Response obterPerfil(@PathParam("id") Long id) {
        return perfilServico.encontrar(id).map(perfil -> Response.ok(perfil).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    @Path("/paginados")
    public Response obterPerfil(
        @QueryParam("indexAtual") Integer indexAtual,
        @QueryParam("qtdPagina") Integer qtdPagina) {
      return Response.ok(perfilServico.paginarPerfil(indexAtual, qtdPagina)).build();
    }

}
