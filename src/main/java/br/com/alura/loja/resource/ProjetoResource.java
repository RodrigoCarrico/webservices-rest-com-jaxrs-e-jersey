package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import org.glassfish.jersey.server.Uri;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("projetos")
public class ProjetoResource {
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(@PathParam("id") long id){
        Projeto projeto = new ProjetoDao().busca(id);
        return projeto.toXML();

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo){
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDao().adiciona(projeto);
        URI uri = URI.create("/projetos/"+ projeto.getId() );
        return Response.created(uri).build();
    }
}
