package name.syndarin.githubbrowser.logging;

import android.support.annotation.RequiresPermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import timber.log.Timber;

/**
 * Created by vtiahotenkov on 13.03.17.
 */

public class FileTree extends Timber.DebugTree {

    private File logFile;

    public FileTree(File logFile) {
        this.logFile = logFile;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        try {
            Writer writer = new FileWriter(logFile, true);
            writer.write(String.format("%20s %s\n", tag, message));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
