import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import netscape.javascript.JSObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //consultar a API viaCEP

        //menu para o susuario informar o cep

        //gerar uma json com os endereços

        String cep = "";
        Scanner leitor = new Scanner(System.in);
        String endereco = "";
        String json = "";
        String objeto = "";
        List<String> lista = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while (!cep.equalsIgnoreCase("sair")) {

            try {
                System.out.println("Informe um CEP para consulta: ");
                cep = leitor.next();
                if (cep.equalsIgnoreCase("sair")) {
                    break;
                }

                endereco = "https://viacep.com.br/ws/" + cep + "/json/";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                json = response.body();
                System.out.println(endereco);
                System.out.println(json);
                lista.add(json);


            } catch (Exception e) {
                System.out.println("Não foi possível realizar a consulta." + e.getMessage());
            }

        }

        FileWriter arquivo = new FileWriter("cep.json");
        arquivo.write(lista.toString());
        arquivo.close();

        System.out.println("Consulta encerrada." +
                "Verifique o arquivo.");
    }
}
