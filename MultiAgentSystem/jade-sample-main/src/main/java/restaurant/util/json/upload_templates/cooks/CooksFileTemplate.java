package restaurant.util.json.upload_templates.cooks;

import lombok.Getter;
import lombok.Setter;
import restaurant.model.Cook;

import java.util.ArrayList;

@Getter
@Setter
public class CooksFileTemplate {
    ArrayList<Cook> cooks;
}
