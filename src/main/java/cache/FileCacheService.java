package cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Create sipmle Cache service for save/load files insperation:
 * http://www.tutorialspoint.com/guava/guava_caching_utilities.htm
 *
 * @author chlupnoha
 */
public class FileCacheService {

    private static final FileCacheService cacheInstance = new FileCacheService();

    private final LoadingCache<String, File> fileCache
            = CacheBuilder.newBuilder()
            .maximumSize(100) // maximum 100 records can be cached
            .expireAfterAccess(30, TimeUnit.MINUTES) // cache will expire after 30 minutes of access
            .build(new CacheLoader<String, File>() { // build the cacheloader
                @Override
                public File load(String path) throws Exception {
                    return getFile(path);
                }

            });

    private FileCacheService() {
    }

    // Runtime initialization
    // By defualt ThreadSafe
    public static FileCacheService getInstance() {
        return cacheInstance;
    }

    private synchronized File getFile(String path) throws ExecutionException, ExecutionException, ExecutionException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }
    
    public synchronized File getFileFromCach(String path) throws ExecutionException {
        File f = fileCache.get(path);
        return f;
    }
}