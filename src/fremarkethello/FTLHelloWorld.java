package fremarkethello;
 
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
 
public class FTLHelloWorld {
     
    public static void main(String[] args) throws IOException {
        
         String classer="hello";
         String packager="fremarkethello.wutu";
       CreateClass(classer, packager);
       
       runIt(classer, packager); 
    }
    public static void CreateClass(String cla, String pac) throws IOException{
        File sourceFile   = new File("src/fremarkethello/wutu/"+cla+".java");
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("/helloworld.ftl");
             
            // Build the data-model
            Map<String, Object> data = new HashMap<>();
            
            
            data.put("message01", pac);
            data.put("message1", cla);
            data.put("message2", "Hello Worlder!");
            
           
            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();
 
            try ( // File output
                    
                    Writer file = new FileWriter (sourceFile)) {
                template.process(data, file);
                file.flush();
            }
            
             
        } catch (IOException | TemplateException e) {
        }
        
    JavaCompiler compiler    = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
                    Arrays.asList(new File("src/")));
            // Compile the file
            compiler.getTask(null,
                    fileManager,
                    null,
                    null,
                    null,
                    fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)))
                    .call();
        }
    }
    
 //@SuppressWarnings("unchecked")
  public static void runIt(String cla, String pac) {
    try {
      Class params[] = {};
      Object paramsObj[] = {};
      Class thisClass = Class.forName(pac+"."+cla);
      Object iClass = thisClass.newInstance();
      Method thisMethod = thisClass.getDeclaredMethod("doit", params);
      thisMethod.invoke(iClass, paramsObj);
      }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void runIt2(String cla, String pac) {
      try {
            ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
             
             // Step 2: Define a class to be loaded.
              
            String classNameToBeLoaded = pac+"."+cla;
 
             
             // Step 3: Load the class
              
            Class myClass = myClassLoader.loadClass(classNameToBeLoaded);
 
             
             // Step 4: create a new instance of that class
              
            Object whatInstance = myClass.newInstance();
 
            String methodParameter = "a quick brown fox";
             
             // Step 5: get the method, with proper parameter signature.
             // The second parameter is the parameter type.
             // There can be multiple parameters for the method we are trying to call,
             // hence the use of array.
 
            Method myMethod = myClass.getMethod("doit");
 
             
             // Step 6:
             // Calling the real method. Passing methodParameter as
             // parameter. You can pass multiple parameters based on
             // the signature of the method you are calling. Hence
             // there is an array.
              
             myMethod.invoke(whatInstance);
 
            
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
  }

}