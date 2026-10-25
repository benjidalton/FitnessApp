package com.benjidalton.fitnessapp;

import com.benjidalton.fitnessapp.controller.DashboardController;
import com.benjidalton.fitnessapp.controller.MuscleGroupController;
import com.benjidalton.fitnessapp.controller.WorkoutController;
import com.benjidalton.fitnessapp.repository.BaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories(
        repositoryBaseClass = BaseRepositoryImpl.class
)
public class FitnessAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessAppApplication.class, args);
    }

    @Bean
    CommandLineRunner testOnStartup(
            DashboardController dashboardController,
            WorkoutController workoutController,
            MuscleGroupController muscleGroupController
    ) {
        return args -> {
            System.out.println("Running startup tests...");

            var dashboardMetrics = dashboardController.getDashboardMetrics();
            var allWorkouts = workoutController.getAllWorkouts(50);
            var muscleGroupVolumes = muscleGroupController.getMuscleGroupVolumes(null);

            log.info(String.valueOf(dashboardMetrics));
            log.info(String.valueOf(allWorkouts));
            log.info(String.valueOf(muscleGroupVolumes));
        };
    }

}
