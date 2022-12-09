package presentation;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public interface Transitions {
    public static void addScaleFadeTransition(StackPane itemPane, double maxScaleSize) {
        ScaleTransition st = new ScaleTransition();
        st.setNode(itemPane);
        st.setDuration(Duration.millis(200));
        st.setInterpolator(Interpolator.EASE_BOTH);

        FadeTransition ftRegion = new FadeTransition();
        StackPane r = (StackPane) itemPane.getChildren().get(1);
        ftRegion.setNode(r);
        ftRegion.setDuration(Duration.millis(200));
        ftRegion.setInterpolator(Interpolator.EASE_BOTH);

        itemPane.setOnMouseEntered(e -> {
            ftRegion.stop();
            ftRegion.setFromValue(r.getOpacity());
            ftRegion.setToValue(1);
            ftRegion.play();

            st.stop();
            st.setFromX(itemPane.getScaleX());
            st.setFromY(itemPane.getScaleY());
            st.setToX(maxScaleSize);
            st.setToY(maxScaleSize);
            st.play();
        });
        itemPane.setOnMouseExited(e -> {
            ftRegion.stop();
            ftRegion.setFromValue(r.getOpacity());
            ftRegion.setToValue(0);
            ftRegion.play();

            st.stop();
            st.setFromX(itemPane.getScaleX());
            st.setFromY(itemPane.getScaleY());
            st.setToX(1f);
            st.setToY(1f);
            st.play();
        });
    }
}
