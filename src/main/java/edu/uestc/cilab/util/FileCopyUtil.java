package edu.uestc.cilab.util;
import	java.util.ArrayList;

import java.io.*;
import java.util.List;

/**
 * @author: zhangfeng
 * @date: 2020-05-10 16:12
 * @description: 文件相关工具类
 */
public class FileCopyUtil {
    /**
     *
     * @param  oldFilePath
     * @param  newFilePath
     * @return 返回复制的文件路径list
     */
    public static List<String> copyFileToNewFile(String oldFilePath, String newFilePath) {

        File destFile = new File(newFilePath);
        if (!destFile.exists()){
            destFile.mkdirs();
        }
        File[] oldFileList = new File(oldFilePath).listFiles();
        List<String> newFilePathList = new ArrayList<String> ();
        for (File file : oldFileList){
            String newFileName = System.currentTimeMillis() + file.getName();
            newFilePathList.add(newFilePath + File.separator + newFileName);
            File newFile = new File(newFilePath, newFileName);
            try {
                newFile.createNewFile();
                fileChannelCopy(file, newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFilePathList;
    }
    public static void fileChannelCopy(File s, File t) { // 以流的方式读取、写入文件
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
