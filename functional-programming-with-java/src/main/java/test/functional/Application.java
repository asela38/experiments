package test.functional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	private static Logger LOG = LoggerFactory.getLogger(Application.class);

	@Autowired
	private UserDao dao;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		File file = File.createTempFile("Application", ".lock");
		LOG.info("Lock file created : {}", file.getAbsolutePath());

		List<User> allUsers = dao.getAllUsers();

		for (User user : allUsers) {
			LOG.info(user.toString());
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				Files.delete(file.toPath());
				LOG.info("Successfully delete the lock file : {}", file.getAbsolutePath());
			} catch (IOException e) {
				LOG.error("Fail to delete log file", e);
			}
		}));
	}
}
