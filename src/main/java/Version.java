public class Version {
	// Semantic versioning https://semver.org/
	public static final int major = 2;
	public static final int minor = 1;
	public static final int patch = 0;

	public static String getString() {
		return String.format("%d.%d.%d", major, minor, patch);
	}

	public String toString() {
		return getString();
	}
}