package com.gleb.vinnikov.social_network.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class ExceptionUtils {

    public static void throwIllegalArgumentException(
            String messageProperty,
            Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        String message = bundle.getString(messageProperty);
        throw new IllegalArgumentException(MessageFormat.format(message, args));
    }

}
