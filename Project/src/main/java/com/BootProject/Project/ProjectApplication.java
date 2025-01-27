package com.BootProject.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.BootProject.Project.SecurityController",
		"com.BootProject.Project.RouteController",

		"com.BootProject.Project.Config",
		"com.BootProject.Project.Service",
		"com.BootProject.Project.Classes",
		"com.BootProject.Project.DTO"
})
@EntityScan(basePackages = {"com.BootProject.Project.Models"})
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
