package dev.strace.twings.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SpigotMcAPI {

    String resourceID;
    String rawHtml;
    String version;
    String downloads;

    public SpigotMcAPI(String id) throws IOException {
         this.resourceID = String.valueOf(id);
         
         URL url;
         URLConnection uc;
         String urlString="https://www.spigotmc.org/resources/" + resourceID + "/";
         System.out.println("Getting content for URl : " + urlString);
         url = new URL(urlString);
         uc = url.openConnection();
         uc.connect();
         uc = url.openConnection();
         uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
         uc.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
             rawHtml += line;
        }
        in.close();
     }

    public String getResourceID() {
        return resourceID;
    }

    public String getVersion() throws IOException {
        URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID);

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String line;
        while ((line = in.readLine()) != null) {
            return line;
        }
        in.close();
        return null;
    }

    public String getDownloads() throws IOException {

        boolean test = false;
        boolean next = false;

        for(String strg : rawHtml.split("<")){
            if (next) {
                return strg.replace("dd>", "");
            } else if(test) {
                next = true;
            } else {
                if(strg.contains("Total Downloads:")){
                    test = true;
                }
            }
        }
        return null;
    }
}
