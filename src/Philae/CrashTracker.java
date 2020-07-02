package Philae;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CrashTracker {
	private File f;
	
	public CrashTracker(String path) {
		f = new File(path);
	}
	
	/**
	 * Write log.
	 * @param event 
	 */
	public void write(String event){
		try {
			BufferedWriter bw 
				= new BufferedWriter(new FileWriter(f, true));
			bw.write(new Date().toString() + "\n");
			bw.write(event + "\n");
			bw.flush();
			bw.close();
		}catch(IOException e) {
			
		}
	}
	
	/**
	 * Clean log.
	 */
	public void clean() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("\0");
			bw.flush();
			bw.close();
		}catch(IOException e) {
			
		}
	}
}
