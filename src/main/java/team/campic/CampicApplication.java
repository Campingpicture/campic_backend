package team.campic;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import team.campic.collector.CampingService;


@SpringBootApplication
@RequiredArgsConstructor
public class CampicApplication implements CommandLineRunner {

	private final CampingService campingService;

	public static void main(String[] args) {
		SpringApplication.run(CampicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		campingService.fetchAndSaveCampingData();
	}
}
