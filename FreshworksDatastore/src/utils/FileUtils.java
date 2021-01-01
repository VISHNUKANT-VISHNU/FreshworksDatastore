package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import bean.DataDetails;

public class FileUtils {

	public static boolean isFileExist(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return ValidationUtils.isFileSizeValid(file);
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean writeData(DataDetails data, String filePath) {
		HashMap<String, DataDetails> dataMap = readData(filePath);
		if (dataMap == null) {
			dataMap = new HashMap<String, DataDetails>();
		}
		dataMap.put(data.getKey(), data);
		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(dataMap);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static HashMap<String, DataDetails> readData(String filePath) {
		HashMap<String, DataDetails> dataMap = null;
		if (isFileExist(filePath)) {
			try (FileInputStream fileInputStream = new FileInputStream(filePath);
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
				dataMap = (HashMap<String, DataDetails>) objectInputStream.readObject();
				return dataMap;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public static boolean deleteKey(String key, String filePath) {
		HashMap<String, DataDetails> dataMap = readData(filePath);
		if (dataMap == null) {
			return false;
		}
		dataMap.remove(key);
		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
			objectOutputStream.writeObject(dataMap);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
