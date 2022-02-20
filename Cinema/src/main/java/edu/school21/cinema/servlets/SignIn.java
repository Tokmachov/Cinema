package edu.school21.cinema.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignIn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


//        ApplicationContext context = (ApplicationContext)getServletContext().getAttribute(ServletContextAttributes.SPRING_CONTEXT);
//        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
//        try {
//            jdbcTemplate.execute("CREATE TABLE another_test_table_aIII ( id int )");
//        } catch (Exception e) {
//            System.out.println("");
//        }
//
//        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/html/signIn.html");
//        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        final String phoneNumber = req.getParameter("phoneNumber");
//        final String passWord = req.getParameter("passWord");
//        Optional<User> user = userRepository.findByPhone(phoneNumber);
//        if (user.isPresent()) {
//            PrintWriter pw = resp.getWriter();
//            pw.write("User is found");
//        } else {
//            PrintWriter pw = resp.getWriter();
//            pw.write("User is not found");
//        }
    }
}
