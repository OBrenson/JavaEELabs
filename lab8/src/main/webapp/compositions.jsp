<html>
    <h1>Compositions</h1>
    <table>
        <tr>
            <th>Name</th>
        </tr>
        <c:forEach items="${compositions}" var="composition">
            <tr>
                <td>${composition.name}</td>
            </tr>
        </c:forEach>
    </table>
</html>