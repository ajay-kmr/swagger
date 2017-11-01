package swagger.dto

public enum Rating {
    EXCELLENT("EXCELLENT"),
    GOOD("GOOD"),
    AVERAGE("AVERAGE"),
    POOR("POOR")

    final String value

    Rating(String value) {
        this.value = value
    }

    String toString() { value }

    String getKey() { name() }

}
