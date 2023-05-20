package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.io.Serializable;

// Equipment class
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Equipment implements Serializable {
    @JsonAlias("equip_type_id")
    Long type;
    String name;
    @JsonAlias("equip_type_name")
    String typeName;

    Equipment() {

    }
}
