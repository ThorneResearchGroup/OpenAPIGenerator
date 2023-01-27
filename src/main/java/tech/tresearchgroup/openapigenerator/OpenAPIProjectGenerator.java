package tech.tresearchgroup.openapigenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

import java.lang.reflect.Method;
import java.util.List;

public class OpenAPIProjectGenerator {
    private static final Logger logger = LoggerFactory.getLogger(OpenAPIProjectGenerator.class);
    private static final String[] packages = new String[]{
        "tech.tresearchgroup.babygalago.view.endpoints",
        "tech.tresearchgroup.babygalago.view.endpoints.api",
        "tech.tresearchgroup.babygalago.view.endpoints.ui"
    };

    public static void main(String[] args) {
        List<String> classNames = ReflectionMethods.getClassNames(packages);
        for (String theClassName : classNames) {
            try {
                String[] classParts = theClassName.split("\\.");
                String noPackage = classParts[classParts.length - 1];
                Class theClass = ReflectionMethods.findClass(noPackage.toLowerCase(), packages);
                Method getEndpoints = theClass.getMethod("getEndpoints", null);
                Object classObject = ReflectionMethods.getNewInstance(theClass);
                Endpoint[] endpoints = (Endpoint[]) getEndpoints.invoke(classObject);
                System.out.println(endpoints);
                if (theClass != null) {
                    logger.info(theClassName);
                } else {
                    logger.info("Null class: " + theClassName);
                }
            } catch (Exception e) {
                logger.error(theClassName);
                e.printStackTrace();
            }
        }
    }
}
