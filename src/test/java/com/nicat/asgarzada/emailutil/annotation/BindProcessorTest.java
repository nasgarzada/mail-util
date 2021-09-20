package com.nicat.asgarzada.emailutil.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BindProcessorTest {

    @Test
    void testProcessSuccess() {
        //Arrange
        var exampleObject = new Example("RandomName", "RandomLastname");

        //Act
        var actual = BindProcessor.process(exampleObject);

        //Arrange
        assertFalse(actual.isEmpty());
        assertEquals(actual.get("name"), "RandomName");
        assertEquals(actual.get("lastname"), "RandomLastname");
    }

    public static class Example {
        @Bind(key = "name")
        private final String name;
        @Bind(key = "lastname")
        private final String lastname;

        public Example(String name, String lastname) {
            this.name = name;
            this.lastname = lastname;
        }

        public String getName() {
            return name;
        }

        public String getLastname() {
            return lastname;
        }

        @Override
        public String toString() {
            return "Example{" +
                    "name='" + name + '\'' +
                    ", lastname='" + lastname + '\'' +
                    '}';
        }
    }
}