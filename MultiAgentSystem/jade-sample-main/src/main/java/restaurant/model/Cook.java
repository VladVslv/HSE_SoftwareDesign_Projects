package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

// Cook class
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cook {
    @JsonAlias("cook_id")
    Long id;
    @JsonAlias("cook_name")
    String name;
    @JsonAlias("cook_active")
    Boolean isActive;

    public Cook() {
    }
}
