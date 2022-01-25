<%@ page language="java" contentType="text/html;charset=euc-kr" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
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
                    + ', "address2":"' + $('#address2').val() + '"'
                    + ', "userZipcodeSeq":"' + $('#userZipcodeSeq').val() + '"'
                    + '}';
                //alert(data);

                window.opener.postMessage({
                    juso : data
                } , '*');

                window.close();
            }

            function replaceWithWrapper(obj, property, callback) {
                Object.defineProperty(obj, property, new function() {
                    var _value = obj[property];
                    return {
                        set: function(value) {
                            _value = value;
                            callback(obj, property, value)
                        },
                        get: function() {
                            return _value;
                        }
                    }
                });
            }

            $(document).ready(function() {
                replaceWithWrapper($("#userZipcodeSeq")[0], "value", function(obj, property, value) {
                    console.log("new value:", value)
                    fnPopup();
                });
            });
        </script>

    </head>
    <body onload="addr()">

        <input type="hidden" id="zipcode" name="" value="">
        <input type="hidden" id="address1" name="" value="">
        <input type="hidden" id="address2" name="" value="">
        <input type="hidden" id="userZipcodeSeq" name="" value="">

        <div id="addrPop">
        </div>

    </body>
</html>