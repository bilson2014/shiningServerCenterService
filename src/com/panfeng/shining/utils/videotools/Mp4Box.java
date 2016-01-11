package com.panfeng.shining.utils.videotools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

public class Mp4Box {

	 public static void main(String[] args)
	  {
	    String file = "C:\\Download\\lovelive.mp4";
	    try {
	      addhint(file, null);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  public static void addhint(String aFile, Writer aWriter) throws Exception {
	    Process process = Runtime.getRuntime().exec("MP4Box -hint " + aFile);
	    InputStream stderr = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(stderr);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null)
	    {
	    	System.out.println(line);
	      if ((line.startsWith("Hinting:")) || (line.startsWith("ISO")))
	        continue;
	      if (aWriter != null)
	        aWriter.append(line + "\n");
	      else {
	        System.out.println(line);
	      }
	    }

	    process.waitFor();

	    if (br != null)
	      br.close();
	    if (isr != null)
	      isr.close();
	    if (stderr != null)
	      stderr.close();
	  }
}
