package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:YunGui Hhuang
 * Date:2022/12/14
 * Time:20:41
 */
public class ConfigData {
    private List<String> paths = new ArrayList<>();

    private static volatile ConfigData instance  = null;

    private static final String configFilePath = "/application.properties";

    //单例
    private ConfigData(){
        PropertiesConfig propertiesConfig = PropertiesConfig.config().loadConfig(configFilePath,ConfigData.class);
        Map<String, ArrayList<String>> keyArrMap = propertiesConfig.getKeyArrMap();
        ArrayList<String> arrayList = keyArrMap.get("paths");
        this.setPaths(arrayList);
    }

    public  static ConfigData meConfig(){
        if (instance ==null){
           synchronized (ConfigData.class){
               if (instance ==null){
                   instance  = new ConfigData();
               }
           }
        }
        return instance ;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }



    public static void main(String[] args) {
        ConfigData configData = ConfigData.meConfig();
        configData.getPaths().stream().forEach(System.out::println);
    }
}
