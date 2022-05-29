package etake.autoorderbatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValuePair<K, V> implements Serializable, Cloneable {
    private K name;
    private V value;

    @Override
    protected NameValuePair<K, V> clone() {
        return new NameValuePair<K, V>(this.name, this.value);
    }
}
