package restaurant.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

// Operation class
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"oper_async_point"})
public class Operation implements Serializable {
    @JsonAlias("oper_type")
    Long id;
    @JsonAlias("oper_time")
    Double time;
    @JsonAlias("oper_products")
    ArrayList<Ingredient> ingredients;

    public Operation() {
    }
}
