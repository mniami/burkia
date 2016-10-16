package pl.guideme.data.datas;


import org.androidannotations.annotations.EBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

@EBean(scope = EBean.Scope.Singleton)
public class DataCache {
    private final Object locker = new Object();
    private File cacheName;
    private boolean isInitialized;

    public void init(File databasePath) {
        if (isInitialized) {
            return;
        }
        isInitialized = true;

        cacheName = databasePath;

        if (!cacheName.exists()) {
            cacheName.mkdirs();
        }
    }

    public <T> T get(String key, Class<T> type) {
        synchronized (locker) {
            if (key == null) {
                return null;
            }
            FileInputStream fin = null;
            File file = new File(cacheName, key);
            T result = null;

            if (!file.exists()) {
                return result;
            }

            boolean exceptionOccurred = false;
            try {
                fin = new FileInputStream(file);
                byte[] data = readFileBytes(fin);
                Object o = fromByteArr(data);

                if (o != null) {
                    if (o.getClass().isAssignableFrom(type)) {
                        result = (T) fromByteArr(data);
                    } else {
                        exceptionOccurred = true;
                    }
                } else {
                    exceptionOccurred = true;
                }
            } catch (IOException e) {
                exceptionOccurred = true;
            } catch (ClassNotFoundException e) {
                exceptionOccurred = true;
            } catch (ClassCastException cce) {
                exceptionOccurred = true;
            } catch (Throwable th) {
                exceptionOccurred = true;
            } finally {
                if (fin != null) {
                    try {
                        fin.close();
                    } catch (IOException e) {
                    }
                }
            }
            if (exceptionOccurred) {
                file.delete();
            }

            return result;
        }
    }

    public static byte[] readFileBytes(FileInputStream in) {
        byte[] buffer = new byte[1024 * 1024];
        byte[] bufferCumulative = new byte[0];
        int bytesRead = 0;
        try {
            do {
                bytesRead = in.read(buffer, 0, buffer.length);
                byte[] tmp = new byte[bufferCumulative.length + bytesRead];
                System.arraycopy(bufferCumulative, 0, tmp, 0, bufferCumulative.length);
                System.arraycopy(buffer, 0, tmp, bufferCumulative.length, bytesRead);
                bufferCumulative = tmp;
            } while (bytesRead == buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferCumulative;
    }

    public static Object fromByteArr(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (StreamCorruptedException ex) {
        } finally {
            try {
                bis.close();
            } catch (IOException ignored) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
        return o;
    }

    public <T> void put(String key, T item) {
        synchronized (locker) {
            File file = new File(cacheName, key);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                }
            }
            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(file);
                byte[] data = fromObject(item);
                byte[] encryptedData = data;
                if (encryptedData == null) {
                    return;
                }
                fout.write(encryptedData, 0, encryptedData.length);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (fout != null) {
                    try {
                        fout.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    public static byte[] fromObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            bytes = bos.toByteArray();
        } catch (Exception ignored) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
            try {
                bos.close();
            } catch (IOException ignored) {
            }
        }
        return bytes;
    }

    public void clearCacheStartedWith(String prefix) {
        synchronized (locker) {
            File file = new File(cacheName.getAbsolutePath());
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.startsWith(prefix);
                }
            };
            try {
                String[] cmsFiles = file.list(filter);
                for (String fileName : cmsFiles) {
                    File cmsFile = new File(fileName);
                    if (cmsFile.exists()) {
                        cmsFile.delete();
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    public void clear(String cacheKey) {
        File file = new File(cacheName, cacheKey);
        if (file.exists()) {
            file.delete();
        }
    }
}
