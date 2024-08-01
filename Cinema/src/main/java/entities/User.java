package entities;

/**
 * User entity
 */
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role userRole = Role.USER;
    private boolean notification;

    public User() {
    }

    public User(int id, String firstName, String lastName, String email, String password, String phoneNumber, User.Role role, boolean notification) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userRole = role;
        this.notification = notification;
    }

    public User(int id, String firstName, String lastName, String email, String password, boolean notification) {
        this(id, firstName, lastName, email, password, null, Role.GUEST, notification);
    }

    /**
     * Not recommended to use this constructor as might be forgotten to set the ID for user
     *
     * @param firstName   User's firstName
     * @param lastName    User's lastName
     * @param email       User's email
     * @param password    User's password
     * @param phoneNumber User's phoneNumber (not important)
     * @param notification
     */
    public User(String firstName, String lastName, String email, String password, String phoneNumber, boolean notification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.notification = notification;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * Possible User roles
     */
    public enum Role {
        GUEST("GUEST"),
        USER("USER"),
        ADMIN("ADMIN");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public static User.Role getUserRoleFromString(String roleValue) throws IllegalArgumentException {
            return User.Role.valueOf(roleValue.toUpperCase());
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
