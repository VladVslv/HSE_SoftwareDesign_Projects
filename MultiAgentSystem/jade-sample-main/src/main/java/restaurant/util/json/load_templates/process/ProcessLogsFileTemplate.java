package restaurant.util.json.load_templates.process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

// Class of process logs for file template
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProcessLogsFileTemplate {
    ArrayList<ProcessLog> process_log;

    public ProcessLogsFileTemplate() {
    }
}
