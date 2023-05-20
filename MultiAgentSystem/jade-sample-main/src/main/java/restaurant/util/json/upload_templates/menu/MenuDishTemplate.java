package restaurant.util.json.upload_templates.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MenuDishTemplate {
    Long menu_dish_id;
    Long menu_dish_card;
    Integer menu_dish_price;
    Boolean menu_dish_active;
}