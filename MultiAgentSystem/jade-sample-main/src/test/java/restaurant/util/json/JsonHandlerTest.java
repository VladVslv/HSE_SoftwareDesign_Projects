package restaurant.util.json;

import org.junit.jupiter.api.Test;
import restaurant.model.Cook;
import restaurant.model.Equipment;
import restaurant.model.Ingredient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

class JsonHandlerTest {
    @Test
    void loadRecipes() throws IOException {
        var data = JsonHandler.upLoadRecipes();
        assert Objects.equals(data.get(0).getName(), "Princess Nuri tea bag in a paper cup");
        assert data.get(0).getId() == 518;
        assert data.get(0).getTime() == 0.15;
        assert data.get(0).getIngredients().equals(List.of(new Ingredient[]{new Ingredient(18L, 1.0),
                new Ingredient(23L, 2.0), new Ingredient(24L, 0.2), new Ingredient(12L, 1.0),
                new Ingredient(19L, 1.0)}));
        assert data.get(0).getEquipType() == 25;
        assert data.get(0).getTime() == 0.15;
    }

    @Test
    void loadEquipment() {
        var data = JsonHandler.upLoadEquipment();
        assert data.equals(List.of(new Equipment[]{new Equipment(2L, "LIDER 250", "rotary oven"), new Equipment(25L, "DAZHENG LOOKYAMI", "thermopot")}));

    }

    @Test
    void loadIngredients() {
        var data = JsonHandler.upLoadIngredients();
        assert data.equals(List.of(new Ingredient[]{new Ingredient(11L, 15.3), new Ingredient(18L, 874.0),
                new Ingredient(22L, 0.0), new Ingredient(23L, 952.0), new Ingredient(24L, 174.22),
                new Ingredient(12L, 1032.0), new Ingredient(19L, 12874.0)}));

    }

    @Test
    void loadCooks() {
        var data = JsonHandler.upLoadCooks();
        assert data.equals(List.of(new Cook[]{new Cook(15L, "Ivanov A. S.", true), new Cook(16L, "Petrov I. V.", true)}));
    }
}