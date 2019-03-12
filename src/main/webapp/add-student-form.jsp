<%@page isELIgnored="false" %>
<!DOCTYPE html>

<head>
    <title>Add Student</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
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
        <h3>Add Student</h3>

        <form action="students" method="post">
            <input type="hidden" name="command" value="ADD"/>

            <table>
                <tbody>
                    <tr>
                        <td><label>First Name</label></td>
                        <td><input type="text" name="firstName"/></td>
                    </tr>

                    <tr>
                        <td><label>Last Name</label></td>
                        <td><input type="text" name="lastName"/></td>
                    </tr>

                    <tr>
                        <td><label>Email Name</label></td>
                        <td><input type="text" name="emailName"/></td>
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

<body>

</body>

</html>