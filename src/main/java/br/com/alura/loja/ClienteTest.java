package br.com.alura.loja;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteTest {
    private HttpServer server;

    @Before
    public void startServer(){
        server = Servidor.initializeServer();
        System.out.println("Server Up");
    }

    @After
    public void downServer(){
        server.stop();
        System.out.println("Server Down");
    }
    @Test
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/carrinhos/1").request().get(String.class);
        System.out.println(conteudo);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaAdicionarNoCarrinho(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");

        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Tablet"));
    }


    @Test
    public void testaProjetosId1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/projetos/1").request().get(String.class);
        System.out.println(conteudo);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals("Minha loja", projeto.getNome());
    }


    @Test
    public void testaAdicionarProjeto(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");

        Projeto projeto = new Projeto();
        projeto.setNome("Minha store");
        projeto.setAnoDeInicio(2020);
        new ProjetoDao().adiciona(projeto);
        String xml = projeto.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Minha store"));
    }

}
