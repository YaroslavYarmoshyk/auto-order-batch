package etake.autoorderbatch.configuration;

import etake.autoorderbatch.batch.JobItemProcessor;
import etake.autoorderbatch.batch.JobItemReader;
import etake.autoorderbatch.batch.JobItemWriter;
import etake.autoorderbatch.dto.PositionDTO;
import etake.autoorderbatch.dto.PositionResponseDTO;
import etake.autoorderbatch.service.HeaderMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    @Autowired
    private HeaderMapper headerMapper;
    @Autowired
    private JobItemReader jobItemReader;
    @Autowired
    private JobItemProcessor jobItemProcessor;
    @Autowired
    private JobItemWriter jobItemWriter;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Bean
    public Step step1() {

        return this.stepBuilderFactory.get("step1")
                .<PositionDTO, PositionResponseDTO>chunk(50)
                .reader(new JobItemReader(headerMapper).excelPositionReader())
                .processor(jobItemProcessor)
                .writer(jobItemWriter)
                .build();
    }

    @Bean
    public Job autoOrderBatchJob() {

        return this.jobBuilderFactory.get("autoOrderBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }
}
