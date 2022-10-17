<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet"  href="style.css" />
    <meta charset="UTF-8" />
</head>
<body>
<h1>Compositions</h1>
<table id="compositionsTable">
    <tr>
        <th>Name</th>
        <th>Duration</th>
        <th>Album</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${compositions}" var="composition">
        <tr>
            <form action = "compositions" method = "POST">
                <td>
                    <input type="hidden" name="id" value="${composition.id}"/>
                    <input type="text" value="${composition.name}" name="name" />
                </td>
                <td>
                    <input type="text" value="${composition.duration}" name="duration" />
                </td>
                <td>
                    <input type="text" value="${composition.album.name}" name="albumName" />
                </td>
                <td>
                    <input type = "submit" value = "Save"/>
                </td>
            </form>
            <c:if test="${composition.name != \"\"}">
                <form action="remove" method = "POST">
                    <td>
                        <input type="hidden" name = "id" value="composition=${composition.id}"/>
                        <button type="submit" class="remove">Remove</button>
                    </td>
                </form>
            </c:if>
        </tr>
    </c:forEach>
</table>
<c:if test="${nameException != null && !nameException.equals(\"\")}">
    <div>Composition with name ${nameException} is already exists</div>
</c:if>
<c:if test="${durException != null && durException.equals(\"true\")}">
    <div>Duration must be greater then 0</div>
</c:if>
<c:if test="${albumException != null && !albumException.equals(\"\")}">
    <div>No album ${albumException}</div>
</c:if>
<form action="singers" method = "GET">
    <td>
        <button type="submit">Singers</button>
    </td>
</form>
<form action="albums" method = "GET">
    <td>
        <button type="submit">Albums</button>
    </td>
</form>
<script>

</script>
</body>
</html>