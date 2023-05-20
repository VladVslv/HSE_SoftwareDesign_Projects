package restaurant.util.json.upload_templates.product;

import lombok.Getter;
import lombok.Setter;
import restaurant.model.Ingredient;

import java.util.ArrayList;

@Getter
@Setter
public class IngredientFileTemplate {
    ArrayList<Ingredient> products;
}
