<%@ page language="java" contentType="text/html;charset=utf-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width = 1050, user-scalable = no" />
        <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>

        <script type="text/javascript">

            function addr(){
                $('#addrPop').load('https://hansollive.com/address/index.html');
            }

            function fnPopup(){
                var data = '{'
                    + '"zipcode":"' + $('#zipcode').val() + '"'
                    + ', "address1":"' + $('#address1').val() + '"'
                    + ', "address1":"' + $('#address2').val() + '"'
                    + ', "address1":"' + $('#userZipcodeSeq').val() + '"'
                    + '}';
                // alert(data);

                window.opener.postMessage({
                    message : data
                } , '*');

                window.close();
            }

        </script>

    </head>
    <body onload="addr()">

        <input type="hidden" id="zipcode" name="" onchange="fnPopup()">
        <input type="hidden" id="address1" name="">
        <input type="hidden" id="address2" name="">
        <input type="hidden" id="userZipcodeSeq" name="">
        <button type="button" >
            주소검색
        </button>

        <div id="addrPop">
        </div>

    </body>
</html>