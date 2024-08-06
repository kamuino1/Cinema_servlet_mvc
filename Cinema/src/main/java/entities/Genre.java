package entities;

/**
 * Film's genre
 */
public class Genre extends BaseEntity {

    private String name;

    public Genre(int id, String name) {
        super(id);
        this.name = name;
    }

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{"
                + "genreId=" + getId()
                + ", name='" + name + '\''
                + '}';
    }
}
