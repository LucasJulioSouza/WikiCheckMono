
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WikiChecker {

    public static void main(String[] args) {
        System.out.println("Executando de forma sequencial:");

        HttpClient httpClient = HttpClient.newHttpClient();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 50; i++) {
            String wikiPageUrl = "https://en.wikipedia.org/wiki/" + (i + 1);
            String pageStatus = getPageExistence(httpClient, wikiPageUrl);
            System.out.println(wikiPageUrl + " - " + pageStatus);
        }

        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;

        System.out.println("Tempo sequencial: " + elapsedTime + " segundos");
    }

    public static String getPageExistence(HttpClient httpClient, String wikiPageUrl) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(wikiPageUrl))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return "Existe";
            } else if (response.statusCode() == 404) {
                return "Não existe";
            } else {
                return "Desconhecido";
            }
        } catch (IOException | InterruptedException e) {
            return "Erro: " + e.getMessage();
        }
    }

}
