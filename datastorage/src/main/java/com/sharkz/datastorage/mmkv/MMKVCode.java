package com.sharkz.datastorage.mmkv;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/15  09:19
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MMKVCode {

}

// TODO 下面是源码
/*

下面是源码

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tencent.mmkv;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.Parcelable.Creator;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MMKV implements SharedPreferences, Editor {
    private static EnumMap<MMKVRecoverStrategic, Integer> recoverIndex = new EnumMap(MMKVRecoverStrategic.class);
    private static EnumMap<MMKVLogLevel, Integer> logLevel2Index;
    private static MMKVLogLevel[] index2LogLevel;
    private static String rootDir;
    public static final int SINGLE_PROCESS_MODE = 1;
    public static final int MULTI_PROCESS_MODE = 2;
    private static final int CONTEXT_MODE_MULTI_PROCESS = 4;
    private static final int ASHMEM_MODE = 8;
    private static final HashMap<String, Creator<?>> mCreators;
    private static MMKVHandler gCallbackHandler;
    private static boolean gWantLogReDirecting;
    private static MMKVContentChangeNotification gContentChangeNotify;
    private long nativeHandle;

    public static String initialize(Context context) {
        String root = context.getFilesDir().getAbsolutePath() + "/mmkv";
        MMKVLogLevel logLevel = MMKVLogLevel.LevelInfo;
        return initialize(root, (MMKV.LibLoader)null, logLevel);
    }

    public static String initialize(Context context, MMKVLogLevel logLevel) {
        String root = context.getFilesDir().getAbsolutePath() + "/mmkv";
        return initialize(root, (MMKV.LibLoader)null, logLevel);
    }

    public static String initialize(String rootDir) {
        MMKVLogLevel logLevel = MMKVLogLevel.LevelInfo;
        return initialize(rootDir, (MMKV.LibLoader)null, logLevel);
    }

    public static String initialize(String rootDir, MMKVLogLevel logLevel) {
        return initialize(rootDir, (MMKV.LibLoader)null, logLevel);
    }

    public static String initialize(String rootDir, MMKV.LibLoader loader) {
        MMKVLogLevel logLevel = MMKVLogLevel.LevelInfo;
        return initialize(rootDir, loader, logLevel);
    }

    public static String initialize(String rootDir, MMKV.LibLoader loader, MMKVLogLevel logLevel) {
        if (loader != null) {
            if ("StaticCpp".equals("SharedCpp")) {
                loader.loadLibrary("c++_shared");
            }

            loader.loadLibrary("mmkv");
        } else {
            if ("StaticCpp".equals("SharedCpp")) {
                System.loadLibrary("c++_shared");
            }

            System.loadLibrary("mmkv");
        }

        MMKV.rootDir = rootDir;
        jniInitialize(MMKV.rootDir, logLevel2Int(logLevel));
        return rootDir;
    }

    public static String getRootDir() {
        return rootDir;
    }

    private static int logLevel2Int(MMKVLogLevel level) {
        byte realLevel;
        switch(level) {
        case LevelDebug:
            realLevel = 0;
            break;
        case LevelInfo:
            realLevel = 1;
            break;
        case LevelWarning:
            realLevel = 2;
            break;
        case LevelError:
            realLevel = 3;
            break;
        case LevelNone:
            realLevel = 4;
            break;
        default:
            realLevel = 1;
        }

        return realLevel;
    }

    public static void setLogLevel(MMKVLogLevel level) {
        int realLevel = logLevel2Int(level);
        setLogLevel(realLevel);
    }

    public static native void onExit();

    public static MMKV mmkvWithID(String mmapID) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getMMKVWithID(mmapID, 1, (String)null, (String)null);
            return new MMKV(handle);
        }
    }

    public static MMKV mmkvWithID(String mmapID, int mode) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getMMKVWithID(mmapID, mode, (String)null, (String)null);
            return new MMKV(handle);
        }
    }

    public static MMKV mmkvWithID(String mmapID, int mode, String cryptKey) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getMMKVWithID(mmapID, mode, cryptKey, (String)null);
            return new MMKV(handle);
        }
    }

    @Nullable
    public static MMKV mmkvWithID(String mmapID, String relativePath) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getMMKVWithID(mmapID, 1, (String)null, relativePath);
            return handle == 0L ? null : new MMKV(handle);
        }
    }

    @Nullable
    public static MMKV mmkvWithID(String mmapID, int mode, String cryptKey, String relativePath) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getMMKVWithID(mmapID, mode, cryptKey, relativePath);
            return handle == 0L ? null : new MMKV(handle);
        }
    }

    @Nullable
    public static MMKV mmkvWithAshmemID(Context context, String mmapID, int size, int mode, String cryptKey) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            String processName = MMKVContentProvider.getProcessNameByPID(context, Process.myPid());
            if (processName != null && processName.length() != 0) {
                if (processName.contains(":")) {
                    Uri uri = MMKVContentProvider.contentUri(context);
                    if (uri == null) {
                        simpleLog(MMKVLogLevel.LevelError, "MMKVContentProvider has invalid authority");
                        return null;
                    } else {
                        simpleLog(MMKVLogLevel.LevelInfo, "getting parcelable mmkv in process, Uri = " + uri);
                        Bundle extras = new Bundle();
                        extras.putInt("KEY_SIZE", size);
                        extras.putInt("KEY_MODE", mode);
                        if (cryptKey != null) {
                            extras.putString("KEY_CRYPT", cryptKey);
                        }

                        ContentResolver resolver = context.getContentResolver();
                        Bundle result = resolver.call(uri, "mmkvFromAshmemID", mmapID, extras);
                        if (result != null) {
                            result.setClassLoader(ParcelableMMKV.class.getClassLoader());
                            ParcelableMMKV parcelableMMKV = (ParcelableMMKV)result.getParcelable("KEY");
                            if (parcelableMMKV != null) {
                                MMKV mmkv = parcelableMMKV.toMMKV();
                                if (mmkv != null) {
                                    simpleLog(MMKVLogLevel.LevelInfo, mmkv.mmapID() + " fd = " + mmkv.ashmemFD() + ", meta fd = " + mmkv.ashmemMetaFD());
                                }

                                return mmkv;
                            }
                        }

                        return null;
                    }
                } else {
                    simpleLog(MMKVLogLevel.LevelInfo, "getting mmkv in main process");
                    mode |= 8;
                    long handle = getMMKVWithIDAndSize(mmapID, size, mode, cryptKey);
                    return new MMKV(handle);
                }
            } else {
                simpleLog(MMKVLogLevel.LevelError, "process name detect fail, try again later");
                return null;
            }
        }
    }

    public static MMKV defaultMMKV() {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getDefaultMMKV(1, (String)null);
            return new MMKV(handle);
        }
    }

    public static MMKV defaultMMKV(int mode, String cryptKey) {
        if (rootDir == null) {
            throw new IllegalStateException("You should Call MMKV.initialize() first.");
        } else {
            long handle = getDefaultMMKV(mode, cryptKey);
            return new MMKV(handle);
        }
    }

    public native String cryptKey();

    public native boolean reKey(String var1);

    public native void checkReSetCryptKey(String var1);

    public static native int pageSize();

    public native String mmapID();

    public native void lock();

    public native void unlock();

    public native boolean tryLock();

    public boolean encode(String key, boolean value) {
        return this.encodeBool(this.nativeHandle, key, value);
    }

    public boolean decodeBool(String key) {
        return this.decodeBool(this.nativeHandle, key, false);
    }

    public boolean decodeBool(String key, boolean defaultValue) {
        return this.decodeBool(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, int value) {
        return this.encodeInt(this.nativeHandle, key, value);
    }

    public int decodeInt(String key) {
        return this.decodeInt(this.nativeHandle, key, 0);
    }

    public int decodeInt(String key, int defaultValue) {
        return this.decodeInt(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, long value) {
        return this.encodeLong(this.nativeHandle, key, value);
    }

    public long decodeLong(String key) {
        return this.decodeLong(this.nativeHandle, key, 0L);
    }

    public long decodeLong(String key, long defaultValue) {
        return this.decodeLong(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, float value) {
        return this.encodeFloat(this.nativeHandle, key, value);
    }

    public float decodeFloat(String key) {
        return this.decodeFloat(this.nativeHandle, key, 0.0F);
    }

    public float decodeFloat(String key, float defaultValue) {
        return this.decodeFloat(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, double value) {
        return this.encodeDouble(this.nativeHandle, key, value);
    }

    public double decodeDouble(String key) {
        return this.decodeDouble(this.nativeHandle, key, 0.0D);
    }

    public double decodeDouble(String key, double defaultValue) {
        return this.decodeDouble(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, String value) {
        return this.encodeString(this.nativeHandle, key, value);
    }

    public String decodeString(String key) {
        return this.decodeString(this.nativeHandle, key, (String)null);
    }

    public String decodeString(String key, String defaultValue) {
        return this.decodeString(this.nativeHandle, key, defaultValue);
    }

    public boolean encode(String key, Set<String> value) {
        return this.encodeSet(this.nativeHandle, key, (String[])value.toArray(new String[0]));
    }

    public Set<String> decodeStringSet(String key) {
        return this.decodeStringSet(key, (Set)null);
    }

    public Set<String> decodeStringSet(String key, Set<String> defaultValue) {
        return this.decodeStringSet(key, defaultValue, HashSet.class);
    }

    public Set<String> decodeStringSet(String key, Set<String> defaultValue, Class<? extends Set> cls) {
        String[] result = this.decodeStringSet(this.nativeHandle, key);
        if (result == null) {
            return defaultValue;
        } else {
            Set a;
            try {
                a = (Set)cls.newInstance();
            } catch (IllegalAccessException var7) {
                return defaultValue;
            } catch (InstantiationException var8) {
                return defaultValue;
            }

            a.addAll(Arrays.asList(result));
            return a;
        }
    }

    public boolean encode(String key, byte[] value) {
        return this.encodeBytes(this.nativeHandle, key, value);
    }

    public byte[] decodeBytes(String key) {
        return this.decodeBytes(key, (byte[])null);
    }

    public byte[] decodeBytes(String key, byte[] defaultValue) {
        byte[] ret = this.decodeBytes(this.nativeHandle, key);
        return ret != null ? ret : defaultValue;
    }

    public boolean encode(String key, Parcelable value) {
        Parcel source = Parcel.obtain();
        value.writeToParcel(source, value.describeContents());
        byte[] bytes = source.marshall();
        source.recycle();
        return this.encodeBytes(this.nativeHandle, key, bytes);
    }

    public <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass) {
        return this.decodeParcelable(key, tClass, (Parcelable)null);
    }

    public <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass, T defaultValue) {
        if (tClass == null) {
            return defaultValue;
        } else {
            byte[] bytes = this.decodeBytes(this.nativeHandle, key);
            if (bytes == null) {
                return defaultValue;
            } else {
                Parcel source = Parcel.obtain();
                source.unmarshall(bytes, 0, bytes.length);
                source.setDataPosition(0);

                Parcelable var8;
                try {
                    String name = tClass.toString();
                    Creator creator;
                    synchronized(mCreators) {
                        creator = (Creator)mCreators.get(name);
                        if (creator == null) {
                            Field f = tClass.getField("CREATOR");
                            creator = (Creator)f.get((Object)null);
                            if (creator != null) {
                                mCreators.put(name, creator);
                            }
                        }
                    }

                    if (creator == null) {
                        throw new Exception("Parcelable protocol requires a non-null static Parcelable.Creator object called CREATOR on class " + name);
                    }

                    var8 = (Parcelable)creator.createFromParcel(source);
                } catch (Exception var16) {
                    simpleLog(MMKVLogLevel.LevelError, var16.toString());
                    return defaultValue;
                } finally {
                    source.recycle();
                }

                return var8;
            }
        }
    }

    public int getValueSize(String key) {
        return this.valueSize(this.nativeHandle, key, false);
    }

    public int getValueActualSize(String key) {
        return this.valueSize(this.nativeHandle, key, true);
    }

    public boolean containsKey(String key) {
        return this.containsKey(this.nativeHandle, key);
    }

    public native String[] allKeys();

    public long count() {
        return this.count(this.nativeHandle);
    }

    public long totalSize() {
        return this.totalSize(this.nativeHandle);
    }

    public void removeValueForKey(String key) {
        this.removeValueForKey(this.nativeHandle, key);
    }

    public native void removeValuesForKeys(String[] var1);

    public native void clearAll();

    public native void trim();

    public native void close();

    public native void clearMemoryCache();

    public void sync() {
        this.sync(true);
    }

    public void async() {
        this.sync(false);
    }

    private native void sync(boolean var1);

    public static native boolean isFileValid(String var0);

    public int importFromSharedPreferences(SharedPreferences preferences) {
        Map<String, ?> kvs = preferences.getAll();
        if (kvs != null && kvs.size() > 0) {
            Iterator var3 = kvs.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, ?> entry = (Entry)var3.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    if (value instanceof Boolean) {
                        this.encodeBool(this.nativeHandle, key, (Boolean)value);
                    } else if (value instanceof Integer) {
                        this.encodeInt(this.nativeHandle, key, (Integer)value);
                    } else if (value instanceof Long) {
                        this.encodeLong(this.nativeHandle, key, (Long)value);
                    } else if (value instanceof Float) {
                        this.encodeFloat(this.nativeHandle, key, (Float)value);
                    } else if (value instanceof Double) {
                        this.encodeDouble(this.nativeHandle, key, (Double)value);
                    } else if (value instanceof String) {
                        this.encodeString(this.nativeHandle, key, (String)value);
                    } else if (value instanceof Set) {
                        this.encode(key, (Set)value);
                    } else {
                        simpleLog(MMKVLogLevel.LevelError, "unknown type: " + value.getClass());
                    }
                }
            }

            return kvs.size();
        } else {
            return 0;
        }
    }

    public Map<String, ?> getAll() {
        throw new UnsupportedOperationException("use allKeys() instead, getAll() not implement because type-erasure inside mmkv");
    }

    @Nullable
    public String getString(String key, @Nullable String defValue) {
        return this.decodeString(this.nativeHandle, key, defValue);
    }

    public Editor putString(String key, @Nullable String value) {
        this.encodeString(this.nativeHandle, key, value);
        return this;
    }

    @Nullable
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return this.decodeStringSet(key, defValues);
    }

    public Editor putStringSet(String key, @Nullable Set<String> values) {
        this.encode(key, values);
        return this;
    }

    public Editor putBytes(String key, @Nullable byte[] bytes) {
        this.encode(key, bytes);
        return this;
    }

    public byte[] getBytes(String key, @Nullable byte[] defValue) {
        return this.decodeBytes(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return this.decodeInt(this.nativeHandle, key, defValue);
    }

    public Editor putInt(String key, int value) {
        this.encodeInt(this.nativeHandle, key, value);
        return this;
    }

    public long getLong(String key, long defValue) {
        return this.decodeLong(this.nativeHandle, key, defValue);
    }

    public Editor putLong(String key, long value) {
        this.encodeLong(this.nativeHandle, key, value);
        return this;
    }

    public float getFloat(String key, float defValue) {
        return this.decodeFloat(this.nativeHandle, key, defValue);
    }

    public Editor putFloat(String key, float value) {
        this.encodeFloat(this.nativeHandle, key, value);
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.decodeBool(this.nativeHandle, key, defValue);
    }

    public Editor putBoolean(String key, boolean value) {
        this.encodeBool(this.nativeHandle, key, value);
        return this;
    }

    public Editor remove(String key) {
        this.removeValueForKey(key);
        return this;
    }

    public Editor clear() {
        this.clearAll();
        return this;
    }

    public boolean commit() {
        this.sync(true);
        return true;
    }

    public void apply() {
        this.sync(false);
    }

    public boolean contains(String key) {
        return this.containsKey(key);
    }

    public Editor edit() {
        return this;
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException("Not implement in MMKV");
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException("Not implement in MMKV");
    }

    public static MMKV mmkvWithAshmemFD(String mmapID, int fd, int metaFD, String cryptKey) {
        long handle = getMMKVWithAshmemFD(mmapID, fd, metaFD, cryptKey);
        return new MMKV(handle);
    }

    public native int ashmemFD();

    public native int ashmemMetaFD();

    public static NativeBuffer createNativeBuffer(int size) {
        long pointer = createNB(size);
        return pointer <= 0L ? null : new NativeBuffer(pointer, size);
    }

    public static void destroyNativeBuffer(NativeBuffer buffer) {
        destroyNB(buffer.pointer, buffer.size);
    }

    public int writeValueToNativeBuffer(String key, NativeBuffer buffer) {
        return this.writeValueToNB(this.nativeHandle, key, buffer.pointer, buffer.size);
    }

    public static void registerHandler(MMKVHandler handler) {
        gCallbackHandler = handler;
        if (gCallbackHandler.wantLogRedirecting()) {
            setCallbackHandler(true, true);
            gWantLogReDirecting = true;
        } else {
            setCallbackHandler(false, true);
            gWantLogReDirecting = false;
        }

    }

    public static void unregisterHandler() {
        gCallbackHandler = null;
        setCallbackHandler(false, false);
        gWantLogReDirecting = false;
    }

    private static int onMMKVCRCCheckFail(String mmapID) {
        MMKVRecoverStrategic strategic = MMKVRecoverStrategic.OnErrorDiscard;
        if (gCallbackHandler != null) {
            strategic = gCallbackHandler.onMMKVCRCCheckFail(mmapID);
        }

        simpleLog(MMKVLogLevel.LevelInfo, "Recover strategic for " + mmapID + " is " + strategic);
        Integer value = (Integer)recoverIndex.get(strategic);
        return value == null ? 0 : value;
    }

    private static int onMMKVFileLengthError(String mmapID) {
        MMKVRecoverStrategic strategic = MMKVRecoverStrategic.OnErrorDiscard;
        if (gCallbackHandler != null) {
            strategic = gCallbackHandler.onMMKVFileLengthError(mmapID);
        }

        simpleLog(MMKVLogLevel.LevelInfo, "Recover strategic for " + mmapID + " is " + strategic);
        Integer value = (Integer)recoverIndex.get(strategic);
        return value == null ? 0 : value;
    }

    private static void mmkvLogImp(int level, String file, int line, String function, String message) {
        if (gCallbackHandler != null && gWantLogReDirecting) {
            gCallbackHandler.mmkvLog(index2LogLevel[level], file, line, function, message);
        } else {
            switch(index2LogLevel[level]) {
            case LevelDebug:
                Log.d("MMKV", message);
                break;
            case LevelInfo:
                Log.i("MMKV", message);
                break;
            case LevelWarning:
                Log.w("MMKV", message);
                break;
            case LevelError:
                Log.e("MMKV", message);
            case LevelNone:
            }
        }

    }

    private static void simpleLog(MMKVLogLevel level, String message) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[stacktrace.length - 1];
        Integer i = (Integer)logLevel2Index.get(level);
        int intLevel = i == null ? 0 : i;
        mmkvLogImp(intLevel, e.getFileName(), e.getLineNumber(), e.getMethodName(), message);
    }

    public static void registerContentChangeNotify(MMKVContentChangeNotification notify) {
        gContentChangeNotify = notify;
        setWantsContentChangeNotify(gContentChangeNotify != null);
    }

    public static void unregisterContentChangeNotify() {
        gContentChangeNotify = null;
        setWantsContentChangeNotify(false);
    }

    private static void onContentChangedByOuterProcess(String mmapID) {
        if (gContentChangeNotify != null) {
            gContentChangeNotify.onContentChangedByOuterProcess(mmapID);
        }

    }

    private static native void setWantsContentChangeNotify(boolean var0);

    public native void checkContentChangedByOuterProcess();

    private MMKV(long handle) {
        this.nativeHandle = handle;
    }

    private static native void jniInitialize(String var0, int var1);

    private static native long getMMKVWithID(String var0, int var1, String var2, String var3);

    private static native long getMMKVWithIDAndSize(String var0, int var1, int var2, String var3);

    private static native long getDefaultMMKV(int var0, String var1);

    private static native long getMMKVWithAshmemFD(String var0, int var1, int var2, String var3);

    private native boolean encodeBool(long var1, String var3, boolean var4);

    private native boolean decodeBool(long var1, String var3, boolean var4);

    private native boolean encodeInt(long var1, String var3, int var4);

    private native int decodeInt(long var1, String var3, int var4);

    private native boolean encodeLong(long var1, String var3, long var4);

    private native long decodeLong(long var1, String var3, long var4);

    private native boolean encodeFloat(long var1, String var3, float var4);

    private native float decodeFloat(long var1, String var3, float var4);

    private native boolean encodeDouble(long var1, String var3, double var4);

    private native double decodeDouble(long var1, String var3, double var4);

    private native boolean encodeString(long var1, String var3, String var4);

    private native String decodeString(long var1, String var3, String var4);

    private native boolean encodeSet(long var1, String var3, String[] var4);

    private native String[] decodeStringSet(long var1, String var3);

    private native boolean encodeBytes(long var1, String var3, byte[] var4);

    private native byte[] decodeBytes(long var1, String var3);

    private native boolean containsKey(long var1, String var3);

    private native long count(long var1);

    private native long totalSize(long var1);

    private native void removeValueForKey(long var1, String var3);

    private native int valueSize(long var1, String var3, boolean var4);

    private static native void setLogLevel(int var0);

    private static native void setCallbackHandler(boolean var0, boolean var1);

    private static native long createNB(int var0);

    private static native void destroyNB(long var0, int var2);

    private native int writeValueToNB(long var1, String var3, long var4, int var6);

    static {
        recoverIndex.put(MMKVRecoverStrategic.OnErrorDiscard, 0);
        recoverIndex.put(MMKVRecoverStrategic.OnErrorRecover, 1);
        logLevel2Index = new EnumMap(MMKVLogLevel.class);
        logLevel2Index.put(MMKVLogLevel.LevelDebug, 0);
        logLevel2Index.put(MMKVLogLevel.LevelInfo, 1);
        logLevel2Index.put(MMKVLogLevel.LevelWarning, 2);
        logLevel2Index.put(MMKVLogLevel.LevelError, 3);
        logLevel2Index.put(MMKVLogLevel.LevelNone, 4);
        index2LogLevel = new MMKVLogLevel[]{MMKVLogLevel.LevelDebug, MMKVLogLevel.LevelInfo, MMKVLogLevel.LevelWarning, MMKVLogLevel.LevelError, MMKVLogLevel.LevelNone};
        rootDir = null;
        mCreators = new HashMap();
        gWantLogReDirecting = false;
    }

    public interface LibLoader {
        void loadLibrary(String var1);
    }
}


*/


// TODO 实现原理
/*

MMKV——基于 mmap 的高性能通用 key-value 组件
MMKV 是基于 mmap 内存映射的 key-value 组件，底层序列化/反序列化使用 protobuf 实现，性能高，稳定性强。
从 2015 年中至今在微信上使用，其性能和稳定性经过了时间的验证。近期也已移植到 Android / macOS / Win32 / POSIX 平台，一并开源。

MMKV 源起
在微信客户端的日常运营中，时不时就会爆发特殊文字引起系统的 crash，参考文章，文章里面设计的技术方案是在关键代码前后进行计数器的加减，
通过检查计数器的异常，来发现引起闪退的异常文字。在会话列表、会话界面等有大量 cell 的地方，希望新加的计时器不会影响滑动性能；
另外这些计数器还要永久存储下来——因为闪退随时可能发生。这就需要一个性能非常高的通用 key-value 存储组件，
我们考察了 SharedPreferences、NSUserDefaults、SQLite 等常见组件，发现都没能满足如此苛刻的性能要求。
考虑到这个防 crash 方案最主要的诉求还是实时写入，而 mmap 内存映射文件刚好满足这种需求，我们尝试通过它来实现一套 key-value 组件。

MMKV 原理
    内存准备
    通过 mmap 内存映射文件，提供一段可供随时写入的内存块，App 只管往里面写数据，由操作系统负责将内存回写到文件，不必担心 crash 导致数据丢失。
    数据组织
    数据序列化方面我们选用 protobuf 协议，pb 在性能和空间占用上都有不错的表现。
    写入优化
    考虑到主要使用场景是频繁地进行写入更新，我们需要有增量更新的能力。我们考虑将增量 kv 对象序列化后，append 到内存末尾。
    空间增长
    使用 append 实现增量更新带来了一个新的问题，就是不断 append 的话，文件大小会增长得不可控。我们需要在性能和空间上做个折中。
    更详细的设计原理参考 MMKV 原理。


*/
