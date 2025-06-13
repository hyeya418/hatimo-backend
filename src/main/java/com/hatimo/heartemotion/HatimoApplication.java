package com.hatimo.heartemotion;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HatimoApplication {

	public static void main(String[] args) {
		// 1) JVM 옵션으로 받은 .env 디렉터리/파일명 가져오기 (없으면 기본)
		String dotenvDir  = System.getProperty("dotenv.dir",  "./");
		String dotenvFile = System.getProperty("dotenv.file", ".env");

		// 2) dotenv 로드
		Dotenv dotenv = Dotenv.configure()
				.directory(dotenvDir)        // JVM 프로퍼티 반영
				.filename(dotenvFile)
				.ignoreIfMalformed()
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
