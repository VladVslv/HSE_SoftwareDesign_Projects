package restaurant.util.json.upload_templates.dish_cards;

import lombok.Getter;
import lombok.Setter;
import restaurant.model.Recipe;

import java.util.ArrayList;

@Getter
@Setter
public class DishCardsFileTemplate {
    ArrayList<Recipe> dish_cards;
}
