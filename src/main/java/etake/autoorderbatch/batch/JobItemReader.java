package etake.autoorderbatch.batch;

import etake.autoorderbatch.dto.PositionDTO;
import etake.autoorderbatch.dto.mapper.ExcelRowMapper;
import etake.autoorderbatch.service.HeaderMapper;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JobItemReader {
    private final HeaderMapper headerMapper;

    public JobItemReader(HeaderMapper headerMapper) {
        this.headerMapper = headerMapper;
    }

    @Bean
    public ItemReader<PositionDTO> excelPositionReader() {
        PoiItemReader<PositionDTO> poiItemReader = new PoiItemReader<>();
        poiItemReader.setLinesToSkip(1);
        poiItemReader.setResource(new ClassPathResource("input.xlsx"));
        poiItemReader.setRowMapper(excelRowMapper());

        return poiItemReader;
    }

    private RowMapper<PositionDTO> excelRowMapper() {
        return new ExcelRowMapper(headerMapper);
    }

}
