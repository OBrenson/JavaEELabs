<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet"  href="style.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="script.js"></script>
    <meta charset="UTF-8" />
</head>
<body>
<h1>Albums</h1>
<table id="compositionsTable">
    <tr>
        <th>Name</th>
        <th>Genre</th>
        <th>Singer</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${albums}" var="album">
        <tr>
            <form action = "albums" method = "POST" autocomplete="off">
                <td>
                    <input type="hidden" name="id" value="${album.id}"/>
                    <input type="text" value="${album.name}" name="name" />
                </td>
                <td>
                    <input type="text" value="${album.genre}" name="genre" />
                </td>
                <td>
                    <input type="text" id="${album.id == null ? "emp" : album.id}"
                    value="${album.singer.name}"  class="autoComplete" name="singerName" />
                    <div id="suggesstion-box${album.id == null ? "emp" : album.id}" style="position: absolute"></div>
                </td>
                <td>
                    <input type = "submit" value = "Save"/>
                </td>
            </form>
            <c:if test="${album.name != \"\"}">
                <form action="remove" method = "POST">
                    <td>
                        <input type="hidden" name = "id" value="album=${album.id}"/>
                        <button type="submit" class="remove">Remove</button>
                    </td>
                </form>
            </c:if>
        </tr>
    </c:forEach>
</table>
<c:if test="${nameException != null && !nameException.equals(\"\")}">
    <div>Album with name ${nameException} is already exists</div>
</c:if>
<c:if test="${singerException != null && !singerException.equals(\"\")}">
    <div>No singer ${singerException}</div>
</c:if>
<form action="singers" method = "GET">
    <td>
        <button type="submit" class="remove">Singers</button>
    </td>
</form>
<form action="compositions" method = "GET">
    <td>
        <button type="submit">Compositions</button>
    </td>
</form>
<h3>Maximum songs num in one album: ${count}</h3>
<h3>Albums with maximum songs number</h3>
<p>${maxNames}</p>
<script>
    $(document).ready(auto("Singer"));
</script>
</body>
</html>