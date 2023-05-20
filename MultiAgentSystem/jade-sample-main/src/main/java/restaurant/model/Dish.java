package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Dish class
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"card_descr"})
public class Dish implements Serializable {
    @JsonAlias("card_id")
    Long id;
    @JsonAlias("dish_name")
    String name;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    @JsonAlias("card_time")
    Double time;

    Dish() {
    }
}
