import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Library Results";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM Library";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM Library WHERE BOOK_TITLE LIKE ?";
            String theBookTitle = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theBookTitle);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String ISBN = rs.getString("ISBN").trim();
            String BOOK_TITLE = rs.getString("BOOK_TITLE").trim();
            String AUTHOR = rs.getString("AUTHOR").trim();
            String PUBLISHER = rs.getString("PUBLISHER").trim();
            String PUBLICATION_DATE = rs.getString("PUBLICATION_DATE").trim();
            String GENRE = rs.getString("GENRE").trim();

            if (keyword.isEmpty() || BOOK_TITLE.contains(keyword)) {
               out.println("ISBN: " + ISBN + ", ");
               out.println("Book Title: " + BOOK_TITLE + ", ");
               out.println("Author: " + AUTHOR + ", ");
               out.println("Publisher: " + PUBLISHER + ", ");
               out.println("Publication Date: " + PUBLICATION_DATE + ", ");
               out.println("Genre: " + GENRE + "<br>");
            }
         }
         out.println("<a href=/TechExercise/simpleFormSearch.html>Search Library</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
