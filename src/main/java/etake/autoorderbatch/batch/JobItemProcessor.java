package etake.autoorderbatch.batch;

import etake.autoorderbatch.dto.PositionDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JobItemProcessor implements ItemProcessor<PositionDTO, PositionDTO> {
    public static final Logger LOGGER = Logger.getLogger(JobItemProcessor.class.getName());

    @Override
    public PositionDTO process(PositionDTO positionDTO) throws Exception {
        return positionDTO;
    }
}
