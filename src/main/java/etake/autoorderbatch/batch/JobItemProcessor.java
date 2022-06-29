package etake.autoorderbatch.batch;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.dto.PositionDTO;
import etake.autoorderbatch.dto.PositionResponseDTO;
import etake.autoorderbatch.service.AttributeService;
import etake.autoorderbatch.service.DemandService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static etake.autoorderbatch.util.Constants.AM;
import static etake.autoorderbatch.util.Constants.DEMAND;
import static etake.autoorderbatch.util.Constants.DEMAND_MAIN;
import static etake.autoorderbatch.util.Constants.DEMAND_WITH_COEFFICIENTS;

@Component
public class JobItemProcessor implements ItemProcessor<PositionDTO, PositionResponseDTO> {
    @Autowired
    private AttributeService attributeService;

    @Autowired
    private DemandService demandService;

    @Override
    public PositionResponseDTO process(PositionDTO positionDTO) {
        final Set<NameValuePair<String, String>> attributes = positionDTO.getAttributes().stream()
                .map(n -> new NameValuePair<>(n.getName(), Objects.isNull(n.getValue()) ? Strings.EMPTY : String.valueOf(n.getValue())))
                .collect(Collectors.toSet());

        attributeService.init(attributes);
        PositionResponseDTO positionResponseDTO = new PositionResponseDTO().setStore(positionDTO.getStore())
                .setCategory(positionDTO.getCategory())
                .setGroup3(positionDTO.getGroup3())
                .setCode(positionDTO.getCode())
                .setName(positionDTO.getName())
                .setAttributes(attributes);

        if (isMatrixPosition(positionDTO)) {
            return positionResponseDTO.setAttributesAnalyst(Set.of());
        }

        final BigDecimal mainDemand = demandService.calculateDemand();
        final BigDecimal mainDemandWithCoefficients = demandService.calculateDemandWithCoefficient(mainDemand);
        final BigDecimal finalDemand = demandService.calculateFinalDemand(mainDemandWithCoefficients);


        return positionResponseDTO.setAttributesAnalyst(getCalculatedAttributes(mainDemand, mainDemandWithCoefficients, finalDemand));
    }

    private Set<NameValuePair<String, String>> getCalculatedAttributes(BigDecimal mainDemand, BigDecimal mainDemandWithCoefficients, BigDecimal finalDemand ) {
        Set<NameValuePair<String, String>> attributes = new HashSet<>();
        attributes.add(new NameValuePair<>(DEMAND_MAIN, Objects.equals(mainDemand, BigDecimal.ZERO) ? Strings.EMPTY : String.valueOf(mainDemand)));
        attributes.add(new NameValuePair<>(DEMAND_WITH_COEFFICIENTS, Objects.equals(mainDemandWithCoefficients, BigDecimal.ZERO) ? Strings.EMPTY : String.valueOf(mainDemandWithCoefficients)));
        attributes.add(new NameValuePair<>(DEMAND, Objects.equals(finalDemand, BigDecimal.ZERO) ? Strings.EMPTY : String.valueOf(finalDemand)));

        return attributes;
    }

    private boolean isMatrixPosition(PositionDTO positionDTO) {
        return positionDTO.getAttributes().stream()
                .filter(a -> a.getName().equalsIgnoreCase(AM))
                .findFirst()
                .orElse(new NameValuePair<>())
                .getValue()
                .equals(Strings.EMPTY);
    }
}
