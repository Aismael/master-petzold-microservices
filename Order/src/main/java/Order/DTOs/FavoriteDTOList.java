package Order.DTOs;
import Order.Entities.OrderConcepts.Favorite;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aismael on 19.04.2017.
 */

public class FavoriteDTOList {
    ArrayList<FavoriteDTO> list = new ArrayList<>();

    public FavoriteDTOList(List<Favorite> favoriteList) {
        this.setFavoriteList(favoriteList);
    }

    public ArrayList<FavoriteDTO> getList() {
        return list;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        for (Favorite favorite : favoriteList) {
            list.add(new FavoriteDTO(favorite));
        }
    }
}