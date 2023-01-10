package files;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created with IDEA
 * author:YunGui Hhuang
 * Date:2022/12/14
 * Time:16:03
 */
public class PomFileUtil {

    private  String DEMO_PATH = "F:\\learn\\java-core-examples\\java-core\\XML-Document";

    /**
     * 项目
     * @param dirPath 项目绝对路径
     * @return
     */
    public String getPomPath(String dirPath) {
        File file = new File(DEMO_PATH);
        File[] files = file.listFiles(new XmlFilter());
        if (files!=null&&files.length==1){
            //pom.xml文件
            File  pomFile = files[0];
            String absolutePath = pomFile.getAbsolutePath();
            return absolutePath;
        } else return null;
    }


}
//pom.xml过滤
class XmlFilter implements FilenameFilter{

    @Override
    public boolean accept(File dir, String name) {
        if (name.equals("pom.xml")) {
            return true;
        }
        return false;
    }
}
