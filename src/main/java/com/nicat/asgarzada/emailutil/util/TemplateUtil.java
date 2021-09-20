package com.nicat.asgarzada.emailutil.util;

import com.nicat.asgarzada.emailutil.exception.FileParseException;
import groovy.text.SimpleTemplateEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * {@link TemplateUtil} class renders html or plain text with key value bindings.
 * It uses groovy {@link SimpleTemplateEngine} for creating template, binding and converting to string.
 *
 * @author nasgarzada
 * @version 1.0.0
 * @since 2021-09-17
 */
public final class TemplateUtil {
    private TemplateUtil() {

    }

    /**
     * Renders text or html file with given binding values.
     *
     * @param templatePath path of template that used in rendering
     * @param bindings key value pair for rendering template with values
     * @return rendered template text
     */
    public static String renderHtml(String templatePath, Map<String, String> bindings) {
        try (var reader = getTemplate(templatePath)) {

            var template = new SimpleTemplateEngine()
                    .createTemplate(reader);

            if (!bindings.isEmpty()) {
                return template.make(bindings).toString();
            }
            return template.make().toString();
        } catch (IOException e) {
            throw new FileParseException("file parsing failed:", e);
        }
    }

    /**
     *
     * @param templatePath path of template that used in rendering
     * @return reads file
     * @throws IOException 
     */
    private static BufferedReader getTemplate(String templatePath) throws IOException {
        return new BufferedReader(new FileReader(templatePath, UTF_8));
    }
}
