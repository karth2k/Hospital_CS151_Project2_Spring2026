package hospital;

/**
 * Thrown when a hospital resource has reached its maximum capacity.
 * author: Qingheng Fang
 * id: 015323763
 */
public class MaxCapacityException extends RuntimeException {

    private final int maxCapacity;
    private final String resourceType;

    /**
     * Constructs a MaxCapacityException with a detail message.
     *
     * @param message description of the capacity error
     */
    public MaxCapacityException(String message) {
        super(message);
        this.maxCapacity = -1;
        this.resourceType = "Unknown";
    }

    /**
     * Constructs a MaxCapacityException with a detail message,
     * the resource type that is full, and the capacity limit.
     *
     * @param resourceType the type of resource that hit its limit (e.g. "Patient", "Doctor")
     * @param maxCapacity  the maximum number of instances allowed
     */
    public MaxCapacityException(String resourceType, int maxCapacity) {
        super("Cannot add more " + resourceType + " objects: maximum capacity of "
                + maxCapacity + " has been reached.");
        this.resourceType = resourceType;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Returns the maximum capacity that was exceeded.
     *
     * @return the capacity limit, or -1 if not specified
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Returns the name of the resource type that is at capacity.
     *
     * @return the resource type string
     */
    public String getResourceType() {
        return resourceType;
    }
}
