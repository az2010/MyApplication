package test.owen.com.myapplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

public class LogUtils {

    private static final String GLOBAL_TAG = "GN_GameHall";
    private static final String SAVELOG_FILE_NAME = "gamelog.txt";
    private static final String ENABLE_SAVELOG_FLAG_FOLDER = "log";
    private static final String TAG = "LogUtils";
    private static final int SINGLE_LOG_LENGTH = 3000;
    private static boolean sEnableLog = true;
    private static boolean sIsSaveLog = false;

    private static SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS", Locale.US);

    public static void log(String tag, String msg) {
        log(tag, msg, sIsSaveLog);
    }

    private static void log(String tag, String msg, boolean isSave) {
        if (sEnableLog) {
            if (msg.length() > SINGLE_LOG_LENGTH) {
                Log.i(GLOBAL_TAG + "." + tag, "" + msg.substring(0, SINGLE_LOG_LENGTH));
                log(tag, msg.substring(SINGLE_LOG_LENGTH), false);
            } else {
                Log.i(GLOBAL_TAG + "." + tag, "" + msg);
            }

            if (isSave) {
                try {
                    saveToSDCard(formatLog(msg, tag, "i"));
                } catch (Exception e) {
                }
            }
        }
    }

    public static void logv(String tag, String msg) {
        if (sEnableLog) {
            Log.v(GLOBAL_TAG + "." + tag, "" + msg);
            if (sIsSaveLog) {
                try {
                    saveToSDCard(formatLog(msg, tag, "V"));
                } catch (Exception e) {
                }
            }
        }
    }

    public static void logd(String tag, String msg) {
        if (sEnableLog) {
            Log.d(GLOBAL_TAG + "." + tag, msg);
            if (sIsSaveLog) {
                try {
                    saveToSDCard(formatLog(msg, tag, "D"));
                } catch (Exception e) {
                }
            }
        }
    }

    public static void loge(String tag, String msg) {
        if (sEnableLog) {
            Log.e(GLOBAL_TAG + "." + tag + ".E", "" + msg);
            if (sIsSaveLog) {
                try {
                    saveToSDCard(formatLog(msg, tag, "E"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loge(String tag, String msg, Throwable e) {
        if (sEnableLog) {
            Log.e(GLOBAL_TAG + "." + tag, "" + msg, e);
            if (sIsSaveLog) {
                try {
                    saveToSDCard(formatLog(msg, tag, "E"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void e(String msg) {
        Log.e("TAG", msg);
    }

    public static void saveToSDCard(String content) {
//        if (isSDCardMounted()) {
//            try {
//                String data = "Time : ";
//                synchronized (sFormatter) {
//                    data = data + sFormatter.format(Calendar.getInstance().getTime()) + "\n" + content + "\n";
//                }
//                String sdCardDir = StorageUtils.getHomeDirAbsolute();
//                File file = new File(sdCardDir, SAVELOG_FILE_NAME);
//                RandomAccessFile raf = new RandomAccessFile(file, "rw");
//                raf.seek(file.length());
//                raf.write(data.getBytes(Constant.UTF_8));
//                raf.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static String getFunctionName() {
        StringBuffer sb = new StringBuffer();
        sb.append("-> ");
        sb.append(Thread.currentThread().getStackTrace()[3].getMethodName());
        sb.append("()");
        sb.append("-> ");
        return sb.toString();
    }

    public static String getThreadName() {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(Thread.currentThread().getName());
            sb.append("-> ");
            sb.append(Thread.currentThread().getStackTrace()[3].getMethodName());
            sb.append("()");
            sb.append(" ");
        } catch (Exception e) {
            LogUtils.loge(TAG, e.getMessage());
        }
        return sb.toString();
    }

    public static void printStack() {
        if (sEnableLog) {
            try {
                throw new Exception("printStack");
            } catch (Exception e) {
                printException(e);
            }
        }
    }

    public static void printException(Exception e) {
        if (sEnableLog) {
            Log.e("azheng", e.getLocalizedMessage(), e);
        }
    }

    private static String formatLog(String log, String type, String level) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        synchronized (sFormatter) {
            builder.append(sFormatter.format(Calendar.getInstance().getTime()));
        }
        builder.append("][");
        builder.append(type);
        builder.append("][");
        builder.append(level);
        builder.append("]");
        builder.append(log);
        builder.append("\n");
        return builder.toString();
    }

    private static String getExternalStorageDirectory() {
        String rootpath = Environment.getExternalStorageDirectory().getPath();
        if (!rootpath.endsWith(File.separator)) {
            rootpath += File.separator;
        }
        Log.d(TAG, "getExternalStorageDirectory() path = " + rootpath);
        return rootpath;
    }

}
