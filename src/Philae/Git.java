package Philae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Git {
	private static CrashTracker gitTracker = new CrashTracker("GitCrashLog.txt");
	
	public static void runCommand(Path directory, String... command) {
		Objects.requireNonNull(directory, "directory must be nat null");
		
		if(Files.exists(directory)) {
			System.err.println("directory didn't exist!");
		}
		
		ProcessBuilder pb = new ProcessBuilder()
							.directory(directory.toFile())
							.command(command);
		
		try {
			Process p = pb.start();
			StreamGobbler output = new StreamGobbler(p.getInputStream(), "output");
			//error logt
		} catch (IOException e) {
			gitTracker.write(e.getMessage());
		}
	}
	
	private static class StreamGobbler extends Thread{
		private final InputStream is;
		private final String type;
		private static CrashTracker gitLog = new CrashTracker("gitLog.txt");
		public StreamGobbler(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}
		@Override
		public void run() {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
				String line;
				while((line = br.readLine()) != null) {
					gitLog.output(line);
				}
				gitLog.output("\n");
			}catch (IOException e) {
				gitTracker.write(e.getMessage());
			}
		}
	}
	
	
}
