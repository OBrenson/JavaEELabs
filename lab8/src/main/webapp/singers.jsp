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
                                <c:if test="${singer.name != \"\"}">
                                    <td><button type="button" id="${singer.id}" class="remove">Remove</button></td>
                                </c:if>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </table>
    <script>
        $(document).ready(function() {
            // crating new click event for save button
            $(".remove").click(function() {
                var id = this.id;
                $.ajax({
                    url: "singers",
                    type: "delete",
                    data: {
                        id : id,
                    },
                    success : function(data){
                        if (data == 'true') {
                            location.reload();
                        }
                    }
                });
            });
        });
    </script>
    </body>
</html>