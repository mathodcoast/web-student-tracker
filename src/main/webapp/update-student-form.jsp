
<%@page isELIgnored="false" %>
<!DOCTYPE html>

<head>
    <title>Update Student</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>FooBar University</h2>
            <hr>
            <form action="logout" method="post">
                <input type="submit" value="Logout" class="add-student-button">
            </form>
        </div>
    </div>

    <div id="container">
        <h3>Update Student</h3>

        <form action="students" method="post">
            <input type="hidden" name="command" value="UPDATE"/>

            <input type="hidden" name="studentId" value="${THE_STUDENT.id}"/>

            <table>
                <tbody>
                <tr>
                    <td><label>Id</label></td>
                    <td>${THE_STUDENT.id}<td/>

                </tr>
                    <tr>
                        <td><label>First Name</label></td>
                        <td><input type="text" name="firstName" value="${THE_STUDENT.firstName}"/></td>
                    </tr>

                    <tr>
                        <td><label>Last Name</label></td>
                        <td><input type="text" name="lastName" value="${THE_STUDENT.lastName}"/></td>
                    </tr>

                    <tr>
                        <td><label>Email Name</label></td>
                        <td><input type="text" name="emailName" value="${THE_STUDENT.email}"/></td>
                    </tr>

                    <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save"/></td>
                    </tr>
                </tbody>
            </table>
        </form>
        <div style="clear: both"></div>

        <p>
            <a href="students">Back to list</a>
        </p>
    </div>

<html>



</body>

</html>