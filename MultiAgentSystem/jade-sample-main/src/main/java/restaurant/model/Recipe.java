package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Recipe class
@Getter
@Setter
@ToString
@AllArgsConstructor

public class Recipe extends Dish {
    @JsonAlias("equip_type")
    Long equipType;
    @JsonAlias("operations")
    ArrayList<Operation> operations;

    public Recipe() {
    }
}
