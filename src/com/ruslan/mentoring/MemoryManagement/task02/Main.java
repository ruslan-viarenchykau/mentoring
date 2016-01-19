package com.ruslan.mentoring.MemoryManagement.task02;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Choose an option:");
        System.out.println("1. OutOfMemoryError: Java heap space.");
        System.out.println("2. OutOfMemoryError: Metaspace (PermGen).");
        System.out.println("3. StackOverflowError.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                int option = Integer.parseInt(reader.readLine());
                switch (option) {
                    case 1:
                        introduceOutOfMemoryErrorHeap();
                        return;
                    case 2:
                        introduceOutOfMemoryErrorMetaspace();
                        return;
                    case 3:
                        introduceStackOverflowError();
                        return;
                    default:
                        System.out.println("No such option...");
                }
            } catch (NumberFormatException e) {
                System.out.println("No such option...");
            }
        }
    }

    private static void introduceOutOfMemoryErrorHeap() {
        String s = "1234567890";
        while (true) {
            s = s + s;
        }
    }

    /**
     * Loads all classes from classpath
     */
    private static void introduceOutOfMemoryErrorMetaspace() throws ClassNotFoundException, MalformedURLException {
        String classPath = System.getProperty("java.class.path");
        String[] classpathEntries = classPath.split(File.pathSeparator);
        List<URL> urlsList = new ArrayList<>();
        for (String classPathEntry: classpathEntries) {
            urlsList.add(new File(classPathEntry).toURI().toURL());
        }
        URLClassLoader urlClassLoader = new URLClassLoader(urlsList.toArray(new URL[10000]), Main.class.getClassLoader());
        int i = 1, j = 1;
        for (String classPathEntry: classpathEntries) {
            if (classPathEntry.endsWith(".jar")) {
                List<String> classesNames = getClassesNamesInJar(classPathEntry);
                for (String className: classesNames) {
                    System.out.println("Load " + className);
                    try {
                        urlClassLoader.loadClass(className);
                        System.out.println("Loaded " + i++ + " classes");
                    } catch (NullPointerException | NoClassDefFoundError e) {
                        System.out.println("NOT loaded " + j++ + " classes. " + e.toString());
                    } catch (OutOfMemoryError e) {
                        // to log the error message to console
                        System.out.println(e.toString());
                        throw e;
                    }
                }
            }
        }
    }

    private static void introduceStackOverflowError() {
        new A();
    }

    public static List<String> getClassesNamesInJar(String jarName) {
        List<String> classes = new ArrayList<>();
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while(true) {
                jarEntry = jarFile.getNextJarEntry();
                if(jarEntry == null){
                    break;
                }
                if(jarEntry.getName().endsWith(".class")) {
                    classes.add(jarEntry.getName().replaceAll("/", "\\.").replace(".class", ""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}

class A {
    public A() {
        new B();
    }
}

class B {
    public B() {
        new A();
    }
}