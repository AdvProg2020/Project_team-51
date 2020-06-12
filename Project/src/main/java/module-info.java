module Project {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.controlsfx.controls;
    requires java.naming;
    requires java.management;
    requires org.apache.commons.lang3;
    requires yagson;
    requires com.google.gson;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter;
    requires org.junit.jupiter.engine;
    requires com.jfoenix;
    opens main to javafx.fxml;
    exports main;
}