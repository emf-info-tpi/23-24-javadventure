package ch.emf.javadventure.services;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CustomClassLoader extends ClassLoader {
    private final String classPath;
    private Map<String, Class<?>> loadedClasses = new HashMap<>();

    public CustomClassLoader(String classPath) {
        //loadedClasses.clear();
        this.classPath = classPath;
    }

    public void unloadClasses() {
        loadedClasses.clear();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = loadClassData(name);
            Class<?> clazz = defineClass(name, classData, 0, classData.length);
            loadedClasses.put(name, clazz);
            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException("Error loading class " + name, e);
        }
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // Check if the class has already been loaded by this loader
        if (loadedClasses.containsKey(name)) {
            return loadedClasses.get(name);
        }

        // Delegate to the parent class loader for java.* classes and already loaded classes
        if (name.startsWith("java.")) {
            try {
                return getParent().loadClass(name);
            } catch (ClassNotFoundException e) {
                // Let it continue to load locally
            }
        }

        // Load the class data from file
        return findClass(name);
    }

    private byte[] loadClassData(String className) throws IOException {
        String fileName = className.replace('.', File.separatorChar) + ".class";
        Path path = Paths.get(classPath, fileName);
        return Files.readAllBytes(path);
    }
}
