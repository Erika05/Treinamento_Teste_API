import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Treinamento_Spotify {

    public static String token = "BQDVaR7GzMYae43yBoa0_4y8C8rPeLdKspMt6QxMg_I_Ghc2QOpmEY5d7uPLtJTlGbyx7zed_GR9uA2_oZ0RzPDbx-pIotOtyYmK5AT-IjjrIdWJzb4vpbz5gyItJtgy22Qj3K3HjyxIdyHTHeZGltyHGxvoLD6LxfKQVqFZMNY9YP2U6ZqmNLj1hZBiMGsM1X_vlo6Gezrnbj1XbHGVheTRp4G2D_i5LGnfRd18miLChIbaT-vEvmMVD3ZpkjeKQctkieoCrVJ5z0-hLCei-SHB-quE0QDwBmOfmQ";
    public static String URL = "https://api.spotify.com/v1";
    public static Response response;

    @Test
    public void buscarPlayList() {
        response = given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(URL + "/me/playlists")
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 200);
    }
}
