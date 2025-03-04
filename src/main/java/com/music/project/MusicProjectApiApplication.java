package com.music.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.music.project.helpers.string.StringHelper.toSlug;

@SpringBootApplication
public class MusicProjectApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicProjectApiApplication.class, args);

	}

}
