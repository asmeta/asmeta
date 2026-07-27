package atgt.testseqexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import atgt.coverage.AsmTestSequence;

public abstract class TestSeqTrad {

	protected OutputStream out;
	private File file;
	protected AsmTestSequence testSequence;

	public TestSeqTrad(File f, AsmTestSequence ts) {
		this((OutputStream) null, ts);
		file = f;
	}

	public TestSeqTrad(OutputStream out, AsmTestSequence ts) {
		this.out = out;
		this.testSequence = ts;
	}

	public final void save() {
		// check if no outputstream is given yet
		if (out == null) {
			try {
				// if file already exists will do nothing
				file.createNewFile();
				out = new FileOutputStream(file, false);
			} catch (IOException e) {
				System.err.println("impossible to create file " + file.getAbsolutePath());
				return;
			} 
		}
		saveToStream();
	}

	// metodo implementato nelle sotto-classi
	public abstract void saveToStream();

}
