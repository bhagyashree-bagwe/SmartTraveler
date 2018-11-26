var req;
var isIE;
var searchId;
var completeTable;
var autoRow;

function init() {
    searchId = document.getElementById("searchField");
    completeTable = document.getElementById("complete-table");
    autoRow = document.getElementById("auto-row");
}

function doAutoCompletion() {

    var url = "AutoComplete?action=complete&searchId=" + escape(searchId.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    clearTable();

    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages(req.responseXML);
        }
    }
}

function appendProduct(hotelName,hotelId) {

    var row;
    var cell;
    var linkElement;

    if (isIE) {
        completeTable.style.display = 'block';
        row = completeTable.insertRow(completeTable.rows.length);
        cell = row.insertCell(0);
    } else {
        completeTable.style.display = 'table';
        row = document.createElement("tr");
        cell = document.createElement("td");
        row.appendChild(cell);
        completeTable.appendChild(row);
    }

    cell.className = "popupCell";

    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.setAttribute("href", "AutoComplete?action=lookup&searchId=" + hotelId);
    linkElement.appendChild(document.createTextNode(hotelName));
    cell.appendChild(linkElement);
}

function clearTable() {
    if (completeTable.getElementsByTagName("tr").length > 0) {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
}


function parseMessages(responseXML) {

    // no matches returned
    if (responseXML == null) {
        return false;
    } else {

        var hotels = responseXML.getElementsByTagName("hotels")[0];

        if (hotels.childNodes.length > 0) {
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");

            for (loop = 0; loop < hotels.childNodes.length; loop++) {
                var hotel = hotels.childNodes[loop];
                var hotelName = hotel.getElementsByTagName("hotelName")[0];
                var hotelId = hotel.getElementsByTagName("hotelId")[0];
                appendProduct(hotelName.childNodes[0].nodeValue,
                    hotelId.childNodes[0].nodeValue);
            }
        }
    }
}
