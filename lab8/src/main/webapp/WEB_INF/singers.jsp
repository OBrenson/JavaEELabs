<html>
    <h1>Singers</h1>
    <table>
        <tr>
            <th>Name</th>
        </tr>
        <c:forEach items="${singers}" var="singer">
            <tr>
                <td>${singer.name}</td>
            </tr>
        </c:forEach>
    </table>
</html>