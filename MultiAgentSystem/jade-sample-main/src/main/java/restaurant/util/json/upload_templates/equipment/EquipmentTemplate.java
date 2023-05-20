package restaurant.util.json.upload_templates.equipment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class EquipmentTemplate {
    Long equip_type;
    String equip_name;
    Boolean equip_active;
}
