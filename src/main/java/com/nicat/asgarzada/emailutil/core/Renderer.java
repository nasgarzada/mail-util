package com.nicat.asgarzada.emailutil.core;

import com.nicat.asgarzada.emailutil.annotation.BindProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Enables operations on templates.
 * @author nasgarzada
 * @version 1.0.0
 *
 */
public class Renderer {
    private String templatePath;
    private Map<String, String> bindings;
    private final Email email;

    protected Renderer(Email helper) {
        this.bindings = new HashMap<>();
        this.email = helper;
    }

    /**
     *
     * @param templatePath path of template file
     * @return renderer {@link Renderer}
     */
    public Renderer path(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    /**
     *
     * @param object for processing bindings and generate map of key value
     * @return renderer {@link Renderer}
     */
    public Renderer bindingObject(Object object) {
        this.bindings = BindProcessor.process(object);
        return this;
    }

    /**
     *
     * @return building renderer and return {@link Email}
     */
    public Email build() {
        return this.email;
    }

    protected String getTemplatePath() {
        return this.templatePath;
    }

    protected Map<String, String> getBindings() {
        return this.bindings;
    }

    @Override
    public String toString() {
        return "Renderer{" +
                "templatePath='" + templatePath + '\'' +
                ", bindings=" + bindings +
                '}';
    }
}
