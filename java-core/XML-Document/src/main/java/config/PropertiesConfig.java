package config;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IDEA
 * author:YunGui Hhuang
 * Date:2022/12/14
 * Time:16:19
 */
public class PropertiesConfig {

    //--------------------------------------------------------
    //<数组key，index索引>
    private Map<String, Integer> keyIndexMap = new HashMap<>();

    //<数组属性名，数组内容>
    private Map<String, ArrayList<String>> keyArrMap = new HashMap<>();

    private final Properties properties = new Properties();

    public static PropertiesConfig config(){
        return new PropertiesConfig();
    }

    public PropertiesConfig loadConfig(String filePath,Class<?> clazz) {
        try {
            properties.load(Object.class.getResourceAsStream(filePath));
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                this.setValues(key,clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     *类加载器得类名和字段名
     * "key = className.fielName"
     * @param key 文件key
     */
    private void setValues(String key,Class<?> clazz) throws NoSuchFieldException, IllegalAccessException {
        String fielName = this.match(key);//xx[0] =>xx
        String className = clazz.getName();
        Field field = clazz.getDeclaredField(fielName);
        //List数组
        if (field.getType().equals(List.class)) {
            putList(className, field);
        }
        if (field.getType().equals(String.class)){
            puthString(field);
        }
    }

    private void puthString(Field field) {
        //TODO
    }

    /**
     * eg. path[0] => path
     *
     * @param key
     * @return
     */
    private String match(String key) {
        //类名.属性名[N] =》属性名[N] 正则匹配=》List数组
        String property = key.substring(key.lastIndexOf(".") + 1);
        Pattern pattern = Pattern.compile("([\\w]+)(\\[)([\\d]+)(\\]$)");
        Matcher matcher = pattern.matcher(property);
        if (matcher.find()) {
            property = matcher.group(1);//属性名
            Integer size = Integer.valueOf(matcher.group(3));//List数组索引
            keyIndexMap.put(property, size);
        }
        return property;
    }

    private void putList(String className, Field field) throws IllegalAccessException {
        ArrayList<String> arrayList = null;
        if (keyArrMap.containsKey(field.getName())){
            arrayList = keyArrMap.get(field.getName());
        } else {
            arrayList = new ArrayList<>();
        }
        Integer index = keyIndexMap.get(field.getName());
        String key = new StringBuilder().append(className).append(".").append(field.getName()).append("[" + index + "]").toString();
        String property = properties.getProperty(key);
        arrayList.add(property);
        keyArrMap.put(field.getName(), arrayList);
    }


    public Map<String, Integer> getKeyIndexMap() {
        return keyIndexMap;
    }

    public void setKeyIndexMap(Map<String, Integer> keyIndexMap) {
        this.keyIndexMap = keyIndexMap;
    }

    public Map<String, ArrayList<String>> getKeyArrMap() {
        return keyArrMap;
    }

    public void setKeyArrMap(Map<String, ArrayList<String>> keyArrMap) {
        this.keyArrMap = keyArrMap;
    }

    public Properties getProperties() {
        return properties;
    }
}
