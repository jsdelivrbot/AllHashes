import java.io.*;

public class SD {
 public static String SD() throws Exception {
  if (System.getProperty("user.home").equals("/Users/rawa")) {
   return "/Volumes/SPACEDRIVE1/SpaceDrive/";
  }
  return "/home/rawa/SpaceDrive/";
 }
 public static String ext(String hash) throws Exception {
  LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream("TmpFileExt.txt")));
  String s;
  while((s=lnr.readLine())!=null) {
   if(s.substring(0,64).equals(hash)) {
    return s.substring(65);
   }
  }
  return null;
 }
}