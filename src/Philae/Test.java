package Philae;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		Path p = Paths.get("C:\\Users\\9987j\\Desktop\\eclipse-program\\raspberrypi");
		Git git = new Git(p);
		git.runCommand("git", "add", ".");

	}

}
