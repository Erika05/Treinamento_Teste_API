import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Musicas extends Helper {
    public static String idMusica;
    public static String nomeArtista = "Omnitica";
    String nomeMusica = "Dubwoofer Substep";
    PlayList playList = new PlayList();
    String nomeMusicaAdcionada = "Please Mister Postman - Remastered 2009";
    String idMusicaAdicionar = "spotify:track:6wfK1R6FoLpmUA9lk5ll4T";
    String idMusicaDelete = "spotify:track:6wfK1R6FoLpmUA9lk5ll4T";

    @BeforeClass
    public void login() {
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
            throw new SkipException("Usuário não logado");
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
            result = adicionarMusicaResquest(idMusicaAdicionar);
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
    @Ignore
    public void adicionaMusicaPlayListVerificacaoPorQuantidade()//Validação verificando a quantidade de música
    {
        Response result;
        int qtdInicio;
        int qtdFinal;
        buscaMusicaRequest();
        qtdInicio = countMusicas(nomeMusicaAdcionada);
        result = adicionarMusicaResquest(idMusicaAdicionar);
        if (result.statusCode() != 401) {
            Assert.assertEquals(result.statusCode(), 201);
            buscaMusicaRequest();
            qtdFinal = countMusicas(nomeMusicaAdcionada);
            Assert.assertEquals(qtdFinal, qtdInicio + 1);
        } else {
            System.out.println("Usuário não logado!");
        }
    }

    @Test
    @Ignore
    public void adicionarMusicaArquivoJson() {
        File json = new File("src/arquivosJson/adicionarMusica.json");
        response = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(json)
                .when()
                .post(URL + "/playlists/" + idPlayList + "/tracks")
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 201);
        assertThat(response.statusCode(), anyOf(is(200), is(201)));
    }

    @Test
    public void reordenaPlayList() {
        buscaMusicaRequest();
        String idMusicaPrimeiraPosicao = response.path("items[0].track.id");
        String nomeMusicaPrimeiraPosicao = response.path("items[0].track.name");
        String idMusicaSegundaPosicao = response.path("items[1].track.id");
        String nomeMusicaSegundaPosica = response.path("items[1].track.name");
        response = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\"range_start\":0,\"range_length\":1,\"insert_before\":2}")
                .when()
                .put(URL + "/playlists/" + idPlayList + "/tracks")
                .then()
                .extract()
                .response();
        if (response.statusCode() != 401) {
            Assert.assertEquals(response.statusCode(), 200);
            buscaMusicaRequest();
            Assert.assertEquals(idMusicaPrimeiraPosicao, response.path("items[1].track.id"));
            Assert.assertEquals(nomeMusicaPrimeiraPosicao, response.path("items[1].track.name"));
            Assert.assertEquals(idMusicaSegundaPosicao, response.path("items[0].track.id"));
            Assert.assertEquals(nomeMusicaSegundaPosica, response.path("items[0].track.name"));
        } else {
            System.out.println("Usuário não está logado;");
        }
    }

    @Test
    public void deleteMusica() {
        buscaMusicaRequest();
        int quantidadeMusica;
        if(verificaPorNome(nomeMusicaAdcionada)) {
            quantidadeMusica = list.size();
            deleteMusicaRequest(idMusicaDelete);
        } else {
            adicionarMusicaResquest(idMusicaDelete);
            buscaMusicaRequest();
            quantidadeMusica = list.size();
            deleteMusicaRequest(idMusicaDelete);
        }
        if (response.statusCode() != 401) {
            Assert.assertEquals(response.statusCode(), 200);
            buscaMusicaRequest();
            Assert.assertEquals(list.size(), quantidadeMusica - 1);
            Assert.assertFalse(verificaPorNome(nomeMusicaAdcionada));
        } else {
            System.out.println("Usuário não está logado!");
        }
    }

    public Response adicionarMusicaResquest(String idMusica) {
        Response result;
        result = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\"uris\":[\"" + idMusica + "\"]}")
                .when()
                .post(URL + "/playlists/" + idPlayList + "/tracks")
                .then()
                .extract()
                .response();
        Assert.assertEquals(result.statusCode(), 201);
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

    public void deleteMusicaRequest(String idMusica) {
        response = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\"tracks\":[{\"uri\":\"" + idMusica + "\"}]}")
                .when()
                .delete(URL + "/playlists/" + idPlayList + "/tracks")
                .then()
                .extract()
                .response();
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
