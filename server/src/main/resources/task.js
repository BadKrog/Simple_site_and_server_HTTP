/* Данная функция создаёт кроссбраузерный объект XMLHTTP */
function getXmlHttp() {
    var xmlhttp;
    try {
        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (E) {
            xmlhttp = false;
        }
    }
    if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
        xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
}
  
function summa() {
    // Считываем значение в строке
    var a = document.getElementById("in").value;

    // Создаём объект XMLHTTP
    var xmlhttp = getXmlHttp();

    // Открываем асинхронное соединение
    xmlhttp.open('POST', 'task', true);

    // Отправляем кодировку
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Отправляем POST-запрос
    //xmlhttp.send("a=" +  + "&b=" + encodeURIComponent(b));
    xmlhttp.send(a+"\n");

    // Ждём ответа от сервера
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) { // Ответ пришёл
            if(xmlhttp.status == 200) { // Сервер вернул код 200 (что хорошо)

                var request = xmlhttp.responseText;
                var index = request.indexOf(' ');
                document.getElementById("up").innerHTML = request.slice(0,index); // Выводим ответ сервера
                document.getElementById("low").innerHTML = request.slice(index); // Выводим ответ сервера
            }
        }
    };
}