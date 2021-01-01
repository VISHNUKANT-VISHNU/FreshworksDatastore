package service;

import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;

import bean.DataDetails;
import constants.DataStoreConstants;
import utils.FileUtils;
import utils.ValidationUtils;

public class DataStoreService {
	
	private String filePath;
	
	public DataStoreService() {
		filePath = System.getProperty("user.dir") + "\\" + DataStoreConstants.FILE_NAME;
	}
	
	public DataStoreService(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * hi mintu
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized String create(String key, JSONObject value) {
		try {
			return create(key, value, -1);
		} catch (Exception exception) {
			return DataStoreConstants.FAILURE_CREATE;
		}
	}

	public synchronized String create(String key, JSONObject value, int timeToLive) {
		try {
			// validate the key
			if (!ValidationUtils.isKeyNameValid(key)) {
				return DataStoreConstants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (isKeyExistInFile(key)) {
				return DataStoreConstants.FAILURE_KEY_ALREADY_AVAILABLE;
			}
			long creationTime = new Date().getTime();
			DataDetails data = new DataDetails(key, timeToLive, value, creationTime);
			if (FileUtils.writeData(data, filePath)) {
				return DataStoreConstants.SUCCESS_CREATE;
			} else {
				return DataStoreConstants.FAILURE_CREATE;
			}
		} catch (Exception exception) {
			return DataStoreConstants.FAILURE_CREATE;
		}
	}

	public synchronized Object read(String key) {
		try {
			// validate the key
			if (!ValidationUtils.isKeyNameValid(key)) {
				return DataStoreConstants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!isKeyExistInFile(key)) {
				return DataStoreConstants.FAILURE_KEY_NOT_AVAILABLE;
			}
			HashMap<String, DataDetails> dataMap = FileUtils.readData(filePath);
			if(dataMap.containsKey(key)) {
				return dataMap.get(key);
			}
			return DataStoreConstants.FAILURE_READ;
		} catch (Exception exception) {
			return DataStoreConstants.FAILURE_READ;
		}
	}
	
	public synchronized Object delete(String key) {
		try {
			// validate the key
			if (!ValidationUtils.isKeyNameValid(key)) {
				return DataStoreConstants.FAILURE_KEY_LENGTH_EXCEEDED;
			}
			if (!isKeyExistInFile(key)) {
				return DataStoreConstants.FAILURE_KEY_NOT_AVAILABLE;
			}
			if (FileUtils.deleteKey(key, filePath)) {
				return DataStoreConstants.SUCCESS_DELETE;
			}
			return DataStoreConstants.FAILURE_DELETE;
		} catch (Exception exception) {
			return DataStoreConstants.FAILURE_DELETE;
		}
	}

	private boolean isKeyExistInFile(String key) {
		HashMap<String, DataDetails> dataMap = FileUtils.readData(filePath);
		if (dataMap != null && dataMap.containsKey(key)) {
			// key exist
			// now with check time to live
			DataDetails data = dataMap.get(key);
			long currentTime = new Date().getTime();
			if (data.getTimeToLive() > 0 && (currentTime - data.getCreationTime()) >= (data.getTimeToLive()
					* DataStoreConstants.MILLISECONDS)) {
				FileUtils.deleteKey(key, filePath);
				return false;
			}
			return true;
		}
		return false;
	}
}
