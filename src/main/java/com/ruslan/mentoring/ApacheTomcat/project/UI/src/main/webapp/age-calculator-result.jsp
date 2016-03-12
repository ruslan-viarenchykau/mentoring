<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1>So, ${user.firstName} ${user.lastName}</h1>
<div>
    <span>It seems that your age is ${userAgeMessage}</span>
</div>
<a href="/age-calculator/age-calculator-form.html">Back</a>