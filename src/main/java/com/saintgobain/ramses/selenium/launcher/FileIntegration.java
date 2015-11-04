package com.saintgobain.ramses.selenium.launcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class FileIntegration extends AbstractRamsesSelenium {

	private final static Logger logger = Logger.getLogger(FileIntegration.class);

	void run() {
		logger.info("[FILE INTEGRATION] starting -----------------------------------------------");

		String incomingDirectory = getResourceValue("file.path.incoming");
		String integrationDirectory = getResourceValue("file.path.integration");
		String waitingDirectory = getResourceValue("file.path.waiting");

		File folderZip = new File(incomingDirectory);
		File folderIntegration = new File(integrationDirectory);
		manageZip(folderZip, folderIntegration, integrationDirectory);
		logger.info("[FILE INTEGRATION] - [UNZIP] done");
		logger.info("[FILE INTEGRATION] move waiting files starting");
		moveDir(waitingDirectory, integrationDirectory + "//");
		logger.info("[FILE INTEGRATION] integration done");
		logger.info("[FILE INTEGRATION] ending ------------------------------------------------");
	}

	private void manageZip(File folderZip, File folderIntegration, String integrationDirectory) {
		String errorDirectory = getResourceValue("file.path.error");
		File[] listOfFiles = folderZip.listFiles();
		if (listOfFiles != null) {
			manageFilesInFolderZip(folderIntegration, integrationDirectory, errorDirectory, listOfFiles);
		}
	}

	private void manageFilesInFolderZip(File folderIntegration, String integrationDirectory, String errorDirectory, File[] listOfFiles) {
		for (File currentFile : listOfFiles) {
			if (currentFile.isFile()) {
				String fileName = currentFile.getName();
				if (fileName.endsWith(".zip")) {
					logger.info("[INTEGRATION] - [UNZIP] starting unzip file " + fileName + " (size : " + currentFile.length() + " bytes)");
					File ackFile = searchAckFile(fileName, listOfFiles);
					unzipFileCompleted(folderIntegration, integrationDirectory, errorDirectory, currentFile, fileName, ackFile);
					logger.info("[INTEGRATION] - [UNZIP] file " + fileName + " unzipped");
				}
			}
		}
	}

	private void unzipFileCompleted(File folderIntegration, String integrationDir, String errorDir, File currentFile, String fileName, File ackFile) {
		if (ackFile != null && ackFile.isFile()) {
			try {
				File newDir = new File(integrationDir + "//" + fileName);
				if (!newDir.exists()) {
					createDirAndZip(folderIntegration, integrationDir, currentFile, fileName, ackFile, newDir);
				} else {
					newDir.delete();
					createDirAndZip(folderIntegration, integrationDir, currentFile, fileName, ackFile, newDir);
				}
			} catch (IOException ex) {
				logger.error("[INTEGRATION] - [UNZIP] procedure unzip pb- failed ", ex);
				moveFile(errorDir, ackFile);
				moveFile(errorDir, currentFile);
			}
		} else {
			logger.warn("[INTEGRATION] - [UNZIP] ack file is missing for zip file " + fileName);
		}
	}

	private void createDirAndZip(File folderInt, String intDir, File currentFile, String fileName, File ackFile, File newDir) throws IOException {
		String zipArchiveDirectory = getResourceValue("file.path.ziparchive");
		newDir.mkdir();
		unzip(currentFile, folderInt, intDir, fileName);
		moveFile(zipArchiveDirectory, ackFile);
		moveFile(zipArchiveDirectory, currentFile);
	}

	private void moveFile(String directory, File currentFile) {
		File newDir = new File(directory);
		if (!newDir.exists()) {
			newDir.mkdir();
			moveFileInDir(directory, currentFile);
		} else {
			moveFileInDir(directory, currentFile);
		}
	}

	private void moveFileInDir(String directory, File currentFile) {
		File file = new File(directory + "//" + currentFile.getName());
		if (!file.exists()) {
			if (currentFile.renameTo(file)) {
				logger.info("[INTEGRATION] - " + currentFile.getName() + " is successfully moved to " + file.getPath());
			} else {
				logger.info("[INTEGRATION] - " + currentFile.getName() + " is failed to move!");
			}
		} else {
			file.delete();
			moveFile(directory, currentFile);
		}
	}

	private void unzip(File currentFile, File folderIntegration, String integrationDirectory, String zipFolderName) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(currentFile.getCanonicalFile())));
		ZipEntry zipUnit = null;
		try {
			while ((zipUnit = zipInputStream.getNextEntry()) != null) {
				File file = new File(integrationDirectory + "//" + zipFolderName + "//" + zipUnit.getName());
				OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(file));
				unzipFile(zipInputStream, file, fileOutputStream);
				logger.info("[FILE INTEGRATION] - [UNZIP] success, for file " + file.getName() + " from " + currentFile.getName());
			}
		} catch (IOException ex) {
			logger.error("[FILE INTEGRATION] - [UNZIP] cannot unzip correctly - read unit file pb " + currentFile.getName(), ex);
			throw ex;
		} finally {
			zipInputStream.close();
		}
	}

	private void unzipFile(ZipInputStream zipInputStream, File file, OutputStream fileOutputStream) throws IOException {

		try {
			try {
				final byte[] buf = new byte[8192];
				int bytesRead;
				while ((bytesRead = zipInputStream.read(buf)) != -1) {
					fileOutputStream.write(buf, 0, bytesRead);
				}
			} finally {
				fileOutputStream.close();
			}
		} catch (final IOException ex) {
			file.delete();
			logger.error("[FILE INTEGRATION] - [UNZIP] cannot unzip correctly - write on new file pb", ex);
			throw ex;
		}
	}

	private File searchAckFile(String fileName, File[] listOfFiles) {
		for (File searchFile : listOfFiles) {
			if (searchFile.isFile()) {
				String searchFileName = searchFile.getName();
				if (searchFileName.endsWith(".ack") && searchFileName.contains(fileName)) {
					return searchFile;
				}
			}
		}
		return null;
	}

	private void moveDir(String waitingDirectory, String integrationDirectory) {
		File source = new File(waitingDirectory);
		File dest = new File(integrationDirectory);
		try {
			FileUtils.copyDirectory(source, dest);
			File[] listOfDirectory = source.listFiles();
			for (File dir : listOfDirectory) {
				deleteDir(dir);
			}
		} catch (IOException ex) {
			logger.error("[FILE INTEGRATION] Error while copy waiting directory", ex);
		}
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private String getResourceValue(String propertyKey) {
		return ResourceBundle.getBundle("cfg_selenium").getString(propertyKey);
	}
}
