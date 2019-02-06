package com.liskovsoft.appbackupmanager.support;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream; 

public class Compression
{
    final static String TAG = Compression.class.getSimpleName();
    public static int zip(File dir)
    {
        ArrayList<String> fileList = new ArrayList<>();
        byte[] buffer = new byte[1024];
        File zipDir = new File(dir.getAbsolutePath() + ".zip");
        String baseDir = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().length() - dir.getName().length());
        try(FileOutputStream fos = new FileOutputStream(zipDir);
            ZipOutputStream zos = new ZipOutputStream(fos)) {
            getFiles(dir, fileList);
            for(String file : fileList)
            {
                ZipEntry entry = new ZipEntry(file.substring(baseDir.length(), file.length()));
                try(FileInputStream in = new FileInputStream(file)) {
                    zos.putNextEntry(entry);
                    int len;
                    while((len = in.read(buffer)) > 0)
                    {
                        zos.write(buffer, 0, len);
                    }
                }
                catch(FileNotFoundException e)
                {
                    // some files in /data/system/ can give this error (e.g. ndebugsocket)
                }
            }
            zos.closeEntry();
            zos.close();
            return 0;
        }
        catch(ZipException e)
        {
            if(e.toString().contains("No entries"))
            {
                return 2;
            }
            else
            {
                e.printStackTrace();
                Log.i(TAG, e.toString());
                return 1;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.i(TAG, e.toString());
            return 1;
        }
    }
    public static int unzip(File zipfile, File outputDir)
    {
        return unzip(zipfile, outputDir, null);
    }
    public static int unzip(File zipfile, File outputDir, ArrayList<String> files)
    {
        byte[] buffer = new byte[1024];
        try(FileInputStream in = new FileInputStream(zipfile);
                ZipInputStream zis = new ZipInputStream(in)) {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null)
            {
                String filename = entry.getName();
                // if a list of filenames is given, only extract those files
                if(files != null && !files.contains(filename))
                    continue;
                File file = new File(outputDir, filename);
                new File(file.getParent()).mkdirs();
                try(FileOutputStream fos = new FileOutputStream(file)) {
                    int len;
                    while((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
            zis.closeEntry();
            return 0;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.i(TAG, e.toString());
            return 1;
        }
    }
    public static ArrayList<String> list(File zipfile, String... matches)
    {
        ArrayList<String> filelist = new ArrayList<>();
        try(FileInputStream in = new FileInputStream(zipfile);
            ZipInputStream zis = new ZipInputStream(in))
        {
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null)
            {
                String name = entry.getName();
                for(String match : matches)
                {
                    if(name.contains(match))
                    {
                        filelist.add(name);
                        continue;
                    }
                }
            }
            zis.closeEntry();
            return filelist;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.e(TAG, "Compression.list: " + e.toString());
        }
        return null;
    }
    private static void getFiles(File dir, ArrayList<String> fileList)
    {
        // handling an uncreated directory in case of missing busybox
        if(dir.exists())
        {
            File[] list = dir.listFiles();
            if(list != null)
            {
                for(File file : list)
                {
                    if(file.isDirectory())
                    {
                        getFiles(file, fileList);
                    }
                    else if(file.isFile())
                    {
                        fileList.add(file.getAbsolutePath());
                    }
                    else
                    {
                        // fixes a bug where the paid version of titanium backup would never complete compression - not seen with any other app
                        Log.w(TAG, "special file " + file.getAbsolutePath() + " is not compressed");
                    }
                }
            }
            else
            {
                Log.e(TAG, "Compression.getFiles: listFiles returned null");
            }
        }
    }
}
