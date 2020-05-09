import io.restassured.response.Response;

import java.util.ArrayList;

public class Helper {
    public static String token = "BQDve1W6JEqwQJOCb5gSW7o32MitycaJmqcfCIlx3g6mNHyCsu_t0zHEvPuuwyu9ATjLYtwb0stUHLsHcoOwlzCc8jHqOXeK-tbdhVddMKGX0tuyWX6ldKmztkPA1wUtlbKaTdROIPsEgH-WhEtmQ-o7bWRkcVMNbF2VgA0f2Zd-vVlVkeSThd1HqpicKBCKcjNfMUwq0mUCO6vCmnUPht3BQQl5W4vFpL4BgMkGZIFd2PATa-MwlZC9kB2esFDE0klZxQRH7IhZPxfdYEHsOp4VQud5JvJI8bXCJ7rT";
    public static String URL = "https://api.spotify.com/v1";
    public static String nomePlayList = "Teste Postman";
    public static Response response;
    public static ArrayList list;
    public static  String idPlayList;
    int index;

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
        return  qtdMusica;
    }
}
