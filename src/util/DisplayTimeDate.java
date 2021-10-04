package util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DisplayTimeDate {

    public static void loadDateAndTime(Label lbDate, Label lbTime) {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lbDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            String state = "AM";
            if (currentTime.getHour() >= 12) {
                state = "PM";
            }
            lbTime.setText(
                    String.format("%02d", currentTime.getHour()) + " : " + String.format("%02d", currentTime.getMinute()) +
                            " : " + String.format("%02d", currentTime.getSecond()) + " " + state
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
