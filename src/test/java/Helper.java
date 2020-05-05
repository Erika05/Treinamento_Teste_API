import io.restassured.response.Response;

import java.util.ArrayList;

public class Helper {
    public static String token = "BQDgvTQS1YPdWM5HJmlkniUv8F3gHQ7Uo7VQweHuJxm5KCCQLPRjqNooAkMnR7wvhz87QSsC0qxpUzgCbreITXupvTXSAggOSxqCz0I-Md0h1aACDUJgppa0ZpOYaUzHWxGVvYQ9LWcE2H1fbf7yw_1NaOyKdB6hR0yuCFagfFKpq25KUKJkc6MulH6qXSv1ypWx9dOGuoPZVn5bKg7PvaDLIYRyvj9JpzHwYKq-f6ugv4C6N1k4cBIRZburvdqoTkCp9xYWTkus2RO93UwvlZF-Wsj9KrCWIAAWhWxH";
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
