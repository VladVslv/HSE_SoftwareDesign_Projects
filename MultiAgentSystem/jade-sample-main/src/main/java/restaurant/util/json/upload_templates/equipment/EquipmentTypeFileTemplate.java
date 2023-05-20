package restaurant.util.json.upload_templates.equipment;

import lombok.Getter;
import lombok.Setter;
import restaurant.model.Equipment;


import java.util.HashSet;

@Getter
@Setter
public class EquipmentTypeFileTemplate {
    HashSet<Equipment> equipment_type;
}