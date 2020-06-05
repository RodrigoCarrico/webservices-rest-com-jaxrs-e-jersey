package br.com.alura.loja;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {
    public static void main(String [] args ) throws IOException {
        HttpServer server = initializeServer();
        System.out.println("Servidor rodando");
        System.in.read();
        server.stop();
    }

    public static HttpServer initializeServer() {
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        URI uri = URI.create("http://localhost:8080/");
        return GrizzlyHttpServerFactory.createHttpServer(uri, config);
    }
}

