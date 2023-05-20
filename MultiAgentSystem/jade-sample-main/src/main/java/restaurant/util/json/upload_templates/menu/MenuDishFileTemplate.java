package restaurant.util.json.upload_templates.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MenuDishFileTemplate {
    ArrayList<MenuDishTemplate> menu_dishes;
}