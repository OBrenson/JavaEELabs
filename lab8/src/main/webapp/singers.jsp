<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet"  href="style.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <meta charset="UTF-8" />
    </head>
    <body>
    <h1>Singers</h1>
            <table id="singersTable">
                <tr>
                    <th>Name</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${singers}" var="singer">
                    <tr>
                        <form action = "singers" method = "POST">
                            <td>
                                <input type="hidden" name="id" value="${singer.id}"/>
                                <input type="text" value="${singer.name}" name="name" />
                            </td>
                            <td>
                                <input type = "submit" value = "Save"/>
                            </td>
                        </form>
                        <c:if test="${singer.name != \"\"}">
                            <form action="remove" method = "POST">
                                <td>
                                    <input type="hidden" name = "id" value="singer=${singer.id}"/>
                                    <button type="submit" class="remove">Remove</button>
                                </td>
                            </form>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
    <c:if test="${nameException != null && !nameException.equals(\"\")}">
        <div>Singer with name ${nameException} is already exists</div>
    </c:if>
        <form action="compositions" method = "GET">
            <td>
                <button type="submit">Compositions</button>
            </td>
        </form>
    <form action="albums" method = "GET">
        <td>
            <button type="submit">Albums</button>
        </td>
    </form>
    <h2>Singers' songs with minimal duration</h2>
    <p>${minDuration}</p>
    <script>

    </script>
    </body>
</html>