module com.example.acer_rgb {
    requires javafx.controls;
    requires javafx.fxml;


    opens dhruv8sh to javafx.fxml;
    exports dhruv8sh;
}