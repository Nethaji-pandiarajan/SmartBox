import java.util.Date;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Validations {

	public static boolean isValidConfirmMessage() {
		boolean isValidMessage = false;
		ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
		ButtonType No = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING, "Have you closed your Locker ? ", Yes, No);

		alert.setTitle("Confirmation");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.orElse(No) == Yes) {
			isValidMessage = true;
			Date curr_date = new java.util.Date(System.currentTimeMillis());
			return isValidMessage;
		}
		return isValidConfirmMessage();
	}

}
