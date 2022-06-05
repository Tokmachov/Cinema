<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.io.File" %>
<%@ page import="edu.school21.cinema.models.domain.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Profile.</title>
        <link rel="stylesheet" href="jsp/profileStyles.css">
    </head>
    <body>
        <h2>Profile.</h2>

        <form method="POST" action="log/out">
            <button type="submit">log out</button>
        </form>

        <h4>${user.name} ${user.lastName} <br>Phone: ${user.phoneNumber}</h4>


        <img id="avatar" src="images/${user.avatarFileName}" alt="User avatar">

        <form action ="profile" enctype="multipart/form-data" method="POST">
            <input name="userAvatar" type="file" />
            <input type="submit" value="ok" />
        </form>

        <form action ="profile/avatar/select" method="POST">
            <table>
                <th>Avatar links</th>
                <c:forEach items="${avatarMetas}" var="avatarMeta">
                    <tr>
                        <td>
                            <a href='<c:url value="/images/${avatarMeta.name}"/>' target="_blank"> ${avatarMeta.name}</a>
                        </td>
                        <td>
                            <c:out value="${avatarMeta.sizeKb} kb"/>
                        </td>
                        <td>
                            <c:out value="${avatarMeta.mime}"/>
                        </td>
                        <td>
                            <input type="radio" name="avatarChoice" value="${avatarMeta.name}">
                        </td>
                    </tr>

                </c:forEach>
            </table>
            <input type="submit" value="Submit">
        </form>

        <p> User login dates </p>

        <table>
            <th>Date</th>
            <th>Time</th>
            <th>Ip</th>
            <c:forEach items = "${authentications}" var="authentication">
                <tr>
                    <td>
                            ${authentication.date}
                    </td>
                    <td>
                            ${authentication.time}
                    </td>
                    <td>
                            ${authentication.address}
                    </td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>