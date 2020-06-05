package br.com.alura.loja;

import br.com.alura.loja.dao.ProjetoDao;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
    public void testaProjetosId1() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/projetos/1").request().get(String.class);
        System.out.println(conteudo);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals("Minha loja", projeto.getNome());
    }

}
