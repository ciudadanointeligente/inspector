package cl.votainteligente.inspector.shared;

public class Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validate emailAddress with regular expression
	 *
	 * @param emailAddress email address for validation
	 * @return true valid email address, false invalid email address
	 */
	public static boolean validateEmail(String emailAddress) {
		return emailAddress.matches(EMAIL_PATTERN);
	}
}