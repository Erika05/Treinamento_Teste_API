import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PlayList extends Helper {

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

    public void retornaIdPlayList(String nomePlayList) {
        busPlayListRequest();
        if (verificaPorNome(nomePlayList)) {

            idPlayList = response.path("items[" + index + "].id");
        } else {
            System.out.println("PlayList não encontrada");
        }
    }
}
