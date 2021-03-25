
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String isbn = request.getParameter("ISBN");
      String bookTitle = request.getParameter("BOOK_TITLE");
      String author = request.getParameter("AUTHOR");
      String publisher = request.getParameter("PUBLISHER");
      String publicationDate = request.getParameter("PUBLICATION_DATE");
      String genre = request.getParameter("GENRE");

      Connection connection = null;
      String insertSql = " INSERT INTO Library (ISBN, BOOK_TITLE, AUTHOR, PUBLISHER, PUBLICATION_DATE, GENRE) values (?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, isbn);
         preparedStmt.setString(2, bookTitle);
         preparedStmt.setString(3, author);
         preparedStmt.setString(4, publisher);
         preparedStmt.setString(5, publicationDate);
         preparedStmt.setString(6, genre);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Add Book to Library";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>ISBN</b>: " + isbn + "\n" + //
            "  <li><b>Book Title</b>: " + bookTitle + "\n" + //
            "  <li><b>Author</b>: " + author + "\n" + //
            "  <li><b>Publisher</b>: " + publisher + "\n" + //
            "  <li><b>Publication Date</b>: " + publicationDate + "\n" + //
            "  <li><b>Genre</b>: " + genre + "\n" + //

            "</ul>\n");

      out.println("<a href=/TechExercise/simpleFormSearch.html>Search Library</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
