function auto(name) {
    // crating new click event for save button
    $(".autoComplete").keyup(function() {
        let id = this.id;
        if ($(this).val() !== "") {
            $.ajax({
                url: "autocomplete",
                type: "POST",
                data: {
                    [name]: $(this).val(),
                },
                success: function (data) {
                    $('#suggesstion-box' + id).show();
                    autoComplete(data, id)
                }
            });
        } else {
            closeAllLists();
        }
    })
}
function autoComplete(data, id) {
    closeAllLists();
    data = data.split("\n")
    s = document.getElementById('suggesstion-box'+id)
    for(i = 0; i < data.length; i++) {
        if (data[i] !== "") {
            a = document.createElement("div")
            a.innerHTML += "<input value='" + data[i] + "' class = 'auto-item' readonly='readonly'>";
            let v = data[i]
            a.addEventListener("click", function (e) {
                document.getElementById(id).value = v;
                closeAllLists()
            })
            s.appendChild(a)
        }
    }
}
function closeAllLists() {
    var x = document.getElementsByClassName("auto-item");
    l = x.length;
    for (var i = 0; i < l; i++) {
        x[0].remove();
    }
}