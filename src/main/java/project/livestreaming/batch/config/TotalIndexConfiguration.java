package project.livestreaming.batch.config;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TotalIndexConfiguration {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

//    @Bean
//    public Job totalIndexJob() {
//        return new JobBuilder("totalIndexJob", jobRepository)
//                .start(totalIndexStep())
//                .build();
//    }

//    @Bean
//    public Step totalIndexStep() {
//        return new StepBuilder("totalIndexStep", jobRepository)
//                .transactionManager(transactionManager)
//                .tasklet(new TotalIndexTasklet())
//                .build();
//
//    }


}
