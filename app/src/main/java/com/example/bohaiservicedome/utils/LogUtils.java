package com.example.bohaiservicedome.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

/**
* @author: JiangNan
* @Date:2020/4/4
 *  * Log统一管理类
*/
public class LogUtils {

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "way";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, "----->" + msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void logSaveFile(final String log) {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File outputFileDir = new File(Environment.getExternalStorageDirectory() + File.separator + "iChoiceBp");
                    if (!outputFileDir.exists()) {
                        outputFileDir.mkdirs();
                    }
                    FileWriter fw = new FileWriter(outputFileDir + File.separator + "BpLog.txt", true);
                    fw.write("日志时间：" + FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime) + log + "\r\n\n");
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void log2File(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "ytjLog.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void log2File2(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "ytjLoglilinhai.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log2FileCS1Result(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "hdfYTJ" + "/" + "CS1RESULT.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void log2File3(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "cacacacacaqca.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void log2File4(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "ecgzhuanyong.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log2File5(String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + "ecgpackage.txt", true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log2File(String fileName, String log) {
        try {
            FileWriter fw = new FileWriter(Environment
                    .getExternalStorageDirectory().getPath() + File.separator + fileName, true);
            fw.write(log + "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readFileByBytes(String fileName) {

        File file = new File(fileName);
        InputStream in = null;
        byte[] tempbytes = new byte[2];

        int byteread = 0;
        try {
            in = new FileInputStream(fileName);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                String s = new String(tempbytes);
                Log.e("cs1=================", s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


}