package etake.autoorderbatch.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class PositionDTO {
    private String store;
    private String category;
    private String group3;
    private Integer code;
    private String name;
    private Set<NameValuePair<String, Object>> attributes;
}
