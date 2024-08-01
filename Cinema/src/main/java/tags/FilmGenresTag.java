package tags;

import entities.Film;
import entities.Genre;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class FilmGenresTag extends SimpleTagSupport {
    private Film film;

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public void doTag() throws JspTagException {
        try {
            JspWriter out = getJspContext().getOut();
            final List<Genre> genreList = film.getGenreList();
            final int size = genreList.size();
            for (int i = 0; i < size; i++) {
                final Genre genre = genreList.get(i);
                if (i < size - 1) {
                    out.write(genre.getName() + ", ");
                } else {
                    out.write(genre.getName());
                }
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
    }
}
