<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<!DOCTYPE html>
<html>

<head>
    <title>Student Tracker App </title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>


<body>
    <div id="wrapper">
        <div id="header">
            <h2>FooBar University</h2>
        </div>
    </div>

    <div id="container">
        <div id="content">

            <%--   ADD BUTTON  --%>
            <input type="button" value="Add Student"
                   onclick="window.location.href='add-student-form.jsp'; return false"
            class="add-student-button"/>

            <%--  SEARCH BOX --%>
            <form action="students" method="get">
                <input type="hidden" name="command" value="SEARCH" />
                <input type="text" name="searchValue"/>
                <input type="submit" value="Search" class="add-student-button"/>
            </form>

            <%--   STUDENT TABLE   --%>
            <table>

                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email Name</th>
                    <th>Action</th>
                </tr>


                <c:forEach var="tempStudent" items="${STUDENT_LIST}">

                    <c:url var="tempLink" value="students">
                        <c:param name="command" value="LOAD" />
                        <c:param name="studentId" value="${tempStudent.id}" />
                    </c:url>

                    <c:url var="deleteLink" value="students">
                        <c:param name="command" value="DELETE" />
                        <c:param name="studentId" value="${tempStudent.id}" />
                    </c:url>

                    <tr>
                        <td>${tempStudent.getFirstName()}</td>
                        <td>${tempStudent.getLastName()}</td>
                        <td>${tempStudent.getEmail()}</td>
                        <td>
                            <a href="${tempLink}">Update</a>
                            |
                            <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

</body>
</html>