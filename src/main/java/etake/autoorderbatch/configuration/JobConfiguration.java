package etake.autoorderbatch.configuration;

import etake.autoorderbatch.batch.JobItemProcessor;
import etake.autoorderbatch.batch.JobItemReader;
import etake.autoorderbatch.batch.JobItemWriter;
import etake.autoorderbatch.dto.PositionDTO;
import etake.autoorderbatch.service.HeaderMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
    private final HeaderMapper headerMapper;
    private final JobItemProcessor jobItemProcessor;
    private final JobItemWriter jobItemWriter;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    public JobConfiguration(HeaderMapper headerMapper,
                            JobItemProcessor jobItemProcessor,
                            JobItemWriter jobItemWriter,
                            StepBuilderFactory stepBuilderFactory,
                            JobBuilderFactory jobBuilderFactory) {
        this.headerMapper = headerMapper;
        this.jobItemProcessor = jobItemProcessor;
        this.jobItemWriter = jobItemWriter;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }


    @Bean
    public Step step1() {

        return this.stepBuilderFactory.get("step1")
                .<PositionDTO, PositionDTO>chunk(10)
                .reader(new JobItemReader(headerMapper).excelPositionReader())
                .processor(jobItemProcessor)
                .writer(jobItemWriter)
//                .faultTolerant()
//                .skip(Exception.class)
//                .skipLimit(10)
                .build();
    }

    @Bean
    public Job autoOrderBatchJob() {

        return this.jobBuilderFactory.get("autoOrderBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

//    @Bean
//    public Job autoOrderBatchJob() {
//
//        return this.jobBuilderFactory.get("autoOrderBatchJob")
//                .incrementer(new RunIdIncrementer())
////                .start(extractDataStep())
//                .start(extractDataStep())
//                .build();
//    }

//    private TaskletStep extractDataStep() {
//        return stepBuilderFactory.get("Extract headers from file")
//                .<List<NameValuePair<String, Integer>>, List<NameValuePair<String, Integer>>>chunk(1)
//                .reader(new JobHeaderReader(new ExcelHeaderMapper()))
//                .writer(jobHeaderWriter)
//                .build();
//    }

}
