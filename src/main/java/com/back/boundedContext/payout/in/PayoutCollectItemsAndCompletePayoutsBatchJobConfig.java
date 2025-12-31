package com.back.boundedContext.payout.in;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.back.boundedContext.payout.app.PayoutFacade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PayoutCollectItemsAndCompletePayoutsBatchJobConfig {
	private static final int CHUNK_SIZE = 10;

	private final PayoutFacade payoutFacade;

	public PayoutCollectItemsAndCompletePayoutsBatchJobConfig(PayoutFacade payoutFacade) {
		this.payoutFacade = payoutFacade;
	}

	@Bean
	public Job payoutCollectItemsAndCompletePayoutsJob(
		JobRepository jobRepository,
		Step payoutCollectItemsStep,
		Step payoutCompletePayouts
	) {
		return new JobBuilder("payoutCollectItemsJob", jobRepository)
			.start(payoutCollectItemsStep)
			.next(payoutCompletePayouts)
			.build();
	}

	//정산할 내역 정산 대기 목록에서 가져오기 스텝
	@Bean
	public Step payoutCollectItemsStep(JobRepository jobRepository) {
		return new StepBuilder("payoutCollectItemsStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				int processedCount = payoutFacade.collectPayoutItemsMore(CHUNK_SIZE).getData();

				if (processedCount == 0) {
					return RepeatStatus.FINISHED;
				}

				contribution.incrementWriteCount(processedCount);

				return RepeatStatus.CONTINUABLE;
			})
			.build();
	}

	//정산 집행 스텝
	@Bean
	public Step payoutCompletePayouts(JobRepository jobRepository) {
		return new StepBuilder("payoutCompletePayouts", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				int processedCount = payoutFacade.completePayoutsMore(CHUNK_SIZE).getData();

				if (processedCount == 0) {
					return RepeatStatus.FINISHED;
				}

				contribution.incrementWriteCount(processedCount);

				return RepeatStatus.CONTINUABLE;
			})
			.build();
	}
}
