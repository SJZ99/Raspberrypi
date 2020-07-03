package Philae;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
/**
 * CrashTracker
 * @author YuYue
 * @version 1.0
 */
public class Git {
	private static CrashTracker gitTracker = new CrashTracker("GitCrashLog.txt");
	private Path directory;
	private ArrayList<String> node = new ArrayList<String>();
	public Git(Path directory) {
		this.directory = directory;
	}
	/**
	 * run git add .
 	 * @param directory
	 */
	public void gitAdd() {
		runCommand("git", "add", ".");
	}
	/**
	 * run git commit -m "message"
	 * @param directory
	 * @param message
	 */
	public void gitCommit(String message) {
		runCommand("git", "commit", "-m", "\"" + message + "\"");
	}
	/**
	 * run git remote add "node" "connect"
	 * @param directory
	 * @param node
	 * @param connect
	 */
	public void gitRemoteAdd(String node, String connect) {
		if(runCommand("git", "remote", "add", node, connect) == 0) {
			this.node.add(node);
		}
	}
	/**
	 * run git push -u "node" master
	 * @param node
	 */
	public void gitPush(String node) {
		if(node.contains(node)) {
			runCommand("git", "push", "-u", node, "master");
		}
		
	}
	/**
	 * run shell command
	 * @param directory
	 * @param command
	 */
	public int runCommand(String... command) {
		Objects.requireNonNull(directory, "directory must be nat null");
		int exit = -1;
		if(!Files.exists(directory)) {
			System.err.println("directory didn't exist!");
		}
		
		ProcessBuilder pb = new ProcessBuilder()
							.directory(directory.toFile())
							.command(command);
		
		try {
			Process p = pb.start();
			ErrorSolver error = new ErrorSolver(p.getErrorStream());
			OutputSolver output = new OutputSolver(p.getInputStream());
			error.start();
			output.start();
			exit = p.waitFor();
			p.destroy();
		} catch (IOException e) {
			gitTracker.write(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exit;
	}
	
	/**
	 * Error Solver
	 * @author YuYue
	 */
	private static class ErrorSolver extends Thread{
		private final InputStream is;
		private static CrashTracker gitLog = new CrashTracker("gitErrorLog.txt");
		public ErrorSolver(InputStream is) {
			this.is = is;
		}
		@Override
		public void run() {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
				String line;
				while((line = br.readLine()) != null) {
					gitLog.writeln(line);
				}
				
			}catch (IOException e) {
				gitTracker.write(e.getMessage());
			}
		}
	}
	private static class OutputSolver extends Thread{
		private final InputStream is;
		public OutputSolver(InputStream is) {
			this.is = is;
		}
		@Override
		public void run() {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
				String line;
				while((line = br.readLine()) != null) {
					Printer.println(line);
				}
				
			}catch (IOException e) {
				gitTracker.write(e.getMessage());
			}
		}
	}
	
	
}
