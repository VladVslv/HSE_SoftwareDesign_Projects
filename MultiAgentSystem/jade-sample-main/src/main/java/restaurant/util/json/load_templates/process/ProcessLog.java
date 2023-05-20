package restaurant.util.json.load_templates.process;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


// Class of cooking dish process
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@JsonIgnoreProperties("proc_operations")
public class ProcessLog {
    @JsonAlias("proc_id")
    Long id;
    @JsonAlias("ord_dish")
    Long dishId;
    @JsonAlias("proc_started")
    String startTime;
    @JsonAlias("proc_ended")
    String endTime;
    @JsonAlias("proc_active")
    boolean isActive;

    public ProcessLog() {
    }
}
