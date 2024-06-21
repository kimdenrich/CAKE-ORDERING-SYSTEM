module project.final_ordering_system_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.logging.log4j;
    requires itextpdf;

    opens project.final_ordering_system_project to javafx.fxml;
    exports project.final_ordering_system_project;
}