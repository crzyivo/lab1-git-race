<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<%--@elvariable id="deadline" type="java.util.String"--%>
<!DOCTYPE html>

<html lang="en">
<title>Hello world</title>
<link rel="stylesheet" type="text/css"
      href="webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
<body>
<h1>${salute}</h1>
<kbd>${time}<span class="glyphicon glyphicon-console"></span>${message}</kbd>
<div class="text-center"><kbd id="countdown_clock"></kbd></div>

<script>
// Set the date we're counting down to
var countDownDate = new Date("${deadline}").getTime();

// Update the count down every 1 second
var interval = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();
    
    // Find the distance between now an the count down date
    var distance = countDownDate - now;
    
    // Time calculations for hours, minutes and seconds
    var hours = Math.floor(distance / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    
    // Output the result in an element with id="countdown_clock"
    var clock = document.getElementById("countdown_clock");
    clock.innerHTML = "Tiempo hasta final de Git Race: " 
          + hours + ":" + minutes + ":" + seconds;
    
    // If the count down is over, notify it
    if (distance < 0) {
        clearInterval(interval);
        clock.innerHTML = "PLAZO FINALIZADO";
    }
}, 1000);
</script>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</body>

</html>
