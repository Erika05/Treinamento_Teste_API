import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Musicas extends Helper {
    public static String idMusica;
    public static String nomeArtista = "Omnitica";
    String nomeMusica = "Dubwoofer Substep";
    PlayList playList = new PlayList();
    String nomeMusicaAdcionada = "Please Mister Postman - Remastered 2009";

    @BeforeClass
    public void buscaIdPlayList() {
        playList.retornaIdPlayList(nomePlayList);
    }

    @Test
    public void buscaMusica() {
        buscaMusicaRequest();
        if (response.statusCode() != 401) {
            // list = response.path("items.track.name");
            Assert.assertTrue(verificaPorNome(nomeMusica), "Música não encontrada.");
            Assert.assertEquals(response.statusCode(), 200);
        } else {
            System.out.println("Usuário não está logado!");
        }
    }

    @Test
    public void buscaArtista() {
        retornaIdMusica(nomeMusica);
        response = given()
                .accept("appication/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(URL + "/tracks/" + idMusica)
                .then()
                .extract()
                .response();
        if (response.statusCode() != 401) {
            Assert.assertEquals(response.statusCode(), 200);
            list = response.path("artists.name");
            Assert.assertTrue(verificaPorNome(nomeArtista));
        } else {
            System.out.println("Usuário não está logado");
        }
    }

    @Test
    public void adicionaMusicaPlayListCerificacaoCadastroJaExistente()//Validação com música já cadastrada
    {
        Response result;
        buscaMusicaRequest();
        if (!verificaPorNome(nomeMusicaAdcionada)) {
            result = adicionarMusicaResquest();
            if (result.statusCode() != 401) {
                Assert.assertEquals(result.statusCode(), 201);
                buscaMusicaRequest();
                Assert.assertTrue(verificaPorNome(nomeMusicaAdcionada));
            } else {
                System.out.println("Usuário não logado!");
            }
        } else {
            System.out.println("Música já cadastrada!");
        }
    }

    @Test
    public void adicionaMusicaPlayListVerificacaoPorQuantidade()//Validação verificando a quantidade de música
    {
        Response result;
        int qtdInicio;
        int qtdFinal;
        buscaMusicaRequest();
        qtdInicio = countMusicas(nomeMusicaAdcionada);
        result = adicionarMusicaResquest();
        if (result.statusCode() != 401) {
            Assert.assertEquals(result.statusCode(), 201);
            buscaMusicaRequest();
            qtdFinal = countMusicas(nomeMusicaAdcionada);
            Assert.assertEquals(qtdFinal, qtdInicio + 1);
        } else {
            System.out.println("Usuário não logado!");
        }
}

    public Response adicionarMusicaResquest() {
        Response result;
        result = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\"uris\":[\"spotify:track:6wfK1R6FoLpmUA9lk5ll4T\"]}")
                .when()
                .post(URL + "/playlists/" + idPlayList + "/tracks")
                .then()
                .extract()
                .response();
        return result;
    }

    public void buscaMusicaRequest() {
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

    public void retornaIdMusica(String nomeMusica) {
        buscaMusicaRequest();
        if (verificaPorNome(nomeMusica)) {
            idMusica = response.path("items[" + index + "].track.id");
        } else {
            System.out.println("Música não encontrada!");
        }
    }
}
