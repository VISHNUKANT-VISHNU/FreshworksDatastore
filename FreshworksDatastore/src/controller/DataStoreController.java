package controller;

import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constants.DataStoreConstants;
import service.DataStoreService;

public class DataStoreController {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("*****Welcome to DataStore*****");
		System.out.println("Continue with default path(Y/N)?");
		String choice = sc.next();
		String path = null;
		if (choice.equalsIgnoreCase("y")) {
			path = System.getProperty("user.dir") + "\\" + DataStoreConstants.FILE_NAME;
		} else if (choice.equalsIgnoreCase("n")) {
			System.out.println("Enter new path (without fileName) : ");
			path = sc.next();
			path = path + "\\" + DataStoreConstants.FILE_NAME;
		} else {
			System.out.println(DataStoreConstants.INVALID_CHOICE);
			sc.close();
			return;
		}

		DataStoreService dataStoreService = new DataStoreService(path);
		int selectValue = 4, counter = 0;
		do {
			try {
				System.out.println("Select operation to perform :");
				System.out.println("1. Create key \n2. Read Key \n3. Delete Key \n4. Exit");
				selectValue = sc.nextInt();
				switch (selectValue) {
				case 1: // create
					createKey(dataStoreService, sc);
					break;
				case 2: // read
					readKey(dataStoreService, sc);
					break;
				case 3: // delete
					deleteKey(dataStoreService, sc);
					break;
				case 4: // exit
					break;
				default: // try again
					System.out.println(DataStoreConstants.INVALID_SELECTED_VALUE);
					counter++;
					if (counter == 3) {
						System.out.println(DataStoreConstants.RETRIES_EXHAUSTED);
						selectValue = 4;
					}
					break;
				}
			} catch (Exception e) {
				System.out.println(DataStoreConstants.LAST_INPUT_VALIDATION_FAILED);
			}
		} while (selectValue != 4);
		sc.close();
	}

	private static void createKey(DataStoreService dataStoreService, Scanner sc) {
		System.out.println("Enter key to be created : ");
		String key = sc.next();
		System.out.println("Enter json value for key : ");
		String value = sc.next();
		JSONParser parser = new JSONParser();
		try {
			JSONObject valJsonObject = (JSONObject) parser.parse(value);
			System.out.println("Want to set Time to live for key(Y/N)?");
			String choice = sc.next();
			int timeToLive = 0;
			if (choice.equalsIgnoreCase("y")) {
				System.out.println("Enter Time to live value for key (in seconds)");
				timeToLive = sc.nextInt();
			} else if (choice.equalsIgnoreCase("n")) {
				timeToLive = -1;
			} else {
				System.out.println(DataStoreConstants.INVALID_CHOICE);
				return;
			}
			System.out.println(dataStoreService.create(key, valJsonObject, timeToLive));
		} catch (ParseException e) {
			System.out.println(DataStoreConstants.INVALID_JSON_VALUE);
		} catch (Exception e) {
			System.out.println(DataStoreConstants.LAST_INPUT_VALIDATION_FAILED);
		}
	}

	private static void readKey(DataStoreService dataStoreService, Scanner sc) {
		System.out.println("Enter key to be read : ");
		String key = sc.next();
		System.out.println(dataStoreService.read(key));
	}

	private static void deleteKey(DataStoreService dataStoreService, Scanner sc) {
		System.out.println("Enter key to be deleted : ");
		String key = sc.next();
		System.out.println(dataStoreService.delete(key));
	}

}
