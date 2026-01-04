import ui.CommandLineInterface;
import repository.JobSeekerRepository;
import repository.FileJobSeekerRepository;
import repository.JobOpeningRepository;
import repository.FileJobOpeningRepository;
import service.MatchingService;

public class Main {
    public static void main(String[] args) {
        // create the repositories to save to our files
        JobSeekerRepository seekerRepo = new FileJobSeekerRepository("jobSeekers.txt");
        JobOpeningRepository openingRepo = new FileJobOpeningRepository("jobOpenings.txt");

        // create the service that handles the logic
        MatchingService matchingService = new MatchingService(seekerRepo, openingRepo);

        // Start the CLI
        CommandLineInterface cli = new CommandLineInterface(matchingService);
        cli.start();
    }
}
