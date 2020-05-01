import io.restassured.response.Response;

import java.util.ArrayList;

public class Helper {
    public static String token = "BQD667u5jpZPK1hvvhIlBoPpRCeSqKXYvqOgZDccDV1UUhp6WZo06TXyPecXBbbJftLI-j5uH3OZ_Hat_ZK5mzhCNOJrXDmnmU3opAjKg6Ozp5DmMdzhs3hUuiC-CCKHCYj9WobMsZ1MUoIFIGvrC6h06oqqZ30tysf27JSnsk-aftQCp9htPysvpN99acg1sNIKFqQMQ7_MvNAVr7aisTzd6BliwTBn2pm7JhmsK6RDcURvKOasM9dKyefN7BYFHX-XUStLMBHsVmfAiDMCn4PsvJQXb1Do6rQeIhfc";
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
