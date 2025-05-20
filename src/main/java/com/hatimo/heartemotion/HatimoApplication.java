package com.hatimo.heartemotion;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HatimoApplication {

	public static void main(String[] args) {
		// .env 파일 로딩
		Dotenv dotenv = Dotenv.configure()
				.directory("./") // 루트 경로
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("KAKAO_REST_API_KEY", dotenv.get("KAKAO_REST_API_KEY"));
		System.setProperty("KAKAO_REDIRECT_URI", dotenv.get("KAKAO_REDIRECT_URI"));
		System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));

		SpringApplication.run(HatimoApplication.class, args);
	}

}
