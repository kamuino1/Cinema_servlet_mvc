package tags;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PaginationTag extends SimpleTagSupport {
    private HttpServletRequest request;
    private int totalPages;
    private String prev;
    private String next;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public void doTag() throws JspException, IOException {
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        StringBuilder paginationHtml = new StringBuilder();
        paginationHtml.append("<nav aria-label='Page navigation'>");
        paginationHtml.append("<ul class='pagination'>");

        // Previous button
        if (currentPage > 1) {
            paginationHtml.append("<li class='page-item'>");
            paginationHtml.append("<a class='page-link' href='?page=")
                          .append(currentPage - 1)
                          .append("' aria-label='").append(prev).append("'>");
            paginationHtml.append("<span aria-hidden='true'>&laquo;</span>");
            paginationHtml.append("</a>");
            paginationHtml.append("</li>");
        } else {
            paginationHtml.append("<li class='page-item disabled'>");
            paginationHtml.append("<a class='page-link' href='#' aria-label='").append(prev).append("'>");
            paginationHtml.append("<span aria-hidden='true'>&laquo;</span>");
            paginationHtml.append("</a>");
            paginationHtml.append("</li>");
        }

        // Page numbers
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                paginationHtml.append("<li class='page-item active'>");
                paginationHtml.append("<span class='page-link'>").append(i).append("</span>");
                paginationHtml.append("</li>");
            } else {
                paginationHtml.append("<li class='page-item'>");
                paginationHtml.append("<a class='page-link' href='?page=").append(i).append("'>").append(i).append("</a>");
                paginationHtml.append("</li>");
            }
        }

        // Next button
        if (currentPage < totalPages) {
            paginationHtml.append("<li class='page-item'>");
            paginationHtml.append("<a class='page-link' href='?page=")
                          .append(currentPage + 1)
                          .append("' aria-label='").append(next).append("'>");
            paginationHtml.append("<span aria-hidden='true'>&raquo;</span>");
            paginationHtml.append("</a>");
            paginationHtml.append("</li>");
        } else {
            paginationHtml.append("<li class='page-item disabled'>");
            paginationHtml.append("<a class='page-link' href='#' aria-label='").append(next).append("'>");
            paginationHtml.append("<span aria-hidden='true'>&raquo;</span>");
            paginationHtml.append("</a>");
            paginationHtml.append("</li>");
        }

        paginationHtml.append("</ul>");
        paginationHtml.append("</nav>");

        getJspContext().getOut().write(paginationHtml.toString());
    }
}