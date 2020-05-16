import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.util.Base64;
import java.util.ArrayList;

public class Helper {
    public static String token;
    public static String URL = "https://api.spotify.com/v1";
    public static String nomePlayList = "Teste Postman";
    public static Response response;
    public static ArrayList list;
    public static String idPlayList;
    int index;
    public static String client_id = "";
    public static String client_security = "";
    public static String refreshToken = "";

    @BeforeSuite
    public void gerarToken() {
        String client_credendial = Base64.getEncoder().encodeToString((client_id + ":" + client_security).getBytes());
        response = given()
                .formParam("grant_type", "refresh_token")
                .formParam("refresh_token", refreshToken)
                .header("Authorization", "Basic " + client_credendial)
                .post("https://accounts.spotify.com/api/token")
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 200);
        token = response.path("access_token");
    }

    public Boolean verificaPorNome(String nome) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(nome)) {
                index = i;
                return true;
            }
        }
        return false;
    }

    public int countMusicas(String nome) {
        int qtdMusica = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(nome)) {
                qtdMusica++;
            }
        }
        return qtdMusica;
    }
}
