package utils;

import java.io.File;

import constants.DataStoreConstants;

public class ValidationUtils {

	public static boolean isKeyNameValid(String key) {
		if (key.length() > DataStoreConstants.KEY_MAX_LENGTH) {
			return false;
		}
		return true;
	}
	
	public static boolean isFileSizeValid(File file) {
		if(file.length()/(1024*1024) < 1024) {
			return true;
		}
		return false;
	}
}
