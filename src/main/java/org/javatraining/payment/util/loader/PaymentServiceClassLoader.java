package org.javatraining.payment.util.loader;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class PaymentServiceClassLoader extends ClassLoader {
    private String pathToClass;

    public PaymentServiceClassLoader(String pathToClass, ClassLoader parent) {
        super(parent);
        this.pathToClass = pathToClass;
    }

    @Override
    @SneakyThrows
    public Class<?> findClass(String className) {
        String shortClassName = className.substring(className.lastIndexOf(".") + 1);
        String fileName = pathToClass + shortClassName + ".class";
        byte[] buffer = Files.newInputStream(Path.of(fileName)).readAllBytes();
        return defineClass(className, buffer, 0, buffer.length);
    }
}
