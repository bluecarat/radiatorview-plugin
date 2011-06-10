package hudson.model

import java.io.File

class DiskSpaceUtil {

	static long getPercentageFileSystemUse(File hudsonWorkingDirectory) {
		final long usableSpace = hudsonWorkingDirectory.getUsableSpace()
		final long totalSpace = hudsonWorkingDirectory.getTotalSpace()
		return 100 - (usableSpace * 100) / (totalSpace) // multiply with 100 first, to get a result > 0
	}
}