package etake.autoorderbatch.dto.mapper;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.dto.PositionDTO;
import etake.autoorderbatch.service.HeaderMapper;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static etake.autoorderbatch.util.Constants.ATTRIBUTES;

public class ExcelRowMapper implements RowMapper<PositionDTO> {
    private final HeaderMapper headerMapper;

    public ExcelRowMapper(HeaderMapper headerMapper) {
        this.headerMapper = headerMapper;
    }


    @Override
    public PositionDTO mapRow(RowSet rowSet) throws Exception {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setStore(getStore(rowSet.getCurrentRow()[1]));
        positionDTO.setCategory(rowSet.getCurrentRow()[2]);
        positionDTO.setGroup3(rowSet.getCurrentRow()[3]);
        positionDTO.setCode(getCode(rowSet.getCurrentRow()[4]));
        positionDTO.setName(rowSet.getCurrentRow()[5]);
        positionDTO.setAttributes(getAttributes(rowSet));

        return positionDTO;
    }

    private Set<NameValuePair<String, Object>> getAttributes(RowSet rowSet) {
        Set<NameValuePair<String, Object>> attributes = new HashSet<>();
        Map<String, Integer> headers = headerMapper.getHeaders();

        for (String attribute : ATTRIBUTES) {

            int attributeIndex = headers.get(attribute);
            attributes.add(new NameValuePair<>(attribute, rowSet.getCurrentRow()[attributeIndex]));
        }

        return attributes;
    }

    private Integer getCode(String stringCode) {
        return Integer.valueOf(stringCode.replaceAll("\\s+", ""));
    }

    private String getStore(String store) {
        return store.replaceAll("\\(торговий зал\\)", "");
    }
}
