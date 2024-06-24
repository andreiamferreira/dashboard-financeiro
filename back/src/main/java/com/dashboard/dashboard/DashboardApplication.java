package com.dashboard.dashboard;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dashboard.dashboard.dto.BankAccountDTO;
import com.dashboard.dashboard.dto.FinancialGoalDTO;
import com.dashboard.dashboard.model.BankAccount;
import com.dashboard.dashboard.model.FinancialGoal;

@SpringBootApplication
@EnableScheduling
public class DashboardApplication {

	@Configuration
	public class ModelMapperConfig {

		@Bean
		public ModelMapper modelMapper() {
			ModelMapper modelMapper = new ModelMapper();

			modelMapper.addMappings(new PropertyMap<FinancialGoal, FinancialGoalDTO>() {
				@Override
				protected void configure() {
					map().setUser(source.getUser().getId());
				}
			});

			modelMapper.addMappings(new PropertyMap<BankAccount, BankAccountDTO>() {
				@Override
				protected void configure() {
					map().setInstitution(source.getInstitution());
				}
			});

			return modelMapper;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

}
