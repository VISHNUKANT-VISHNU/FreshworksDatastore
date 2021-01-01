# DataStore
A file-based key-value data store that supports the basic CRD (create, read, and delete) operations. This data store is meant to be used as a local storage for one single process on one laptop. The data store can be exposed as a library to clients that can instantiate a class and work with the data store.

#DataStore.jar
This is a runnable jar. Based on user input different functionalities is performed

How to run jar? -> 
1. Place jar in any directory
2. Open CMD, navigate to that folder.
3. Run command -> java -jar <jar_name>

#DataStoreService
1. public DataStoreService(); -> Initialize with Default path (Default path will be place where we run the jar file)

2. public DataStoreService(String filePath); -> Initialize path with file path by user

3. public synchronized String create(String key, JSONObject value); -> Create key in datastore without time to live value

4. public synchronized String create(String key, JSONObject value, int timeToLive); -> Create key in datastore

5. public synchronized Object read(String key); -> Read key from datastore

6. public synchronized Object delete(String key); -> Delete key from datastore