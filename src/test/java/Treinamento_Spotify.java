import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Treinamento_Spotify {

    public static String token = "BQBnPpXV9f5reFoDevXI2KWP8GkpttcrbBMV5Baw1pZkVljSvidgKXgJFU8PPDWJw6AeDD8xRUUxIMWylQoMX2SYzjAH7mVguMlKwOy8LBsolCfSsiuTigJcDw7GMVh4jUSS0qz4fvBp6hfEUEN-IHCRfEeE87XSzaF-5366gBCz02tqoe4KpX2nXc_zjrXPcW-lmyIsEDgiLQM2rmXkOXdhvIPUXuf8EpAQuKcP_876OgpBw8uQ3nYODNTrv-HdTEJCRnbeMvvwdLnvaJVxG2GTcXuua5ZiXThWoA";
    public static String URL = "https://api.spotify.com/v1";
    public static Response response;
    public static String nomePlayList = "Teste Postman";
    public static ArrayList list;
    public static  String idPlayList;
    public static  String idMusica;
    public static  String nomeArtista = "Omnitica";
    String nomeMusica = "Dubwoofer Substep";
    int index;

    @Test
    public void buscarPlayList() {
        busPlayListRequest();
        if (response.statusCode() != 401) {
            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertTrue(verificaPorNome(nomePlayList));
        } else {
            System.out.println("Usuário não está logado!");
        }
    }

    @Test
    public void buscaMusica() {
        buscaMusicaRequest(nomePlayList);
        if (response.statusCode() != 401) {
           // list = response.path("items.track.name");
            Assert.assertTrue(verificaPorNome(nomeMusica), "Música não encontrada.");
            Assert.assertEquals(response.statusCode(), 200);
        } else {
            System.out.println("Usuário não está logado!");
        }
    }

    @Test
    public void buscaArtista()
    {
        retornaIdMusica(nomePlayList, nomeMusica);
        response = given()
                .accept("appication/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(URL + "/tracks/" + idMusica)
                .then()
                .extract()
                .response();
        if(response.statusCode() != 401) {
            Assert.assertEquals(response.statusCode(), 200);
            list = response.path("artists.name");
            Assert.assertTrue(verificaPorNome(nomeArtista));
        }
        else {
            System.out.println("Usuário não está logado");
        }
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

    public void busPlayListRequest() {
        response = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(URL + "/me/playlists")
                .then()
                .extract()
                .response();
        list = response.path("items.name");
    }

    public void buscaMusicaRequest(String nomePlayList)
    {
        retornaIdPlayList(nomePlayList);
        response =
                given()
                        .accept("application/json")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get(URL + "/playlists/" + idPlayList + "/tracks")
                        .then()
                        .extract()
                        .response();
        if (response.statusCode() == 200) {
            list = response.path("items.track.name");
        }
    }

    public void retornaIdPlayList(String nomePlayList) {
        busPlayListRequest();
        if (verificaPorNome(nomePlayList)) {

            idPlayList = response.path("items[" + index + "].id");
        } else {
            System.out.println("PlayList não encontrada");
        }
    }

    public void  retornaIdMusica(String nomePlayList, String nomeMusica)
    {
        buscaMusicaRequest(nomePlayList);
        if(verificaPorNome(nomeMusica)) {
            idMusica = response.path("items[" + index + "].track.id");
        }
        else {
            System.out.println("Música não encontrada!");
        }
    }
}
