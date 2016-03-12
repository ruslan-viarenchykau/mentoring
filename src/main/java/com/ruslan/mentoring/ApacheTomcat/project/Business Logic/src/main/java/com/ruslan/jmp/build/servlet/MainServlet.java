package com.ruslan.jmp.build.servlet;

import com.ruslan.jmp.build.bean.User;
import com.ruslan.jmp.build.dao.IWelcomeMessageDao;
import com.ruslan.jmp.build.dao.WelcomeMessageDaoFactory;
import com.ruslan.jmp.build.util.AgeCalculatorUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/calculate")
public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        User user = null;
        try {
            user = new User(request.getParameter("firstName"), request.getParameter("lastName"), format.parse(request.getParameter("birthday")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setAttribute("user", user);
        request.setAttribute("userAgeMessage", AgeCalculatorUtils.getUserAgeMessage(user));
        request.getRequestDispatcher("/age-calculator-result.jsp").forward(request, response);
    }
}
