package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("projetos")
public class ProjetoResource {
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Projeto busca(@PathParam("id") long id){
        Projeto projeto = new ProjetoDao().busca(id);
        return projeto;

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo){
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDao().adiciona(projeto);
        URI uri = URI.create("/projetos/"+ projeto.getId() );
        return Response.created(uri).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeProjeto(@PathParam("id") long id){
        new ProjetoDao().remove(id);
        return Response.ok().build();
    }
}
