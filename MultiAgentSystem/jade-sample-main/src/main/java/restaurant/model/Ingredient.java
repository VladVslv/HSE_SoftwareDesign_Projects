package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

// Ingredient class
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"prod_item_id", "prod_item_company", "prod_item_unit", "prod_item_cost", "prod_item_delivered", "prod_item_name", "prod_item_valid_until"})
public class Ingredient implements Serializable {
    @JsonAlias({"prod_item_type", "prod_type"})
    Long productId;
    @JsonAlias({"prod_item_quantity", "prod_quantity"})
    Double quantity;

    public Ingredient() {
    }
}
